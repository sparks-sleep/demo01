package com.demo01.controller;

import com.demo01.controller.viewobject.UserVO;
import com.demo01.error.BusinessException;
import com.demo01.error.EmBusinessError;
import com.demo01.response.CommonReturnType;
import com.demo01.service.UserService;
import com.demo01.service.model.UserModel;
import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Random;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import sun.misc.BASE64Encoder;

/**
 * //DEFAULT_ALLOWED_HEADERS：允许跨域传输所有的header参数，将用于使用token放入header域做session共享的跨域请求
 * @author sparks.org.cn
 */
@Controller("user")
@RequestMapping("/user")
@CrossOrigin(allowCredentials="true",allowedHeaders="*")
public class UserController extends BaseController{
    private final UserService userService;
    private final HttpServletRequest httpServletRequest;

    public UserController(UserService userService, HttpServletRequest httpServletRequest) {
        this.userService = userService;
        this.httpServletRequest = httpServletRequest;
    }

    /**
     *
     * @param telephone
     * @param password
     * @return
     * @throws BusinessException
     * @throws UnsupportedEncodingException
     * @throws NoSuchAlgorithmException
     */
    @RequestMapping(value="/login",method={RequestMethod.POST},consumes={CONTENT_TYPE_FORMED})
    @ResponseBody
    public CommonReturnType login(@RequestParam(name="telephone") String telephone,
                                  @RequestParam(name="password") String password) throws BusinessException, UnsupportedEncodingException, NoSuchAlgorithmException {

        //参数效验
        if(StringUtils.isEmpty(telephone) || StringUtils.isEmpty(password)){
            throw new BusinessException(EmBusinessError.PARAMETER_VALIDATION_ERROR);
        }
        //用户登录服务
        UserModel userModel = userService.validateLogin(telephone,this.EncodeByMd5(password));
        //将登录凭证加入到用户登录成功的session中
        this.httpServletRequest.getSession().setAttribute("IS_LOGIN",true);
        this.httpServletRequest.getSession().setAttribute("LOGIN_USER",userModel);
        //返回通用对象
        return CommonReturnType.create(null);
    }
    /**
     * 用户注册接口
     * @param telephone
     * @param otpCode
     * @param name
     * @param password
     * @param gender
     * @param age
     * @return
     * @throws com.demo01.error.BusinessException
     * @throws NoSuchAlgorithmException
     * @throws UnsupportedEncodingException
     */
    @RequestMapping(value="/register",method={RequestMethod.POST},consumes={CONTENT_TYPE_FORMED})
    @ResponseBody
    public CommonReturnType register(@RequestParam(name="telephone") String telephone,
            @RequestParam(name="otpCode") String otpCode,
            @RequestParam(name="name") String name,
            @RequestParam(name="password") String password,
            @RequestParam(name="gender") Integer gender,
            @RequestParam(name="age") Integer age) throws BusinessException, NoSuchAlgorithmException, UnsupportedEncodingException{
        
        //验证手机号与对应的otpCode相符合
        String inSessionOtpCode = (String) this.httpServletRequest.getSession().getAttribute(telephone);
        boolean isOtoCode = com.alibaba.druid.util.StringUtils.equals(otpCode, inSessionOtpCode);
        if(!isOtoCode){
            throw new BusinessException(EmBusinessError.PARAMETER_VALIDATION_ERROR,"短信验证码错误");
        }else{
            System.out.println("短信验证码通过"  + otpCode);
        }
        //用户的注册流程
        UserModel userModel = new UserModel();
        userModel.setUsername(name);//用户名
        userModel.setAge(age);//年龄
        userModel.setEncryptPassword(this.EncodeByMd5(password));//加密密码
        userModel.setGender(new Byte(String.valueOf(gender.intValue())));//性别
        userModel.setRegisterMode("ByTelephone");//注册模式
        userModel.setThirdPartyId(telephone);//来源
        userModel.setTelephone(telephone);//手机号
        userService.register(userModel);
        
        //返回通用对象
        return CommonReturnType.create(null);
    }
    /**
     * 
     * @param str
     * @return
     * @throws NoSuchAlgorithmException
     * @throws UnsupportedEncodingException 
     */
    public String EncodeByMd5(String str) throws NoSuchAlgorithmException, UnsupportedEncodingException{
        //确定计算方式
        MessageDigest md5 = MessageDigest.getInstance("MD5");
        BASE64Encoder base64en = new BASE64Encoder();
        //加密字符串
        String newstr = base64en.encode(md5.digest(str.getBytes("utf-8")));
        //String newstr = Base64.encodeBase64String(md5.digest(str.getBytes("utf-8")));
        return newstr;
    }
    /**
     * 用户获取otp短信接口
     * @param telephone
     * @return
     */
    @RequestMapping(value="/getotp",method={RequestMethod.POST},consumes={CONTENT_TYPE_FORMED})
    @ResponseBody
    public CommonReturnType getOtp(@RequestParam(name="telephone") String telephone){
        //需要一定的规则生成OTP验证码
        Random random = new Random();
        int randmonInt = random.nextInt(99999);
        randmonInt += 10000;//10000-109999
        String otpCode = String.valueOf(randmonInt);
        //将OTP验证码与用户的手机号关联
        httpServletRequest.getSession().setAttribute(telephone, otpCode);
        
        //将OTP验证码通过短信通道发送给用户
        System.out.println("telephone" + telephone + "& otpCode" + otpCode);
        
        //返回通用对象
        return CommonReturnType.create(null);
    }
    /**
     * 
     * @param uid
     * @return
     * @throws BusinessException 
     */
    @RequestMapping("/get")
    @ResponseBody
    public CommonReturnType getUser(@RequestParam(name="uid") Integer uid) throws BusinessException{
        //调用service服务获取对应id的用户对象，并返回给前端 
        UserModel userModel = userService.getUserById(uid);
        //获取对应信息不存在
        if(userModel == null){
            //userModel.setEncryptPassword("123");--未知错误
            throw new BusinessException(EmBusinessError.USER_NOT_EXIST);
        }
        //将核心领域模型用户对象转化为可供UI使用的viewobject
        UserVO userVO = convertFromModel(userModel);
        //返回通用对象
        return CommonReturnType.create(userVO);
    }
    private UserVO convertFromModel(UserModel userModel){
        if(userModel == null){
            return null;
        }
        UserVO userVO = new UserVO();
        BeanUtils.copyProperties(userModel, userVO);
        return userVO;
    }

}
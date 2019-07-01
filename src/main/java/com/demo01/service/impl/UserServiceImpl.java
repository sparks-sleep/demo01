package com.demo01.service.impl;

import com.demo01.dao.UserDOMapper;
import com.demo01.dao.UserPasswordDOMapper;
import com.demo01.dataobject.UserDO;
import com.demo01.dataobject.UserPasswordDO;
import com.demo01.error.BusinessException;
import com.demo01.error.EmBusinessError;
import com.demo01.service.UserService;
import com.demo01.service.model.UserModel;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;

/**
 *
 * @author sparks.org.cn
 */
@Service
public class UserServiceImpl implements UserService{
    @Resource
    private UserDOMapper userDOMapper;
    @Resource
    private UserPasswordDOMapper userPasswordDOMapper;
    @Override
    public UserModel getUserById(Integer uid){
        //调用UserDOMapper获取到对应的用户dataobject
        UserDO userDO = userDOMapper.selectByPrimaryKey(uid);
        if (userDO == null){
            return null;
        }
        //通过用户ID获取用户加密密码信息
        UserPasswordDO userPasswordDO = userPasswordDOMapper.selectByUserId(userDO.getUid());

        return convertFromDataObject(userDO,userPasswordDO);
    }

    private UserModel convertFromDataObject(UserDO userDO,UserPasswordDO userPasswordDO){
        if(userDO == null){
            return null;
        }
        UserModel userModel = new UserModel();
        BeanUtils.copyProperties(userDO, userModel);
        if(userPasswordDO != null){
            userModel.setEncryptPassword(userPasswordDO.getEncryptPassword());
        }
        
        return userModel;
    }

    @Override
    @Transactional
    public void register(UserModel userModel) throws BusinessException{
        if(userModel == null){
            throw new BusinessException(EmBusinessError.PARAMETER_VALIDATION_ERROR);
        }
        if(StringUtils.isEmpty(userModel.getUsername())
                || userModel.getGender() == null
                || userModel.getAge() == null
                || StringUtils.isEmpty(userModel.getTelephone())){
            throw new BusinessException(EmBusinessError.PARAMETER_VALIDATION_ERROR);
        }
        //实现model-》dataobject方法
        UserDO userDO = convertFromModel(userModel);
        try{
            userDOMapper.insertSelective(userDO);
        }catch (DuplicateKeyException e){
            e.printStackTrace();
            String name = e.getClass().getName();
            System.out.println(name);
            throw new BusinessException(EmBusinessError.PARAMETER_VALIDATION_ERROR,"手机号已重复注册");
        }
        int uid = userDO.getUid();
        if(uid > 0){
            UserPasswordDO userPasswordDO = convertPasswordFromModel(userModel);
            userPasswordDO.setUserId(userDO.getUid());
            userPasswordDOMapper.insertSelective(userPasswordDO);
        }else{
            throw new BusinessException(EmBusinessError.PARAMETER_VALIDATION_ERROR,"用户信息没有录入到数据库！");
        }
    }

    @Override
    public UserModel validateLogin(String telephone, String encryptPassword) throws BusinessException {
        //通过用户的手机获取用户登录信息
        UserDO userDO = userDOMapper.selectByTelephone(telephone);
        if (userDO != null) {
            UserPasswordDO userPasswordDO = userPasswordDOMapper.selectByUserId(userDO.getUid());
            UserModel userModel = convertFromDataObject(userDO, userPasswordDO);
            //密码匹配
            boolean isPassword = StringUtils.equals(encryptPassword, userModel.getEncryptPassword());
            if (!isPassword) {
                throw new BusinessException(EmBusinessError.USER_LOGIN_FAIL);
            }
            return userModel;
        } else {
            throw new BusinessException(EmBusinessError.USER_LOGIN_FAIL);
        }
    }

    private UserDO convertFromModel(UserModel userModel){
        if(userModel == null){
            return null;
        }
        UserDO userDO = new UserDO();
        BeanUtils.copyProperties(userModel, userDO);
        return userDO;
    }

    private UserPasswordDO convertPasswordFromModel(UserModel userModel){
        if(userModel == null){
            return null;
        }
        UserPasswordDO userPasswordDO = new UserPasswordDO();
       // BeanUtils.copyProperties(userModel, userPasswordDO);
        userPasswordDO.setEncryptPassword(userModel.getEncryptPassword());
        return userPasswordDO;
    }
    
}

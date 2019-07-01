/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.demo01.service;

import com.demo01.error.BusinessException;
import com.demo01.service.model.UserModel;

/**
 *
 * @author sparks.org.cn
 */
public interface UserService {
    //通过用户ID获取用户对象的方法
    UserModel getUserById(Integer uid);
    //用户的注册流程
    void register(UserModel userModel) throws BusinessException;
    //用户登录,加密后的密码验证
    UserModel validateLogin(String telephone,String encryptPassword) throws BusinessException;
}

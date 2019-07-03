package com.demo01.service.model;

import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.*;

/**
 * 模型
 *
 * @author sparks.org.cn
 */
public class UserModel {

    private Integer uid;

    @NotBlank(message = "用户名不能为空")
    @Length(max = 30, min = 2,message = "用户名长度限制2~30字符")
    private String username;

    @NotNull(message = "性别不能不填写")
    private Byte gender;

    @NotNull(message = "年龄不能不填写")
    @Min(value = 1, message = "年龄必须大于等于1岁")
    @Max(value = 149, message = "年龄必须小于150岁")
    private Integer age;

    @NotBlank(message = "手机号不能为空")
    @Pattern(regexp = "1[3|4|5|7|8][0-9]\\d{8}",message = "您输入的手机号不正确")
    private String telephone;

    private String registerMode;
    private String thirdPartyId;

    @NotBlank(message = "密码不能为空")
    private String encryptPassword;

    public Integer getUid() {
        return uid;
    }

    public void setUid(Integer uid) {
        this.uid = uid;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public Byte getGender() {
        return gender;
    }

    public void setGender(Byte gender) {
        this.gender = gender;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public String getTelephone() {
        return telephone;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }

    public String getRegisterMode() {
        return registerMode;
    }

    public void setRegisterMode(String registerMode) {
        this.registerMode = registerMode;
    }

    public String getThirdPartyId() {
        return thirdPartyId;
    }

    public void setThirdPartyId(String thirdPartyId) {
        this.thirdPartyId = thirdPartyId;
    }

    public String getEncryptPassword() {
        return encryptPassword;
    }

    public void setEncryptPassword(String encryptPassword) {
        this.encryptPassword = encryptPassword;
    }
}

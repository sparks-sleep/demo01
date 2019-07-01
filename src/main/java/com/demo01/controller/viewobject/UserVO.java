package com.demo01.controller.viewobject;

/**
 *  返回给前端展示的字段（视图页面）
 * @author sparks.org.cn
 */
public class UserVO {
    private Integer uid;
    private String username;
    private Byte gender;
    private Integer age;
    private String telephone;

    /**
     * @return the age
     */
    public Integer getAge() {
        return age;
    }

    /**
     * @return the gender
     */
    public Byte getGender() {
        return gender;
    }

    /**
     * @return the telephone
     */
    public String getTelephone() {
        return telephone;
    }

    /**
     * @return the uid
     */
    public Integer getUid() {
        return uid;
    }

    /**
     * @return the username
     */
    public String getUsername() {
        return username;
    }

    /**
     * @param age the age to set
     */
    public void setAge(Integer age) {
        this.age = age;
    }

    /**
     * @param gender the gender to set
     */
    public void setGender(Byte gender) {
        this.gender = gender;
    }

    /**
     * @param telephone the telephone to set
     */
    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }

    /**
     * @param uid the uid to set
     */
    public void setUid(Integer uid) {
        this.uid = uid;
    }

    /**
     * @param username the username to set
     */
    public void setUsername(String username) {
        this.username = username;
    }

}

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
    private String telphone;

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
     * @return the telphone
     */
    public String getTelphone() {
        return telphone;
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
     * @param telphone the telphone to set
     */
    public void setTelphone(String telphone) {
        this.telphone = telphone;
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

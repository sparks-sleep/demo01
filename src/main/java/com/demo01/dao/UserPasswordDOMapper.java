package com.demo01.dao;

import com.demo01.dataobject.UserPasswordDO;

public interface UserPasswordDOMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table user_password
     *
     * @mbg.generated Thu Jun 27 17:39:25 CST 2019
     */
    int deleteByPrimaryKey(Integer pid);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table user_password
     *
     * @mbg.generated Thu Jun 27 17:39:25 CST 2019
     */
    int insert(UserPasswordDO record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table user_password
     *
     * @mbg.generated Thu Jun 27 17:39:25 CST 2019
     */
    int insertSelective(UserPasswordDO record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table user_password
     *
     * @mbg.generated Thu Jun 27 17:39:25 CST 2019
     */
    //UserPasswordDO selectByPrimaryKey(Integer pid);
    UserPasswordDO selectByUserId(Integer userId);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table user_password
     *
     * @mbg.generated Thu Jun 27 17:39:25 CST 2019
     */
    int updateByPrimaryKeySelective(UserPasswordDO record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table user_password
     *
     * @mbg.generated Thu Jun 27 17:39:25 CST 2019
     */
    int updateByPrimaryKey(UserPasswordDO record);
}
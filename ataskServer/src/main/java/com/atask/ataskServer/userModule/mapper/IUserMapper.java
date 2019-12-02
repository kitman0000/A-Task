package com.atask.ataskServer.userModule.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

/**
 * Created by zhong on 2019-9-27.
 */
@Mapper
public interface IUserMapper {
    void addUser(@Param("phoneNumber") String phoneNumber, @Param("pwd") String pwd);

    int checkUserPwdWithPhone(@Param("phoneNumber") String phoneNumber, @Param("pwd") String pwd);

    int checkUserPwdWithID(@Param("userID") int userID, @Param("pwd") String pwd);

    int checkPhoneNumberExist(String phoneNumber);

    int getUserID(String phoneNumber);

    void updateUserPwd(@Param("userID") int userID, @Param("newPwd") String newPwd);

    String getUserPhoneNumber(int userID);
}

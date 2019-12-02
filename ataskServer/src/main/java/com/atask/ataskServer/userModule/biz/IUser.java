package com.atask.ataskServer.userModule.biz;

/**
 * Created by zhong on 2019-9-27.
 */
public interface IUser {
    String login(String phoneNumber,String pwd);

    String sendRegSMS(String phoneNumber);

    String register(String phoneNumber,String pwd,String captcha);

    String sendForgetSMS(String phoneNumber);

    String resetPwd(String phoneNumber,String captcha,String newPwd);

    String changePwd(String token,String newPwd,String oldPwd);

    String checkToken(String token);
}

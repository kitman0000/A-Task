package com.atask.ataskServer.userModule.controller;

import com.atask.ataskServer.userModule.biz.IUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by zhong on 2019-9-27.
 */

@RestController
public class UserCl {
    @Autowired
    private IUser user;

    @RequestMapping(value = "/user/login",method = RequestMethod.POST)
    public String login(String phoneNumber,String pwd)
    {
        return user.login(phoneNumber,pwd);
    }

    @RequestMapping(value = "/user/sendRegSMS",method = RequestMethod.POST)
    public String sendRegSMS(String phoneNumber)
    {
        return user.sendRegSMS(phoneNumber);
    }

    @RequestMapping(value = "/user/register",method = RequestMethod.POST)
    public String register(String phoneNumber,String pwd,String captcha)
    {
        return user.register(phoneNumber,pwd,captcha);
    }

    @RequestMapping(value = "/user/sendForgetSMS",method = RequestMethod.POST)
    public String sendForgetSMS(String phoneNumber)
    {
        return user.sendForgetSMS(phoneNumber);
    }

    @RequestMapping(value = "/user/resetPwd",method = RequestMethod.POST)
    public String resetPwd(String phoneNumber,String captcha,String resetPwd)
    {
        return user.resetPwd(phoneNumber,captcha,resetPwd);
    }

    @RequestMapping(value = "/user/changePwd",method = RequestMethod.POST)
    public String changePwd(String token,String newPwd,String oldPwd){
        return user.changePwd(token,newPwd,oldPwd);
    }

    @RequestMapping(value = "/user/checkToken",method = RequestMethod.POST)
    public String checkToken(String token){return user.checkToken(token);}
}

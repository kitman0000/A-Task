package com.atask.ataskServer.userModule.impl;

import com.atask.ataskServer.userModule.biz.IUser;
import com.atask.ataskServer.userModule.entry.UsrInfoPayLoad;
import com.atask.ataskServer.userModule.mapper.IUserMapper;
import com.atask.ataskServer.userModule.type.UserResult;
import com.atask.ataskServer.utills.JwtUtil;
import com.atask.ataskServer.utills.SMSUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.util.concurrent.TimeUnit;

/**
 * Created by zhong on 2019-9-27.
 */
@Service
public class UserImpl implements IUser {
    @Autowired
    private IUserMapper userMapper;

    @Autowired
    StringRedisTemplate stringRedisTemplate;

    @Override
    public String login(String phoneNumber, String pwd) {
        int userID;

        try {
            userID = userMapper.checkUserPwdWithPhone(phoneNumber, pwd);
        }catch (Exception ex){
            return UserResult.LOGIN_FAILED.toString();
        }

        return createToken(phoneNumber,userID);
    }

    @Override
    public String sendRegSMS(String phoneNumber) {
        // 判断手机号是否已经存在
        if(userMapper.checkPhoneNumberExist(phoneNumber) != 0)
            return UserResult.SMS_SEND_FAILED.toString();

        // 获取ip
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = attributes.getRequest();
        String ipAddress = request.getHeader("X-Forwarded-For");

        // 查询是否发送过
        boolean hasSend = (stringRedisTemplate.opsForValue().get("ATask:uSendMsgIp:"+ipAddress) != null);
        if(hasSend)
            return UserResult.SMS_PLEASE_WAIT.toString();

        // 记录ip
        stringRedisTemplate.opsForValue().set("ATask:uSendMsgIp:"+ipAddress,"1",1,TimeUnit.MINUTES);

        // 发送
        String captcha = SMSUtil.sendRegCAPTCHA(phoneNumber);
        stringRedisTemplate.opsForValue().set("ATask:uRegCaptcha:" + phoneNumber , captcha,30,TimeUnit.MINUTES);


        return UserResult.SMS_SEND_SUCCESS.toString();
    }

    @Override
    public String register(String phoneNumber, String pwd, String captcha) {
        // 判断验证码是否存在
        String serverCaptcha = stringRedisTemplate.opsForValue().get("ATask:uRegCaptcha:"+phoneNumber);
        if(!serverCaptcha.equals(captcha))
            return UserResult.USER_REG_FAILED.toString();
        stringRedisTemplate.delete("ATask:uRegCaptcha:"+phoneNumber);

        // 判断手机号是否已经存在
        if(userMapper.checkPhoneNumberExist(phoneNumber) != 0)
            return UserResult.USER_HAS_EXIST.toString();

        // 添加用户
        userMapper.addUser(phoneNumber,pwd);

        // 获取userID
        int userID = userMapper.getUserID(phoneNumber);

        // 设置用户默认主题
        String key = "ATask:theme:" + userID;
        stringRedisTemplate.opsForValue().set(key,"theme_skyblue.css");

        // 生成token
        return createToken(phoneNumber,userID);
    }

    @Override
    public String sendForgetSMS(String phoneNumber) {
        // 判断手机号是否已经存在
        if(userMapper.checkPhoneNumberExist(phoneNumber) == 0)
            return UserResult.SMS_SEND_FAILED.toString();

        // 获取ip
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = attributes.getRequest();
        String ipAddress = request.getHeader("X-Forwarded-For");

        // 查询是否发送过
        boolean hasSend = (stringRedisTemplate.opsForValue().get("ATask:uSendMsgIp:"+ipAddress) != null);
        if(hasSend)
            return UserResult.SMS_PLEASE_WAIT.toString();

        // 记录ip
        stringRedisTemplate.opsForValue().set("ATask:uSendMsgIp:"+ipAddress,"1",1,TimeUnit.MINUTES);

        // 发送
        String captcha = SMSUtil.sendRstPwdCAPTCHA(phoneNumber);
        stringRedisTemplate.opsForValue().set("ATask:uRstPwdCaptcha:" + phoneNumber , captcha,30,TimeUnit.MINUTES);

        return UserResult.SMS_SEND_SUCCESS.toString();
    }

    @Override
    public String resetPwd(String phoneNumber, String captcha, String newPwd) {
        // 判断验证码是否存在
        String serverCaptcha = stringRedisTemplate.opsForValue().get("ATask:uRstPwdCaptcha:"+phoneNumber);
        if(!serverCaptcha.equals(captcha))
            return UserResult.PWD_RESET_FAILED.toString();

        stringRedisTemplate.delete("ATask:uRegCaptcha:"+phoneNumber);

        int userID = userMapper.getUserID(phoneNumber);

        userMapper.updateUserPwd(userID,newPwd);

        return UserResult.PWD_RESET_SUCCESS.toString();
    }

    private String createToken(String phoneNumber,int userID)
    {
        // 生成token
        JwtUtil jwtUtil = new JwtUtil();
        jwtUtil.setHeader();
        jwtUtil.setPayload(phoneNumber,userID);
        jwtUtil.createSignature();
        jwtUtil.createToken();
        String token = jwtUtil.getToken();

        // 将token放入redis
        stringRedisTemplate.opsForValue().set("ATask:uToken:" + userID,token);

        return token;
    }

    @Override
    public String changePwd(String token, String newPwd, String oldPwd) {
        UsrInfoPayLoad usrInfoPayLoad = JwtUtil.getUsrInfoPayLoad(token);
        int userID = usrInfoPayLoad.getAccount();

        int intResult = userMapper.checkUserPwdWithID(userID, oldPwd);
        if(intResult == 0)
            return UserResult.PWD_CHANGE_FAILED.toString();

        stringRedisTemplate.delete("ATask:uToken" + userID);
        userMapper.updateUserPwd(userID,newPwd);
        String phoneNumber = userMapper.getUserPhoneNumber(userID);

        return createToken(phoneNumber,userID);
    }

    @Override
    public String checkToken(String token) {
        if(JwtUtil.checkToken(token)) {
            return UserResult.TOKEN_CHECK_SUCCESS.toString();
        }else {
            return UserResult.TOKEN_CHECK_FAILED.toString();
        }
    }
}

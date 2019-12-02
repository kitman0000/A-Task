package com.atask.ataskServer;

import com.atask.ataskServer.userModule.impl.UserImpl;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.stereotype.Component;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Created by zhong on 2019-9-28.
 */
@RunWith(SpringRunner.class)
@SpringBootTest
@Component
public class UserTest {
    @Autowired
    private UserImpl user;

    private Logger logger = Logger.getLogger("TestLogger");

    private final String TOKEN = "eyd0eXAnOidKV1QnLCdhbGcnOidIUzI1NicnaWF0JzonMTU3MDYyMTIwNTYyMid9.eyJzdWIiOiIxODcwMTcxMDk3MCIsImFjY291bnQiOiIzIn0=.ds7MVT72/jC/Q+lnHHTjd4Lqc0m6bZ9foGez3bYoUVI=";
    
    public void loginTest(){
        String result = user.login("18701710970","123456");
        System.out.println(result);
    }

    @Test
    public void regTest(){
        String result = user.sendRegSMS("18221646680");
        // String result = user.register("18701710970","123456","6745");
        logger.log(Level.INFO,result);
    }

    public void resetPwdTest(){
        String result = user.sendForgetSMS("18930594090");
        // String result = user.resetPwd("18930594090","8275","654321");
        logger.log(Level.INFO,result);
    }

    @Test
    public void changePwdTest(){
        String result = user.changePwd(TOKEN,"abcdefg","123456");
        logger.log(Level.INFO,result);
    }
}

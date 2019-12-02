package com.atask.ataskServer;

import com.atask.ataskServer.userModule.controller.ThemeCl;
import com.atask.ataskServer.userModule.impl.ThemeImpl;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.stereotype.Component;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Created by zhong on 2019-10-15.
 */
@RunWith(SpringRunner.class)
@SpringBootTest
@Component
public class ThemeTest {
    @Autowired
    ThemeImpl theme;

    @Autowired
    ThemeCl themeCl;

    final String TOKEN = "eyd0eXAnOidKV1QnLCdhbGcnOidIUzI1NicnaWF0JzonMTU3MDI2MzQ4MTk3NCd9.eyJzdWIiOiIxODkzMDU5NDA5MCIsImFjY291bnQiOiIxIn0=.e4UibwEL5aNm44XJ0eyiYnfJhS+4NXPz4yJhRhjHgjU=";

    Logger logger = Logger.getLogger("TestLogger");

    @Test
    public void setTheme(){
        String result = themeCl.setTheme(TOKEN,"theme1");
        logger.log(Level.INFO,result);
    }

    @Test
    public void getTheme(){
        String result = theme.getTheme(TOKEN);
        logger.log(Level.INFO,result);
    }
}

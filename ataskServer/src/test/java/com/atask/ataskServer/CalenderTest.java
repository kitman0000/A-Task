package com.atask.ataskServer;

import com.atask.ataskServer.taskModule.impl.CalenderImpl;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.stereotype.Component;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Created by zhong on 2019-10-8.
 */
@RunWith(SpringRunner.class)
@SpringBootTest
@Component
public class CalenderTest {
    final String TOKEN = "eyd0eXAnOidKV1QnLCdhbGcnOidIUzI1NicnaWF0JzonMTU3MDI2MzQ4MTk3NCd9.eyJzdWIiOiIxODkzMDU5NDA5MCIsImFjY291bnQiOiIxIn0=.e4UibwEL5aNm44XJ0eyiYnfJhS+4NXPz4yJhRhjHgjU=";

    Logger logger = Logger.getLogger("TestLogger");

    @Autowired
    CalenderImpl calender;

    @Test
    public void getUserDateTaskTest(){
        String result = calender.getUserDateTask(TOKEN,"2019-10-09");
        logger.log(Level.INFO,result);
    }
}

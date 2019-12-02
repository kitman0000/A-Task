package com.atask.ataskServer;

import com.atask.ataskServer.taskModule.impl.TaskManageImpl;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.stereotype.Component;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Created by zhong on 2019-10-4.
 */
@RunWith(SpringRunner.class)
@SpringBootTest
@Component
public class TaskManageTest {
    final String TOKEN = "eyd0eXAnOidKV1QnLCdhbGcnOidIUzI1NicnaWF0JzonMTU3MDI2MzQ4MTk3NCd9.eyJzdWIiOiIxODkzMDU5NDA5MCIsImFjY291bnQiOiIxIn0=.e4UibwEL5aNm44XJ0eyiYnfJhS+4NXPz4yJhRhjHgjU=";

    Logger logger = Logger.getLogger("TestLogger");

    @Autowired
    TaskManageImpl taskManage;

    @Test
    public void addTaskTest(){
        String result = taskManage.addTask("做ppt","在办公室做ppt","finish","2019-10-09",TOKEN);
        logger.log(Level.INFO,result);
    }

    @Test
    public void editTask(){
        String result = taskManage.editTask(2,"做ppt","在办公室做ppt","todo","2019-10-10",TOKEN);
        logger.log(Level.INFO,result);
    }

    @Test
    public void getTask(){
        String result = taskManage.getUserTask(TOKEN,"finish");
        logger.log(Level.INFO,result);
    }
}

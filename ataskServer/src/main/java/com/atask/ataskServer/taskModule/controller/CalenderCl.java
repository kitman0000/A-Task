package com.atask.ataskServer.taskModule.controller;

import com.atask.ataskServer.taskModule.impl.CalenderImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by zhong on 2019-10-8.
 */
@RestController
public class CalenderCl {
    @Autowired
    CalenderImpl calender;

    @RequestMapping(value = "/task/getUserDateTask",method = RequestMethod.POST)
    public String getUserDateTask(String token,String date){
        return calender.getUserDateTask(token,date);
    }

    @RequestMapping(value = "/task/getUserTaskDetail",method = RequestMethod.POST)
    public String getUserTaskDetail(int taskID,String token){
        return calender.getUserTaskDetail(taskID,token);
    }

}

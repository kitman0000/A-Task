package com.atask.ataskServer.taskModule.controller;

import com.atask.ataskServer.taskModule.biz.ITaskManage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by zhong on 2019-10-3.
 */

@RestController
public class TaskManageCl {
    @Autowired
    ITaskManage taskManage;

    @RequestMapping(value = "/task/addTask",method = RequestMethod.POST)
    public String addTask(String title,String content,String status,String date,String token)
    {
         return taskManage.addTask(title,content,status,date,token);
    }

    @RequestMapping(value = "/task/editTask",method = RequestMethod.POST)
    public String editTask(int taskID,String title,String content,String status,String date,String token)
    {
        return taskManage.editTask(taskID,title,content,status,date,token);
    }

    @RequestMapping(value = "/task/deleteTask",method = RequestMethod.POST)
    public String deleteTask(int taskID,String token)
    {
        return taskManage.deleteTask(taskID,token);
    }

    @RequestMapping(value = "/task/getUserTask",method = RequestMethod.POST)
    public String getUserTask(String token,String status)
    {
        return taskManage.getUserTask(token,status);
    }

    @RequestMapping(value = "/task/setTaskStatus",method = RequestMethod.POST)
    public String setTaskStatus(String token,int taskID,String status)
    {
        return taskManage.setTaskStatus(token,taskID,status);
    }
}

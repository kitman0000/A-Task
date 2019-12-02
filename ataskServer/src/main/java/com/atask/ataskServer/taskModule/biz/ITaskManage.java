package com.atask.ataskServer.taskModule.biz;

/**
 * Created by zhong on 2019-10-3.
 */
public interface ITaskManage {
    String addTask(String title,String content,String status,String date,String token);

    String editTask(int taskID,String title,String content,String status,String date,String token);

    String deleteTask(int taskID,String token);

    String getUserTask(String token,String status);

    String setTaskStatus(String token,int taskID,String status);
}

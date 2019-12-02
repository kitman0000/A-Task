package com.atask.ataskServer.taskModule.biz;

/**
 * Created by zhong on 2019-10-8.
 */
public interface ICalender {
    String getUserDateTask(String token,String date);

    String getUserTaskDetail(int taskID,String token);
}

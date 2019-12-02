package com.atask.ataskServer.taskModule.impl;

import com.atask.ataskServer.taskModule.biz.ICalender;
import com.atask.ataskServer.taskModule.bo.UserTask;
import com.atask.ataskServer.taskModule.component.TaskOper;
import com.atask.ataskServer.userModule.entry.UsrInfoPayLoad;
import com.atask.ataskServer.utills.JsonUtil;
import com.atask.ataskServer.utills.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import java.util.Set;

/**
 * Created by zhong on 2019-10-8.
 */

@Service
public class CalenderImpl implements ICalender{
    @Autowired
    StringRedisTemplate redisTemplate;

    @Override
    public String getUserDateTask(String token, String date) {
        UsrInfoPayLoad usrInfoPayLoad = JwtUtil.getUsrInfoPayLoad(token);
        int userID = usrInfoPayLoad.getAccount();

        Set<String> userKeys = redisTemplate.opsForSet().members("ATask:index:taskDate:" + userID + ":" + date);

        return TaskOper.taskKeysToJson(userKeys,redisTemplate);
    }

    @Override
    public String getUserTaskDetail(int taskID, String token) {
        UsrInfoPayLoad usrInfoPayLoad = JwtUtil.getUsrInfoPayLoad(token);
        int userID = usrInfoPayLoad.getAccount();
        String key = "ATask:task:" + userID + ":" + taskID;

        String taskTitle = redisTemplate.opsForHash().get(key,"title").toString();
        String taskContent = redisTemplate.opsForHash().get(key,"content").toString();
        String taskStatus = redisTemplate.opsForHash().get(key,"status").toString();
        String taskDate = redisTemplate.opsForHash().get(key,"date").toString();

        UserTask task = new UserTask();
        task.setTaskID(Integer.parseInt(key.split(":")[3]));
        task.setTitle(taskTitle);
        task.setContent(taskContent);
        task.setStatus(taskStatus);
        task.setDate(taskDate);

        return JsonUtil.objectToJson(task);
    }
}

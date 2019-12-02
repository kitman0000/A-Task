package com.atask.ataskServer.taskModule.impl;

import com.atask.ataskServer.taskModule.biz.ITaskManage;
import com.atask.ataskServer.taskModule.component.TaskOper;
import com.atask.ataskServer.taskModule.type.TaskManageResult;
import com.atask.ataskServer.userModule.entry.UsrInfoPayLoad;
import com.atask.ataskServer.userModule.impl.UserImpl;
import com.atask.ataskServer.utills.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.util.Set;

/**
 * Created by zhong on 2019-10-3.
 */
@Service
@Component
public class TaskManageImpl implements ITaskManage {

    UserImpl user;

    @Autowired
    StringRedisTemplate redisTemplate;

    @Override
    public String addTask(String title, String content, String status, String date, String token){
        UsrInfoPayLoad usrInfoPayLoad = JwtUtil.getUsrInfoPayLoad(token);
        int userID = usrInfoPayLoad.getAccount();

        redisTemplate.opsForValue().increment("ATask:taskID");
        String taskID = redisTemplate.opsForValue().get("ATask:taskID");
        String key = "ATask:task:" + userID + ":" + taskID;

        // 储存任务
        redisTemplate.opsForHash().put(key,"title",title);
        redisTemplate.opsForHash().put(key,"content",content);
        redisTemplate.opsForHash().put(key,"status",status);
        redisTemplate.opsForHash().put(key,"date",date);

        // 储存索引
        redisTemplate.opsForSet().add("ATask:index:taskStatus:" + userID +":" + status,key);                    // 状态索引
        redisTemplate.opsForSet().add("ATask:index:taskDate:" + userID + ":" + date,key);                        // 日期索引

        return TaskManageResult.TASK_ADD_SUCCESS.toString();
    }

    @Override
    public String editTask(int taskID, String title, String content, String status, String date, String token) {
        UsrInfoPayLoad usrInfoPayLoad = JwtUtil.getUsrInfoPayLoad(token);
        int userID = usrInfoPayLoad.getAccount();
        String key = "ATask:task:" + userID + ":" + taskID;

        // 删除索引
        String oldStatus = redisTemplate.opsForHash().get(key,"status").toString();             // 状态索引
        String oldDate = redisTemplate.opsForHash().get(key,"date").toString();                 // 日期索引
        redisTemplate.opsForSet().remove("ATask:index:taskStatus:" + userID + ":" + oldStatus,key);
        redisTemplate.opsForSet().remove("ATask:index:taskDate:" + userID + ":" + oldDate,key);

        // 储存任务
        redisTemplate.opsForHash().put(key,"title",title);
        redisTemplate.opsForHash().put(key,"content",content);
        redisTemplate.opsForHash().put(key,"status",status);
        redisTemplate.opsForHash().put(key,"date",date);

        // 储存索引
        redisTemplate.opsForSet().add("ATask:index:taskStatus:" + userID +":" + status,key);                    // 状态索引
        redisTemplate.opsForSet().add("ATask:index:taskDate:" + userID + ":" + date,key);                        // 日期索引

        return TaskManageResult.TASK_EDIT_SUCCESS.toString();
    }

    @Override
    public String deleteTask(int taskID, String token) {
        UsrInfoPayLoad usrInfoPayLoad = JwtUtil.getUsrInfoPayLoad(token);
        int userID = usrInfoPayLoad.getAccount();
        String key = "ATask:task:" + userID + ":" + taskID;

        // 删除索引
        String oldStatus = redisTemplate.opsForHash().get(key,"status").toString();             // 状态索引
        String oldDate = redisTemplate.opsForHash().get(key,"date").toString();                 // 日期索引
        redisTemplate.opsForSet().remove("ATask:index:taskStatus:" + userID + ":" + oldStatus,key);
        redisTemplate.opsForSet().remove("ATask:index:taskDate:" + userID + ":" + oldDate,key);

        // 删除任务键
        redisTemplate.delete(key);

        return TaskManageResult.TASK_DELETE_SUCCESS.toString();
    }

    @Override
    public String getUserTask(String token, String status) {
        UsrInfoPayLoad usrInfoPayLoad = JwtUtil.getUsrInfoPayLoad(token);
        int userID = usrInfoPayLoad.getAccount();

        Set<String> userKeys;
        if(status.equals("null"))
            userKeys = redisTemplate.keys("ATask:task:" + userID + ":*");
        else
            userKeys = redisTemplate.opsForSet().members("ATask:index:taskStatus:" + userID + ":" + status);

        if(userKeys.isEmpty())
            return "[]";

        return TaskOper.taskKeysToJson(userKeys,redisTemplate);
    }

    @Override
    public String setTaskStatus(String token,int taskID, String status) {
        UsrInfoPayLoad usrInfoPayLoad = JwtUtil.getUsrInfoPayLoad(token);
        int userID = usrInfoPayLoad.getAccount();
        String key = "ATask:task:" + userID + ":" + taskID;

        // 删除索引
        String oldStatus = redisTemplate.opsForHash().get(key,"status").toString();             // 状态索引
        redisTemplate.opsForSet().remove("ATask:index:taskStatus:" + userID + ":" + oldStatus,key);

        // 储存索引
        redisTemplate.opsForSet().add("ATask:index:taskStatus:" + userID +":" + status,key);    // 状态索引

        // 储存任务
        redisTemplate.opsForHash().put(key,"status",status);

        return TaskManageResult.STATUS_SET_SUCCESS.toString();
    }
}

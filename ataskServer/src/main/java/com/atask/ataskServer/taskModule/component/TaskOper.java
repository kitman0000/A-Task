package com.atask.ataskServer.taskModule.component;

import com.atask.ataskServer.taskModule.bo.UserTask;
import com.atask.ataskServer.utills.JsonUtil;
import org.springframework.data.redis.core.StringRedisTemplate;

import java.util.*;

/**
 * Created by zhong on 2019-10-8.
 */
public class TaskOper {
    public static String taskKeysToJson(Set<String> keys, StringRedisTemplate redisTemplate){

        Stack<UserTask> userTasks = new Stack<>();

        for(String key :keys){
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

            userTasks.push(task);
        }

        return JsonUtil.objectToJson(userTasks);
    }
}

package com.atask.ataskServer.userModule.impl;

import com.atask.ataskServer.userModule.biz.ITheme;
import com.atask.ataskServer.userModule.entry.UsrInfoPayLoad;
import com.atask.ataskServer.userModule.type.ThemeResult;
import com.atask.ataskServer.utills.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;


/**
 * Created by zhong on 2019-10-15.
 */

@Component
@Service
public class ThemeImpl implements ITheme {
    @Autowired
    StringRedisTemplate redisTemplate;

    @Override
    public String setTheme(String token, String themeName) {
        UsrInfoPayLoad usrInfoPayLoad = JwtUtil.getUsrInfoPayLoad(token);
        int userID = usrInfoPayLoad.getAccount();

        String key = "ATask:theme:" + userID;
        redisTemplate.opsForValue().set(key,themeName);

        return ThemeResult.THEME_SET_SUCCESS.toString();
    }

    @Override
    public String getTheme(String token) {
        UsrInfoPayLoad usrInfoPayLoad = JwtUtil.getUsrInfoPayLoad(token);
        int userID = usrInfoPayLoad.getAccount();

        String key = "ATask:theme:" + userID;

        return redisTemplate.opsForValue().get(key);
    }
}

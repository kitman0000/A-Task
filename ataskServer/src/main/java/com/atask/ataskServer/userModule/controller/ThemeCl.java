package com.atask.ataskServer.userModule.controller;

import com.atask.ataskServer.userModule.biz.ITheme;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by zhong on 2019-10-15.
 */
@RestController
public class ThemeCl {
    @Autowired
    ITheme theme;

    @RequestMapping(value = "/user/setTheme")
    public String setTheme(String token,String themeName){
        return theme.setTheme(token,themeName);
    }

    @RequestMapping(value = "/user/getTheme")
    public String getTheme(String token){
        return theme.getTheme(token);
    }
}

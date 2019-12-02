package com.atask.ataskServer.userModule.biz;

/**
 * Created by zhong on 2019-10-15.
 */
public interface ITheme {
    String setTheme(String token,String themeName);

    String getTheme(String token);
}

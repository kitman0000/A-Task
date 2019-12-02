package com.atask.ataskServer.component;

import com.atask.ataskServer.userModule.impl.UserImpl;
import com.atask.ataskServer.userModule.type.UserResult;
import com.atask.ataskServer.utills.JwtUtil;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Created by zhong on 2019-10-15.
 */
@Aspect
@Component
public class AuthAspect {
    @Autowired
    UserImpl user;

    Logger logger = Logger.getLogger("Auth Logger");

    @Pointcut("execution(public * com.atask.ataskServer.*.impl.*.*(..))")
    public void userOperate(){
    }

    @Around("userOperate()")
    public String checkToken(ProceedingJoinPoint joinPoint)throws Throwable {
        Object[] args = joinPoint.getArgs();
        String[] argsNames = ((MethodSignature)joinPoint.getSignature()).getParameterNames();
        String token = null;
        for (int i = 0;i<argsNames.length;i++){
            if(argsNames[i].equals("token")){
                token = args[i].toString();
            }
        }

        if(token == null){                                          // 没有token参数，正常执行函数
            return joinPoint.proceed().toString();
        }

        if(JwtUtil.checkToken(token)){                              // token验证成功，正常执行函数
            logger.log(Level.INFO,"用户token验证成功");
            return joinPoint.proceed().toString();
        }else {                                                     // token验证失败，不运行函数，直接返回
            logger.log(Level.INFO,"用户token验证失败");
            return UserResult.TOKEN_CHECK_FAILED.toString();
             //throw new Exception(UserResult.TOKEN_CHECK_FAILED.toString());
        }
    }
}

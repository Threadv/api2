package com.ifenghui.storybookapi.active1902.aop;


import com.ifenghui.storybookapi.config.MyEnv;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

/**
 * Created by narella on 2017/6/30.
 */
@Aspect
@Component
public class ApiActivity1902ConfigAop {

    @Autowired
    Environment env;
    @Autowired
    MessageSource messageSource;

    @Pointcut("execution(* com.ifenghui.storybookapi.active1902.*..*(..))")
    public void beginCut() {
    }


    @Before("beginCut()")
    public void Iterecport(JoinPoint joinPoint) {

        if (MyEnv.env == null) {
            MyEnv.env = this.env;
            MyEnv.locale = LocaleContextHolder.getLocale();
            MyEnv.messageSource = this.messageSource;
        }
        if(env != null){
            MyEnv.env = this.env;
        }
        MyEnv.hasAopInit=1;
    }

}

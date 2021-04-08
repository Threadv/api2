package com.ifenghui.storybookapi.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.MessageSource;
import org.springframework.core.env.Environment;

import java.util.Locale;

/**
 * Created by narella on 2017/6/30.
 */

public class MyEnv {
//    @Value("spring.datasource.username")
    static public Environment env;

    static public MessageSource messageSource;

    static public Locale locale;

    static public boolean TEST_USER_GROUP;

    static public String ver;

    static public StoryConfig.Platfrom platfrom= StoryConfig.Platfrom.OTHER;
//    static public boolean isIos=false;

//    static public boolean isAndroid=false;

    /**
     * 返回国际化文字
     * @param key
     * @return
     */
    static public String  getMessage(String key){
        return messageSource.getMessage(key,null,locale);
    }


    //标记当前对象里的参数是否已经再aop中初始化过
    static public int hasAopInit=0;
}

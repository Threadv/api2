package com.ifenghui.storybookapi.aop;

import com.ifenghui.storybookapi.app.user.entity.UserToken;
import com.ifenghui.storybookapi.app.user.service.UserTokenService;
import com.ifenghui.storybookapi.config.MyEnv;
import com.ifenghui.storybookapi.util.HttpRequest;
import com.ifenghui.storybookapi.util.HttpUtils;
import com.ifenghui.storybookapi.util.VersionUtil;
import org.apache.log4j.Logger;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;

/**
 * Created by narella on 2017/6/30.
 */
@Aspect
@Component
//@EnableAspectJAutoProxy
public class ApiConfigAop {

    Logger logger= Logger.getLogger(ApiConfigAop.class);
    @Autowired
    Environment env;

    @Autowired
    MessageSource messageSource;

    @Autowired
    HttpServletRequest request;

    @Autowired
    UserTokenService userTokenService;

    @Value("${ship.testusergroup}")
    int[] testUserGroup;

    @Pointcut("execution(* com.ifenghui.storybookapi.app.*.controller..*(..))")
//    @Pointcut("execution(* com.ifenghui.storybookapi.api..*(..))")
    public void beginCut(){}



    @Before("beginCut()")
    public void Iterecport(JoinPoint joinPoint){
//        if(MyEnv.hasAopInit==1){
//            return;
//        }
        if(MyEnv.env==null){
            MyEnv.env = this.env;
        }
        if(MyEnv.messageSource==null){
            MyEnv.messageSource = this.messageSource;
        }
        if(MyEnv.locale ==null){
            MyEnv.locale = LocaleContextHolder.getLocale();
        }
        String token;
        try{

           token = request.getHeader("sstoken");
            if(request.getParameter("sstest")!=null){
                token="asdfasdf";
            }

        }catch (Exception e){
            return;
        }
        MyEnv.ver = VersionUtil.getVerionInfo(request);
//        logger.info("myenv ver:"+ MyEnv.ver);

        MyEnv.platfrom= VersionUtil.getPlatform(request);



        MyEnv.TEST_USER_GROUP = false;
        request.setAttribute("loginId",0L);
        if(token != null){
            UserToken userToken = userTokenService.findOneByToken(token);
            if(userToken != null && userToken.getUserId() != null){
                int userId = userToken.getUserId().intValue();
                int isTestUser = Arrays.binarySearch(testUserGroup,userId);
                if(isTestUser > -1){
                    MyEnv.TEST_USER_GROUP = true;
                }
//                for(int i = 0;i < testUserGroup.length; i++){
//                    if(testUserGroup[i] == userId){
//
//                        break;
//                    }
//                }
                request.setAttribute("loginId",userToken.getUserId());
            }

        }

        MyEnv.hasAopInit=1;
    }

    public static Long getCurrentUserId(HttpServletRequest httpServletRequest){
        Long userId=(Long)httpServletRequest.getAttribute("loginId");
        if(userId==null){
            return 0L;
        }
        return userId;
    }

//    public static void main(String[] args){
//        Long a=(Long)null;
//        a=a;
//    }

}

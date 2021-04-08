package com.ifenghui.storybookapi.app.presale.aop;



import com.ifenghui.storybookapi.adminapi.controlleradmin.ManagerController;
import com.ifenghui.storybookapi.app.app.controller.SmsController;
import com.ifenghui.storybookapi.app.app.response.BaseResponse;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.Order;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 服务端，子服务的的权限验证，用户子服务后台验证权限
 */
@Component
@Aspect
@Order(0)
public class AdminAuthClientAop {

    @Autowired
    HttpServletRequest request;

    @Autowired
    HttpServletResponse response;

    @Autowired
    Environment env;


//    RemoteAdminRoleApiService remoteAdminRoleApiService;
    @Autowired
ManagerController managerController;


    @Order(0)
    @Around("execution(* com.ifenghui.storybook.sale.admin.*.*(..)))")
    public Object afterStatusService(ProceedingJoinPoint jp) throws Throwable {



       String managerToken=request.getHeader("manager-token");
        //超级权限限制为内部服务器间调用, 请不要在页面用这个参数跨越权限！！
        String serverPwd=request.getHeader("fh-server-api-password");
        if(serverPwd!=null&&serverPwd.equals("vista688")){
            return jp.proceed();
        }
        JSONObject jsonObj = new JSONObject();
        BaseResponse baseResponseResult=new BaseResponse();
        baseResponseResult.getStatus().setCode(302);
        if(managerToken==null) {
            jsonObj= new JSONObject(baseResponseResult);
            response.getWriter().write(jsonObj.toString());
            return null;
        }
            String uri=request.getRequestURI();
           BaseResponse baseResponse= managerController.checkManagerRole(uri,managerToken);
           if(baseResponse.getStatus().getCode()!=1){

               jsonObj= new JSONObject(baseResponseResult);
               response.getWriter().write(jsonObj.toString());
               return null;
           }


        return jp.proceed();
    }

}

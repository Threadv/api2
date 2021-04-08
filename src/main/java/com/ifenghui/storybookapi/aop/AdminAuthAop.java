package com.ifenghui.storybookapi.aop;


import com.ifenghui.storybookapi.app.app.response.BaseResponse;
import com.ifenghui.storybookapi.adminapi.manager.entity.Manager;
import com.ifenghui.storybookapi.adminapi.manager.entity.ManagerToken;
import com.ifenghui.storybookapi.adminapi.manager.service.ManagerService;
import com.ifenghui.storybookapi.adminapi.manager.service.ManagerTokenService;
import com.ifenghui.storybookapi.config.MyEnv;
import com.ifenghui.storybookapi.style.AdminResponseType;
import com.ifenghui.storybookapi.style.RoleStyle;
import com.ifenghui.storybookapi.util.HeadManagerUtil;
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

@Component
@Aspect
@Order(0)
public class AdminAuthAop {

    @Autowired
    HttpServletRequest request;

    @Autowired
    HttpServletResponse response;

    @Autowired
    ManagerTokenService managerTokenService;

    @Autowired
    ManagerService managerService;

    @Autowired
    Environment env;

    @Order(0)
    @Around("execution(* com.ifenghui.storybookapi.adminapi.controlleradmin.*.*(..))||execution(* com.ifenghui.storybookapi.adminapi.controlleradmin.*.*.*(..))")
    public Object afterStatusService(ProceedingJoinPoint jp) throws Throwable {



        if(MyEnv.env==null){
            MyEnv.env=env;
        }
        //超级权限限制为内部服务器间调用, 请不要在页面用这个参数跨越权限！！
        String serverPwd=request.getHeader("fh-server-api-password");
        if(serverPwd!=null&&serverPwd.equals("vista688")){
            return jp.proceed();
        }

String uri=request.getRequestURI();
if(uri.startsWith("/v3/admin/auth/checkLogin")||uri.startsWith("/v3/admin/auth/login")||uri.indexOf("ostObjectPolicy")!=-1){
    return jp.proceed();
}
        if(uri.startsWith("/v3/admin")||uri.indexOf("admin")!=-1){
            String managerToken= HeadManagerUtil.getManagerToken(request);
//            managerTokenService.findOneByToken(managerToken);

            Manager ma=getManager(managerToken);
            if(ma==null){
//                BaseResponse baseResponse=new BaseResponse(new FhException("please refresh,and login"));
                BaseResponse fhBaseResponse=new BaseResponse(AdminResponseType.AUTH_LOW);
//                fhBaseResponse.s(AdminResponseType.AUTH_ERR);
//                return fhBaseResponse;
                JSONObject jsonObj = new JSONObject(fhBaseResponse);
                response.setContentType("text/json");
                response.getWriter().print(jsonObj.toString());
                return null;
            }

            //验证其他权限
            if(managerService.checkRole((Manager) ma,uri)==false){
                BaseResponse fhBaseResponse=new BaseResponse(AdminResponseType.AUTH_LOW);

                JSONObject jsonObj = new JSONObject(fhBaseResponse);
                response.setContentType("text/json");
                response.getWriter().print(jsonObj.toString());
                return null;
            }


        }

        return jp.proceed();

    }

    private Manager getManager(String token){
        if(token==null){
            return null;
        }
        ManagerToken managerToken=managerTokenService.findOneByToken(token);
        if(managerToken==null){
            return null;
        }
        long overday=(System.currentTimeMillis()/3600/1000/24)- (managerToken.getCreateTime().getTime()/3600/1000/24);
//        if(overday>30){
//            //token超过30天
//            return null;
//        }
        Manager manager=managerService.findOne(managerToken.getManagerId());
        return manager;
    }

//    private boolean checkRole(Manager manager,String uri){
//
//        for(RoleStyle roleStyle:RoleStyle.values()){
//            System.out.println(roleStyle.toString()+" :in:"+RoleStyle.values().length);
//
////            for(String roleKey:manager.getRoles().split(",")){
////                String key=roleStyle.getKey().toLowerCase().trim();
////                String key2=roleKey.toLowerCase().trim();
////                if(key.equals(key2)){
////                    if(!uri.startsWith(roleStyle.getPath())){
////                        continue;
////                    }
//////                    匹配权限
////                    return true;
////                }
////            }
//            //新版本权限匹配2019-3 不再使用映射，改用自己维护枚举的方式，主要用于多服务权限控制
//            for(String roleKey:manager.getRoles().split(",")){
//                String key=roleStyle.toString().toLowerCase();
//                //用户的权限
//                String key2=roleKey.toLowerCase().trim();
//                if(key.equals(key2)){
////                    匹配权限
//                    for(String path:roleStyle.getPaths()){
//                        if(uri.startsWith(path)){
//                            return true;
//                        }
//                    }
//
//                }
//            }
//
//        }
//        //没有匹配权限
//        return false;
//        //没有设置权限是否允许访问
////        return true;
//    }


}

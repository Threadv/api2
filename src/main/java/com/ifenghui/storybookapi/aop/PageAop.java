package com.ifenghui.storybookapi.aop;

import javassist.*;
import javassist.bytecode.CodeAttribute;
import javassist.bytecode.LocalVariableAttribute;
import javassist.bytecode.MethodInfo;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

@Component
@Aspect
@Order(2)
public class PageAop {
    /*
     if(request.getParameter("startOne")!=null&&request.getParameter("pageNo")!=null){
            try{
                int pageNo=Integer.parseInt(request.getParameter("pageNo"));
                List<String> pageNos=new ArrayList();
                pageNos.add((pageNo-1)+"");
                ctx.getRequestQueryParams().put("pageNo",pageNos);
                ctx.getRequestQueryParams().remove("startOne");
            }catch (Exception e){
                e.printStackTrace();
            }


        }
     */
    @Autowired
    HttpServletRequest request;
    @Around("execution(* com.ifenghui.storybookapi.app.*.controller.*.*(..))")
    public Object afterStatusService(ProceedingJoinPoint jp) throws Throwable {

        //接口统一从第一页开始
        if(request!=null&&request.getParameter("pageNo")==null){
            return jp.proceed();
        }
        String methodName = jp.getSignature().getName();
        Object[] args = jp.getArgs();
        String classType = jp.getTarget().getClass().getName();
        Class<?> clazz = Class.forName(classType);
        String clazzName = clazz.getName();

//        Map<String,Object > nameAndArgs = this.getFieldsName(this.getClass(), clazzName, methodName,args);
//        this.resetPageNo(this.getClass(), clazzName, methodName,args);
        this.resetPageNo(jp.getTarget().getClass(), clazzName, methodName,args);
        Object response=jp.proceed(args);

        return response;
    }



    private void resetPageNo(Class cls, String clazzName, String methodName, Object[] args) throws Exception {
        Method[] methods=cls.getDeclaredMethods();
        for(Method m:methods){
            String mName=m.getName();

            if(m.getName().equals(methodName)){

                for (int i=0;i<m.getParameters().length;i++) {
                    System.out.println("parameter: " + m.getParameters()[i].getType().getName() + ", " + m.getParameters()[i].getName());

                    if(m.getParameters()[i].getName().equals("pageNo")){
                        args[i]=(Integer)args[i]-1;
                        if((Integer)args[i]<0){
                            args[i]=0;
                        }
                        return;
//                map.put( attr.variableName(i + pos),args[i]);//paramNames即参数名
                    }
                }
            }
        }
//        return map;
    }

}

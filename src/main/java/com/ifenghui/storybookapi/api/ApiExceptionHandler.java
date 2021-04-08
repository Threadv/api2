package com.ifenghui.storybookapi.api;


import com.ifenghui.storybookapi.api.response.exception.ExceptionResponse;
import com.ifenghui.storybookapi.api.response.exception.ExceptionStoryOrderRepeatResponse;
import com.ifenghui.storybookapi.api.response.base.ApiStatus;
import com.ifenghui.storybookapi.api.response.exception.ExceptionUserTokenBeyondLimitResponse;

import com.ifenghui.storybookapi.config.MyEnv;
import com.ifenghui.storybookapi.exception.*;
import com.ifenghui.storybookapi.app.system.service.ElasticService;
import com.ifenghui.storybookapi.style.OrderStyle;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
/**
 * 异常处理类
 * Created by wslhk on 2016/12/22.
 * @author wsl
 */
@ControllerAdvice
public class ApiExceptionHandler {


    @Autowired
    ElasticService elasticService;

   public void postToElasticSearch(){

   }
//


    /**
     * Api接口默认异常
     * @param req
     * @param e
     * @return
     * @throws Exception
     */
    @ExceptionHandler(value = ApiException.class)
    @ResponseBody
    public ExceptionResponse jsonErrorHandler(HttpServletRequest req, Exception e) throws Exception {
        
        ExceptionResponse response = new ExceptionResponse();
        ApiStatus apiStatus=new ApiStatus();
        apiStatus.setMsg(e.getMessage());
        apiStatus.setCode(500);
        //查询数据不存在
        if(e instanceof ApiException){
            apiStatus.setCode(((ApiException) e).getApicode());
            apiStatus.setMsg(((ApiException) e).getApimsg());
        }

        response.setStatus(apiStatus);

        return response;
    }

    //缺少参数
    @ExceptionHandler(value = MissingServletRequestParameterException.class)
    @ResponseBody
    public ExceptionResponse jsonMissingServletRequestParameterExceptionHandler(HttpServletRequest req, MissingServletRequestParameterException e) throws Exception {

        ExceptionResponse response = new ExceptionResponse();
        ApiStatus apiStatus=new ApiStatus();
        apiStatus.setMsg(e.getMessage());
        apiStatus.setCode(502);
        //查询数据不存在


        response.setStatus(apiStatus);

        return response;
    }

    /**
     * 订单重复异常
     * @param req
     * @param e
     * @return
     * @throws Exception
     */
    @ExceptionHandler(value = ApiStoryOrderRepeatException.class)
    @ResponseBody
    public ExceptionStoryOrderRepeatResponse ApiStoryOrderRepeatExceptionHandler(HttpServletRequest req, ApiStoryOrderRepeatException e) throws Exception {

//        ExceptionStoryOrderRepeatResponse response = new ExceptionStoryOrderRepeatResponse(e);
        ExceptionStoryOrderRepeatResponse response = new ExceptionStoryOrderRepeatResponse();
        ApiStatus apiStatus=new ApiStatus();
        if (e.getApimsg() != null){
            apiStatus.setMsg(e.getApimsg());
        } else {
            apiStatus.setMsg(MyEnv.getMessage("order.story.exists"));
        }
        if (e.getvPayOrder().getOrderStyle().equals(OrderStyle.STORY_ORDER)){
            apiStatus.setCode(113);
            response.setPayOrder(e.getvPayOrder());
        } else {
            apiStatus.setCode(114);
            response.setStandardOrder(e.getStandardOrder());
        }

        //查询数据不存在
        response.setStatus(apiStatus);

        return response;
    }

    /**
     * token超过数量限制异常
     * @param req
     * @param e
     * @return
     * @throws Exception
     */
    @ExceptionHandler(value = ApiUserTokenBeyondLimitException.class)
    @ResponseBody
    public ExceptionUserTokenBeyondLimitResponse ApiUserTokenBeyondLimitExceptionHandler(HttpServletRequest req, ApiUserTokenBeyondLimitException e) throws Exception {
        ExceptionUserTokenBeyondLimitResponse response = new ExceptionUserTokenBeyondLimitResponse(e);
        ApiStatus apiStatus=new ApiStatus();
        apiStatus.setMsg(e.getApimsg());
        apiStatus.setCode(e.getApicode());
        response.setStatus(apiStatus);
        return response;
    }


    /**
     * 默认系统异常，会被elastic捕获
     * @param req
     * @param e
     * @throws Exception
     */
    @ExceptionHandler(value={Exception.class})
    public void jsonEntityNotFoundExceptionHandler(HttpServletRequest req, Exception e) throws Exception {
        e.printStackTrace();
        try{
            elasticService.addException(e);
        }catch (Exception e1){
            e1.printStackTrace();
        }

        throw e;
    }


}

package com.ifenghui.storybookapi.app.presale.exception;



import com.ifenghui.storybookapi.api.response.base.ApiStatus;
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
public class PreSaleExceptionHandler {

   public void postToElasticSearch(){

   }
    /**
     * Api接口默认异常
     * @param req
     * @param e
     * @return
     * @throws Exception
     */
    @ExceptionHandler(value = PreSaleException.class)
    @ResponseBody
    public PreSaleExceptionResponse jsonErrorHandler(HttpServletRequest req, Exception e) throws Exception {

        PreSaleExceptionResponse response = new PreSaleExceptionResponse();
        ApiStatus apiStatus=new ApiStatus();
        apiStatus.setMsg(e.getMessage());
        apiStatus.setCode(500);
        //查询数据不存在
        if(e instanceof PreSaleException){
            apiStatus.setCode(((PreSaleException) e).getApicode());
            apiStatus.setMsg(((PreSaleException) e).getApimsg());
        }

        response.setStatus(apiStatus);

        return response;
    }

    //缺少参数
    @ExceptionHandler(value = MissingServletRequestParameterException.class)
    @ResponseBody
    public PreSaleExceptionResponse jsonMissingServletRequestParameterExceptionHandler(HttpServletRequest req, MissingServletRequestParameterException e) throws Exception {

        PreSaleExceptionResponse response = new PreSaleExceptionResponse();
        ApiStatus apiStatus=new ApiStatus();
        apiStatus.setMsg(e.getMessage());
        apiStatus.setCode(502);
        //查询数据不存在
        response.setStatus(apiStatus);
        return response;
    }

}

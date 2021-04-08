package com.ifenghui.storybookapi.app.system.service;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.io.PrintWriter;
import java.io.Serializable;
import java.io.StringWriter;
import java.util.Date;

public class ErrorLog implements Serializable{

    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss Z")
//    @JsonFormat(timezone = "GMT+8", pattern = "dd/MMM/yyyy:HH:mm:ss Z")
    Date createTime;
    String message;
    //        Exception exception;
    String printStackTrace;

    String requestUrl;
    String requestData;
    String header;

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

//        public Exception getException() {
//            return exception;
//        }

    public void setException(Exception exception) {
//            this.exception = exception;
        StringWriter sw = new StringWriter();
        PrintWriter pw = new PrintWriter(sw);
        exception.printStackTrace(pw);
        this.message=exception.getMessage();
//            String msg=sw.toString();
        printStackTrace=sw.toString();
//            this.setMessage(exception.getMessage());
    }

    public String getPrintStackTrace() {
        return printStackTrace;
    }

    public void setPrintStackTrace(String printStackTrace) {
        this.printStackTrace = printStackTrace;
    }

    public String getRequestUrl() {
        return requestUrl;
    }

    public void setRequestUrl(String requestUrl) {
        this.requestUrl = requestUrl;
    }

    public String getRequestData() {
        return requestData;
    }

    public void setRequestData(String requestData) {
        this.requestData = requestData;
    }

    public String getHeader() {
        return header;
    }

    public void setHeader(String header) {
        this.header = header;
    }
}

package com.ifenghui.storybookapi.util.ios;

/**
 * Created by wslhk on 2017/8/25.
 */
public class IosNotify {
    int status;
    String exception;
    String environment;
    Receipt receipt=new Receipt();
    int isSandBox;

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getEnvironment() {
        return environment;
    }

    public void setEnvironment(String environment) {
        this.environment = environment;
    }

    public Receipt getReceipt() {
        return receipt;
    }

    public void setReceipt(Receipt receipt) {
        this.receipt = receipt;
    }

    public int getIsSandBox() {
        return isSandBox;
    }

    public void setIsSandBox(int isSandBox) {
        this.isSandBox = isSandBox;
    }

    public String getException() {
        return exception;
    }

    public void setException(String exception) {
        this.exception = exception;
    }
}

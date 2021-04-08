package com.ifenghui.storybookapi.api.response.base;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;

/**
 * 分页信息
 * Created by wslhk on 2016/12/20.
 */
public class ApiPage {
    public ApiPage(){}
    int pageNo;
    int pageSize;
    int pageCount;
    boolean hasNext;
    long rsCount;

    public int getPageNo() {
        return pageNo;
    }

    public void setPageNo(int pageNo) {
        this.pageNo = pageNo;
    }

    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

    public boolean isHasNext() {
        return hasNext;
    }

    public void setHasNext(boolean hasNext) {
        this.hasNext = hasNext;
    }

    public int getPageCount() {
        return pageCount;
    }

    public void setPageCount(int pageCount) {
        this.pageCount = pageCount;
    }

    public long getRsCount() {
        return rsCount;
    }

    public void setRsCount(long rsCount) {
        this.rsCount = rsCount;
    }
}

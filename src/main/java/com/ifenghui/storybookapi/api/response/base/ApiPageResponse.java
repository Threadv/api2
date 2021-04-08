package com.ifenghui.storybookapi.api.response.base;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.springframework.data.domain.Page;

/**
 * 分页返回数据
 * Created by wslhk on 2016/12/20.
 */
public abstract class ApiPageResponse {
    ApiStatus status;

    ApiPage page;


    public ApiPageResponse(){
        status=new ApiStatus();
        status.setCode(1);
        status.setMsg("成功");
        page=new ApiPage();
    }


    public ApiStatus getStatus() {
        return status;
    }

    public void setStatus(ApiStatus status) {
        this.status = status;
    }

    public ApiPage getPage() {
        return page;
    }

    public void setPage(ApiPage page) {
        this.page = page;
    }

    @JsonIgnore
    public void setJpaPage(Page page) {
        this.page.setPageCount(page.getTotalPages());
        this.page.setPageNo(page.getNumber()+1);
        this.page.setPageSize(page.getSize());
        this.page.setHasNext(page.hasNext());
        this.page.setRsCount(page.getTotalElements());

    }
}

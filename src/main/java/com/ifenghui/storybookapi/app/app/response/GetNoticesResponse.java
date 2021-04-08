package com.ifenghui.storybookapi.app.app.response;

import com.ifenghui.storybookapi.api.response.base.ApiPageResponse;
import com.ifenghui.storybookapi.app.app.entity.Notice;

import java.util.List;

public class GetNoticesResponse extends ApiPageResponse {

    List<Notice> noticeList;

    public List<Notice> getNoticeList() {
        return noticeList;
    }

    public void setNoticeList(List<Notice> noticeList) {
        this.noticeList = noticeList;
    }
}

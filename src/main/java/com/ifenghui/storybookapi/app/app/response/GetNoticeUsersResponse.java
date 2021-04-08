package com.ifenghui.storybookapi.app.app.response;

import com.ifenghui.storybookapi.app.app.entity.NoticeUser;
import com.ifenghui.storybookapi.api.response.base.ApiPageResponse;

import java.util.List;

public class GetNoticeUsersResponse extends ApiPageResponse {

    List<NoticeUser> noticeUserList;

    public List<NoticeUser> getNoticeUserList() {
        return noticeUserList;
    }

    public void setNoticeUserList(List<NoticeUser> noticeUserList) {
        this.noticeUserList = noticeUserList;
    }
}

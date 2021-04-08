package com.ifenghui.storybookapi.app.app.response;

import com.ifenghui.storybookapi.api.response.base.ApiPageResponse;
import com.ifenghui.storybookapi.app.app.entity.NoticeUser;

public class GetNoticeUserResponse extends ApiPageResponse {

    NoticeUser noticeUser;

    public NoticeUser getNoticeUser() {
        return noticeUser;
    }

    public void setNoticeUser(NoticeUser noticeUser) {
        this.noticeUser = noticeUser;
    }
}

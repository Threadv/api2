package com.ifenghui.storybookapi.app.app.service.impl;

import com.ifenghui.storybookapi.app.app.entity.Notice;
import com.ifenghui.storybookapi.app.app.entity.NoticeUser;
import com.ifenghui.storybookapi.app.app.service.NoticeService;
import com.ifenghui.storybookapi.app.app.service.NoticeUserService;
import com.ifenghui.storybookapi.app.app.service.TemplateNoticeService;
import com.ifenghui.storybookapi.style.NoticeStyle;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.beans.Transient;
import java.util.Map;

@Transactional
@Component
public class TemplateNoticeServiceImpl implements TemplateNoticeService {

    @Autowired
    NoticeService noticeService;

    @Autowired
    NoticeUserService noticeUserService;

    @Override
    public String getTemplateByNoticeStyleAndMap(NoticeStyle noticeStyle, Map<String, String> contentMap) {
        return noticeStyle.getContent(contentMap);
    }

    @Transactional
    @Override
    public void addNoticeToUserByUserIdAndMessage(NoticeStyle noticeStyle, Map<String, String> contentMap, Integer userId){
        String content = this.getTemplateByNoticeStyleAndMap(noticeStyle, contentMap);
        Notice notice = noticeService.addNotice(content, noticeStyle.getNoticeTypeStyle(),0,"", noticeStyle.getIcon());
        NoticeUser noticeUser = noticeUserService.addNoticeUser(userId, notice.getId());
    }
}


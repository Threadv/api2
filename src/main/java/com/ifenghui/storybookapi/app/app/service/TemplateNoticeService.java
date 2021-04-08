package com.ifenghui.storybookapi.app.app.service;

import com.ifenghui.storybookapi.style.NoticeStyle;

import java.util.Map;

public interface TemplateNoticeService {

    String getTemplateByNoticeStyleAndMap(NoticeStyle noticeStyle, Map<String, String> contentMap);

    public void addNoticeToUserByUserIdAndMessage(NoticeStyle noticeStyle, Map<String, String> contentMap, Integer userId);

}


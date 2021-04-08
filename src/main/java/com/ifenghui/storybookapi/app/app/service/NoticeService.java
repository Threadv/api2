package com.ifenghui.storybookapi.app.app.service;

import com.ifenghui.storybookapi.app.app.entity.Notice;
import com.ifenghui.storybookapi.style.RedirectStyle;

public interface NoticeService {

    /**
     * c查询通知
     */
    Notice findNotice(Integer noticeId);

    /**
     * 添加通知
     */
    Notice addNotice(String content, RedirectStyle targetType, Integer targetValue, String url, String icon);

}

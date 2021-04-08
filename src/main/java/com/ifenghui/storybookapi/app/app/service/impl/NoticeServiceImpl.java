package com.ifenghui.storybookapi.app.app.service.impl;

import com.ifenghui.storybookapi.app.app.dao.NoticeDao;
import com.ifenghui.storybookapi.app.app.entity.Notice;
import com.ifenghui.storybookapi.app.app.service.NoticeService;
import com.ifenghui.storybookapi.style.RedirectStyle;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;

@Transactional
@Component
public class NoticeServiceImpl implements NoticeService {

    @Autowired
    NoticeDao noticeDao;

    @Override
    public Notice findNotice(Integer noticeId) {
        return noticeDao.findOne(noticeId);
    }

    @Override
    public Notice addNotice(String content, RedirectStyle targetType, Integer targetValue, String url, String icon) {

        Notice notice = new Notice();
        notice.setCreateTime(new Date());
        notice.setContent(content);
        notice.setRedirectStyle(targetType);
        notice.setTargetValue(targetValue);
        notice.setUrl(url);
        notice.setIcon(icon);
        noticeDao.save(notice);
        return notice;
    }

}

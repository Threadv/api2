package com.ifenghui.storybookapi.app.app.service;

import com.ifenghui.storybookapi.app.app.entity.NoticeUser;
import org.springframework.data.domain.Page;

import java.util.List;

public interface NoticeUserService {


    /**
     * 消息列表
     * @return
     */
    List<NoticeUser> getNoticeUserList(Integer userId);
    /**
     * 用户消息列表
     * @param userId
     * @param pageNo
     * @param pageSize
     * @return
     */
    Page<NoticeUser> findAll(Long userId, Integer pageNo, Integer pageSize);

    /**
     * 用户未读消息
     * @param userId
     * @param pageNo
     * @param pageSize
     * @return
     */
    Page<NoticeUser> getUnreadList(Long userId, Integer pageNo, Integer pageSize);

    /**
     * 获得单个消息
     * @param noticeUserId
     * @return
     */
    NoticeUser getNotice(Integer noticeUserId);

    /**
     * 保存用户通知
     * @param noticeUser
     * @return
     */
    NoticeUser save(NoticeUser noticeUser);

    /**
     * 设置消息已读
     * @param userId
     */
    void setToRead(Integer userId);

    /**
     * 添加用户通知
     * @param userId
     * @return
     */
    NoticeUser addNoticeUser(Integer userId,Integer noticeId);


}

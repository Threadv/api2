package com.ifenghui.storybookapi.app.app.service;


import com.ifenghui.storybookapi.app.app.entity.PopupUser;
import com.ifenghui.storybookapi.app.user.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

/**
 * 用户弹窗关联
 */
public interface PopupUserService {

    PopupUser add(PopupUser popupUser);

    PopupUser update(PopupUser popupUser);

    PopupUser findOne(Integer id);

    void delete(Integer id);

    Page<PopupUser> findAll(PopupUser popupUser, Pageable pageable);

//    /**
//     * 通过线程增加弹窗记录，非阻塞
//     * @param popupId
//     * @param userId
//     */
////    void addPopupUserByThread(Integer popupId, Integer userId);

    /**
     * 无线程创建,状态是0未阅读，弹窗列表显示的时候添加记录
     * @param popupId
     * @param userId
     */
    void addPopupUser(Integer popupId, Integer userId);


    public boolean isRead(Integer popupId, Integer userId);

    public PopupUser findOneByPopupIdAndUserId(Integer popupId, Integer userId);

    public PopupUser findOneByPopupIdAndUserIdUnRead(Integer popupId, Integer userId);

    List<PopupUser> findAllByUserIdAndIsRead(Integer userId, Integer isRead);

//    public PopupUser setRead(Integer popupId, Integer userId);

//    public void setReadAll(Integer userId);


}

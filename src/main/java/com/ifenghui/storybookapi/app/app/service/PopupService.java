package com.ifenghui.storybookapi.app.app.service;


import com.ifenghui.storybookapi.app.app.entity.Popup;
import com.ifenghui.storybookapi.app.user.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

/**
 * 弹窗任务,and其他活动任务
 */
public interface PopupService {

//    //获得弹窗资源
//     getPopup();
//
//    //是否显示弹窗
//    void isShow();
//
//
//    //现有的功能都是自动记录关闭
//    void closePopup();

    Popup add(Popup popup);

    Popup update(Popup popup);

    Popup findOne(Integer id);

    void delete(Integer id);

    Page<Popup> findAll(Popup popup, Pageable pageable);

    /**
     * 当前时间段允许弹出的弹框
     * @return
     */
    List<Popup> findCurrentAll(Integer userId);

//    void clickAll(User user);

//    Popup findByClassName(String className);




}

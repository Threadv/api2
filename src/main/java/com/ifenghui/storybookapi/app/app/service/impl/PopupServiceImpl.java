package com.ifenghui.storybookapi.app.app.service.impl;


import com.ifenghui.storybookapi.app.app.dao.PopupDao;
import com.ifenghui.storybookapi.app.app.entity.Popup;
import com.ifenghui.storybookapi.app.app.service.PopupService;
import com.ifenghui.storybookapi.app.app.service.PopupUserService;
import com.ifenghui.storybookapi.app.user.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;


@Component
public class PopupServiceImpl implements PopupService {

    @Autowired
    PopupDao popupDao;

//    @Autowired
//    Map<String, PopupTaskService> popupTask;

    @Autowired
    PopupUserService popupUserService;

    @Override
    public Popup add(Popup popup) {
        return popupDao.save(popup);
    }

    @Override
    public Popup update(Popup popup) {
        return popupDao.save(popup);
    }

    @Override
    public Popup findOne(Integer id) {
        return popupDao.findOne(id);
    }

    @Override
    public void delete(Integer id) {
        popupDao.delete(id);
    }

    @Override
    public Page<Popup> findAll(Popup popup, Pageable pageable) {
        return popupDao.findAll(Example.of(popup),pageable);
    }

    @Override
    public List<Popup> findCurrentAll(Integer userId) {
        /**
         * 1，查所有时段内已发布的弹窗
         * 2，比对是否已点击，如果没有点击增加到弹窗列表重
         *
         */
        List<Popup> list=new ArrayList<>();

        List<Popup> listAll=popupDao.findAllPublishPopupByTime(new Date());

        if(userId==0){
            return listAll;
        }


        for(Popup popup:listAll){
            boolean isRead=popupUserService.isRead(popup.getId(),userId);
            if(!isRead){
                list.add(popup);
            }
        }

        return list;

    }



//    @Override
//    public Popup findByClassName(String className) {
//        return popupDao.findByClassName(className);
//    }


}

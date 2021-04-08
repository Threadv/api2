package com.ifenghui.storybookapi.app.app.service.impl;


import com.ifenghui.storybookapi.app.app.dao.PopupDao;
import com.ifenghui.storybookapi.app.app.dao.PopupUserDao;
import com.ifenghui.storybookapi.app.app.entity.PopupUser;
import com.ifenghui.storybookapi.app.app.service.PopupService;
import com.ifenghui.storybookapi.app.app.service.PopupUserService;
import com.ifenghui.storybookapi.app.user.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.List;
import java.util.Map;

@Component
public class PopupUserServiceImpl implements PopupUserService {

    @Autowired
    PopupUserDao popupUserDao;

    @Autowired
    PopupService popupService;
//    @Autowired
//    Map<String, PopupTaskService> popupTask;

    @Autowired
    UserService userService;

    @Autowired
    PopupDao popupDao;

//    @Autowired
//    KtxTransactionService transactionService;

    @Override
    public PopupUser add(PopupUser popupUser) {


        popupUser=popupUserDao.save(popupUser);
        //这里是首次触发，需要增加弹框触发
        return popupUser;
    }

    @Override
    public PopupUser update(PopupUser popupUser) {
        return popupUserDao.save(popupUser);
    }

    @Override
    public PopupUser findOne(Integer id) {
        return popupUserDao.findOne(id);
    }

    @Override
    public void delete(Integer id) {
        popupUserDao.delete(id);
    }

    @Override
    public Page<PopupUser> findAll(PopupUser popupUser, Pageable pageable) {
        return popupUserDao.findAll(Example.of(popupUser),pageable);
    }

//    @Override
//    public void addPopupUser(Integer popupId, Integer userId) {
//
//        PopupUser popupUser=new PopupUser();
//        popupUser.setPopupId(popupId);
//        popupUser.setUserId(userId);
//        popupUser.setCreateTime(new Date());
//        popupUser.setIsRead(0);
//        popupUser.setReadTime(new Date());
//
////        PopupUserService popupUserService=this;
//
//        Runnable runnable= new Runnable(){
//            @Override
//            public void run() {
//                popupUserService.add(popupUser);
//
//            }
//        };
//        Thread t=new Thread(runnable);
//        t.start();
//    }

    @Override
    public void
    addPopupUser(Integer popupId, Integer userId) {

        boolean isRead=this.isRead(popupId,userId);
        if(isRead==true){


            return;
        }

        PopupUser popupUser=new PopupUser();
        popupUser.setPopupId(popupId);
        popupUser.setUserId(userId);
        popupUser.setCreateTime(new Date());
        popupUser.setIsRead(0);
        popupUser.setReadTime(new Date());
         this.add(popupUser);
    }

    /**
     * 验证用户是否已经阅读过
     * @param popupId
     * @param userId
     * @return
     */
    @Override
    public boolean isRead(Integer popupId,Integer userId){
        long count=popupUserDao.countAllByPopupIdAndUserIdAndIsRead(popupId,userId,1);
        if(count>0){
            return true;
        }
        return false;
    }

    @Override
    public PopupUser findOneByPopupIdAndUserId(Integer popupId, Integer userId) {
        return popupUserDao.findByPopupIdAndUserId(popupId,userId);
    }

    @Override
    public PopupUser findOneByPopupIdAndUserIdUnRead(Integer popupId, Integer userId) {
        List<PopupUser> popupUsers= popupUserDao.findAllByPopupIdAndUserIdUnRead(popupId,userId);
        if(popupUsers.size()>0){
            return popupUsers.get(0);
        }
        return null;
    }

    @Override
    public List<PopupUser> findAllByUserIdAndIsRead(Integer userId, Integer isRead) {
        return popupUserDao.findAllByUserIdAndIsRead(userId,isRead);
    }

//    @Override
//    public PopupUser setRead(PopupUser popupUser) {
//        if(popupUser==null){
//            return null;
//        }
//        popupUser.setIsRead(1);
//        popupUser.setReadTime(new Date());
//        return this.update(popupUser);
////        return popupUser;
//    }
//
//    @Override
//    public void setReadAll(Integer userId) {
//        List<PopupUser> popupUsers=this.findAllByUserIdAndIsRead(userId,0);
//        for(PopupUser popupUser:popupUsers){
//            this.addPopupUser(popupUser.getPopupId());
//        }
//        return ;
//    }



}

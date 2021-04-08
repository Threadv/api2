package com.ifenghui.storybookapi.app.app.service.impl;

import com.ifenghui.storybookapi.app.app.dao.NoticeDao;
import com.ifenghui.storybookapi.app.app.dao.NoticeUserDao;
import com.ifenghui.storybookapi.app.app.entity.NoticeUser;
import com.ifenghui.storybookapi.app.app.service.NoticeUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

//@Transactional
@Component
public class NoticeUserServiceImpl implements NoticeUserService {

    @Autowired
    NoticeUserDao noticeUserDao;

    @Autowired
    NoticeDao noticeDao;

    /**
     * 消息列表
     * @return
     */
    @Override
    public List<NoticeUser> getNoticeUserList(Integer userId){
        List<NoticeUser> noticeUserList = noticeUserDao.getNoticeUsersByUserId(userId);
        return new ArrayList<>(noticeUserList);
    }

    /**
     * 查看所有消息
     * @param userId
     * @param pageNo
     * @param pageSize
     * @return
     */
    @Override
    public Page<NoticeUser> findAll(Long userId, Integer pageNo, Integer pageSize) {
        Pageable pageable = new PageRequest(pageNo,pageSize,new Sort(Sort.Direction.DESC,"id"));
        return noticeUserDao.getNoticeUsersByUserIdAndIsDel(userId.intValue(), 0, pageable);
    }

    /**
     * 所有未读消息
     * @param userId
     * @param pageNo
     * @param pageSize
     * @return
     */
    @Override
    public Page<NoticeUser> getUnreadList(Long userId, Integer pageNo, Integer pageSize) {
        Pageable pageable = new PageRequest(pageNo,pageSize,new Sort(Sort.Direction.DESC,"id"));
        return noticeUserDao.getNoticeUsersByUserIdAndIsReadAndIsDel(userId.intValue(), 0, 0, pageable);
    }

    /**
     * 查看单个消息
     * @param noticeId
     * @return
     */
    @Override
    public NoticeUser getNotice(Integer noticeId) {
        return noticeUserDao.findOne(noticeId);
    }

    /**
     * 保存用户通知信息
     * @param noticeUser
     * @return
     */
    @Override
    public NoticeUser save(NoticeUser noticeUser) {
        NoticeUser saveNoticeUser = noticeUserDao.save(noticeUser);
        return saveNoticeUser;
    }

    /**
     * 设置消息已读
     * @param userId
     */
    @Override
    public void setToRead(Integer userId) {
        List<NoticeUser> noticeUserList = noticeUserDao.getNoticeUsersByUserIdAndIsRead(userId, 0);
        for (NoticeUser nu:noticeUserList) {
            nu.setIsRead(1);
            noticeUserDao.save(nu);
        }
    }

    /**
     * 添加用户通知
     * @param userId
     * @return
     */
    @Override
    public NoticeUser addNoticeUser(Integer userId,Integer noticeId){

        //保存消息
        NoticeUser noticeUser = new NoticeUser();
        noticeUser.setUserId(userId);
        noticeUser.setNoticeId(noticeId);
        noticeUser.setCreateTime(new Date());
        noticeUser.setIsRead(0);
        noticeUser.setIsDel(0);
        noticeUserDao.save(noticeUser);
        return noticeUser;
    }
}

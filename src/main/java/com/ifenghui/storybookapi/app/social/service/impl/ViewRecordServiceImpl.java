package com.ifenghui.storybookapi.app.social.service.impl;


import com.ifenghui.storybookapi.app.activity.dao.ActivityViewCountDao;
import com.ifenghui.storybookapi.app.social.dao.ViewRecordDao;
import com.ifenghui.storybookapi.app.social.entity.UserReadRecord;
import com.ifenghui.storybookapi.app.social.service.LessonViewRecordService;
import com.ifenghui.storybookapi.app.story.dao.StoryDao;
import com.ifenghui.storybookapi.app.transaction.dao.BuyStoryRecordDao;
import com.ifenghui.storybookapi.app.user.dao.UserDao;
import com.ifenghui.storybookapi.app.transaction.entity.BuyStoryRecord;
import com.ifenghui.storybookapi.app.social.entity.ViewRecord;

import com.ifenghui.storybookapi.app.activity.entity.ActivityViewCount;
import com.ifenghui.storybookapi.app.story.entity.Story;
import com.ifenghui.storybookapi.app.user.entity.User;
import com.ifenghui.storybookapi.app.story.service.StoryService;
import com.ifenghui.storybookapi.app.social.service.ViewRecordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletRequest;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

/**
 * Created by jia on 2016/12/23.
 */
@Transactional
@Component
public class ViewRecordServiceImpl implements ViewRecordService {
    @Autowired
    ViewRecordDao viewRecordDao;
    @Autowired
    BuyStoryRecordDao buyStoryRecordDao;
    @Autowired
    StoryDao storyDao;
    @Autowired
    UserDao userDao;

    @Autowired
    ActivityViewCountDao activityViewCountDao;

    @Autowired
    StoryService storyService;

    @Autowired
    LessonViewRecordService lessonViewRecordService;


    @Override
    public Page<ViewRecord> getViewrecordByUserIdAndType(Long userId, Integer pageNo, Integer pageSize) {

        Pageable pageable = new PageRequest(pageNo, pageSize, new Sort(Sort.Direction.DESC,"createTime","id"));
        User user = userDao.findOne(userId);
        Page<ViewRecord> viewrecordPage = viewRecordDao.getViewrecordByUserIdAndType(user, pageable);

        Story story;
        for (ViewRecord item :viewrecordPage.getContent()) {
            story=storyDao.findOne(item.getStoryId());
            if(story!=null){
                storyService.setStoryAppFile(story);
                storyService.setStoryIsBuy(user,story);
                item.setStory(story);
            }
        }
        return viewrecordPage;
    }


    @Override
    public ViewRecord getViewrecord(Long id){
        return viewRecordDao.findOne(id);
    }

    @Override
    public Void delViewrecord(Long id) {
        viewRecordDao.delete(id);
        return null;
    }

    @Override
    public Void eidtViewRecord(ViewRecord viewRecord) {
        viewRecordDao.save(viewRecord);
        return null;
    }
    @Override
    public ViewRecord addViewrecord(ViewRecord viewrecord){
        ViewRecord ts = viewRecordDao.save(viewrecord);
        return ts;
    }

    @Override
    public List<ViewRecord> getViewrecordsByUser(Long userId){
        List<ViewRecord> vs=viewRecordDao.findAllByUserId(userId);
        return vs;
    }
    @Override
    public Page<ViewRecord> getViewrecordsByUserAndStoryId(Long userId,Long storyId,Integer pageNo,Integer pageSize){
        Pageable pageable = new PageRequest(pageNo, pageSize, new Sort(Sort.Direction.DESC,"id"));
        Page<ViewRecord> vs=viewRecordDao.getViewRecordByUserIdAndStoryId(userId,storyId,pageable);
        return vs;
    }
    //个人中心累计阅读
    @Override
    public void addUserReadRecordCount(Long userId, Long storyId){
        User user= userDao.findOne(userId);
        //领取任务
        Story story = storyDao.findOne(storyId);
        Integer type = 1;
        if(story.getType().intValue() == 2 || story.getType().intValue()==3){//音频+游戏
            return;
        }
//        查询此用户有没有阅读过这个故事，有则删除，添加个新的
        Integer pageNo = 0;
//        Integer pageSize = 100;
//        Page<UserReadRecord> userReadRecords = this.getReadRecordsByUserIdAndStoryId(userId,storyId,pageNo,pageSize);
        //获取用户最近一条记录，判断阅读时间是否为昨天是则readDays+1,如果没有数据则设置为1
        Page<ViewRecord> userReadRecordPage= this.getViewRecordsByUserIdAndPage(userId,pageNo,1);
        if(userReadRecordPage.getContent().size()>0){
            //判断时间
            Date recordTime = userReadRecordPage.getContent().get(0).getCreateTime();
            //当前时间
            Date now = new Date();
            SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd");
            String recordDay = sf.format(recordTime);
            //获取今天的日期
            String nowDay = sf.format(now);
            //对比的时间
            if(recordDay.equals(nowDay)){
//                System.out.println("jintian");
            }else{
                //判断是不是昨天
                //获取昨天的时间热ad
                Calendar calendar = new GregorianCalendar();
                calendar.setTime(new Date());
                calendar.add(Calendar.DATE,-1);//把日期往后增加一天.整数往后推,负数往前移动
                Date yesterDay =calendar.getTime(); //这个时间就是日期往后推一天的结果
                String ydayStr = sf.format(yesterDay);
                if(type == 1){
                    user.setReadDays(user.getReadDays()+1);//累计+1
                }

            }
        }else{
            if(type == 1){
                user.setReadDays(1);
            }

        }

        //判断是否是故事，故事则添加阅读
        userDao.save(user);


        return ;
    }
    @Override
    public Page<ViewRecord> getViewRecordsByUserIdAndPage(Long userId, Integer pageNo, Integer pageSize) {
        Pageable pageable = new PageRequest(pageNo, pageSize, new Sort(Sort.Direction.DESC,"id"));
        User user = userDao.findOne(userId);
        Page<ViewRecord> readRecords=viewRecordDao.getUserViewRecord(user,pageable);

        return readRecords;
    }

    @Override
    public Page<ViewRecord> getViewrecordsByUserAndPage(Long userId, Integer pageNo, Integer pageSize, HttpServletRequest request) {
        Pageable pageable = new PageRequest(pageNo, pageSize, new Sort(Sort.Direction.DESC,"createTime","id"));
        User user = userDao.findOne(userId);
        //故事足迹修改为只查询 类型1  4
//        Page<ViewRecord> viewRecords=viewRecordDao.getViewrecordsByUserId(userId,pageable);
        Page<ViewRecord> viewRecords = this.getViewrecordByUserIdAndType(userId, pageNo, pageSize);
        Long buyStoryRecordCount ;
        Story story;
        for (ViewRecord item :viewRecords.getContent()) {
            story=storyDao.findOne(item.getStoryId());
            if(story!=null){
                item.setStory(story);
                //判断此用户是否已购买
                buyStoryRecordCount=this.buyStoryRecordDao.countByUserIdAndStoryId(userId,item.getStoryId());
                if (buyStoryRecordCount==0){
                    item.getStory().setIsBuy(0);
                }else{
                    item.getStory().setIsBuy(1);
                    item.getStory().setIsPurchased(1);
                }

                if(user!=null && user.getSvip()==1){
                    item.getStory().setIsBuy(1);
                }
                if(user!=null && user.getSvip()==2&&story.getIsNow()==1){
                    item.getStory().setIsBuy(1);
                }

                storyService.setStoryAppFile(item.getStory());

            }

        }
        return viewRecords;
    }

    @Override
    public Page<ViewRecord> getViewrecordsByPage(Integer pageNo, Integer pageSize) {
        Pageable pageable = new PageRequest(pageNo, pageSize, new Sort(Sort.Direction.DESC,"createTime","id"));
        Page<ViewRecord> viewRecords=viewRecordDao.findAll(pageable);
        return viewRecords;
    }


    @Override
    public Void addActivityViewRecord(Integer id) {
        ActivityViewCount activityViewCount =activityViewCountDao.findOne(id) ;
        if(activityViewCount==null){
            return null;
        }
        activityViewCount.setCount(activityViewCount.getCount()+1);
        activityViewCountDao.save(activityViewCount);
        return null;
    }

    @Override
    public void addLessonViewRecord(Integer userId, Integer storyId){
        lessonViewRecordService.addLessonViewRecordByTargetValueAndIsVideo(userId, storyId, false);
    }
}

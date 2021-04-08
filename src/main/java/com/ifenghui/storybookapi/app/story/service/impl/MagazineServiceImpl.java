package com.ifenghui.storybookapi.app.story.service.impl;

import com.ifenghui.storybookapi.app.story.response.GetUserSubscribeRecordsResponse;
import com.ifenghui.storybookapi.app.social.response.SubscriptionSchedule;
import com.ifenghui.storybookapi.app.story.dao.MagazineDao;
import com.ifenghui.storybookapi.app.story.dao.StoryDao;
import com.ifenghui.storybookapi.app.transaction.dao.BuyMagazineRecordDao;
import com.ifenghui.storybookapi.app.transaction.dao.SubscriptionRecordDao;
import com.ifenghui.storybookapi.app.user.dao.UserDao;
import com.ifenghui.storybookapi.app.transaction.entity.subscription.BuyMagazineRecord;
import com.ifenghui.storybookapi.app.transaction.entity.subscription.SubscriptionRecord;
import com.ifenghui.storybookapi.app.story.entity.Magazine;
import com.ifenghui.storybookapi.app.story.entity.Story;
import com.ifenghui.storybookapi.app.user.entity.User;
import com.ifenghui.storybookapi.app.story.service.MagazineService;
import com.ifenghui.storybookapi.app.user.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.text.SimpleDateFormat;
import java.util.*;


/**
 * Created by wml on 2017/2/15.
 */
@Transactional
@Component
public class MagazineServiceImpl implements MagazineService {


    @Autowired
    MagazineDao magazineDao;
    @Autowired
    BuyMagazineRecordDao buyMagazineRecordDao;
    @Autowired
    SubscriptionRecordDao subscriptionRecordDao;

    @Autowired
    StoryDao storyDao;

    @Autowired
    UserService userService;

    @Transactional
    @Override
    public List<Magazine> getMagazineByIsNow(Integer isNow) {

        Integer status = 1;
        List<Magazine> magazines=magazineDao.getMagazinesByIsNow(isNow);

        return magazines;
    }
    @Override
    public Magazine getMagazineById(Long id) {

        Magazine magazine=this.magazineDao.findOne(id);

        return magazine;
    }
    @Transactional
    @Override
    public Page<BuyMagazineRecord> getBuyMagazineRecordByUserId(Long userId, Integer pageNo, Integer pageSize) {

        Pageable pageable = new PageRequest(pageNo, pageSize, new Sort(Sort.Direction.DESC,"id"));
        Page<BuyMagazineRecord> buyMagazineRecord=this.buyMagazineRecordDao.getRecordByUserId(userId,pageable);

        return buyMagazineRecord;
    }

    @Override
    public SubscriptionRecord getUserSubscribeInfo(Long userId) {
        Integer pageNo = 0;
        Integer pageSize = 1;//获取最后一条
        Pageable pageable = new PageRequest(pageNo, pageSize, new Sort(Sort.Direction.DESC,"id"));
        User user = userService.getUser(userId);
        Page<SubscriptionRecord> subscriptionRecords=this.subscriptionRecordDao.getSubscriptionRecordsByUserId(user,pageable);

        //获取当前时间对应的记录
        Date nowDate = new Date();
        Date stime = new Date();
//        SubscriptionRecord subsRecord=this.subscriptionRecordDao.getSubscriptionRecordsByUserIdAndTime(userId,nowDate);
        pageSize =100;
        pageable = new PageRequest(pageNo, pageSize, new Sort(Sort.Direction.DESC,"id"));
        Page<SubscriptionRecord> subscriptionRecords2=this.subscriptionRecordDao.getSubscriptionRecordsByUserId(user,pageable);
        for (SubscriptionRecord item:subscriptionRecords2.getContent()){
            if ((item.getEndTime().getTime()-nowDate.getTime()>0) && (item.getStartTime().getTime()-nowDate.getTime()<0)){
                stime = item.getStartTime();//获取开始时间
                continue;
            }
        }
        //zuzhaugn
        SubscriptionRecord subscriptionRecord = new SubscriptionRecord();
        for (SubscriptionRecord item:subscriptionRecords.getContent()){
            subscriptionRecord.setStartTime(stime);
            subscriptionRecord.setEndTime(item.getEndTime());
        }
        return subscriptionRecord;
    }

    @Override
    public SubscriptionRecord getUserLastSubscribeInfo(Long userId) {
        Integer pageNo = 0;
        Integer pageSize = 1;//获取最后一条
        Pageable pageable = new PageRequest(pageNo, pageSize, new Sort(Sort.Direction.DESC,"id"));
        User user = userService.getUser(userId);
        Page<SubscriptionRecord> subscriptionRecords=this.subscriptionRecordDao.getSubscriptionRecordsByUserId(user,pageable);
        //zuzhaugn
        SubscriptionRecord subscriptionRecord = new SubscriptionRecord();
        for (SubscriptionRecord item:subscriptionRecords.getContent()){
            subscriptionRecord.setStartTime(item.getStartTime());
            subscriptionRecord.setEndTime(item.getEndTime());
            this.formatSubscriptionRecord(item);
            if(item.getIntro().equals("故事飞船季度故事包")){
                subscriptionRecord.setIntro("季度故事包");
            }
            if(item.getIntro().equals("故事飞船半年故事包")){
                subscriptionRecord.setIntro("半年故事包");
            }
            if(item.getIntro().equals("故事飞船年度故事包")){
                subscriptionRecord.setIntro("年度故事包");
            }
            subscriptionRecord.setIntro(item.getIntro());
        }
        return subscriptionRecord;
    }
    @Override
    public GetUserSubscribeRecordsResponse getUserSubscribeRecords(Long userId){
        Integer pageNo = 0;
        Integer pageSize = 100;//获取最后一条
        Pageable pageable = new PageRequest(pageNo, pageSize, new Sort(Sort.Direction.DESC,"id"));
//        Page<SubscriptionRecord> subscriptionRecords=this.subscriptionRecordDao.getSubscriptionRecordsByUserId(userId,pageable);

        //获取当前时间对应的记录
        Date nowDate = new Date();
        Date stime = new Date();
//        SubscriptionRecord subsRecord=this.subscriptionRecordDao.getSubscriptionRecordsByUserIdAndTime(userId,nowDate);
//        pageSize =100;
        pageable = new PageRequest(pageNo, pageSize, new Sort(Sort.Direction.DESC,"id"));
        User user = userService.getUser(userId);
        Page<SubscriptionRecord> subscriptionRecords=this.subscriptionRecordDao.getSubscriptionRecordsByUserId(user,pageable);
        for (SubscriptionRecord item:subscriptionRecords.getContent()){
            if ((item.getEndTime().getTime()-nowDate.getTime()>0) && (item.getStartTime().getTime()-nowDate.getTime()<0)){
                stime = item.getStartTime();//获取开始时间
                continue;
            }
        }

        //获取当前订阅startTime 》= stime
        Sort sort = new Sort(Sort.Direction.DESC,"id");
        List<SubscriptionRecord> subscriptionRecords2 = this.subscriptionRecordDao.getSubscriptionCurrentRecords(user,stime,sort);
        for (SubscriptionRecord item:subscriptionRecords2){
            this.formatSubscriptionRecord(item);
        }
        //获取历史订阅startTime《stime
        List<SubscriptionRecord> subscriptionRecords3 = this.subscriptionRecordDao.getSubscriptionHistoryRecords(user,stime,sort);
        for (SubscriptionRecord item:subscriptionRecords3){
            this.formatSubscriptionRecord(item);
        }

        GetUserSubscribeRecordsResponse recordsResponse = new GetUserSubscribeRecordsResponse();
        recordsResponse.setCurrentSubscription(subscriptionRecords2);
        recordsResponse.setHistorySubscription(subscriptionRecords3);

        return recordsResponse;
    }
    private void formatSubscriptionRecord(SubscriptionRecord subscriptionRecord){
//        Long tmp = subscriptionRecord.getEndTime().getTime()-subscriptionRecord.getStartTime().getTime();
//        int months = tmp/(1000*60*60*24*30);

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM");
//        String str1 = "2012-02";
//        String str2 = "2010-01";
        Calendar bef = Calendar.getInstance();
        Calendar aft = Calendar.getInstance();
        bef.setTime(subscriptionRecord.getStartTime());
        aft.setTime(subscriptionRecord.getEndTime());
        int result = aft.get(Calendar.MONTH) - bef.get(Calendar.MONTH);
        int month = (aft.get(Calendar.YEAR) - bef.get(Calendar.YEAR)) * 12;
//        System.out.println("---------month----"+Math.abs(month + result));
        int months = Math.abs(month + result);
        String intro = "";
        if(months == 0){
            intro = "飞船阅读专区（当期）";
        }
        if(months == 1){
            intro = "飞船阅读专区（月度）";
        }

        if(months == 3){
            intro = "飞船阅读专区（季度）";
        }
        if(months == 6){
            intro = "飞船阅读专区（半年）";
        }
        if(months == 12){
            intro = "飞船阅读专区（年度）";
        }

        subscriptionRecord.setIntro(intro);
    }

    @Transactional
    @Override
    public Magazine setMagazineToNow(Long id) {

        Story storyNow=new Story();
        storyNow.setIsNow(1);
        //设置当期内容为往期
        Page<Story> magazineList;
        List<Magazine> magazines= magazineDao.findByIsNow(1);
        for(Magazine magazine:magazines){
            magazine.setIsNow(0);
            magazineDao.save(magazine);
//            magazineList= storyDao.getStorysByMagazineId(id,new PageRequest(1,50));
//            for(Story story:magazineList.getContent()){
//                story.setIsNow(0);
//                storyDao.save(story);
//            }

        }
        List<Story> stories= storyDao.findAll(Example.of(storyNow));
        for(Story story:stories){
            story.setIsNow(0);
            storyDao.save(story);
        }

        //设置为当期
        Magazine magazine=this.magazineDao.findOne(id);
        magazine.setIsNow(1);
        this.magazineDao.save(magazine);

        Story storyFind=new Story();
//        storyFind.setMagazine(magazine);
        List<Story> storyInMag= storyDao.findAll(Example.of(storyFind));

//        magazineList= storyDao.getStorysByMagazineId(id,new PageRequest(1,50));

        for(Story story:storyInMag){
            story.setIsNow(1);
            storyDao.save(story);
        }
        return magazine;
    }
    @Override
   public List<SubscriptionSchedule> getSubscriptionSchedules(String month){
        //查询此月所有排期
       // 获取当前年份、月份、日期
       SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
//       String str = "2017-08-00";
       String str = month + "-01";
       Date date = new Date();
       String day_first;
       String day_last;
       try{
            date = df.parse(str);

       }catch (Exception e){
//            throw new
       }
       Calendar calendar = Calendar.getInstance();
       calendar.setTime(date);
       calendar.add(Calendar.MONTH, 0);
       Date theDate = calendar.getTime();

       //上个月第一天
       GregorianCalendar gcLast = (GregorianCalendar) Calendar.getInstance();
       gcLast.setTime(theDate);
       gcLast.set(Calendar.DAY_OF_MONTH, 1);
       day_first = df.format(gcLast.getTime());
       StringBuffer str2 = new StringBuffer().append(day_first).append(" 00:00:00");
       day_first = str2.toString();
       //上个月最后一天
       calendar.add(Calendar.MONTH, 1);    //加一个月
       calendar.set(Calendar.DATE, 1);        //设置为该月第一天
       calendar.add(Calendar.DATE, -1);    //再减一天即为上个月最后一天
       day_last = df.format(calendar.getTime());
       StringBuffer endStr = new StringBuffer().append(day_last).append(" 23:59:59");
       day_last = endStr.toString();


       SimpleDateFormat df2 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
       Date startTime = new Date();
       Date endTime = new Date();
        try{
            startTime = df2.parse(day_first);
            endTime = df2.parse(day_last);
        }catch (Exception e){}

       List<Magazine> magazines = magazineDao.getSubscriptionSchedules(startTime,endTime);
       List<SubscriptionSchedule> subscriptionSchedules = new ArrayList<>();
        for (Magazine item:magazines){
            SubscriptionSchedule subscriptionSchedule = new SubscriptionSchedule();
            subscriptionSchedule.setTime(item.getPublishTime());
            subscriptionSchedule.setStatus(item.getStatus());
            subscriptionSchedules.add(subscriptionSchedule);
        }

        return subscriptionSchedules;
   }
}

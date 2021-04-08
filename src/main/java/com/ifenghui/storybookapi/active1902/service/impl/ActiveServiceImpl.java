package com.ifenghui.storybookapi.active1902.service.impl;

import com.ifenghui.storybookapi.active1902.dao.ScheduleDao;
import com.ifenghui.storybookapi.active1902.dao.UserAwardsDao;
import com.ifenghui.storybookapi.active1902.entity.Schedule;
import com.ifenghui.storybookapi.active1902.entity.UserAwards;
import com.ifenghui.storybookapi.active1902.service.ActiveService;
import com.ifenghui.storybookapi.app.story.controller.StoryController;
import com.ifenghui.storybookapi.app.transaction.dao.BuySerialStoryRecordDao;
import com.ifenghui.storybookapi.app.transaction.dao.BuyStoryRecordDao;
import com.ifenghui.storybookapi.app.transaction.entity.BuyStoryRecord;
import com.ifenghui.storybookapi.app.transaction.entity.serial.BuySerialStoryRecord;
import com.ifenghui.storybookapi.app.transaction.service.BuySerialStoryRecordService;
import com.ifenghui.storybookapi.app.transaction.service.BuyStoryRecordService;
import com.ifenghui.storybookapi.app.user.dao.UserDao;
import com.ifenghui.storybookapi.app.user.entity.User;
import com.ifenghui.storybookapi.app.user.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @Date: 2019/2/19 17:15
 * @Description:
 */
@Component
public class ActiveServiceImpl implements ActiveService {

    @Autowired
    UserDao userDao;
    @Autowired
    BuyStoryRecordDao buyStoryRecordDao;
    @Autowired
    BuySerialStoryRecordDao buySerialStoryRecordDao;
    @Autowired
    ScheduleDao scheduleDao;
    @Autowired
    UserAwardsDao userAwardsDao;

    @Autowired
    UserService userService;

    @Autowired
    BuyStoryRecordService buyStoryRecordService;

    @Autowired
    BuySerialStoryRecordService buySerialStoryRecordService;

    private  void addCount(Integer userId,Integer scheduleId){

        Schedule schedule = scheduleDao.findOne(scheduleId);
        int count = schedule.getFinish_num()+1;
        schedule.setFinish_num(count);
        scheduleDao.save(schedule);
    }

    @Override
    public void addBuyStoryRecord( String token,Integer userId,Integer storyId,Integer scheduleId) {

        //判断本期奖励是否领取
        UserAwards userAwards = userAwardsDao.findUserAwardsByUserIdAndScheduleId(userId, scheduleId);
        if(userAwards !=null){
            return;
        }

        User user = userDao.findOne(userId.longValue());
        /**
         * 判断是否已购买
         */
        List<BuyStoryRecord> buyStoryRecordLists = buyStoryRecordDao.getBuyStoryRecordsByUserIdAndStoryId(userId.longValue(), storyId.longValue());
        if (buyStoryRecordLists.size() == 0) {
            //添加过的数据不用添加
//            BuyStoryRecord buyStoryRecord = new BuyStoryRecord();
//            buyStoryRecord.setIsTest(user.getIsTest());
//            buyStoryRecord.setStoryId(storyId);
//            buyStoryRecord.setType(1);
//            buyStoryRecord.setUserId(userId);
//            buyStoryRecord.setCreateTime(new Date());
//            buyStoryRecordDao.save(buyStoryRecord);
//            this.addCount(userId,scheduleId);
//            buyStoryRecordService.addBuyStoryRecord(userId, item.getStoryId(), 1);
//            String buyStoryRecordUrl = MyEnv.env.getProperty("buyStoryRecordUrl");
////            String buySerialStoryRecordUrl = MyEnv.env.getProperty("buySerialStoryRecordUrl");
//            String param = "token=" + token
//                    + "&storyId=" + storyId
//                  ;
//            HttpRequest.sendPost(buyStoryRecordUrl, param);
//            Long userId;
//            if (token == null || token.length() <= 0) {
//                return;
//            } else {
//                userId = userService.checkAndGetCurrentUserId(token);
//            }
            buyStoryRecordService.addBuyStoryRecord(userId.longValue(),storyId.longValue(),4);
            this.addCount(userId,scheduleId);
        }
    }

    @Override
    public void addBuySerialStoryRecord( String token,Integer userId, Integer serialStoryId, Integer scheduleId) {

        //判断本期奖励是否领取
        UserAwards userAwards = userAwardsDao.findUserAwardsByUserIdAndScheduleId(userId, scheduleId);
        if(userAwards !=null){
            return;
        }

        User user = userDao.findOne(userId.longValue());

        List<BuySerialStoryRecord> buySerialStoryRecords = buySerialStoryRecordDao.getBuySerialStoryRecordsBySerialStoryIdAndUserId(serialStoryId, userId);

        if (buySerialStoryRecords.size() == 0) {
//            BuySerialStoryRecord buySerialStoryRecord = new BuySerialStoryRecord();
//            buySerialStoryRecord.setIsTest(user.getIsTest());
//            buySerialStoryRecord.setSerialStoryId(serialStoryId);
//            buySerialStoryRecord.setUserId(userId);
//            buySerialStoryRecord.setCreateTime(new Date());
//            buySerialStoryRecordDao.save(buySerialStoryRecord);


//            String buyStoryRecordUrl = MyEnv.env.getProperty("buyStoryRecordUrl");
//            String buySerialStoryRecordUrl = MyEnv.env.getProperty("buySerialStoryRecordUrl");
//            String param = "token=" + token
//                    + "&serialStoryId=" + serialStoryId
//                    ;
//            HttpRequest.sendPost(buySerialStoryRecordUrl, param);
//            remoteAppApiService.saveStorySerial(token,serialStoryId);
            buySerialStoryRecordService.addBuySerialStoryRecord(user,serialStoryId.intValue());
            this.addCount(userId,scheduleId);
        }
    }

}

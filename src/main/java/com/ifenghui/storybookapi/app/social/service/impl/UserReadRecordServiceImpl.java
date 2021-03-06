package com.ifenghui.storybookapi.app.social.service.impl;


import com.ifenghui.storybookapi.app.social.response.AddUserReadRecordResponse;
import com.ifenghui.storybookapi.app.social.response.TaskFinish;
import com.ifenghui.storybookapi.app.social.dao.UserReadRecordDao;
import com.ifenghui.storybookapi.app.social.dao.UserReadWordRecordDao;
import com.ifenghui.storybookapi.app.social.service.*;
import com.ifenghui.storybookapi.app.story.dao.StoryDao;
import com.ifenghui.storybookapi.app.story.service.StoryService;
import com.ifenghui.storybookapi.app.transaction.dao.BuyStoryRecordDao;
import com.ifenghui.storybookapi.app.transaction.service.lesson.BuyLessonItemRecordService;
import com.ifenghui.storybookapi.app.user.dao.UserDao;
import com.ifenghui.storybookapi.app.wallet.service.WalletService;
import com.ifenghui.storybookapi.config.StarConfig;

import com.ifenghui.storybookapi.app.social.entity.UserReadRecord;
import com.ifenghui.storybookapi.app.social.entity.UserReadWordRecord;
import com.ifenghui.storybookapi.app.story.entity.Story;
import com.ifenghui.storybookapi.app.user.entity.User;
import com.ifenghui.storybookapi.app.user.entity.UserExtend;
import com.ifenghui.storybookapi.exception.ApiNotFoundException;
import com.ifenghui.storybookapi.app.user.service.UserExtendService;
import com.ifenghui.storybookapi.app.user.service.UserService;
import com.ifenghui.storybookapi.app.wallet.service.UserStarRecordService;
import com.ifenghui.storybookapi.style.AddStyle;
import com.ifenghui.storybookapi.style.RangeTimeStyle;
import com.ifenghui.storybookapi.style.StarContentStyle;
import com.ifenghui.storybookapi.style.StarRechargeStyle;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Component;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
//import java.sql.Date;

/**
 * Created by jia on 2016/12/23.
 */
@Component
public class UserReadRecordServiceImpl implements UserReadRecordService {
    @Autowired
    UserReadRecordDao userReadRecordDao;
    @Autowired
    BuyStoryRecordDao buyStoryRecordDao;

//    @Autowired
//    ScheduleDao scheduleDao;
//
//    @Autowired
//    SchedulePayRecordDao schedulePayRecordDao;

    @Autowired
    StoryDao storyDao;
    @Autowired
    UserDao userDao;

    @Autowired
    UserService userService;

    @Autowired
    StoryService storyService;

//    @Autowired
//    ScheduleRecordService scheduleRecordService;

    @Autowired
    DayTaskService dayTaskService;

    @Autowired
    UserStarRecordService userStarRecordService;

//    @Autowired
//    SchedulePayRecordService schedulePayRecordService;

    @Autowired
    UserReadWordRecordService userReadWordRecordService;

    @Autowired
    UserExtendService userExtendService;

    @Autowired
    UserReadWordRecordDao userReadWordRecordDao;

    @Autowired
    WalletService walletService;

    @Autowired
    BuyLessonItemRecordService buyLessonItemRecordService;

    @Autowired
    UserReadRecordLogService userReadRecordLogService;

    @Autowired
    ReadPlanService readPlanService;

    @Override
    public Page<UserReadRecord> getReadRecordsByUserIdAndStoryId(Long userId,Long storyId, Integer pageNo, Integer pageSize) {

        Page<UserReadRecord> readRecords = this.getReadRecordsByUserIdAndStoryIdWithOutStory(userId, storyId, pageNo, pageSize);
        User user = userDao.findOne(userId);
//        List<BuyStoryRecord> buyStoryRecord ;
        Story story;
        for (UserReadRecord item :readRecords.getContent()) {
            story=storyDao.findOne(item.getStoryId());
            if(story!=null){
                storyService.setStoryIsBuy(user,story);
                item.setStory(story);
            }

        }
        return readRecords;
    }

    @Override
    public Long countReadRecordsByUserIdAndStoryId(Long userId, Long storyId) {
        return userReadRecordDao.countByUserIdAndStoryId(userId,storyId);
    }

    @Override
    public Page<UserReadRecord> getReadStoryRecordByUserIdAndType(Long userId,Integer type ,Integer pageNo, Integer pageSize) {

        Pageable pageable = new PageRequest(pageNo, pageSize, new Sort(Sort.Direction.DESC,"createTime","id"));
        Page<UserReadRecord> readRecordPage=userReadRecordDao.findReadRecordStoryByUserIdAndType(userId,type,pageable);

        User user = userDao.findOne(userId);
        Story story;
        for (UserReadRecord item :readRecordPage.getContent()) {
            story=storyDao.findOne(item.getStoryId());
            if(story!=null){
                storyService.setStoryAppFile(story);
                storyService.setStoryIsBuy(user,story);
                item.setStory(story);
            }
        }
        return readRecordPage;
    }

    @Override
    public Page<UserReadRecord> getReadRecordsByUserIdAndStoryIdWithOutStory(Long userId, Long storyId, Integer pageNo, Integer pageSize){
        Pageable pageable = new PageRequest(0, 1, new Sort(Sort.Direction.DESC,"createTime","id"));
//        UserReadRecord userReadRecordFind = new UserReadRecord();
//        userReadRecordFind.setUserId(userId);
//        userReadRecordFind.setStoryId(storyId);
        Page<UserReadRecord> readRecords=userReadRecordDao.getUserReadRecordByUserIdAndStoryId(userId,storyId,pageable);
        return readRecords;
    }

    @Override
    public Page<UserReadRecord> getReadRecordsByUserIdAndPage(Long userId,Integer type, Integer pageNo, Integer pageSize) {
        Pageable pageable = new PageRequest(pageNo, pageSize, new Sort(Sort.Direction.DESC,"createTime","id"));
//        UserReadRecord userReadRecordFind = new UserReadRecord();
//        userReadRecordFind.setUserId(userId);
//        userReadRecordFind.setType(type);
        Page<UserReadRecord> readRecords=userReadRecordDao.findReadRecordStoryByUserIdAndType(userId,type,pageable);
        User user = userDao.findOne(userId);
        Story story;
        for (UserReadRecord item :readRecords.getContent()) {
            story=storyDao.findOne(item.getStoryId());
            if(story!=null){
                storyService.setStoryAppFile(story);
                storyService.setStoryIsBuy(user,story);
                item.setStory(story);
            }

        }
        return readRecords;
    }

    @Override
    public UserReadRecord getFirstStoryReadRecordByUserId(Long userId, Integer type) {
        UserReadRecord userReadRecordFind = new UserReadRecord();
        userReadRecordFind.setUserId(userId);
        userReadRecordFind.setType(type);
        Pageable pageable = new PageRequest(0, 1, new Sort(Sort.Direction.ASC,"createTime","id"));
        Page<UserReadRecord> userReadRecordPage = userReadRecordDao.findReadRecordStoryByUserIdAndType(userId,type, pageable);
        if(userReadRecordPage.getContent().size() > 0){
            return userReadRecordPage.getContent().get(0);
        } else {
            return null;
        }
    }


    @Override
    public UserReadRecord getFirstReadRecordByUserId(Long userId) {
//        UserReadRecord userReadRecordFind = new UserReadRecord();
//        userReadRecordFind.setUserId(userId);
        Pageable pageable = new PageRequest(0, 1, new Sort(Sort.Direction.ASC,"createTime","id"));
        Page<UserReadRecord> userReadRecordPage = userReadRecordDao.getUserReadRecordByUserId(userId, pageable);
        if(userReadRecordPage.getContent().size() > 0){
            return userReadRecordPage.getContent().get(0);
        } else {
            return null;
        }
    }

    @Override
    public Long getFavoriteUserReadRecord(Long userId, Integer type){
        Long storyId = userReadRecordDao.getFavoriteUserReadRecord(userId,1);
        return storyId;
    }

    @Override
    public UserReadRecord addUserReadRecord(UserReadRecord userReadRecord){
        UserReadRecord userReadRecord1 = userReadRecordDao.save(userReadRecord);
        return  userReadRecord1;
    }

//    public AddUserReadRecordResponse addUserReadRecord(Long userId, Long storyId){
//        User user=this.userService.getUser(userId);
//        Integer userReadCount = user.getReadCount();
//        //????????????
//        Story story = storyDao.findOne(storyId);
//        Integer type = 1;
//        if(story.getType() == 2 || story.getType()==3){//??????+??????
//            type = story.getType();
//        }
////        ??????????????????????????????????????????????????????????????????????????????
//        Integer pageNo = 0;
//        Integer pageSize = 100;
//        Page<UserReadRecord> userReadRecords = this.getReadRecordsByUserIdAndStoryId(userId,storyId,pageNo,pageSize);
//        if (userReadRecords.getContent().size() > 0){
//            Date recordTime = userReadRecords.getContent().get(0).getCreateTime();
//            //????????????
//            Date now = new Date();
//            SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd");
//            String recordDay = sf.format(recordTime);
//            //?????????????????????
//            String nowDay = sf.format(now);
//            //???????????????
//            if(recordDay.equals(nowDay)){
//                //???????????????????????????
//                for (UserReadRecord item:userReadRecords.getContent()){
//                    this.delUserReadRecord(item.getId());
//                }
//            }
//        }else{
//            //??????????????????????????????+1
//            if(type == 1){
//                user.setReadCount(user.getReadCount()+1);
//            }
//        }
//        //?????????????????????????????????????????????
//        userService.updateUser(user);
//
//        UserReadRecord userReadRecord = new UserReadRecord();
//
//        userReadRecord.setStoryId(storyId);
//        userReadRecord.setUserId(userId);
//        userReadRecord.setType(type);//1??????2??????3??????
//        userReadRecord.setCreateTime(new Date());
//        this.addUserReadRecord(userReadRecord);
//
//        Integer isFinish = 0;
//        Page<DayTask> dayTaskPage = dayTaskService.getDayTaskByType(userId,type);
//        if(dayTaskPage.getContent().size()>0){
//            if(dayTaskPage.getContent().get(0).getStoryId().intValue() == storyId.intValue() && dayTaskPage.getContent().get(0).getStatus()==0){
//                dayTaskService.addDayTaskRecord(userId,dayTaskPage.getContent().get(0).getId());
//                isFinish =1;
//            }
//        }
//        //?????????????????????
//        String intro = "";//????????????
//        Integer buyType = 13;//????????????
//        Integer num = 0;
//        if(userReadCount != user.getReadCount().intValue()){//?????????????????????????????????????????????
//            switch(user.getReadCount()){
//                case 10:
//                    num=10;
//                    userStarRecordService.addUserStarRecord(userId, StarConfig.STAR_READ_TEN,1,buyType,"????????????10???");
//                    intro = "???????????????????????????"+user.getReadCount()+"???????????????\n??????"+num+"????????????";
//                    break;
//                case 20:
//                    num=20;
//                    userStarRecordService.addUserStarRecord(userId, StarConfig.STAR_READ_TWENTY,1,buyType,"????????????20???");
//                    intro = "???????????????????????????"+user.getReadCount()+"???????????????\n??????"+num+"????????????";
//                    break;
//                case 50:
//                    num=50;
//                    userStarRecordService.addUserStarRecord(userId, StarConfig.STAR_READ_FIFTY,1,buyType,"????????????50???");
//                    intro = "???????????????????????????"+user.getReadCount()+"???????????????\n??????"+num+"????????????";
//                    break;
//                case 100:
//                    num=100;
//                    userStarRecordService.addUserStarRecord(userId, StarConfig.STAR_READ_HUNDRED,1,buyType,"????????????100???");
//                    intro = "???????????????????????????"+user.getReadCount()+"???????????????\n??????"+num+"????????????";
//                    break;
//            }
//        }
//
//        AddUserReadRecordResponse readRecordResponse=new AddUserReadRecordResponse();
//        List<TaskFinish> taskFinishes = new ArrayList<>();
//        //????????????
//        if(!intro.equals("") && type ==1){
//            TaskFinish taskFinish = new TaskFinish();
//            taskFinish.setIntro(intro);
//            taskFinishes.add(taskFinish);
//        }
//
//
//        //?????????????????????????????????/?????????????????????
//        if(isFinish == 1){
//            if(type ==1){
//                intro = "?????????????????????????????????\n??????"+StarConfig.STAR_TASK_READ+"????????????";
//            }else if(type == 2){
//                intro = "????????????????????????????????????\n??????"+StarConfig.STAR_TASK_LISTEN+"????????????";
//            }
//            TaskFinish taskFinish = new TaskFinish();
//            taskFinish.setIntro(intro);
//            taskFinishes.add(taskFinish);
//        }
//
//        //??????????????????????????????
//        Schedule scheduleFind = new Schedule();
//        scheduleFind.setTargetType(1);//???????????????
//        scheduleFind.setTargetValue(storyId.toString());
//        List<Schedule> schedulesList = scheduleDao.findAll(Example.of(scheduleFind));
//        Date nowDate =  new java.sql.Date(System.currentTimeMillis());
//        ScheduleRecord scheduleRecord;
//        Boolean isSchedule = false;
//        for (Schedule item:schedulesList){
//            if(item.getPublishTime().toString().equals(nowDate.toString())){
//                //?????????????????????story_schedule_pay_record??????????????????????????????????????????
////                scheduleRecord = scheduleRecordService.addOrEditScheduleRecordByScheduleIdAndUserId(item.getId(),userId);
//                isSchedule = true;
//                try{
//                    scheduleRecord = scheduleRecordService.addOrEditScheduleRecordByScheduleIdAndUserId(item.getId(),userId);
//
//                    if(scheduleRecord != null && scheduleRecord.getTaskFinish()!=null){
//                        taskFinishes.add(scheduleRecord.getTaskFinish());//??????????????????
//                    }
//                }catch (Exception e){
//                    e.printStackTrace();
//                }
//            }else {
//                //?????????????????????????????????
//                SchedulePayRecord schedulePayRecord =schedulePayRecordService.findOneTodaySchedulePayRecord(userId.intValue(),item.getId().intValue());
//                if(schedulePayRecord == null){
//                    continue;
//                }
//                isSchedule = true;
//                //??????????????????
//                try{
//                    scheduleRecord = scheduleRecordService.addOrEditScheduleRecordByScheduleIdAndUserId(item.getId(),userId);
//                    if(scheduleRecord != null && scheduleRecord.getTaskFinish()!=null){
//                        taskFinishes.add(scheduleRecord.getTaskFinish());//??????????????????
//                    }
//                }catch (Exception e){
//                    e.printStackTrace();
//                }
//            }
//
//        }
//
//
//        readRecordResponse.setTaskFinishInfo(taskFinishes);
//        readRecordResponse.setSchedule(isSchedule);
//        return readRecordResponse;
//    }

    @Override
    public AddUserReadRecordResponse addUserReadRecord(Long userId, Long storyId) throws ParseException {
        User user=this.userService.getUser(userId);
        Integer userReadCount = user.getReadCount();
        //????????????
        Story story = storyDao.findOne(storyId);

        if(story == null){
            throw new ApiNotFoundException("????????????????????????????????????????????????");
        }

        Integer type = story.getType();
        userReadRecordLogService.addUserReadRecordLog(storyId.intValue(), userId.intValue(), type);

        if(story.getType().equals(4)){
            type = 1;
        }



        /**
         * ??????????????????????????????????????????????????????????????????????????????
         */
        Integer pageNo = 0;
        Integer pageSize = 100;
        Page<UserReadRecord> userReadRecords = this.getReadRecordsByUserIdAndStoryId(userId,storyId,pageNo,pageSize);

        /**
         * ??????????????????
         */
        Date now = new Date();
        SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd");

        /**
         * ?????????????????????
         */
        String nowDay = sf.format(now);

        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
        Date beginTime = simpleDateFormat.parse(nowDay + " 00:00:00");
        RangeTimeStyle rangeTimeStyle=RangeTimeStyle.CURRENT_DAY;
        String intro = "";
        /**
         * ?????????????????????????????????????????????40
         */
        Integer buyType = 40 + type;

        if(type.equals(1) || type.equals(4)){
            Integer storyReadCountToday =
                    this.getReadRecordCountNumber(userId,1,rangeTimeStyle) +
                            this.getReadRecordCountNumber(userId, 4,rangeTimeStyle);
            if(storyReadCountToday < 1){
                intro = "???????????????????????????????????????\n??????" + StarConfig.STAR_READ_STORY + "????????????";
//                userStarRecordService.addUserStarRecord(userId, StarConfig.STAR_READ_STORY, AddStyle.UP, StarRechargeStyle.READ_STORY2, "?????????????????????????????????");
                //?????????????????????????????????
                walletService.addStarToWallet(userId.intValue(),StarRechargeStyle.READ_STORY2, StarConfig.STAR_READ_STORY, StarContentStyle.HUIBEN_READ.getName());
            }
        }

        if(type.equals(6) || type.equals(7) || type.equals(8)){
            Integer storyReadCountToday =
//                    this.getReadRecordCountNumber(userId,5,beginTime, now) +
                            this.getReadRecordCountNumber(userId,6,rangeTimeStyle) +
                            this.getReadRecordCountNumber(userId,7,rangeTimeStyle) +
                            this.getReadRecordCountNumber(userId, 8, rangeTimeStyle);
            if(storyReadCountToday < 1){
                intro = "?????????????????????????????????\n??????" + StarConfig.STAR_READ_SMART_GAME + "????????????";
//                userStarRecordService.addUserStarRecord(userId, StarConfig.STAR_READ_SMART_GAME, AddStyle.UP, StarRechargeStyle.getById(buyType), "???????????????????????????");
                //intro  ???????????????????????????
                walletService.addStarToWallet(userId.intValue(),StarRechargeStyle.getById(buyType), StarConfig.STAR_READ_SMART_GAME,StarContentStyle.GAME.getName());
            }
        }

        if(type.equals(5)){
            UserReadWordRecord userReadWordRecord = userReadWordRecordService.addUserReadRecord(userId.intValue(),storyId.intValue(), story.getWordCount(), story.getVocabularyCount());
            Integer userStoryWordCount = userReadWordRecordDao.getUserReadWordRecordWordCount(userId.intValue());
            if(userStoryWordCount == null){
                userStoryWordCount = 0;
            }

            Integer userStoryVocabularyCount = userReadWordRecordDao.getUserReadWordRecordVocabularyCount(userId.intValue());
            if(userStoryVocabularyCount == null){
                userStoryVocabularyCount = 0;
            }
            UserExtend userExtend = userExtendService.changeUserExtend(userId,userStoryWordCount,userStoryVocabularyCount);
        }

        if (userReadRecords.getContent().size() > 0){
            Date recordTime = userReadRecords.getContent().get(0).getCreateTime();
            String recordDay = sf.format(recordTime);
            /**
             * ???????????????
             */
            if(recordDay.equals(nowDay)){
                /**
                 * ???????????????????????????
                 */
//                for (UserReadRecord item:userReadRecords.getContent()){
//                    this.delUserReadRecord(item.getId());
//                }
                UserReadRecord userReadRecord = userReadRecords.getContent().get(0);
                userReadRecord.setCreateTime(now);
                userReadRecordDao.save(userReadRecord);
            } else {

                /**
                 * ?????????????????????????????????????????????
                 */
                userService.updateUser(user);
                UserReadRecord userReadRecord = new UserReadRecord();
                userReadRecord.setStoryId(storyId);
                userReadRecord.setUserId(userId);
                /**
                 * 1?????? 2?????? 3??????  5??????????????? 6?????? 7????????? 8????????????
                 */
                userReadRecord.setType(type);
                userReadRecord.setCreateTime(new Date());
                this.addUserReadRecord(userReadRecord);
            }
        } else {
            /**
             * ??????????????????????????????+1
             */
            if (type == 1) {
                user.setReadCount(user.getReadCount() + 1);
            }

            UserReadRecord userReadRecord = new UserReadRecord();
            userReadRecord.setStoryId(storyId);
            userReadRecord.setUserId(userId);
            userReadRecord.setType(type);
            userReadRecord.setCreateTime(new Date());
            this.addUserReadRecord(userReadRecord);
        }

        AddUserReadRecordResponse readRecordResponse = new AddUserReadRecordResponse();
        List<TaskFinish> taskFinishes = new ArrayList<>();
        //????????????
        if(!intro.equals("")){
            TaskFinish taskFinish = new TaskFinish();
            taskFinish.setIntro(intro);
            taskFinishes.add(taskFinish);
        }

        readRecordResponse.setTaskFinishInfo(taskFinishes);
        readRecordResponse.setSchedule(false);


        buyLessonItemRecordService.setStoryOfLessonItemIsRead(storyId, userId, false);

        if(story.getType().equals(1) || story.getType().equals(4) || story.getType().equals(5)){
            readPlanService.createUserReadPlanRecord(userId.intValue());
        }

        return readRecordResponse;
    }

    private Integer getReadRecordCountNumber(Long userId, Integer type, RangeTimeStyle rangeTimeStyle){
        Long readRecordCount=0L;

        if(rangeTimeStyle==RangeTimeStyle.All_DAY){
            readRecordCount =  userReadRecordDao.getReadRecordCountNumberAllDay(userId, type, rangeTimeStyle.getBeginDate(), rangeTimeStyle.getEndDate());
        }else if(rangeTimeStyle==RangeTimeStyle.CURRENT_DAY){
            readRecordCount =  userReadRecordDao.getReadRecordCountNumberCurrentDay(userId, type, rangeTimeStyle.getBeginDate(), rangeTimeStyle.getEndDate());
        }
//        Long readRecordCount =  userReadRecordDao.getReadRecordCountNumber(userId, type, beginTime, endTime);
        if(readRecordCount == null){
            readRecordCount = 0L;
        }
        return readRecordCount.intValue();
    }

    @Override
    public void delUserReadRecord(Long id){
        userReadRecordDao.delete(id);
        return;
    }



}

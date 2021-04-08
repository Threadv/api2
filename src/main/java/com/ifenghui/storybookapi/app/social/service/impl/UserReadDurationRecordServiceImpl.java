package com.ifenghui.storybookapi.app.social.service.impl;

import com.ifenghui.storybookapi.app.social.response.ReadDurationRecordResponse;
import com.ifenghui.storybookapi.app.social.dao.UserReadDurationRecordDao;
import com.ifenghui.storybookapi.app.social.dao.UserReadRecordDao;
import com.ifenghui.storybookapi.app.social.dao.UserReadWordRecordDao;
import com.ifenghui.storybookapi.app.social.entity.UserReadDurationRecord;
import com.ifenghui.storybookapi.app.story.entity.Story;
import com.ifenghui.storybookapi.app.user.entity.UserExtend;
import com.ifenghui.storybookapi.exception.ApiNotFoundException;
import com.ifenghui.storybookapi.app.social.service.DayReadStatistic;
import com.ifenghui.storybookapi.app.story.service.StoryService;
import com.ifenghui.storybookapi.app.social.service.TotalReadStatistic;
import com.ifenghui.storybookapi.app.social.service.UserReadDurationRecordService;
import com.ifenghui.storybookapi.app.user.service.UserExtendService;
import com.ifenghui.storybookapi.style.RangeTimeStyle;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

@Component
public class UserReadDurationRecordServiceImpl implements UserReadDurationRecordService {

    @Autowired
    StoryService storyService;

    @Autowired
    UserReadDurationRecordDao userReadDurationRecordDao;

    @Autowired
    UserReadRecordDao userReadRecordDao;

    @Autowired
    UserReadWordRecordDao userReadWordRecordDao;

    @Autowired
    UserExtendService userExtendService;

    @Override
    public UserReadDurationRecord addUserDurationRecord(Long storyId, Long userId, Integer duration) {
        Story story = storyService.getStory(storyId);
        if(story == null){
            throw new ApiNotFoundException("未找到当前故事！");
        }
        UserReadDurationRecord userReadDurationRecord = new UserReadDurationRecord();
        userReadDurationRecord.setCreateTime(new Date());
        userReadDurationRecord.setDuration(duration);
        userReadDurationRecord.setUserId(userId.intValue());
        userReadDurationRecord.setStoryId(storyId.intValue());
        if(story.getType().equals(4)){
            userReadDurationRecord.setType(1);
        } else {
            userReadDurationRecord.setType(story.getType());
        }

        userReadDurationRecord = userReadDurationRecordDao.save(userReadDurationRecord);
        return userReadDurationRecord;
    }

    @Override
    public DayReadStatistic getReadStatistic(Long userId) throws ParseException {

//        Calendar calendarEnd=Calendar.getInstance();
//        calendarEnd.set(Calendar.HOUR_OF_DAY,23);
//        calendarEnd.set(Calendar.MINUTE,59);
//        calendarEnd.set(Calendar.SECOND,59);
//        Date endTime = calendarEnd.getTime();
//        SimpleDateFormat formatOne = new SimpleDateFormat("yyyy-MM-dd 00:00:00");
//        String beginTimeString = formatOne.format(endTime);
//        Date beginTime = formatOne.parse(beginTimeString);
        DayReadStatistic dayReadStatistic = this.getDayStatisticByTimeAndUserId(userId,RangeTimeStyle.CURRENT_DAY);
        return dayReadStatistic;
    }

    private DayReadStatistic getDayStatisticByTimeAndUserId(Long userId, RangeTimeStyle rangeTimeStyle){
        DayReadStatistic dayReadStatistic = new DayReadStatistic();

        /**
         * 设置益智游戏类的时长 和 次数
         */
        Integer dayGameStoryDuration =
//                this.getDurationCountNumber(userId.intValue(), 5, beginTime, endTime) +
                this.getDurationCountNumber(userId.intValue(), 6, rangeTimeStyle) +
                this.getDurationCountNumber(userId.intValue(), 7, rangeTimeStyle) +
                this.getDurationCountNumber(userId.intValue(), 8, rangeTimeStyle);
        dayReadStatistic.setDayGameStoryDuration(dayGameStoryDuration);

        Integer dayGameStoryNumber =
//                this.getReadRecordCountNumber(userId, 5, beginTime, endTime) +
                this.getReadRecordCountNumber(userId, 6, rangeTimeStyle) +
                this.getReadRecordCountNumber(userId, 7, rangeTimeStyle) +
                this.getReadRecordCountNumber(userId, 8, rangeTimeStyle);
        dayReadStatistic.setDayGameStoryNumber(dayGameStoryNumber);

        /**
         * 设置听音频类的时长 和 次数
         */
        dayReadStatistic.setDayListenStoryDuration(
                this.getDurationCountNumber(userId.intValue(), 2, rangeTimeStyle)
        );
        dayReadStatistic.setDayListenStoryNumber(
                this.getReadRecordCountNumber(userId, 2, rangeTimeStyle)
        );

        /**
         * 设置阅读故事时长 和 次数
         */
        dayReadStatistic.setDayReadStoryDuration(
                this.getDurationCountNumber(userId.intValue(),1, rangeTimeStyle)
        );
        dayReadStatistic.setDayReadStoryNumber(
                this.getReadRecordCountNumber(userId, 1, rangeTimeStyle)
        );

        /**
         * 识字游戏统一字数数量
         */
//        Integer wordCount = userReadWordRecordDao.getUserReadWordCountNumber(userId.intValue(), rangeTimeStyle.getBeginDate(), rangeTimeStyle.getEndDate());

        Integer wordCount=0;
        Integer vocabularyCount=0;
        if(rangeTimeStyle==RangeTimeStyle.All_DAY){
            vocabularyCount = userReadWordRecordDao.getUserReadVocabularyCountNumberAllDay(userId.intValue(), rangeTimeStyle.getBeginDate(), rangeTimeStyle.getEndDate());
            wordCount = userReadWordRecordDao.getUserReadWordCountNumberAllDay(userId.intValue(), rangeTimeStyle.getBeginDate(), rangeTimeStyle.getEndDate());
        }else if (rangeTimeStyle==RangeTimeStyle.CURRENT_DAY){
            vocabularyCount = userReadWordRecordDao.getUserReadVocabularyCountNumberCurrentDay(userId.intValue(), rangeTimeStyle.getBeginDate(), rangeTimeStyle.getEndDate());
            wordCount = userReadWordRecordDao.getUserReadWordCountNumberCurrentDay(userId.intValue(), rangeTimeStyle.getBeginDate(), rangeTimeStyle.getEndDate());
        }
//        Integer vocabularyCount = userReadWordRecordDao.getUserReadVocabularyCountNumber(userId.intValue(), rangeTimeStyle.getBeginDate(), rangeTimeStyle.getEndDate());

        if(wordCount == null){
            wordCount = 0;
        }
        if(vocabularyCount == null){
            vocabularyCount = 0;
        }

        dayReadStatistic.setVocabularyCount(vocabularyCount);
        dayReadStatistic.setWordCount(wordCount);
        return dayReadStatistic;
    }

    private Integer getDurationCountNumber(Integer userId, Integer type, RangeTimeStyle rangeTimeStyle){
//        Integer durationCountNumber = userReadDurationRecordDao.getDurationCountNumber(userId, type, rangeTimeStyle.getBeginDate(), rangeTimeStyle.getEndDate());
        Integer durationCountNumber=0;
        if(rangeTimeStyle==RangeTimeStyle.All_DAY){
            Integer recordCount=userReadDurationRecordDao.countDurationCountNumberAllDay(userId, type, rangeTimeStyle.getBeginDate(),rangeTimeStyle.getEndDate());
            if(recordCount!=0){
                durationCountNumber = userReadDurationRecordDao.sumDurationCountNumberAllDay(userId, type, rangeTimeStyle.getBeginDate(),rangeTimeStyle.getEndDate());
            }

        }else if(rangeTimeStyle==RangeTimeStyle.CURRENT_DAY){
            Integer recordCount = userReadDurationRecordDao.countDurationCountNumberCurrentDay(userId, type, rangeTimeStyle.getBeginDate(),rangeTimeStyle.getEndDate());
            if(recordCount!=0){
                durationCountNumber = userReadDurationRecordDao.sumDurationCountNumberCurrentDay(userId, type, rangeTimeStyle.getBeginDate(),rangeTimeStyle.getEndDate());
            }

        }
//        Integer durationCountNumber = userReadDurationRecordDao.getDurationCountNumber(userId, type, rangeTimeStyle);
        if(durationCountNumber == null){
            durationCountNumber = 0;
        }
        return durationCountNumber;
    }

    private Integer getReadRecordCountNumber(Long userId, Integer type, RangeTimeStyle rangeTimeStyle){
        Integer readRecordCountNumber=0;
        if(rangeTimeStyle==RangeTimeStyle.CURRENT_DAY){
            readRecordCountNumber = userReadRecordDao.getReadRecordCountNumberCurrentDay(userId, type, rangeTimeStyle.getBeginDate(), rangeTimeStyle.getEndDate()).intValue();
        }else if(rangeTimeStyle==RangeTimeStyle.All_DAY){
            readRecordCountNumber = userReadRecordDao.getReadRecordCountNumberAllDay(userId, type, rangeTimeStyle.getBeginDate(), rangeTimeStyle.getEndDate()).intValue();
        }
//        Integer readRecordCountNumber = userReadRecordDao.getReadRecordCountNumber(userId, type, rangeTimeStyle.getBeginDate(), rangeTimeStyle.getEndDate()).intValue();
        if(readRecordCountNumber == null){
            readRecordCountNumber = 0;
        }
        return readRecordCountNumber;
    }

    @Override
    public TotalReadStatistic getTotalReadStatistic(Long userId) throws ParseException {
//        Calendar calendarEnd=Calendar.getInstance();
//        calendarEnd.set(Calendar.HOUR_OF_DAY,23);
//        calendarEnd.set(Calendar.MINUTE,59);
//        calendarEnd.set(Calendar.SECOND,59);
//        Date endTime = calendarEnd.getTime();
//
////        Date endTime = new Date();
//        SimpleDateFormat formatTwo = new SimpleDateFormat("yyyy-MM-dd 00:00:00");
//        Date beginTime = formatTwo.parse("2017-06-01 00:00:00");
        DayReadStatistic dayReadStatistic = this.getDayStatisticByTimeAndUserId(userId,RangeTimeStyle.All_DAY);

        TotalReadStatistic totalReadStatistic = new TotalReadStatistic();

        totalReadStatistic.setTotalGameStoryDuration(dayReadStatistic.getDayGameStoryDuration());
        totalReadStatistic.setTotalGameStoryNumber(dayReadStatistic.getDayGameStoryNumber());
        totalReadStatistic.setTotalListenStoryDuration(dayReadStatistic.getDayListenStoryDuration());
        totalReadStatistic.setTotalListenStoryNumber(dayReadStatistic.getDayListenStoryNumber());
        totalReadStatistic.setTotalReadStoryDuration(dayReadStatistic.getDayReadStoryDuration());
        totalReadStatistic.setTotalReadStoryNumber(dayReadStatistic.getDayReadStoryNumber());

        /**
         * 识字游戏统一字数数量
         */
        UserExtend userExtend = userExtendService.findUserExtendByUserId(userId);
        Integer wordCount = 0;
        Integer vocabularyCount = 0;
        if(userExtend != null){
            wordCount = userExtend.getWordCount();
            vocabularyCount = userExtend.getVocabularyCount();
        }

        totalReadStatistic.setVocabularyCount(vocabularyCount);
        totalReadStatistic.setWordCount(wordCount);
        return totalReadStatistic;
    }

    @Override
    public void addReadDurationRecordList(List<ReadDurationRecordResponse> readDurationRecordResponseList, Long userId) {

        SimpleDateFormat simpleDateFormat = new SimpleDateFormat( "yyyy-MM-dd" );
        for (ReadDurationRecordResponse item:readDurationRecordResponseList){
            try {
                Date createTime = simpleDateFormat.parse(item.getTime());
                UserReadDurationRecord userReadDurationRecord = this.addUserDurationRecord(item.getStoryId().longValue(), userId, item.getDuration());
                userReadDurationRecord.setCreateTime(createTime);
                userReadDurationRecordDao.save(userReadDurationRecord);
            } catch (Exception e) {

            }
        }
    }
}

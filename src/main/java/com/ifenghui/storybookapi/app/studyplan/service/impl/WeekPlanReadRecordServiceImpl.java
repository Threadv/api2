package com.ifenghui.storybookapi.app.studyplan.service.impl;

import com.ifenghui.storybookapi.app.social.dao.UserReadRecordDao;
import com.ifenghui.storybookapi.app.social.entity.UserReadRecord;
import com.ifenghui.storybookapi.app.story.dao.StoryDao;
import com.ifenghui.storybookapi.app.story.entity.Story;
import com.ifenghui.storybookapi.app.studyplan.dao.WeekPlanReadRecordDao;
import com.ifenghui.storybookapi.app.studyplan.entity.WeekPlanLabel;
import com.ifenghui.storybookapi.app.studyplan.entity.WeekPlanLabelStory;
import com.ifenghui.storybookapi.app.studyplan.entity.WeekPlanReadRecord;
import com.ifenghui.storybookapi.app.studyplan.response.GetStatisticWeekPlanLabelResponse;
import com.ifenghui.storybookapi.app.studyplan.response.StatisticWeekPlanLabel;
import com.ifenghui.storybookapi.app.studyplan.service.WeekPlanLabelService;
import com.ifenghui.storybookapi.app.studyplan.service.WeekPlanLabelStoryService;
import com.ifenghui.storybookapi.app.studyplan.service.WeekPlanReadRecordService;
import com.ifenghui.storybookapi.app.wallet.controller.PayController;
import com.ifenghui.storybookapi.style.LabelTargetStyle;
import com.ifenghui.storybookapi.style.ReadRecordTypeStyle;
import com.ifenghui.storybookapi.style.WeekPlanLabelStyle;
import com.ifenghui.storybookapi.style.WeekPlanStyle;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

@Component
public class WeekPlanReadRecordServiceImpl implements WeekPlanReadRecordService {

    @Autowired
    WeekPlanReadRecordDao weekPlanReadRecordDao;

    @Autowired
    WeekPlanLabelStoryService weekPlanLabelStoryService;

    @Autowired
    WeekPlanLabelService weekPlanLabelService;

    @Autowired
    UserReadRecordDao userReadRecordDao;

    @Autowired
    StoryDao storyDao;

    private static Logger logger = Logger.getLogger(WeekPlanReadRecordServiceImpl.class);
    /**
     * 修改week_plan_read_record数据
     * @param count
     */
    @Override
    public void updateWeekPlanDate(Integer count) {

        int id1 = 0;
        int id2 = 0;

        for (int i = 0; i <count ; i++) {
            id1 = i * 100;
            id2 = (i + 1) * 100;
            logger.info("-------------查找数据(修改week_plan_read_record 数据)--------------");
            logger.info("循环次数：第"+i+"次  共"+count+"次");
            logger.info("id1="+id1 +"id2= "+id2);
            List<WeekPlanReadRecord> weekPlanReadRecordList = weekPlanReadRecordDao.getAllByReadType(id1, id2);
            for (WeekPlanReadRecord r:weekPlanReadRecordList) {

                Story story = storyDao.findOne(r.getTargetValue().longValue());
                //故事和互动故事类型设置为isStory=1
                if(story.getType()==1 || story.getType()==4){

                    r.setIsStory(1);
                    weekPlanReadRecordDao.save(r);
                }
            }
        }
    }

    /**
     * 冗余数据 user_read_record -> weekplanreadrecord
     * @param count
     */
    @Override
    public void updateDate(Integer count,Integer first) {

        int id1 = 0;
        int id2 = 0;

        for (int i = first; i <count ; i++) {
            id1=i*100;
            id2=(i+1)*100;
            logger.info("-----start ReadRecordToWeekPlanReadRecord--------查找数据（read_record -> week_plan_read_record）-----  ---------"+"循环次数：第"+i+"次  共"+count+"次");
            logger.info("循环次数：第"+i+"次  共"+count+"次");
            logger.info("id1="+id1 +"id2= "+id2);
            List<UserReadRecord> userReadRecordList = userReadRecordDao.getAll(id1,id2);
            for (UserReadRecord record: userReadRecordList) {

                WeekPlanReadRecord rec = weekPlanReadRecordDao.getWeekPlanReadRecordByUserIdAndPlanTypeAndTargetValue(record.getUserId().intValue(),WeekPlanStyle.ALL_PLAN.getId(),record.getStoryId().intValue());
                if(rec!=null){

                    Story story = storyDao.findOne(record.getStoryId());
                    //故事是否以删除
                    if(story==null||story.getIsDel()==1){
                        continue;
                    }
                    System.out.println(1);
                    //添加create_time
                    rec.setCreateTime(record.getCreateTime());
                    rec.setWordCount(story.getWordCount());
                    weekPlanReadRecordDao.save(rec);
                    continue;
                }
                Story story = storyDao.findOne(record.getStoryId());
                //故事是否以删除
                if(story==null||story.getIsDel()==1){
                    continue;
                }

                WeekPlanReadRecord wr = new WeekPlanReadRecord();
                wr.setWordCount(story.getWordCount());
                if (story.getType() ==1 || story.getType()==4) {
                    wr.setIsStory(1);
                }else {
                    wr.setIsStory(0);
                }
                wr.setTargetValue(record.getStoryId().intValue());
                wr.setLabelTargetType(LabelTargetStyle.Story);
                wr.setReadType(ReadRecordTypeStyle.STORY);
                wr.setPlanType(WeekPlanStyle.ALL_PLAN);
                wr.setUserId(record.getUserId().intValue());
                wr.setCreateTime(record.getCreateTime());
                weekPlanReadRecordDao.save(wr);
            }
        }
    }



    @Override
    public WeekPlanReadRecord addWeekPlanReadRecord(Integer targetValue, Integer userId, Integer wordCount, WeekPlanStyle weekPlanStyle, LabelTargetStyle labelTargetStyle, ReadRecordTypeStyle recordTypeStyle, Integer isStory) {
        WeekPlanReadRecord weekPlanReadRecord = new WeekPlanReadRecord();
        weekPlanReadRecord.setLabelTargetType(labelTargetStyle);
        weekPlanReadRecord.setCreateTime(new Date());
        weekPlanReadRecord.setPlanType(weekPlanStyle);
        weekPlanReadRecord.setUserId(userId);
        weekPlanReadRecord.setWordCount(wordCount);
        weekPlanReadRecord.setTargetValue(targetValue);
        weekPlanReadRecord.setReadType(recordTypeStyle);
        weekPlanReadRecord.setIsStory(isStory);
        return weekPlanReadRecordDao.save(weekPlanReadRecord);
    }

    @Override
    public WeekPlanReadRecord findOneByTargetValueAndUserIdAndPlanTypeAndTargetType(Integer targetValue, Integer userId, WeekPlanStyle weekPlanStyle, LabelTargetStyle labelTargetStyle) {
        return weekPlanReadRecordDao.getDistinctByUserIdAndTargetTypeAndTargetValueAndPlanType(userId, labelTargetStyle.getId(), targetValue, weekPlanStyle.getId());
    }

    @Override
    public void createWeekPlanReadRecord(Integer targetValue, Integer userId, Integer wordCount, WeekPlanStyle weekPlanStyle, LabelTargetStyle labelTargetStyle, ReadRecordTypeStyle recordTypeStyle, Integer isStory) {
        WeekPlanReadRecord weekPlanReadRecord = this.findOneByTargetValueAndUserIdAndPlanTypeAndTargetType(targetValue, userId, weekPlanStyle, labelTargetStyle);
        if(weekPlanReadRecord != null) {
            return ;
        }
        this.addWeekPlanReadRecord(targetValue, userId, wordCount, weekPlanStyle, labelTargetStyle, recordTypeStyle, isStory);
        WeekPlanReadRecord weekPlanReadRecordAll = this.findOneByTargetValueAndUserIdAndPlanTypeAndTargetType(targetValue, userId, WeekPlanStyle.ALL_PLAN, labelTargetStyle);
        if(weekPlanReadRecordAll == null) {
            this.addWeekPlanReadRecord(targetValue, userId, wordCount, WeekPlanStyle.ALL_PLAN, labelTargetStyle, recordTypeStyle, isStory);
        }
    }

    @Override
    public List<WeekPlanReadRecord> getListByPlanTypeAndTargetTypeAndTargetValue(WeekPlanStyle weekPlanStyle, LabelTargetStyle labelTargetStyle, Integer userId) {
        return weekPlanReadRecordDao.getListByUserIdAndPlanTypeAndTargetType(userId, labelTargetStyle.getId(), weekPlanStyle.getId());
    }

    @Override
    public List<WeekPlanReadRecord> getListByPlanTypeAndUserId(WeekPlanStyle weekPlanStyle, Integer userId) {
        return weekPlanReadRecordDao.getListByUserIdAndPlanType(userId, weekPlanStyle.getId());
    }

    /**
     * 通过某条记录获得一个response对象 最后统一处理
     * @return
     */
    @Override
    public StatisticWeekPlanLabel getStatisticByWeekPlanReadRecord(WeekPlanReadRecord weekPlanReadRecord) {
        List<WeekPlanLabelStory> weekPlanLabelStoryList = weekPlanLabelStoryService.findListByTargetTypeAndTargetValue(weekPlanReadRecord.getLabelTargetType(), weekPlanReadRecord.getTargetValue());
        StatisticWeekPlanLabel statisticWeekPlanLabel = new StatisticWeekPlanLabel();
        for(WeekPlanLabelStory item : weekPlanLabelStoryList) {
            WeekPlanLabel weekPlanLabel = weekPlanLabelService.findOne(item.getLabelId());
            if(weekPlanLabel != null) {
                statisticWeekPlanLabel.addWeekPlanLabel(weekPlanLabel);
            }
        }
        return statisticWeekPlanLabel;
    }

    @Override
    public GetStatisticWeekPlanLabelResponse getStatisticWeekPlanLabel(Integer userId, WeekPlanStyle weekPlanStyle) {
        HashMap<Integer, WeekPlanLabel> readTypeMap = weekPlanLabelService.getHashMapLabelListByType(WeekPlanLabelStyle.READ_TYPE);
        HashMap<Integer, WeekPlanLabel> fiveAreaTypeMap = weekPlanLabelService.getHashMapLabelListByType(WeekPlanLabelStyle.FIVE_AREA);
        GetStatisticWeekPlanLabelResponse response = new GetStatisticWeekPlanLabelResponse(readTypeMap, fiveAreaTypeMap);
        List<WeekPlanReadRecord> weekPlanReadRecordList = this.getListByPlanTypeAndUserId(weekPlanStyle, userId);
        if (weekPlanReadRecordList != null && weekPlanReadRecordList.size() > 0) {
            for(WeekPlanReadRecord item : weekPlanReadRecordList) {
                StatisticWeekPlanLabel statisticWeekPlanLabel = this.getStatisticByWeekPlanReadRecord(item);
                response.addStatisticWeekPlanLabel(statisticWeekPlanLabel);
            }
        }
        return response;
    }

    @Override
    public Integer getCountByPlanTypeAndTargetTypeAndUserIdAndReadType(WeekPlanStyle planType, LabelTargetStyle targetType, Integer userId, ReadRecordTypeStyle readType,Integer isStory) {

        Integer count = weekPlanReadRecordDao.countWordCountByPlanTypeAndTargetTypeAndUserIdAndReadType(planType.getId(), targetType.getId(), userId, readType.getId(),isStory);
        if(count != null) {
            return count;
        } else {
            return 0;
        }
    }

    @Override
    public Integer getSumWordCountByPlanTypeAndTargetTypeAndUserIdAndReadType(WeekPlanStyle planType, LabelTargetStyle targetType, Integer userId, ReadRecordTypeStyle readType,Integer isStory) {
        Integer count=weekPlanReadRecordDao.countWordCountByPlanTypeAndTargetTypeAndUserIdAndReadType(planType.getId(), targetType.getId(), userId, readType.getId(),isStory);
        if(count==null||count==0){
            return 0;
        }
        Integer sumWordCount = weekPlanReadRecordDao.getSumWordCountByPlanTypeAndTargetTypeAndUserIdAndReadType(planType.getId(), targetType.getId(), userId, readType.getId(),isStory);
        if(sumWordCount != null) {
            return sumWordCount;
        } else {
            return 0;
        }
    }

    @Override
    public WeekPlanReadRecord getFirstWeekPlanReadRecord(Integer userId, WeekPlanStyle planType) {
        Pageable pageable = new PageRequest(0, 1, new Sort(Sort.Direction.ASC,"createTime","id"));
        Page<WeekPlanReadRecord> weekPlanReadRecordPage = weekPlanReadRecordDao.getByUserIdAndPlanType(userId, planType.getId(), pageable);
        List<WeekPlanReadRecord> weekPlanReadRecordList = weekPlanReadRecordPage.getContent();
        if(weekPlanReadRecordList != null && weekPlanReadRecordList.size() > 0) {
            return weekPlanReadRecordList.get(0);
        } else {
            return null;
        }
    }
}

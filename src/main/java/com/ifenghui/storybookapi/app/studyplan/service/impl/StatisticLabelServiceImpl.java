package com.ifenghui.storybookapi.app.studyplan.service.impl;

import com.ifenghui.storybookapi.app.social.service.TotalReadStatistic;
import com.ifenghui.storybookapi.app.social.service.UserReadDurationRecordService;
import com.ifenghui.storybookapi.app.studyplan.entity.WeekPlanLabel;
import com.ifenghui.storybookapi.app.studyplan.response.GetStatisticWeekPlanLabelResponse;
import com.ifenghui.storybookapi.app.studyplan.response.StaticLabel;
import com.ifenghui.storybookapi.app.studyplan.response.TimeNumber;
import com.ifenghui.storybookapi.app.studyplan.service.StatisticLabelService;
import com.ifenghui.storybookapi.app.studyplan.service.WeekPlanReadRecordService;
import com.ifenghui.storybookapi.style.LabelTargetStyle;
import com.ifenghui.storybookapi.style.ReadRecordTypeStyle;
import com.ifenghui.storybookapi.style.WeekPlanStyle;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Component
public class StatisticLabelServiceImpl implements StatisticLabelService {

    @Autowired
    WeekPlanReadRecordService weekPlanReadRecordService;

    @Autowired
    UserReadDurationRecordService userReadDurationRecordService;

    @Override
    public StaticLabel getStaticLabelOne(Integer userId, WeekPlanStyle planStyle, Integer storyCount, Integer storyWordCount, Integer magCount, Integer magWordCount) {
        StaticLabel staticLabel = new StaticLabel();
        staticLabel.setId(1);
        List<String> stringList = new ArrayList<>();
        stringList.add("·共完成" + storyCount + "个线上故事，阅读" + storyWordCount + "字");
        stringList.add("·共完成" + magCount + "本《锋绘》杂志，阅读" + magWordCount + "字");
        staticLabel.setWordCountList(stringList);
        Integer totalWordCount = storyWordCount + magWordCount;
        staticLabel.setTitle("完成阅读文字量：" + totalWordCount + "字");
        return staticLabel;
    }
    @Override
    public StaticLabel getStaticLabelTwo(Integer userId, TotalReadStatistic totalReadStatistic) throws ParseException {

        List<TimeNumber> timeNumberList = new ArrayList<>();
        timeNumberList.add(new TimeNumber(1, totalReadStatistic.getTotalReadStoryDuration()));
        timeNumberList.add(new TimeNumber(2, totalReadStatistic.getTotalListenStoryDuration()));
        timeNumberList.add(new TimeNumber(3, totalReadStatistic.getTotalGameStoryDuration()));
        StaticLabel staticLabel = new StaticLabel();
        staticLabel.setId(2);
        staticLabel.setTimeNumberList(timeNumberList);
        return staticLabel;
    }

    @Override
    public String getName(Integer number) {
        String endString = "分钟";
        if(number >= 3600) {
            endString = "小时";
        }
        return dateFormat(number) + endString;

}

    private String dateFormat(Integer time) {
        float hour = time.floatValue() / 60;
        if(time >= 3600) {
            hour = time.floatValue() / 3600;
        }
        int hourInt = Math.round(hour);
        return String.valueOf(hourInt);
    }
    @Override
    public StaticLabel getStaticLabelThree(GetStatisticWeekPlanLabelResponse response) {
        StaticLabel staticLabel = new StaticLabel();
        staticLabel.setId(3);
        staticLabel.setWeekPlanLabelList(response.getReadTypeList());
        return staticLabel;
    }

    @Override
    public StaticLabel getStaticLabelFour(GetStatisticWeekPlanLabelResponse response) {
        StaticLabel staticLabel = new StaticLabel();
        staticLabel.setId(4);
        staticLabel.setWeekPlanLabelParentList(response.getCognitionTypeList());
        return staticLabel;
    }
    @Override
    public StaticLabel getStaticLabelFive(GetStatisticWeekPlanLabelResponse response) {
        StaticLabel staticLabel = new StaticLabel();
        staticLabel.setId(5);
        List<WeekPlanLabel> weekPlanLabelList = response.getLiteracyTypeList();
        if(weekPlanLabelList != null && weekPlanLabelList.size() > 0) {
            Integer wordNumber = weekPlanLabelList.size() * 4;
            staticLabel.setSubTitle("宝宝同学认读了" + wordNumber +"个字");
        }
        staticLabel.setWeekPlanLabelList(weekPlanLabelList);
        return staticLabel;
    }
    @Override
    public StaticLabel getStaticLabelSix(GetStatisticWeekPlanLabelResponse response) {
        StaticLabel staticLabel = new StaticLabel();
        staticLabel.setId(6);
        staticLabel.setWeekPlanLabelList(response.getFiveAreaTypeList());
        return staticLabel;
    }

    /**
     * 通过时间秒毫秒数判断两个时间的间隔
     * @param date1
     * @param date2
     * @return
     */
    @Override
    public int differentDaysByMillisecond(Date date1, Date date2) {
        int days = (int) ((date2.getTime() - date1.getTime()) / (1000*3600*24));
        if(days == 0) {
            days = 1;
        }
        return days;
    }

}

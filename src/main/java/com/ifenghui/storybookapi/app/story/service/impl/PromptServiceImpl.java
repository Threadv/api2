package com.ifenghui.storybookapi.app.story.service.impl;

import com.ifenghui.storybookapi.app.social.entity.SignRecord;
import com.ifenghui.storybookapi.app.social.service.CheckInService;
import com.ifenghui.storybookapi.app.social.service.SignRecordService;
import com.ifenghui.storybookapi.app.story.response.Prompt;
import com.ifenghui.storybookapi.app.studyplan.service.WeekPlanUserRecordService;
import com.ifenghui.storybookapi.style.PromptStyle;
import com.ifenghui.storybookapi.app.story.service.PromptService;
import com.ifenghui.storybookapi.app.studyplan.entity.WeekPlanJoin;
import com.ifenghui.storybookapi.app.studyplan.service.WeekPlanJoinService;
import com.ifenghui.storybookapi.style.SignRecordStyle;
import com.ifenghui.storybookapi.style.WeekPlanStyle;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.logging.Logger;

@Component
@Transactional
public class PromptServiceImpl implements PromptService {

    org.apache.log4j.Logger logger= org.apache.log4j.Logger.getLogger(PromptServiceImpl.class);
    @Autowired
    WeekPlanJoinService weekPlanJoinService;

    @Autowired
    SignRecordService signRecordService;

    @Autowired
    CheckInService checkInService;

    @Autowired
    WeekPlanUserRecordService weekPlanUserRecordService;

    /**
     * 添加提示框
     * @param userId
     * @return
     */
    @Override
    public Prompt getPrompt(Integer userId) throws Exception {

        //2-4岁
        Long cont1 = weekPlanJoinService.countWeekPlanJoinByUserIdAndType(userId, WeekPlanStyle.TWO_FOUR);
        //4-6岁
        Long cont2 = weekPlanJoinService.countWeekPlanJoinByUserIdAndType(userId, WeekPlanStyle.FOUR_SIX);

        logger.info("promt------2-4count="+cont1+"count2="+cont2);
        //弹框标识是否参加了解详情弹框
        if (cont1 == 0 && cont2 == 0) {

            return new Prompt(PromptStyle.STUDY_PLAN.getId(),PromptStyle.STUDY_PLAN.getName(),PromptStyle.STUDY_PLAN.getUrl(),0);
        }

        Long signRecord = signRecordService.countSignRecordByUserIdAndType(userId, SignRecordStyle.READ_TYPE);
        //已经提示过弹窗
        if (signRecord > 0) {
            return new Prompt(0,"","",0);
        }

        //2-4岁
        WeekPlanJoin join1 = weekPlanJoinService.getWeekPlanJoinByUserIdAndType(userId, WeekPlanStyle.TWO_FOUR);
        //4-6岁
        WeekPlanJoin join2 = weekPlanJoinService.getWeekPlanJoinByUserIdAndType(userId, WeekPlanStyle.FOUR_SIX);

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd 00:00:00");
        SimpleDateFormat sdf2 = new SimpleDateFormat("yyyy-MM-dd");
        Date createTime = null;
        int planStyle = 0;
        if (join1 != null && join2 == null) {
            createTime = join1.getCreateTime();
        }
        if (join1 == null && join2 != null) {
            createTime = join2.getCreateTime();
        }
        if (join1 != null && join2 != null) {
            Date createTime1 = join1.getCreateTime();
            Date createTime2 = join2.getCreateTime();
            if (createTime1.getTime() < createTime2.getTime()) {
                createTime = createTime1;
                planStyle =1;
            } else {
                createTime = createTime2;
                planStyle =2;
            }
        }
        logger.info("promt-----createTime ="+sdf.format(createTime));
        //判断是否超过一周的时间
        //当前周一 2-4
        Date thisWeekMonday = weekPlanUserRecordService.getThisWeekMonday(createTime);
        Date day = checkInService.getDay( thisWeekMonday, 7);
        //格式化为七天之后0点
        Date cDate = sdf2.parse(sdf.format(day));
        //大于本周周一的七天
        logger.info("promt----本周一-createTime ="+sdf.format(thisWeekMonday));
        if (System.currentTimeMillis() > cDate.getTime()) {
            logger.info("promt-----大于七天"+sdf.format(System.currentTimeMillis()));
            return  new Prompt(PromptStyle.FIRST_REPORT.getId(),PromptStyle.FIRST_REPORT.getName(),PromptStyle.FIRST_REPORT.getUrl(),planStyle);
        }
        return new Prompt(0,"","",planStyle);
    }

    /***
     * 是否达到参加计划的一周
     * @param userId
     * @return
     */
    @Override
    public Integer isAchieve(Integer userId) throws  Exception{

        int sign =0;

        //2-4岁
        WeekPlanJoin join1 = weekPlanJoinService.getWeekPlanJoinByUserIdAndType(userId, WeekPlanStyle.TWO_FOUR);
        //4-6岁
        WeekPlanJoin join2 = weekPlanJoinService.getWeekPlanJoinByUserIdAndType(userId, WeekPlanStyle.FOUR_SIX);

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd 00:00:00");
        SimpleDateFormat sdf2 = new SimpleDateFormat("yyyy-MM-dd");
        Date createTime = null;
        if (join1 != null && join2 == null) {
            createTime = join1.getCreateTime();
        }
        if (join1 == null && join2 != null) {
            createTime = join2.getCreateTime();
        }
        if (join1 != null && join2 != null) {
            Date createTime1 = join1.getCreateTime();
            Date createTime2 = join2.getCreateTime();
            if (createTime1.getTime() < createTime2.getTime()) {
                createTime = createTime1;
            } else {
                createTime = createTime2;
            }
        }

        //判断是否超过一周的时间
        //当前周一
        Date thisWeekMonday = weekPlanUserRecordService.getThisWeekMonday(createTime);
        Date day = checkInService.getDay( thisWeekMonday, 7);
        Date cDate = sdf2.parse(sdf.format(day));
        //大于本周周一的七天
        if (System.currentTimeMillis() > cDate.getTime()) {
            sign=1;
        }
        return sign;
    }

    @Override
    public Integer isAchieveByPlanStyle(Integer userId, WeekPlanStyle planStyle) throws Exception {

        int sign =0;
        WeekPlanJoin join = weekPlanJoinService.getWeekPlanJoinByUserIdAndType(userId, planStyle);

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd 00:00:00");
        SimpleDateFormat sdf2 = new SimpleDateFormat("yyyy-MM-dd");
        Date createTime = null ;
        if (join != null ) {
           createTime = join.getCreateTime();
        }

        //判断是否超过一周的时间
        //当前周一
        Date thisWeekMonday = weekPlanUserRecordService.getThisWeekMonday(createTime);
        Date day = checkInService.getDay( thisWeekMonday, 7);
        Date cDate = sdf2.parse(sdf.format(day));
        //大于本周周一的七天
        if (System.currentTimeMillis() > cDate.getTime()) {
            sign=1;
        }
        return sign;
    }
}

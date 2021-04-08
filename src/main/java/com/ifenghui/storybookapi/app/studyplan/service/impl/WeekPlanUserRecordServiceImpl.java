package com.ifenghui.storybookapi.app.studyplan.service.impl;

import com.ifenghui.storybookapi.app.studyplan.dao.WeekPlanJoinDao;
import com.ifenghui.storybookapi.app.studyplan.dao.WeekPlanUserRecordDao;
import com.ifenghui.storybookapi.app.studyplan.entity.WeekPlanIntro;
import com.ifenghui.storybookapi.app.studyplan.entity.WeekPlanJoin;
import com.ifenghui.storybookapi.app.studyplan.entity.WeekPlanUserRecord;
import com.ifenghui.storybookapi.app.studyplan.service.WeekPlanIntroService;
import com.ifenghui.storybookapi.app.studyplan.service.WeekPlanJoinService;
import com.ifenghui.storybookapi.app.studyplan.service.WeekPlanUserRecordService;
import com.ifenghui.storybookapi.app.transaction.dao.AbilityPlanOrderDao;
import com.ifenghui.storybookapi.app.transaction.entity.abilityplan.AbilityPlanOrder;
import com.ifenghui.storybookapi.app.transaction.service.AbilityPlanOrderService;
import com.ifenghui.storybookapi.app.transaction.service.UserAbilityPlanRelateService;
import com.ifenghui.storybookapi.app.user.entity.User;
import com.ifenghui.storybookapi.app.user.entity.UserExtend;
import com.ifenghui.storybookapi.app.user.service.UserExtendService;
import com.ifenghui.storybookapi.app.user.service.UserService;

import com.ifenghui.storybookapi.style.SvipStyle;
import com.ifenghui.storybookapi.style.WeekPlanStyle;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Component;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

@Component
public class WeekPlanUserRecordServiceImpl implements WeekPlanUserRecordService {

    @Autowired
    WeekPlanUserRecordDao weekPlanUserRecordDao;

    @Autowired
    WeekPlanIntroService weekPlanIntroService;

    @Autowired
    UserService userService;

    @Autowired
    WeekPlanJoinDao weekPlanJoinDao;
    @Autowired
    WeekPlanJoinService weekPlanJoinService;
    @Autowired
    UserAbilityPlanRelateService userAbilityPlanRelateService;

    @Autowired
    UserExtendService userExtendService;

    @Autowired
    AbilityPlanOrderService abilityPlanOrderService;

    @Autowired
    AbilityPlanOrderDao abilityPlanOrderDao;

    Logger logger= Logger.getLogger(WeekPlanUserRecordServiceImpl.class);

    @Override
    public void deleteRecordsByUserIdAndWeekPlanId(Integer userId, Integer weekPlanId) {

        weekPlanUserRecordDao.deleteByUserIdAndPlanId(userId,weekPlanId);
    }

    @Override
    public Integer countFinishWeekNum(WeekPlanStyle planStyle, Integer userId) {

        Integer count =0;
        if(planStyle == WeekPlanStyle.TWO_FOUR){
            count = weekPlanUserRecordDao.countTwoFourFinishWeekNum(userId);
        }else if(planStyle == WeekPlanStyle.FOUR_SIX){
            count = weekPlanUserRecordDao.countFourSixFinishWeekNum(userId);
        }
        return count;
    }

    @Override
    public WeekPlanUserRecord addWeekPlanUserRecord(Integer weekPlanId, Integer userId, Integer magTaskId) {

        WeekPlanUserRecord weekPlanUserRecord = new WeekPlanUserRecord();
        weekPlanUserRecord.setCreateTime(new Date());
        weekPlanUserRecord.setIsFinish(0);
        weekPlanUserRecord.setIsFinishAll(0);
        weekPlanUserRecord.setIsFinishMag(0);
        weekPlanUserRecord.setMagTaskId(magTaskId);
        weekPlanUserRecord.setWeekPlanId(weekPlanId);
        weekPlanUserRecord.setUserId(userId);
        weekPlanUserRecord.setFinishNum(0);
        weekPlanUserRecord.setShowStar(0);
        return weekPlanUserRecordDao.save(weekPlanUserRecord);
    }

    @Override
    public WeekPlanUserRecord createWeekPlanUserRecord(Integer weekPlanId, Integer userId) {

        List<WeekPlanUserRecord> recordList = this.findListByPlanIdAndUserId(weekPlanId,userId);
        if(recordList.size() > 0){
            return recordList.get(0);
        }
        Date monday = this.getThisWeekMonday(new Date());
        Integer magTaskId = this.getMagTaskDate(monday);
        if(!magTaskId.equals(0)) {
            Integer count = this.getCountByUserIdAndMagTaskId(userId, magTaskId);
            if(count > 0) {
                magTaskId = 0;
            }
        }
        return this.addWeekPlanUserRecord(weekPlanId, userId, magTaskId);
    }

    @Override
    public Integer getCountByUserIdAndMagTaskId(Integer userId, Integer magTaskId) {
        Integer count = weekPlanUserRecordDao.getCountByUserIdAndMagTaskId(userId, magTaskId);
        if(count != null) {
            return count;
        } else {
            return 0;
        }
    }

    @Override
    public Integer getCountByUserIdAndWeekPlanId(Integer userId, Integer weekPlanId) {
        Integer count = weekPlanUserRecordDao.getCountByUserIdAndWeekPlanId(userId, weekPlanId);
        if(count != null) {
            return count;
        } else {
            return 0;
        }
    }

    @Override
    public Integer getMagTaskDate(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        int day = calendar.get(Calendar.DATE);
        SimpleDateFormat format = new SimpleDateFormat("yyyyMM");
        String dateNum = format.format(date);
        Integer dateNumInt = 0;
        if(day >= 20 ) {
            dateNumInt = Integer.parseInt(dateNum);
        }
        return dateNumInt;
    }

    /**
     * 获得当前周一
     * @param date
     * @return
     */
    @Override
    public Date getThisWeekMonday(Date date) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        int dayWeek = cal.get(Calendar.DAY_OF_WEEK);
        if (1 == dayWeek) {
            cal.add(Calendar.DAY_OF_MONTH, -1);
        }
        cal.setFirstDayOfWeek(Calendar.MONDAY);
        int day = cal.get(Calendar.DAY_OF_WEEK);
        cal.add(Calendar.DATE, cal.getFirstDayOfWeek() - day);
        return cal.getTime();
    }

    @Override
    public WeekPlanUserRecord isHasWeekPlanUserRecord(Integer weekPlanId, Integer userId) {
        return weekPlanUserRecordDao.getDistinctByUserIdAndWeekPlanId(userId, weekPlanId);
    }

    @Override
    public List<WeekPlanUserRecord> findListByMagTaskIdAndUserId(Integer magTaskId, Integer userId) {
        return weekPlanUserRecordDao.getWeekPlanUserRecordsByUserIdAndMagTaskId(userId, magTaskId);
    }

    @Override
    public List<WeekPlanUserRecord> findListByPlanIdAndUserId(Integer weekPlanId, Integer userId) {
        return weekPlanUserRecordDao.getWeekPlanUserRecordsByUserIdAndWeekPlanId(userId, weekPlanId);
    }

    @Override
    public void setWeekPlanUserRecordFinishStatusByMagTaskId(Integer magTaskId, Integer userId) {
        List<WeekPlanUserRecord> weekPlanUserRecordList = this.findListByMagTaskIdAndUserId(magTaskId, userId);
        this.setWeekPlanUserRecordFinishStatusByList(weekPlanUserRecordList, true);
    }

    @Override
    public void setFinishShowStar(Integer weekPlanId, Integer userId) {
        WeekPlanUserRecord weekPlanUserRecord = this.isHasWeekPlanUserRecord(weekPlanId, userId);
        if(weekPlanUserRecord.getShowStar().equals(0) && weekPlanUserRecord.getIsFinish().equals(1)) {
            weekPlanUserRecord.setShowStar(1);
            weekPlanUserRecordDao.save(weekPlanUserRecord);
        }
    }

    private void setWeekPlanUserRecordFinishStatusByList(List<WeekPlanUserRecord> weekPlanUserRecordList, boolean isMag) {
        if(weekPlanUserRecordList != null && weekPlanUserRecordList.size() > 0) {
            for(WeekPlanUserRecord item : weekPlanUserRecordList) {
                if(isMag) {
                    item.setIsFinishMag(1);
                } else {
                    item.setIsFinish(1);
                }
                if(item.getIsFinish().equals(1) && item.getIsFinishMag().equals(1)) {
                    item.setIsFinishAll(1);
                }
                weekPlanUserRecordDao.save(item);
            }
        }
    }

    @Override
    public void setWeekPlanUserRecordFinishNum(Integer weekPlanId, Integer userId, Integer finishNum) {
        WeekPlanUserRecord weekPlanUserRecord = this.isHasWeekPlanUserRecord(weekPlanId, userId);
        if(weekPlanUserRecord == null) {
            return ;
        }
        weekPlanUserRecord.setFinishNum(finishNum);
        if(finishNum.equals(8)){
            weekPlanUserRecord.setIsFinish(1);
        }
        if(weekPlanUserRecord.getIsFinish().equals(1) && weekPlanUserRecord.getIsFinishMag().equals(1)) {
            weekPlanUserRecord.setIsFinishAll(1);
        }
        weekPlanUserRecordDao.save(weekPlanUserRecord);
    }

    @Override
    public void dayTaskSendWeekPlanUserRecord() {
        Page<WeekPlanJoin> weekPlanJoinPage = weekPlanJoinService.findAllBuyNumBiggerThanOne(0, 30);
        List<WeekPlanJoin> weekPlanJoinList = weekPlanJoinPage.getContent();
        this.sendWeekPlanUserRecordByList(weekPlanJoinList);

            for(int i=1; i < weekPlanJoinPage.getTotalPages();i++) {
                weekPlanJoinPage = weekPlanJoinService.findAllBuyNumBiggerThanOne(i, 30);
                weekPlanJoinList = weekPlanJoinPage.getContent();
                this.sendWeekPlanUserRecordByList(weekPlanJoinList);
            }

    }


    @Override
    public void recoverDayTaskSend() throws ParseException {
        String time = "2018-10-22 00:00:00";
        SimpleDateFormat format = new SimpleDateFormat("yy-MM-dd 00:00:00");
        Date starTime = format.parse(time);
        Page<WeekPlanJoin> weekPlanJoinPage = weekPlanJoinService.getPageByDate(0, 30, starTime);
        if(weekPlanJoinPage.getTotalPages() > 1) {
            for(int i=1; i < weekPlanJoinPage.getTotalPages();i++) {
                this.sendWeekPlanUserRecordByDate(i, 30, starTime);
            }
        }
    }

    @Override
    public void sendWeekPlanUserRecordByList(List<WeekPlanJoin> weekPlanJoinList) {
        if(weekPlanJoinList != null && weekPlanJoinList.size() > 0) {
            for(WeekPlanJoin item : weekPlanJoinList) {
//                logger.info(item.getUserId()+"user study");
//                if(item.getUserId()==5175){
//                    logger.info(item.getUserId()+"user study   5555175");
//
//                }
                this.sendWeekPlanUserRecordByUserIdAndPlanType(item.getUserId(), item.getWeekPlanStyle());
            }
        }
    }

//    @Override
//    public void sendWeekPlanUserRecordByPage(int pageNo, int pageSize) {
//        Page<WeekPlanJoin> weekPlanJoinPage = weekPlanJoinService.getAllPage(pageNo, pageSize);
//        this.sendWeekPlanUserRecordByList(weekPlanJoinPage.getContent());
//    }

    @Override
    public void sendWeekPlanUserRecordByDate(int pageNo, int pageSize, Date date) {
        Page<WeekPlanJoin> weekPlanJoinPage = weekPlanJoinService.getPageByDate(pageNo, pageSize, date);
        this.sendWeekPlanUserRecordByList(weekPlanJoinPage.getContent());
    }

    //每周一推送
    @Override
    public void sendWeekPlanUserRecordByUserIdAndPlanType(Integer userId, WeekPlanStyle weekPlanStyle) {
        //第一步，查用户是否已经加入， 查userid=16
        logger.info(userId);
//        if(userId!=16){
//            return;
//        }
//        if(weekPlanStyle!=WeekPlanStyle.TWO_FOUR){
//            return;
//        }
//        if(true){
//            return;
//        }
        WeekPlanJoin weekPlanJoin = weekPlanJoinService.getWeekPlanJoinByUserIdAndType(userId, weekPlanStyle);
        if(weekPlanJoin == null) {
            return;
        }

        // 第二部，有这种类型的购买记录,验证购买记录，有任意一个类型的购买记录就允许推送
        Sort sort = new Sort(Sort.Direction.ASC, "priceId");
        List<AbilityPlanOrder> orderList = abilityPlanOrderDao.getAbilityPlanOrdersByUserIdAndPlanType(userId, weekPlanStyle.getId(),1,sort);
        if(orderList ==null || orderList.size() ==0){
            WeekPlanStyle weekPlanStyleOther=WeekPlanStyle.TWO_FOUR;
            if(weekPlanStyle==WeekPlanStyle.FOUR_SIX){
                orderList = abilityPlanOrderDao.getAbilityPlanOrdersByUserIdAndPlanType(userId, weekPlanStyleOther.getId(),1,sort);
                if(orderList ==null || orderList.size() ==0){
                    return;
                }
            }
        }

        //确认用户是否存在
        User user = userService.getUser(userId.longValue());
        if(user ==null){
            return;
        }
        //确认用户中svip状态，3 4 或abilityplan=1 兼容之前3重vip状态
        if(!user.getSvip().equals(SvipStyle.LEVEL_THREE.getId()) && !user.getSvip().equals(SvipStyle.LEVEL_FOUR.getId()) && user.getIsAbilityPlan()!= 1 ) {
            return ;
        }
        Pageable pageable = new PageRequest(0, 1, new Sort(Sort.Direction.DESC,"weekPlanId"));
        List<WeekPlanUserRecord> weekPlanUserRecords = null;
        if(weekPlanStyle.equals(WeekPlanStyle.TWO_FOUR)) {
            weekPlanUserRecords = weekPlanUserRecordDao.getListTwoFourByUserId(userId, pageable);
        } else if(weekPlanStyle.equals(WeekPlanStyle.FOUR_SIX)) {
            weekPlanUserRecords = weekPlanUserRecordDao.getListFourSixByUserId(userId, pageable);
        }

        if(weekPlanUserRecords == null|| weekPlanUserRecords.size()==0) {
            //推送该计划第一条
            weekPlanJoinService.addOneWeekPlanByUserId(userId, weekPlanStyle);
        }else {
            //查询优能计划最后一条
            WeekPlanUserRecord weekPlanUserRecord = weekPlanUserRecords.get(0);
            if (this.isSameDate(new Date(), weekPlanUserRecord.getCreateTime())) {
                //如果当天已经推送过，中止推送
                return;
            } else {
                //获得之前的周数
                Integer oldWeekPlanId = weekPlanUserRecord.getWeekPlanId();
                Integer newWeekPlanId = oldWeekPlanId + 1;
                WeekPlanIntro weekPlanIntro = weekPlanIntroService.findOne(newWeekPlanId);
                //如果已经是最后一周,退出
                if (weekPlanIntro == null) {
                    return;
                }
                //不在购买范围
                int buyNum = weekPlanJoin.getBuyNum();
                if(weekPlanStyle == WeekPlanStyle.FOUR_SIX){
                    buyNum = weekPlanJoin.getBuyNum() + 52 ;
                }
                if (newWeekPlanId > buyNum) {
                    return;
                }
                this.createWeekPlanUserRecord(weekPlanIntro.getId(), userId);
            }
        }
    }

    @Override
    public void sendWeekPlanUserRecordByUserIdAndPlanTypeToSomeone(Integer userId, WeekPlanStyle planStyle) {
        //查询购买记录
        WeekPlanJoin weekPlanJoin = weekPlanJoinService.getWeekPlanJoinByUserIdAndType(userId, planStyle);
        if(weekPlanJoin == null) {
            weekPlanJoinService.createWeekPlanJoin(userId, planStyle);
            return;
        }
        Pageable pageable = new PageRequest(0, 60, new Sort(Sort.Direction.DESC,"weekPlanId"));
        List<WeekPlanUserRecord> weekPlanUserRecords = null;
        if(planStyle.equals(WeekPlanStyle.TWO_FOUR)) {
            weekPlanUserRecords = weekPlanUserRecordDao.getListTwoFourByUserId(userId, pageable);
        } else if(planStyle.equals(WeekPlanStyle.FOUR_SIX)) {
            weekPlanUserRecords = weekPlanUserRecordDao.getListFourSixByUserId(userId, pageable);
        }
        //周参与记录
        if(weekPlanUserRecords == null|| weekPlanUserRecords.size()==0) {
            //添加第一周试用
            weekPlanJoinService.addOneWeekPlanByUserId(userId, planStyle);
        }else{
            WeekPlanUserRecord weekPlanUserRecord = weekPlanUserRecords.get(0);

            Integer oldWeekPlanId = weekPlanUserRecord.getWeekPlanId();
            Integer newWeekPlanId = oldWeekPlanId + 1;

            WeekPlanIntro weekPlanIntro = weekPlanIntroService.findOne(newWeekPlanId);
            if(weekPlanIntro == null) {
                return ;
            }
            //判断是否达到join的购买数
            WeekPlanJoin planJoin = weekPlanJoinService.getWeekPlanJoinByUserIdAndType(userId, planStyle);
            //推送一周后的总数
            int count = weekPlanUserRecords.size() + 1;
            if(count > planJoin.getBuyNum()){
                //设置buyNum和推送后的总数一致
                planJoin.setBuyNum(count);
                weekPlanJoinDao.save(planJoin);
            }
            this.createWeekPlanUserRecord(weekPlanIntro.getId(), userId);
        }

    }


    private boolean isSameDate(Date date1, Date date2) {
        Calendar cal1 = Calendar.getInstance();
        cal1.setTime(date1);
        Calendar cal2 = Calendar.getInstance();
        cal2.setTime(date2);
        boolean isSameYear = cal1.get(Calendar.YEAR) == cal2.get(Calendar.YEAR);
        boolean isSameMonth = isSameYear && cal1.get(Calendar.MONTH) == cal2.get(Calendar.MONTH);
        return isSameMonth && cal1.get(Calendar.DAY_OF_MONTH) == cal2.get(Calendar.DAY_OF_MONTH);
    }

    @Override
    public void sendSecondWeek(Integer userId) {
//        createWeekPlanUserRecord(Integer weekPlanId, Integer userId)
//        createWeekPlanUserRecord(Integer weekPlanId, Integer userId)
//        weekPlanJoinService.addOneWeekPlanByUserId(userId, planStyle);
    }
}

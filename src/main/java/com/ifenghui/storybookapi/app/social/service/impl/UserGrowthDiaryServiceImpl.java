package com.ifenghui.storybookapi.app.social.service.impl;

import com.ifenghui.storybookapi.app.social.response.TaskFinish;
import com.ifenghui.storybookapi.app.social.service.*;
import com.ifenghui.storybookapi.app.user.service.UserService;
import com.ifenghui.storybookapi.app.wallet.service.UserStarRecordService;
import com.ifenghui.storybookapi.app.wallet.service.WalletService;
import com.ifenghui.storybookapi.config.StarConfig;
import com.ifenghui.storybookapi.app.social.dao.UserGrowthDiaryDao;
import com.ifenghui.storybookapi.app.user.entity.User;
import com.ifenghui.storybookapi.app.social.entity.UserGrowthDiary;
import com.ifenghui.storybookapi.app.social.entity.UserGrowthDiaryImg;
import com.ifenghui.storybookapi.style.AddStyle;
import com.ifenghui.storybookapi.style.StarContentStyle;
import com.ifenghui.storybookapi.style.StarRechargeStyle;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Component;

import javax.transaction.Transactional;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

@Component
public class UserGrowthDiaryServiceImpl implements UserGrowthDiaryService {

    @Autowired
    UserGrowthDiaryDao userGrowthDiaryDao;

    @Autowired
    UserGrowthDiaryImgService userGrowthDiaryImgService;

    @Autowired
    UserService userService;

    @Autowired
    UserStarRecordService userStarRecordService;

    @Autowired
    WalletService walletService;

    @Transactional
    @Override
    public UserGrowthDiary addUserGrowthDiary(Integer userId, String content, Date recordTime, List<ScheduleSmallImg> diaryImgList, Integer width, Integer height, String imgPath) throws ParseException {

        SimpleDateFormat formatter = new SimpleDateFormat("yyyy");
        String nowYear = formatter.format(recordTime);

        Pageable pageable = new PageRequest(0,1,new Sort(Sort.Direction.DESC,"recordDate","id"));
        Page<UserGrowthDiary> userGrowthDiaryPage = userGrowthDiaryDao.getUserGrowthDiaryPageByUserIdBefore(recordTime,userId,pageable);
        UserGrowthDiary yearLast = null;
        if(userGrowthDiaryPage.getContent().size() > 0){
            yearLast = userGrowthDiaryPage.getContent().get(0);
        }

        Pageable pageableAfter = new PageRequest(0,1,new Sort(Sort.Direction.ASC,"recordDate","id"));
        Page<UserGrowthDiary> userGrowthDiaries = userGrowthDiaryDao.getUserGrowthDiaryPageByUserIdAfter(recordTime,userId,pageableAfter);
        UserGrowthDiary yearAfter = null;
        if(userGrowthDiaries.getContent().size() > 0){
            yearAfter = userGrowthDiaries.getContent().get(0);
        }

        Integer crossYear = 0;
        if(yearLast != null){
            String yearLastString = formatter.format(yearLast.getRecordDate());
            if(yearLastString.equals(nowYear)){
                yearLast.setCrossYear(0);
                userGrowthDiaryDao.save(yearLast);
                crossYear = 1;
            } else {
                crossYear = 1;
            }
        }

        if(yearAfter != null){
            String yearAfterString = formatter.format(yearAfter.getRecordDate());
            if(yearAfterString.equals(nowYear) && yearAfter.getRecordDate().getTime() != recordTime.getTime()){
                crossYear = 0;
            } else {
                crossYear = 1;
            }
        }

        if(yearAfter == null && yearLast == null){
            crossYear = 1;
        }

        String currentYear = formatter.format(new Date());
        if(nowYear.equals(currentYear)){
            crossYear = 0;
        }

        SimpleDateFormat formatYearMonth = new SimpleDateFormat("yyyyMM");
        String yearMonth = formatYearMonth.format(recordTime);

        int isTest =userService.getUser(userId).getIsTest();
        UserGrowthDiary userGrowthDiary = new UserGrowthDiary();
        userGrowthDiary.setIsTest(isTest);
        userGrowthDiary.setContent(content);
        userGrowthDiary.setRecordDate(recordTime);
        userGrowthDiary.setUserId(userId);
        userGrowthDiary.setStatus(1);
        userGrowthDiary.setCreateTime(new java.util.Date());
        userGrowthDiary.setCrossYear(crossYear);
        userGrowthDiary.setHeight(height);
        userGrowthDiary.setImgPath(imgPath);
        userGrowthDiary.setWidth(width);
        userGrowthDiary.setMonthDate(Integer.parseInt(yearMonth));
        userGrowthDiary = userGrowthDiaryDao.save(userGrowthDiary);

        userGrowthDiaryImgService.addUserGrowthDiaryImgList(diaryImgList,userId,userGrowthDiary.getId(),recordTime);
        return userGrowthDiary;
    }
    @Override
    @Transactional
    public List<TaskFinish> isNeedAddUserStarRecord(Integer userId) throws ParseException {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd 00:00:00");
        String beginTimeString = simpleDateFormat.format(new Date());
        Date beginTime = simpleDateFormat.parse(beginTimeString);
        Date endTime = new Date();
        Pageable pageable = new PageRequest(0,1,new Sort(Sort.Direction.DESC,"id"));
        Page<UserGrowthDiary> userGrowthDiaryPage = userGrowthDiaryDao.getUserGrowthDiaryListByTime(beginTime, endTime, userId, pageable);
        List<UserGrowthDiary> userGrowthDiaryList = userGrowthDiaryPage.getContent();
        String intro = "";
        if(userGrowthDiaryList.size() == 0){
            intro = "恭喜您完成每日成长记录任务\n获得" + StarConfig.STAR_GROWTH_DAIRY + "颗小星星";
//            userStarRecordService.addUserStarRecord(userId.longValue(), StarConfig.STAR_GROWTH_DAIRY, AddStyle.UP, StarRechargeStyle.GROWTH_DAIRY, "每日成长记录星星值奖励");

            //INTRO  每日成长记录星星值奖励
            walletService.addStarToWallet(userId,StarRechargeStyle.GROWTH_DAIRY,StarConfig.STAR_GROWTH_DAIRY, StarContentStyle.RECORD.getName());
        }

        List<TaskFinish> taskFinishes = new ArrayList<>();
        //重复观看
        if(!intro.equals("")){
            TaskFinish taskFinish = new TaskFinish();
            taskFinish.setIntro(intro);
            taskFinishes.add(taskFinish);
        }
        return taskFinishes;
    }

    @Override
    public Page<UserGrowthDiary> getUserGrowthDiaryPage(Integer userId,Integer monthDate, Integer pageNo, Integer pageSize) {
        User user = userService.getUser(userId.longValue());
        Pageable pageable = new PageRequest(pageNo,pageSize,new Sort(Sort.Direction.DESC,"recordDate","id"));

        Page<UserGrowthDiary> userGrowthDiaryPage = null;

        if(monthDate != null){
            userGrowthDiaryPage = userGrowthDiaryDao.getUserGrowthDiariesByUserIdAndStatusAndMonthDate(userId, 1, monthDate, pageable);
        } else {
            userGrowthDiaryPage = userGrowthDiaryDao.getUserGrowthDiariesByUserIdAndStatus(userId, 1, pageable);
        }

        List<UserGrowthDiary> userGrowthDiaryList = userGrowthDiaryPage.getContent();
        for(UserGrowthDiary item : userGrowthDiaryList){
            List<UserGrowthDiaryImg> userGrowthDiaryImgList = userGrowthDiaryImgService.getGrowthDiaryImgList(item.getId());
            item.setDiaryImgList(userGrowthDiaryImgList);
            Long birthDay = user.getBirthday().getTime();
            Long itemDay = item.getRecordDate().getTime();
            Long birthToRecord = (itemDay - birthDay)/(3600*24*1000);
            item.setBirthToRecord(birthToRecord.intValue());
            item.setImgSize(userGrowthDiaryImgList.size());
        }
        return userGrowthDiaryPage;
    }

    @Override
    public void deleteUserGrowthDiary(Integer userGrowthDiaryId, Integer userId) {
        UserGrowthDiary userGrowthDiary = userGrowthDiaryDao.getUserGrowthDiaryByIdAndUserId(userGrowthDiaryId, userId);
        if(userGrowthDiary != null){
            if(userGrowthDiary.getCrossYear().equals(1)){
                Pageable pageable = new PageRequest(0,2,new Sort(Sort.Direction.DESC,"recordDate","id"));
                Page<UserGrowthDiary> userGrowthDiaryPage = userGrowthDiaryDao.getUserGrowthDiaryPageByUserIdBefore(userGrowthDiary.getRecordDate(),userId,pageable);
                UserGrowthDiary yearLast = null;
                if(userGrowthDiaryPage.getContent().size() > 1){
                    yearLast = userGrowthDiaryPage.getContent().get(1);
                    yearLast.setCrossYear(1);
                    userGrowthDiaryDao.save(yearLast);
                }
            }
            userGrowthDiaryImgService.deleteUserGrowthDiaryImg(userGrowthDiary.getId(),userId);
            userGrowthDiary.setStatus(0);
            userGrowthDiaryDao.save(userGrowthDiary);
        }
    }

    @Override
    public void recoverUserGrowthDiaryData() {
        List<UserGrowthDiary> userGrowthDiaryList = userGrowthDiaryDao.findAll();
        SimpleDateFormat formatYearMonth = new SimpleDateFormat("yyyyMM");
        for(UserGrowthDiary item : userGrowthDiaryList){
            Integer yearMonth = Integer.parseInt(formatYearMonth.format(item.getRecordDate()));
            item.setMonthDate(yearMonth);
            userGrowthDiaryDao.save(item);
        }
    }

    @Override
    public List<GrowthDiaryMonthDate> getGrowthDiaryMonthDateList(Date startTime, Date endTime, List<Integer> monthDataList) throws ParseException {
        List<GrowthDiaryMonthDate> growthDiaryMonthDateList = new ArrayList<>();

        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyyMM");
        String starTimeString = simpleDateFormat.format(startTime);
        String endTimeString = simpleDateFormat.format(endTime);

        Calendar bef = Calendar.getInstance();
        Calendar aft = Calendar.getInstance();
        bef.setTime(endTime);
        aft.setTime(startTime);
        Integer monthInt = (aft.get(Calendar.MONTH) - bef.get(Calendar.MONTH)) + ((aft.get(Calendar.YEAR) - bef.get(Calendar.YEAR))*12);

        Integer yearMonthInt = Integer.parseInt(starTimeString);
        Integer endTimeInt = Integer.parseInt(endTimeString);

        while(yearMonthInt > endTimeInt){

            Integer isHasData = 0;

            if(monthDataList.contains(Integer.parseInt(starTimeString))){
                isHasData = 1;
            }
            Integer lastMonth = 0;
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(startTime);
            calendar.add(Calendar.MONTH, -1);
            String nextMonth = simpleDateFormat.format(calendar.getTime());

            if(nextMonth.equals(endTimeString)){
                lastMonth = 1;
            }
            growthDiaryMonthDateList.add(
                new GrowthDiaryMonthDate(monthInt,yearMonthInt,isHasData,2,lastMonth)
            );
            monthInt = monthInt - 1;

            starTimeString =  nextMonth;
            yearMonthInt = Integer.parseInt(starTimeString);
            startTime = calendar.getTime();
        }

        return growthDiaryMonthDateList;
    }

    @Override
    public List<MonthDateSelect> getMonthDateSelect(Long userId) throws ParseException {


        User user = userService.getUser(userId);
        SimpleDateFormat formatDate = new SimpleDateFormat("yyyyMM");

        List<Integer> getMonthDataList = userGrowthDiaryDao.getMonthDataList(userId.intValue());

        Date endTime = user.getBirthday();
        Date startTime = new Date();

        Date now = new Date();

        SimpleDateFormat formatYear = new SimpleDateFormat("yyyy");
        Integer birthYearInt = Integer.parseInt(formatYear.format(user.getBirthday()));
        Integer nowInt = Integer.parseInt(formatYear.format(new Date()));

        List<MonthDateSelect> monthDateSelectList = new ArrayList<>();

        Integer age = 0;
        Integer id = 1;

        while(nowInt > birthYearInt){

            Calendar calendar = Calendar.getInstance();
            calendar.setTime(endTime);
            calendar.add(Calendar.YEAR, +1);
            startTime = calendar.getTime();

            if(startTime.getTime() > now.getTime()){
                startTime = now;
            }

            monthDateSelectList.add(
              new MonthDateSelect(id,age, this.getGrowthDiaryMonthDateList(startTime,endTime,getMonthDataList),1)
            );
            id = id + 1;
            age = age + 1;
            nowInt = nowInt - 1;
            endTime = startTime;
        }
        Collections.reverse(monthDateSelectList);
        return monthDateSelectList;
    }

    private Integer getIsShow(List<GrowthDiaryMonthDate> growthDiaryMonthDateList){
        Integer isShow = 0;
        for(GrowthDiaryMonthDate item : growthDiaryMonthDateList){
            if(item.getIsHasData().equals(1)){
                isShow = 1;
                break;
            }
        }
        return isShow;
    }
}

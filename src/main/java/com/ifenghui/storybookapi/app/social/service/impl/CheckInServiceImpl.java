package com.ifenghui.storybookapi.app.social.service.impl;

import com.ifenghui.storybookapi.app.social.dao.CheckInDao;
import com.ifenghui.storybookapi.app.social.dao.CheckInRecordDao;
import com.ifenghui.storybookapi.app.social.entity.CheckIn;
import com.ifenghui.storybookapi.app.social.entity.CheckInRecord;
import com.ifenghui.storybookapi.app.social.service.CheckInService;
import com.ifenghui.storybookapi.app.wallet.service.WalletService;
import com.ifenghui.storybookapi.exception.ApiDuplicateException;
import com.ifenghui.storybookapi.style.StarContentStyle;
import com.ifenghui.storybookapi.style.StarRechargeStyle;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

@Component
public class CheckInServiceImpl implements CheckInService {

    @Autowired
    CheckInDao checkInDao;

    @Autowired
    CheckInRecordDao checkInRecordDao;

    @Autowired
    WalletService walletService;

    @Autowired
    CheckInService checkInService;

    /**
     * 获取后N天日期
     * @param date
     * @return
     */
    @Override
    public Date getDay(Date date,Integer day) {

        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(Calendar.DAY_OF_MONTH, +day);//date的时间加day天
        date = calendar.getTime();
        return date;
    }

    /**
     * 通过userId查询签到
     * @param userId
     * @return
     */
    @Override
    public CheckIn findOneByUserId(Long userId){
        return checkInDao.findCheckInByUserId(userId.intValue());
    }

    /**
     * 保存签到历史记录
     * @return
     */
    @Override
    public CheckInRecord addCheckInRecord(Long userId, Date dateToday, Integer countDays){

        CheckInRecord checkInRecord = new CheckInRecord();
        checkInRecord.setUserId(userId.intValue());
        checkInRecord.setCheckInTime(dateToday);
        checkInRecord.setDayCount(countDays);
        checkInRecordDao.save(checkInRecord);
        return null;
    }

    @Transactional
    @Override
    public CheckIn updateCheckInAndSaveRecord(Long userId) {
        Integer hasCheckIn = this.checkInValidate(userId);
        if(hasCheckIn.equals(1)) {
            throw new ApiDuplicateException("恭喜您，今日已签到！");
        }
        //通过userId查询签到记录
        CheckIn checkIn = this.findOneByUserId(userId);
        if(checkIn == null){
            checkIn = this.addCheckIn(userId.intValue(), new Date(), 1,1);
        } else {
            checkIn = this.updateCheckIn(checkIn);
        }

        //添加签到历史记录
        checkInService.addCheckInRecord(userId,new Date(),checkIn.getCountDays());
        //设置星星数量
        Integer startCount = checkIn.getContinueDays() == 7 ? 35 : 5;
        //添加星星  INTRO  完成签到
        walletService.addStarToWallet(userId.intValue(), StarRechargeStyle.CHECK_IN, startCount, StarContentStyle.CHECK_IN.getName());
        return checkIn;
    }

    @Override
    public CheckIn addCheckIn(Integer userId, Date checkInTime, Integer countDays, Integer continueDays){
        CheckIn checkIn = new CheckIn();
        checkIn.setCheckInTime(checkInTime);
        checkIn.setUserId(userId);
        checkIn.setContinueDays(continueDays);
        checkIn.setCountDays(countDays);
        checkIn = checkInDao.save(checkIn);
        return checkIn;
    }

    @Override
    public CheckIn updateCheckIn(CheckIn checkIn){
        int continueDays;
        int countDays;
        //获得签到记录的时间
        Date checkInTime = checkIn.getCheckInTime();
        //当前时间今天
        Date dateToday = new Date();
        //下1天明天
        Date dateTomorrow = this.getDay(checkInTime, +1);
        //下2天后天
        Date dateThird = this.getDay(checkInTime, +2);
        //判断当前签到时间是否为记录的第二天
        if (dateToday.compareTo(dateTomorrow) >= 0 && dateToday.compareTo(dateThird) < 0) {
            //设置连续签到天数
            continueDays = checkIn.getContinueDays() == 7 ? 1 : checkIn.getContinueDays() + 1;
            countDays = checkIn.getCountDays() + 1;
        } else {
            continueDays = 1;
            countDays = checkIn.getCountDays() + 1;
        }
        //设置签到时间为当前时间
        checkIn.setCheckInTime(dateToday);
        //保存签到天数
        checkIn.setContinueDays(continueDays);
        checkIn.setCountDays(countDays);
        //保存签到记录
        return checkInDao.save(checkIn);
    }

    @Override
    public Integer checkInValidate(Long userId){
        Integer hasCheckIn = 0;
        CheckIn checkIn = checkInService.findOneByUserId(userId);
        if(checkIn != null){
            Date checkInTime = checkIn.getCheckInTime();
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            String strCheckInTime = sdf.format(checkInTime);//第一个时间
            String strToday = sdf.format(new Date());//第二个时间
            if (strToday.equals(strCheckInTime)) {
                hasCheckIn = 1;
            }
        }
        return hasCheckIn;
    }
}

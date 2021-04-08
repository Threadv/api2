package com.ifenghui.storybookapi.app.social.service;

import com.ifenghui.storybookapi.app.social.entity.CheckIn;
import com.ifenghui.storybookapi.app.social.entity.CheckInRecord;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;

public interface CheckInService {

    /**
     * 获取后N天日期
     * @param date
     * @return
     */
    Date getDay(Date date,Integer day);

    /**
     * 通过userId查询签到记录
     * @param userId
     * @return
     */
    CheckIn findOneByUserId(Long userId);

    /**
     * 保存签到历史记录
     * @return
     */
    CheckInRecord addCheckInRecord(Long userId, Date dateToday, Integer countDays);

    @Transactional
    public CheckIn updateCheckInAndSaveRecord(Long userId);

    public CheckIn addCheckIn(Integer userId, Date checkInTime, Integer countDays, Integer continueDays);

    public CheckIn updateCheckIn(CheckIn checkIn);

    Integer checkInValidate(Long userId);
}

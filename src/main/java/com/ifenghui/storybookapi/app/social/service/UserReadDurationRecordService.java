package com.ifenghui.storybookapi.app.social.service;

import com.ifenghui.storybookapi.app.social.response.ReadDurationRecordResponse;
import com.ifenghui.storybookapi.app.social.entity.UserReadDurationRecord;

import java.text.ParseException;
import java.util.List;

public interface UserReadDurationRecordService {

    /**
     * 增加用户阅读时长记录
     * @param storyId
     * @param userId
     * @param duration
     * @return
     */
    UserReadDurationRecord addUserDurationRecord(Long storyId, Long userId, Integer duration);

    /**
     * 获得每日统计
     * @param userId
     * @return
     */
    DayReadStatistic getReadStatistic(Long userId) throws ParseException;

    /**
     * 获得全部统计
     * @param userId
     * @return
     */
    TotalReadStatistic getTotalReadStatistic(Long userId) throws ParseException;

    /**
     * 同步阅读时长记录
     * @param readDurationRecordResponseList
     * @param userId
     */
    void addReadDurationRecordList(List<ReadDurationRecordResponse> readDurationRecordResponseList, Long userId);

}

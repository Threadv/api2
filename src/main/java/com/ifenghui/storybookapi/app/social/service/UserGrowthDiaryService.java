package com.ifenghui.storybookapi.app.social.service;

import com.ifenghui.storybookapi.app.social.entity.UserGrowthDiary;
import com.ifenghui.storybookapi.app.social.response.TaskFinish;
import org.springframework.data.domain.Page;

import java.text.ParseException;
import java.util.Date;
import java.util.List;

public interface UserGrowthDiaryService {

    /**
     * 增加成长日记
     * @param userId
     * @param content
     * @param recordTime
     * @param diaryImgList
     * @return
     */
    UserGrowthDiary addUserGrowthDiary(Integer userId, String content, Date recordTime, List<ScheduleSmallImg> diaryImgList, Integer width, Integer height, String imgPath) throws ParseException;

    public List<TaskFinish> isNeedAddUserStarRecord(Integer userId) throws ParseException;

    /**
     * 成长日记列表
     * @param userId
     * @return
     */
    Page<UserGrowthDiary> getUserGrowthDiaryPage(Integer userId, Integer monthDate,Integer pageNo, Integer pageSize);

    /**
     * 删除成长日记
     * @param userGrowthDiaryId
     * @param userId
     */
    void deleteUserGrowthDiary(Integer userGrowthDiaryId, Integer userId);

    /**
     * 补全userGrowthDiary 数据
     */
    void recoverUserGrowthDiaryData();

    List<GrowthDiaryMonthDate> getGrowthDiaryMonthDateList(Date startTime, Date endTime, List<Integer> monthDataList) throws ParseException;

    List<MonthDateSelect> getMonthDateSelect(Long userId) throws ParseException;

}

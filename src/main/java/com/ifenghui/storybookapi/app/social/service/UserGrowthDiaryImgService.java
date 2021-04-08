package com.ifenghui.storybookapi.app.social.service;

import com.ifenghui.storybookapi.app.social.response.GetUserGrowthImgPageByWeekNumResponse;
import com.ifenghui.storybookapi.app.social.entity.UserGrowthDiaryImg;
import org.springframework.data.domain.Sort;

import java.text.ParseException;
import java.util.Date;
import java.util.List;

public interface UserGrowthDiaryImgService {

    /**
     * 增加用户成长日记
     * @param diaryImgList
     * @param userId
     * @param diaryId
     */
    void addUserGrowthDiaryImgList(List<ScheduleSmallImg> diaryImgList, Integer userId, Integer diaryId, Date recordDate);

    List<UserGrowthDiaryImg> getGrowthDiaryImgList(Integer diaryId);

    /**
     * 删除用户成长记录的小图片地址
     * @param diaryId
     * @param userId
     */
    void deleteUserGrowthDiaryImg(Integer diaryId, Integer userId);

    /**
     * 补全数据
     */
    void recoverUserGrowthDiaryImgData();

    /**
     * 分页获得日记每周数据
     * @param userId
     * @param weekNum
     * @param pageNo
     * @param pageSize
     * @return
     */
    GetUserGrowthImgPageByWeekNumResponse getUserGrowthImgPageByWeekNum(Integer userId, Integer weekNum, Integer pageNo, Integer pageSize, Integer userGrowthImgId);

    /**
     * 获得全部以周为单位日记小图记录
     * @param userId
     * @return
     */
    List<GrowthDiaryWeek> getGrowthDiaryWeekList(Integer userId);

    List<GrowthDiaryDay> getGrowthDiaryDayList(Integer userId, Integer weekNum) throws ParseException;

    public UserGrowthDiaryImg getGrowthDiaryDate(Integer userId, Integer weekNum, Sort.Direction orderString);

    Integer getRsCountByUserIdAndWeekNum(Integer userId, Integer weekNum);

    public UserGrowthDiaryImg getUserGrowthDiaryImgById(Integer id);

}

package com.ifenghui.storybookapi.app.presale.service;



import com.ifenghui.storybookapi.app.social.entity.UserReadRecord;

import java.util.List;
import java.util.Map;

public interface YiZhiActivityUserReadRecordService {


    /**
     * 根据类型查询故事记录
     * @param userId
     * @param typeList
     * @return
     */
    Map<Integer,UserReadRecord> getReadRecordListByTypes(Integer userId, List<Integer> typeList, Integer activityId);

    /**
     * 查看益智阅读记录 type 7/8  活动 益智
     * @param userId
     * @return
     */
    List<UserReadRecord> getUserReadRecordList(Integer userId, Integer activityId);

    /**
     * 查看益智阅读记录 type 7/8  活动 陪娃成长
     * @param userId
     * @return
     */
    List<UserReadRecord> getUserReadRecordListByUserId(Integer userId, Integer activityId);



}

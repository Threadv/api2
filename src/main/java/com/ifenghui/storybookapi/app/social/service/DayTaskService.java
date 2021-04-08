package com.ifenghui.storybookapi.app.social.service;

import com.ifenghui.storybookapi.app.social.response.GetTaskPromptResponse;
import com.ifenghui.storybookapi.app.social.response.SynchroPartsRecordResponse;

import com.ifenghui.storybookapi.app.story.entity.Medal;
import com.ifenghui.storybookapi.exception.ApiException;
import com.ifenghui.storybookapi.style.OrderStyle;
import org.springframework.data.domain.Page;

/**
 * Created by wml on 2016/12/26.
 */
public interface DayTaskService {

//    @Deprecated
//    public Page<DayTask> getDayTaskByType(Long userId,Integer type);

//    @Deprecated
//    public void addDayTaskRecord(Long userId,Long taskId);


    public Page<Medal> getAchievementHandbook(Long userId, Integer pageNo, Integer pageSize);

    public Integer getUserCollectMedalCount(Long userId);

    /**
     *  获取故事勋章详情
     * @param storyId
     * @param userId
     * @return
     */
    Medal getMedalByStoryId(Long storyId,Long userId);


    public void collectParts(Long userId,Long storyId,String keyName);

    public void shareInviteTask(Long userId)throws ApiException;


    /**
     *  同步收集道具
     * @param userId
     * @param jsonData
     * @return
     */
    public SynchroPartsRecordResponse synchroPartsRecord(Long userId, String jsonData )throws ApiException;


    /**
     *  获取不同任务星星值获取提示
     * @param userId,type,value
     * @return
     */
    public GetTaskPromptResponse getTaskPrompt(Long userId,Integer type, String value);

    public GetTaskPromptResponse getTastPromptFromMixOrder(OrderStyle orderStyle, Integer orderId);
}

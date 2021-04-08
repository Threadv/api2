package com.ifenghui.storybookapi.app.social.service;

/**
 * Created by jia on 2016/12/23.
 */
import com.ifenghui.storybookapi.app.social.response.AddUserReadRecordResponse;
import com.ifenghui.storybookapi.app.social.entity.UserReadRecord;
import org.springframework.data.domain.Page;

import java.text.ParseException;

public interface UserReadRecordService {


    /**
     * 通过 userId   type 分页获取阅读故事记录 1 故事足迹 加type新类型
     */
    Page<UserReadRecord> getReadStoryRecordByUserIdAndType(Long userId, Integer type,Integer pageNo, Integer pageSize);
    /**
     * 通过 userId  分页获取阅读记录
     */
    public Page<UserReadRecord> getReadRecordsByUserIdAndPage(Long userId,Integer type, Integer pageNo, Integer pageSize);
    /**
     * 通过 userId +storyId 分页获取阅读记录
     */
    public Page<UserReadRecord> getReadRecordsByUserIdAndStoryId(Long userId,Long storyId, Integer pageNo, Integer pageSize);


    public Long countReadRecordsByUserIdAndStoryId(Long userId,Long storyId);

    /**
     * 判断用户是否课程是否阅读时 判断使用
     * @param userId
     * @param storyId
     * @param pageNo
     * @param pageSize
     * @return
     */
    Page<UserReadRecord> getReadRecordsByUserIdAndStoryIdWithOutStory(Long userId, Long storyId, Integer pageNo, Integer pageSize);

    public UserReadRecord addUserReadRecord(UserReadRecord userReadRecord);

    public AddUserReadRecordResponse addUserReadRecord(Long userId, Long storyId) throws ParseException;


    public void delUserReadRecord(Long id);

    /**
     * 获得第一条故事阅读记录
     * @return
     */
    public UserReadRecord getFirstStoryReadRecordByUserId(Long userId, Integer type);

    public UserReadRecord getFirstReadRecordByUserId(Long userId);
    /**
     * 获取用户最喜欢的
     * @param userId
     * @param type
     * @return
     */
    public Long getFavoriteUserReadRecord(Long userId, Integer type);



}

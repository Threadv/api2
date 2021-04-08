package com.ifenghui.storybookapi.app.story.service;

import com.ifenghui.storybookapi.app.story.entity.SerialStory;
import com.ifenghui.storybookapi.app.story.response.GetUserSerialStorysResponse;
import com.ifenghui.storybookapi.app.story.response.SerialStoryGroup;
import com.ifenghui.storybookapi.app.transaction.entity.serial.BuySerialStoryRecord;
import com.ifenghui.storybookapi.app.user.entity.User;
import com.ifenghui.storybookapi.style.SerialStoryStyle;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

import java.util.List;

/**
 * Created by wml on 2016/12/23.
 */
public interface SerialStoryService {

    /**
     * 通过类型获取故事集列表
     * @param serialStoryStyle
     * @return
     */
    List<SerialStory>  getSerialStoryBySerialType(SerialStoryStyle serialStoryStyle);
    SerialStory getSerialStory(long id);

    /**
     * 获取故事集详情
     * @param userId 用户id
     * @param serialStoryId 故事集id
     * @return
     */
    public SerialStory getSerialStoryDetail(Long userId,Long serialStoryId);

    public void setSerialStoryIsBuy(User user, SerialStory serialStory);

    SerialStory saveSerialStory(SerialStory serialStory);


    /**
     * 获取普通故事集列表（系列故事列表）
     * @param userId
     * @return
     */
    Page<SerialStory> getCommonSerialStoryPage(Long userId, SerialStoryStyle serialStoryStyle, Integer pageNo, Integer pageSize);

//    Page<SerialStory> getIndexSerialStoryPage(Long userId, Integer pageNo, Integer pageSize);

    List<SerialStoryGroup> getSerialStoryGroupList(Long userId, Integer serialStoryId);


    public List<SerialStory> getBuySerialStoryRecordByUserIdAndSerialType(Long userId, Page<BuySerialStoryRecord> buySerialStoryRecordPage);

    /**
     * 获取用户故事集
     * @param userId 用户id
     * @param pageNo 最多限制，最多32条
     * @param pageSize 最多限制，最多32条
     * @return
     */
    public GetUserSerialStorysResponse getUserSerialStorys(Long userId, Integer pageNo, Integer pageSize);


//    后台使用部分

    SerialStory addSerialStory(SerialStory serialStory);

    SerialStory updateSerialStory(SerialStory serialStory);

    SerialStory findOne(Integer id);



    /**
     * 综合查询
     * @param serialStory
     * @return
     */
    Page<SerialStory>  getSerialStoryList(SerialStory serialStory, PageRequest pageRequest);


    /**
     * 查询ip下所有合集
     * 2.11的功能
     * @param ipBrandId
     * @return
     */
    List<SerialStory> getSerialStoryByIpBrandId(Integer ipBrandId);
}

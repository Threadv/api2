package com.ifenghui.storybookapi.app.story.service;

import com.ifenghui.storybookapi.app.story.entity.StoryAudioContent;
import com.ifenghui.storybookapi.app.story.entity.StoryVideoIntro;
import com.ifenghui.storybookapi.app.analysis.entity.StoryRecommend;
import com.ifenghui.storybookapi.app.app.entity.DisplayGroup;
import com.ifenghui.storybookapi.app.story.entity.Story;
import com.ifenghui.storybookapi.app.analysis.entity.StoryHotSale;
import com.ifenghui.storybookapi.app.transaction.entity.BuyStoryRecord;
import com.ifenghui.storybookapi.app.user.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * Created by wml on 2016/12/23.
 */
public interface StoryService {
    Story getStory(long id);



    Page<Story> getStorysByCategoryId(Integer categoryId, Long userId, int pageNo, int pageSize,HttpServletRequest request);//通过内容分类查询故事
    Page<Story> getStorysBySerialId(Long serialStoryId, Long userId,int pageNo, int pageSize);//通过故事合集id查询故事
    Page<Story> getStorysByMagazineId(Long magazineId, Long userId, int pageNo, int pageSize);//通过期刊id查询故事
    Page<Story> getStorysByIsNow(Integer isNow, Long userId, Integer pageNo, Integer pageSize,HttpServletRequest request);//查询当期故事
    Page<Story> getMoreStorys(Long userId,Long storyId ,int pageNo, int pageSize);//获取更多故事

    Page<Story> getOtherStorys(Long userId,Long storyId ,int pageNo, int pageSize,HttpServletRequest request);//获取某个故事外其他故事

    Page<StoryRecommend> getRecommendStorys(Long userId, Long storyId, int pageNo, int pageSize, HttpServletRequest request);//获取推荐故事
    Page<Story> getNowStorys(Long userId,Long storyId ,int pageNo, int pageSize);//获取当期故事

    Integer getStorysCount(Long storyId);//获取所有发布故事总数

    @Deprecated
    Integer isSubscribe(Long userId);//获取用户是否订阅

    Story getStoryDetailById(Long id, Long userId);//查询故事详情
    Story getStoryBookDetailById(Long id, Long userId);//查询故事详情


    Page<Story> getStorysBySearch(Long userId, String content ,Integer pageNo,Integer pageSize, HttpServletRequest request);//模糊查询故事
    Page<Story> getNotNowStorysBySearch(Long userId, String content ,Integer pageNo,Integer pageSize, HttpServletRequest request);//模糊查询非当期故事

    List<Story> getStoryByMaps(Long userId, List<Map> maps, HttpServletRequest request);

    Page<BuyStoryRecord> getUserBuyStoryRecords(Long userId,Integer type,int pageNo, Integer pageSize);//获取用户已购买的故事

    Page<BuyStoryRecord> getUserStorys(Long userId,Integer type,Integer groupId,int pageNo, Integer pageSize);//分类获取用户已购买的故事

    public Page<BuyStoryRecord> getUserHasBuyStorysByNewType(Long userId,Integer groupId,Integer pageNo, Integer pageSize);

    List<Story> getUserBuyStorys(Long userId, Integer type, int pageNo, Integer pageSize,HttpServletRequest request);//获取用户已购买的故事
    Void addUserNewMagazineBuyRecord(Long magazineId);//

    /**
     * 设置单本故事是否已经购买，用于单本详情判断用户是否有权查看。
     * @param user
     * @param story
     */
    public void setStoryIsBuy(User user, Story story);

    public void setStoryAppFile(Story story);

    public String getStoryAppFile(Story story,Integer engineType);


    /**
     * 通过时间范围查故事
     * @param beginDate 开始时间
     * @param endDate 结束时间
     * @param pageSize 最多限制，最多32条
     * @return
     */
    Page<Story> getStorysByDateRange(Date beginDate,Date endDate, int pageSize);
    /**
     * 移动到回收站
     * @param id 故事id
     */
    public void moveToRecycle(long id);

    /**
     * 从回收站恢复
     * @param id
     */
    public void restoreByRecycle(long id);

    /**
     * 修改故事
     * @param story
     * @return
     */
    public Story saveStory(Story story);
    /**
     * 获取热销故事
     * @param userId 用户id
     * @param pageNo 最多限制，最多32条
     * @param pageSize 最多限制，最多32条
     * @return
     */
    @Deprecated
    public Page<StoryHotSale> getMoreHotSaleStorys(Long userId, Integer pageNo, Integer pageSize);//首页热销故事

    /**
     * 分组列表
     */
    public List<DisplayGroup> getStoryGroups();

    public List<DisplayGroup> getNewStoryGroups();

    public List<DisplayGroup> getStoryGroupListByNewCategory();

//    public void setSerialStoryIsBuy(User user, SerialStory serialStory);
//    /**
//     * 获取故事集列表
//     * @param userId 用户id
//     * @param pageNo
//     * @param pageSize
//     * @return
//     */
//    public Page<SerialStory> getSerialStorys(Long userId,Integer pageNo, Integer pageSize);

//    /**
//     * 获取用户故事集
//     * @param userId 用户id
//     * @param pageNo 最多限制，最多32条
//     * @param pageSize 最多限制，最多32条
//     * @return
//     */
//    public GetUserSerialStorysResponse getUserSerialStorys(Long userId, Integer pageNo, Integer pageSize);

//    public List<SerialStory> getBuySerialStoryRecordByUserIdAndSerialType(Long userId, Page<BuySerialStoryRecord> buySerialStoryRecordPage);

//    /**
//     * 获取故事集详情
//     * @param userId 用户id
//     * @param serialStoryId 故事集id
//     * @return
//     */
//    public SerialStory getSerialStoryDetail(Long userId,Long serialStoryId);

    /**
     * 获得第一个阅读的故事
     * @param userId
     * @return
     */
    public Story getFirstReadStory(Long userId);

    /**
     * 获得最爱故事
     * @param userId
     * @return
     */
    public Story getFavoriteReadStory(Long userId);

    /**
     * 获得系列故事故事详情
     * @param serialStoryId
     * @return
     */
    public List<Story> getStorySerialDetail(Long userId, Long serialStoryId);

    /**
     * 故事系列故事详情
     * @param userId
     * @param serialStoryId
     * @param parentId
     * @return
     */
    List<Story> getSecondStorySerialDetail(Long userId, Long serialStoryId, Long parentId);

    StoryVideoIntro getStoryVideoIntroByStoryId(Integer storyId);

    //获取飞船电台列表
    List<Story> getRadioList(Long userId);


    /**
     * 音频内容详情
     * @param storyId
     * @return
     */
    StoryAudioContent getStoryAudioContenById(Integer storyId);

    //后台使用

    /**
     *后台故事综合搜索
     * @param story
     * @param pageRequest
     * @return
     */
    Page<Story> findAllByStory(Story story, PageRequest pageRequest);
}

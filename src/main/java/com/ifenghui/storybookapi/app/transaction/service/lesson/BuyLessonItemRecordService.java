package com.ifenghui.storybookapi.app.transaction.service.lesson;

import com.ifenghui.storybookapi.app.story.entity.LessonItemRelate;
import com.ifenghui.storybookapi.app.transaction.entity.lesson.BuyLessonItemRecord;
import org.springframework.data.domain.Sort;

import java.util.List;

public interface BuyLessonItemRecordService {

    /**
     * 是否购买
     * @param userId
     * @param lessonItemId
     * @return
     */
    Integer isBuyLessonItem(Integer userId, Integer lessonItemId);

    /**
     * 获得购买课程章节数量
     * @param userId
     * @param lessonId
     * @return
     */
    Integer getBuyLessonItemCount(Integer userId, Integer lessonId);

    /**
     * 添加课程购买关系记录
     * @param userId
     * @param lessonId
     * @param lessonItemId
     * @return
     */
    BuyLessonItemRecord addBuyLessonItemRecord(Integer userId, Integer lessonId, Integer lessonItemId,Integer isBaobao);

    /**
     * 获取最多可以购买的课程数量
     * @param lessonId
     * @param userId
     * @return
     */
    Integer getMaxLessonNumByLessonIdAndUserId(Integer lessonId, Integer userId);

    /**
     * 获取课程里51个小课的购买记录
     * @param userId
     * @param lessonItemId
     * @return
     */
    BuyLessonItemRecord getBuyLessonItemRecordByUserIdAndLessonItemId(Integer userId, Integer lessonItemId);

    /**
     * 获取某个没有阅读过的课程购买记录
     * @param userId
     * @param lessonId
     * @param isRead
     * @param sort
     * @return
     */
    BuyLessonItemRecord getHasLessonRecord(Integer userId, Integer lessonId, Integer isRead, Sort.Direction sort);

    /**
     * 设置课程中某个课程是否能完成 通过 故事 或者 视频 完成时候
     * @param storyId
     * @param userId
     * @param isVideo
     */
    void setStoryOfLessonItemIsRead(Long storyId, Long userId, boolean isVideo);

    /**
     * 通过 课程内容的关联信息 判断是否已经阅读全部小内容
     * @param userId
     * @param lessonItemRelateList
     * @return
     */
    Integer getIsReadAllStoryByLessonItemRelateList(Long userId,List<LessonItemRelate> lessonItemRelateList);

    /**
     * 获取某个课程已经阅读的课程数量
     * @param lessonId
     * @param userId
     * @return
     */
    public Integer getHasReadLessonCount(Integer lessonId, Integer userId);

    /**
     * 查询是否阅读了免费的课程
     * @param userId
     * @return
     */
    public boolean isReadFreeLesson(Integer userId);

    /**
     * 变更
     * @param buyLessonItemRecord
     * @return
     */
    public BuyLessonItemRecord update(BuyLessonItemRecord buyLessonItemRecord);
}

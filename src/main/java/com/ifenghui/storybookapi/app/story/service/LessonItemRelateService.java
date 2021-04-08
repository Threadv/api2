package com.ifenghui.storybookapi.app.story.service;

import com.ifenghui.storybookapi.app.story.entity.LessonItem;
import com.ifenghui.storybookapi.app.story.entity.LessonItemRelate;

import java.util.List;

public interface LessonItemRelateService {

    /**
     * 获得LessonItemRelate 列表
     * @param itemId
     * @return
     */
    List<LessonItemRelate> getLessonItemRelateList(Integer itemId, Long userId);

    /**
     * 已经购买返回true，未购买返回false
     * @param storyId
     * @param userId
     * @return 已经购买返回true，未购买返回false
     */
    boolean checkIsBuy(int storyId, int userId);

    public List<LessonItemRelate> getLessonItemListByStoryId(LessonItemRelate lessonItemRelate);

    /**
     * 增加免费课程章节购买记录
     * @param userId
     * @param lessonItem
     * @param lessonItemRelateList
     */
    public void checkAndAddBuyLessonItemRecord(Integer userId, LessonItem lessonItem, List<LessonItemRelate> lessonItemRelateList);

    /**
     * 根据是否是视频获得课程章节内容关联数据
     * @param storyId
     * @param isVideo
     * @return
     */
    LessonItemRelate getLessonItemRelateByStoryIdAndIsVideo(Integer storyId, boolean isVideo);
}

package com.ifenghui.storybookapi.app.story.service;

import com.ifenghui.storybookapi.app.story.entity.LessonItem;
import com.ifenghui.storybookapi.app.story.response.lesson.GetLessonItemListResponse;
import com.ifenghui.storybookapi.app.transaction.entity.lesson.BuyLessonItemRecord;
import org.springframework.data.domain.Page;

import java.util.List;

public interface LessonItemService {

    /**
     * 获得课程章节列表
     * @param lessonId
     * @return
     */
    GetLessonItemListResponse getLessonItemListByLessonId(Integer lessonId, Integer userId);

    /**
     * 获取可以够买的课程列表
     * @param maxLessonNum
     * @param orderNum
     * @param lessonId
     * @return
     */
    List<LessonItem> getNeedBuyLessonItemList(Integer maxLessonNum, Integer orderNum, Integer lessonId);

    Integer getMaxLessonNumInLessonItems(Integer lessonId);

    LessonItem getLessonItemById(Integer itemId);

    Integer getLessonHasUpdateNumByLessonId(Integer lessonId);
}

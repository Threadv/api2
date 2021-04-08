package com.ifenghui.storybookapi.app.transaction.service.lesson;

import com.ifenghui.storybookapi.app.story.entity.Lesson;
import com.ifenghui.storybookapi.app.transaction.entity.lesson.PayLessonPrice;

import java.util.List;

public interface PayLessonPriceService {

    /**
     * 购买课程价格列表
     * @param lessonId
     * @return
     */
    List<PayLessonPrice> getPayLessonPriceList(Integer lessonId, Integer userId);

    /**
     * 获取课程的配置价格
     * @return
     */
    List<PayLessonPrice> getPayLessonPriceList();

    /**
     * 获得剩余价格折扣
     * @return
     */
    Integer getRemainPayLessonPriceDiscount(Integer lessonItemCount);

    /**
     * 获取课程价格对象 并且设置购买课程数量
     * @param payLessonPriceId
     * @param lessonNum
     * @return
     */
    PayLessonPrice getPayLessonPrice(Integer payLessonPriceId, Integer lessonNum);

    /**
     * 根据课程 价格id 和 课程id 获取对应课程价格对象
     * @param priceId
     * @param lessonId
     * @return
     */
    PayLessonPrice getPayLessonPriceItem(Integer priceId, Integer lessonId);

}

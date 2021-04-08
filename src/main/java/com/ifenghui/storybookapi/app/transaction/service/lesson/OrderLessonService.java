package com.ifenghui.storybookapi.app.transaction.service.lesson;

import com.ifenghui.storybookapi.app.transaction.entity.lesson.OrderLesson;

public interface OrderLessonService {

    /**
     * 添加 课程 和 订单 关联
     * @param userId
     * @param orderId
     * @param lessonId
     * @param lessonItemId
     * @return
     */
    OrderLesson addOrderLesson(Integer userId, Integer orderId, Integer lessonId, Integer lessonItemId);

}

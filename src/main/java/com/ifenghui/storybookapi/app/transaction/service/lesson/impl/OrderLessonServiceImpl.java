package com.ifenghui.storybookapi.app.transaction.service.lesson.impl;

import com.ifenghui.storybookapi.app.transaction.dao.lesson.OrderLessonDao;
import com.ifenghui.storybookapi.app.transaction.entity.lesson.OrderLesson;
import com.ifenghui.storybookapi.app.transaction.service.lesson.OrderLessonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
public class OrderLessonServiceImpl implements OrderLessonService {

    @Autowired
    OrderLessonDao orderLessonDao;

    @Override
    public OrderLesson addOrderLesson(Integer userId, Integer orderId, Integer lessonId, Integer lessonItemId) {
        OrderLesson orderLesson = new OrderLesson();
        orderLesson.setUserId(userId);
        orderLesson.setCreateTime(new Date());
        orderLesson.setOrderId(orderId);
        orderLesson.setLessonItemId(lessonItemId);
        orderLesson.setLessonId(lessonId);
        orderLesson = orderLessonDao.save(orderLesson);
        return orderLesson;
    }


}

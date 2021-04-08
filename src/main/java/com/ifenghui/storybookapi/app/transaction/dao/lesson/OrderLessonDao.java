package com.ifenghui.storybookapi.app.transaction.dao.lesson;

import com.ifenghui.storybookapi.app.transaction.entity.lesson.OrderLesson;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderLessonDao extends JpaRepository<OrderLesson, Integer> {
}

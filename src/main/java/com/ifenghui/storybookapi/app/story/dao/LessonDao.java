package com.ifenghui.storybookapi.app.story.dao;

import com.ifenghui.storybookapi.app.story.entity.Lesson;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface LessonDao extends JpaRepository<Lesson, Integer> {

    @Cacheable(cacheNames = "Lesson_findAll",key = "'Lesson_findAll'+#p0")
    List<Lesson> findAllByStatus(Integer status);
}

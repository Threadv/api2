package com.ifenghui.storybookapi.app.story.dao;

import com.ifenghui.storybookapi.app.story.entity.LessonItem;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface LessonItemDao extends JpaRepository<LessonItem, Integer> {


    @Query("select item from LessonItem item where item.lessonId=:lessonId and item.lessonNum <:lessonNum and item.status=1")
    List<LessonItem> findAllByLessonIdAndLessonNum(
            @Param("lessonId") Integer lessonId,
            @Param("lessonNum") Integer lessonNum,
            Sort sort
    );

    @Cacheable(cacheNames = "LessonItem_findOne_v1_",key = "'LessonItem_findOne_v1_'+#p0")
    @Override
    LessonItem findOne(Integer id);

    @Query("select item from LessonItem as item where item.lessonId=:lessonId and item.lessonNum >:lessonNum and item.isFree=:isFree")
    Page<LessonItem> getLessonItemsByMaxLessonNum(
        @Param("lessonId") Integer lessonId,
        @Param("lessonNum") Integer lessonNum,
        @Param("isFree") Integer isFree,
        Pageable pageable
    );

    @Query("select max(item.lessonNum) from LessonItem  as item where item.lessonId=:lessonId")
    Integer getMaxLessonNumInLessonItems(
        @Param("lessonId") Integer lessonId
    );

    @Query("select count(item) from LessonItem as item where item.lessonId =:lessonId and item.hasContent =:hasContent")
    Integer getHasContentLessonItemCount(
        @Param("lessonId") Integer lessonId,
        @Param("hasContent") Integer hasContent
    );
}

package com.ifenghui.storybookapi.app.story.dao;

import com.ifenghui.storybookapi.app.story.entity.LessonItemRelate;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.Caching;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface LessonItemRelateDao extends JpaRepository<LessonItemRelate, Integer> {

    /**
     * 类型总数带缓存，用户判断故事是否包含在课程中
     * @param storyId
     * @return
     */
    @Cacheable(cacheNames = "lessonItemRelate_countByStoryId",key = "'lessonItemRelate_countByStoryId'+#root.args[0]")
    @Query("select count(item) from LessonItemRelate as item where item.storyId =:storyId and item.type < 3")
    public Long countByStoryId(
            @Param("storyId") Integer storyId
    );

    @Cacheable(cacheNames = "lessonItemRelate_findOneByStoryId",key = "'lessonItemRelate_findOneByStoryId'+#root.args[0]")
    @Query("select item from LessonItemRelate as item where item.storyId =:storyId and item.type < 3")
    public LessonItemRelate findOneByStoryId(
        @Param("storyId") Integer storyId
    );
    @Cacheable(cacheNames = "lessonItemRelate_findOneByVideoId",key = "'lessonItemRelate_findOneByVideoId'+#root.args[0]")
    @Query("select item from LessonItemRelate as item where item.storyId =:storyId and item.type = 3")
    public LessonItemRelate findOneByVideoId(
            @Param("storyId") Integer storyId
    );

    @Override
    @Caching(evict = {@CacheEvict(cacheNames = "lessonItemRelate_findOneByStoryId",key = "'lessonItemRelate_findOneByStoryId'+#root.args[0].storyId"),
            @CacheEvict(cacheNames = "lessonItemRelate_countByStoryId",key = "'lessonItemRelate_countByStoryId'+#root.args[0].storyId"),
            @CacheEvict(cacheNames = "lessonItemRelate_findOneByVideoId",key = "'lessonItemRelate_findOneByVideoId'+#root.args[0].storyId")})
    LessonItemRelate save(LessonItemRelate lessonItemRelate);

}

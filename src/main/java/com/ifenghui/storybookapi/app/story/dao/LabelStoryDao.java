package com.ifenghui.storybookapi.app.story.dao;

import com.ifenghui.storybookapi.app.story.entity.LabelStory;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.jpa.repository.JpaRepository;

import javax.transaction.Transactional;
import java.util.List;


/**
 * Created by wml on 2016/12/23.
 */
@Transactional
public interface LabelStoryDao extends JpaRepository<LabelStory, Long> {
    @Cacheable(cacheNames = "findAllStoryLabelsByStoryId",key = "'findAllStoryLabelsByStoryId'+#p0")
    List<LabelStory> findAllStoryLabelsByStoryId(Long storyId);

    @Override
    @Cacheable(cacheNames = "findAllStoryLabelsByStoryId",key = "'findAllStoryLabelsByStoryId'+#p0.storyId")
    LabelStory save(LabelStory labelStory);

}
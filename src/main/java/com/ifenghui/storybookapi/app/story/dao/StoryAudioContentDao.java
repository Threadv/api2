package com.ifenghui.storybookapi.app.story.dao;

import com.ifenghui.storybookapi.app.story.entity.StoryAudioContent;
import org.springframework.data.jpa.repository.JpaRepository;
import javax.transaction.Transactional;

/**
 * Created by wml on 2016/12/23.
 */
@Transactional
public interface StoryAudioContentDao extends JpaRepository<StoryAudioContent, Integer> {

    /**
     * @param storyId
     * @return
     */
    StoryAudioContent findByStoryId(Integer storyId);
}
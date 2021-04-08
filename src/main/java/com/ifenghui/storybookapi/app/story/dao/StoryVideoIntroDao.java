package com.ifenghui.storybookapi.app.story.dao;

import com.ifenghui.storybookapi.app.story.entity.StoryVideoIntro;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface StoryVideoIntroDao extends JpaRepository<StoryVideoIntro, Integer> {

    @Query("select a from StoryVideoIntro as a where a.storyId=:storyId")
    StoryVideoIntro findStoryVideoIntroByStoryId(@Param("storyId") Integer storyId);

}

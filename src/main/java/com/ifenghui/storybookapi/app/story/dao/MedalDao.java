package com.ifenghui.storybookapi.app.story.dao;

import com.ifenghui.storybookapi.app.story.entity.Medal;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

/**
 * Created by wml on 2017/4/10.
 */
//@Transactional
@Deprecated
public interface MedalDao extends JpaRepository<Medal, Long> {

    @Query("select m from  Medal as m where m.storyId = :storyId")
    Medal  getOneByStoryId(@Param("storyId") Long storyId);

}
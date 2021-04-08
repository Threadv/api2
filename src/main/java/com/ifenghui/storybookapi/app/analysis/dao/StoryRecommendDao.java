package com.ifenghui.storybookapi.app.analysis.dao;


import com.ifenghui.storybookapi.app.analysis.entity.StoryRecommend;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import javax.transaction.Transactional;

/**
/**
 * Created by wml on 2017/4/17.
 */
@Transactional
public interface StoryRecommendDao extends JpaRepository<StoryRecommend, Long> {

    @Query("from StoryRecommend s where s.relStoryId =:relStoryId and s.status=1")
    Page<StoryRecommend> getStoryRecommends(@Param("relStoryId")Long relStoryId, Pageable pageable);
}

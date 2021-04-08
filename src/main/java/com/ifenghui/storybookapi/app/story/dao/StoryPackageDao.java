package com.ifenghui.storybookapi.app.story.dao;

import com.ifenghui.storybookapi.app.story.entity.StoryPackage;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import javax.transaction.Transactional;
import java.util.List;

/**
 * Created by jia on 2017/3/27.
 */

@Transactional
public interface StoryPackageDao extends JpaRepository<StoryPackage, Integer> {

    List<StoryPackage> findByStoryId(long storyId);

    @Cacheable(cacheNames = "findByStoryIdAndEngineTypeAndStatus_v5",key = "'findByStoryIdAndEngineTypeAndStatus_v5'+#p0+'_'+#p1+'_'+#p2")
    List<StoryPackage> findByStoryIdAndEngineTypeAndStatus(Integer storyId, Integer engineType, Integer status, Pageable pageable);

    @Override
    @CacheEvict(cacheNames = "findByStoryIdAndEngineTypeAndStatus_v5",key = "'findByStoryIdAndEngineTypeAndStatus_v5'+#p0.storyId+'_'+#p0.engineType+'_'+#p0.status")
    StoryPackage save(StoryPackage storyPackage);
}

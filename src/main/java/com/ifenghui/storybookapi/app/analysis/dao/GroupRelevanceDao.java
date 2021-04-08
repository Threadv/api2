package com.ifenghui.storybookapi.app.analysis.dao;

import com.ifenghui.storybookapi.app.analysis.entity.GroupRelevance;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import javax.transaction.Transactional;
import java.util.List;


/**
 * Created by wml on 2016/12/27.
 */
@Transactional
public interface GroupRelevanceDao extends JpaRepository<GroupRelevance, Long> {


    @Query("select g from GroupRelevance as g where g.groupId=:groupId and g.status=:status and isDel=0")
    Page<GroupRelevance> getGroupRelevancesByGroupIdAndStatus(
            @Param("groupId") Long groupId,
            @Param("status") Integer status,
            Pageable pageable
    );

    /**
     * 带缓存的分组内容，用于orderby id desc
     * @param groupId
     * @param status
     * @param pageable
     * @return
     */
    @Cacheable(cacheNames = "getGroupRelevancesByGroupIdAndStatus",key = "'getGroupRelevancesByGroupIdAndStatus'+#p0+'_'+#p1+'_'")
    @Query("select g from GroupRelevance as g where g.groupId=:groupId and g.status=:status and isDel=0")
    Page<GroupRelevance> getGroupRelevancesByGroupIdAndStatus_cache(
            @Param("groupId") Long groupId,
            @Param("status") Integer status,
            Pageable pageable
    );

    List<GroupRelevance> findGroupRelevancesByGroupIdAndStoryId(Long groupId, Long storyId);

    GroupRelevance findByGroupIdAndStoryId(Long groupId,Long StoryId);


    @CacheEvict(cacheNames = "getGroupRelevancesByGroupIdAndStatus",allEntries = true)
    GroupRelevance save(GroupRelevance groupRelevance);

    @CacheEvict(cacheNames = "getGroupRelevancesByGroupIdAndStatus",allEntries = true)
    void delete(Long id);
}
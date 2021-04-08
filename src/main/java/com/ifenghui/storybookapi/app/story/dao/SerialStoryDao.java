package com.ifenghui.storybookapi.app.story.dao;

import com.ifenghui.storybookapi.app.story.entity.SerialStory;
import org.hibernate.boot.model.source.spi.Sortable;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.Caching;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import javax.transaction.Transactional;
import java.util.List;


/**
 * Created by wml on 2016/12/23.
 */
@Transactional
public interface SerialStoryDao extends JpaRepository<SerialStory, Long> {

    List<SerialStory> findSerialStoriesByTypeAndStatus(Integer type ,Integer status);

    @Query("select serialStory from SerialStory as serialStory where serialStory.type <= 2 and (serialStory.status = 1 or serialStory.status = 2)")
    Page<SerialStory> getCommonSerialStoryAndMagazinePage(
            Pageable pageable
    );

    @Query("select serialStory from SerialStory as serialStory where serialStory.type =:serialType and (serialStory.status = 1 or serialStory.status = 2)")
    Page<SerialStory> getCommonSerialStoryPage(
            Pageable pageable,
            @Param("serialType") Integer serialType
    );

    @Cacheable(cacheNames = "getCommonSerialStoryPageOnCache",key = "'getCommonSerialStoryPageOnCache'+#p0")
    @Query("select serialStory from SerialStory as serialStory where serialStory.type =:serialType and (serialStory.status = 1 or serialStory.status = 2)")
    List<SerialStory> getCommonSerialStoryPageOnCache(
            @Param("serialType") Integer serialType,
            Sort sort
    );

    @Query("select s from SerialStory as s where s.parentId=:parentId order by s.orderBy desc")
    List<SerialStory> getSerialStoriesByParentId(
            @Param("parentId") Integer parentId
    );

    @Override
    @Cacheable(cacheNames = "SerialStory_findOne_v3",key = "'SerialStory_findOne_v3'+#p0")
    SerialStory findOne(Long id);



    @Caching(evict = {
            @CacheEvict(cacheNames = "SerialStory_findOne_v3",key = "'SerialStory_findOne_v3'+#p0.id"),
            @CacheEvict(cacheNames = "getCommonSerialStoryPageOnCache",key = "'getCommonSerialStoryPageOnCache'+#p0.type")
    })
    @Override
    SerialStory save(SerialStory serialStory);
}
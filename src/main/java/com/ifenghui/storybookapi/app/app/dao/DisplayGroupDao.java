package com.ifenghui.storybookapi.app.app.dao;

import com.ifenghui.storybookapi.app.app.entity.DisplayGroup;
//import com.ifenghui.storybookapi.entity.Story;
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
public interface DisplayGroupDao extends JpaRepository<DisplayGroup, Long> {

    List<DisplayGroup> getDisplayGroupsByStatus(Integer status, Pageable pageable);

    @Query("from DisplayGroup g where g.status =:status  and g.targetType!=:targetType")
    Page<DisplayGroup> getGroupsNoTargetType(@Param("targetType")Integer targetType,@Param("status")Integer status,Pageable pageable);

    List<DisplayGroup> getDisplayGroupsByTargetTypeAndStatus(Integer targetType,Integer status);

    List<DisplayGroup> getDisplayGroupsByTargetType(Integer targetType);

    @Override
    DisplayGroup save(DisplayGroup displayGroup);

    @Cacheable(cacheNames = "getDisplayGroupsByNewCategory",key = "'getDisplayGroupsByNewCategory'")
    @Query("select g from DisplayGroup as g where g.id=9 or g.id=10 or g.id=11 or g.id=12 or g.id=14 order by g.orderBy asc")
    List<DisplayGroup> getDisplayGroupsByNewCategory();


    @Cacheable(cacheNames = "DisplayGroup_findOne",key = "'DisplayGroup_findOne'+#p0")
    @Override
    DisplayGroup findOne(Long id);
}
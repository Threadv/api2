package com.ifenghui.storybookapi.app.story.dao;

import com.ifenghui.storybookapi.app.story.entity.Magazine;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import javax.transaction.Transactional;
import java.util.Date;
import java.util.List;


/**
 * Created by wml on 2016/2/15.
 */
@Transactional

public interface MagazineDao extends JpaRepository<Magazine, Long> {

    Magazine findOneByStatusAndIsNow(Integer status,Integer isNow);

    List<Magazine> findByIsNow(Integer isNow);
    @Query("from Magazine m where m.isNow =:isNow and m.status=1 ")
    List<Magazine> getMagazinesByIsNow(@Param("isNow")Integer isNow);

    @Query("from Magazine m where m.isNow =:isNow and m.status=1 ")
    Page<Magazine> getMagazinePageByIsNow(@Param("isNow")Integer isNow,Pageable magazinePageable);

    @Query("from Magazine m where m.status>0 and (m.publishTime>=:startTime and m.publishTime<:endTime)")
    List<Magazine> getSubscriptionSchedules(@Param("startTime") Date startTime,@Param("endTime") Date endTime);

}
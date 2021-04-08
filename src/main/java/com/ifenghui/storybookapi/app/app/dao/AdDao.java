package com.ifenghui.storybookapi.app.app.dao;

import com.ifenghui.storybookapi.app.app.entity.Ads;
import com.ifenghui.storybookapi.app.app.entity.Ads2;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import javax.transaction.Transactional;
import java.util.List;


/**
 * Created by wml on 2016/12/27.
 */
@Transactional
public interface AdDao extends JpaRepository<Ads, Long>,JpaSpecificationExecutor {


    List<Ads> getAdsByStatus(Integer status, Pageable pageable);

    @Query("from Ads s where s.status =:status and platform like CONCAT('%',:platform,'%') and s.isIosVisual=1 and id != 17 and s.adsPosition=1")
    Page<Ads> getAdsByStatusAndIsIosVisual(@Param("status") Integer status,@Param("platform") String platform, Pageable pageable);


    @Cacheable(cacheNames = "ship_index_ads_v2",key = "'ship_index_ads_v4'+#p0+'_'+#p1")
    @Query("from Ads s where s.status =:status and platform like CONCAT('%',:platform,'%') and s.isIosVisual=1 and id !=17 and s.adsPosition=1")
    List<Ads> getAdsByStatusExceptId(@Param("status") Integer status,@Param("platform") String platform, Sort sort);

    @Query("select s from Ads as s where s.status=:status and s.adsPosition=:adsPosition")
    List<Ads> getAdsByStatusAndAdsPosition(
        @Param("status") Integer status,
        @Param("adsPosition") Integer adsPosition,
        Pageable pageable
    );

    @Query("select s from Ads as s where s.status=:status and s.adsPosition=:adsPosition")
    List<Ads2> getAds2ByStatusAndAdsPosition(
        @Param("status") Integer status,
        @Param("adsPosition") Integer adsPosition,
        Pageable pageable
    );
}
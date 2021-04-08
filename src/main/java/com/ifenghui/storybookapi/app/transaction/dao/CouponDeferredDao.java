package com.ifenghui.storybookapi.app.transaction.dao;

import com.ifenghui.storybookapi.app.transaction.entity.coupon.CouponDeferred;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import javax.transaction.Transactional;


/**
 * Created by wml on 2017/6/22.
 */
@Transactional
public interface CouponDeferredDao extends JpaRepository<CouponDeferred, Long> ,JpaSpecificationExecutor {

//    @Query("from Story s where s.magazineId =:magazineId  and s.isDel=0")
//    Page<Story> getStorysByMagazineId(@Param("magazineId") Long magazineId, Pageable pageable);
    

}
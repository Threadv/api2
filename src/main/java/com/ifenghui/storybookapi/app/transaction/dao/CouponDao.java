package com.ifenghui.storybookapi.app.transaction.dao;

import com.ifenghui.storybookapi.app.transaction.entity.coupon.Coupon;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import javax.transaction.Transactional;


/**
 * Created by wml on 2016/12/23.
 */
@Transactional
public interface CouponDao extends JpaRepository<Coupon, Long> ,JpaSpecificationExecutor {

//    @Query("from Story s where s.magazineId =:magazineId  and s.isDel=0")
//    Page<Story> getStorysByMagazineId(@Param("magazineId") Long magazineId, Pageable pageable);
    

}
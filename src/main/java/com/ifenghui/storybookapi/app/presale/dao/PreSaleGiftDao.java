package com.ifenghui.storybookapi.app.presale.dao;

import com.ifenghui.storybookapi.app.presale.entity.PreSaleGift;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface PreSaleGiftDao extends JpaRepository<PreSaleGift, Integer> {


    @Query("select gift FROM PreSaleGift  as gift where gift.userId=:userId and gift.activityId =:activityId")
    PreSaleGift getOneByUserIdAndActivityId(@Param("userId") Integer userId, @Param("activityId")Integer activityId);



    @Query("select item from PreSaleGift as item where item.userId=:userId")
    Page<PreSaleGift> getPreSaleGiftsByUserId(
        @Param("userId") Integer userId,
        Pageable pageable
    );
    /**
     * payId获取礼品信息
     * @param payId
     * @return
     */
    @Query("select gift from PreSaleGift as gift where gift.payId=:payId")
    PreSaleGift getGiftByPayId(@Param("payId") Integer payId);

    /**
     * userId activityId获取礼品列表
     * @param userId
     * @param activityId
     * @return
     */
    @Query("select g from PreSaleGift  as g where g.userId =:userId and g.activityId =:activityId")
    List<PreSaleGift> getGiftListByUserIdAndActivityId(@Param("userId") Integer userId, @Param("activityId") Integer activityId);


}

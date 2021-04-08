package com.ifenghui.storybookapi.app.presale.dao;

import com.ifenghui.storybookapi.app.presale.entity.PreSalePay;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface PreSalePayDao extends JpaRepository<PreSalePay, Integer> {

    @Query("select pay from PreSalePay as pay where pay.goodsId=:goodsId and pay.userId=:userId")
    List<PreSalePay> getPreSalePayByGoodsIdAndUserId(
            @Param("goodsId") Integer goodsId,
            @Param("userId") Integer userId
    );

    @Query("select p from PreSalePay  as p where p.userId =:userId and p.goodsId =:goodsId and p.activityId =:activityId and p.status =1")
    List<PreSalePay> getPayByUserIdAndGoodsId( @Param("userId") Integer userId, @Param("goodsId") Integer goodsId, @Param("activityId") Integer activityId);

    @Query("select p from PreSalePay  as p where  p.goodsId =:goodsId and p.activityId =:activityId and p.status =1")
    List<PreSalePay> findPayList(@Param("goodsId") Integer goodsId, @Param("activityId") Integer activityId);



}

package com.ifenghui.storybookapi.app.presale.dao;



import com.ifenghui.storybookapi.app.presale.entity.PreSaleGiftCheck;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface GiftCheckDao extends JpaRepository<PreSaleGiftCheck, Integer> {


    /**
     * 查找校验礼物领取记录
     * @param userId
     * @param goodsId
     * @param activityId
     * @return
     */
    @Query("select g from PreSaleGiftCheck  as g where g.userId=:userId and g.goodsId = :goodsId and g.activityId = :activityId")
    PreSaleGiftCheck  findOne(@Param("userId") Integer userId, @Param("goodsId") Integer goodsId, @Param("activityId") Integer activityId);
}

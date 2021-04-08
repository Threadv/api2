package com.ifenghui.storybookapi.app.shop.dao;

import com.ifenghui.storybookapi.app.shop.entity.ShopGoodsImg;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Transactional
public interface ShopGoodsImgDao extends JpaRepository<ShopGoodsImg, Integer> {


    @Query("from ShopGoodsImg as img where img.goodsId =:goodsId")
    List<ShopGoodsImg> findGoodsImgByGoodsId(@Param("goodsId") Integer goodsId);
}

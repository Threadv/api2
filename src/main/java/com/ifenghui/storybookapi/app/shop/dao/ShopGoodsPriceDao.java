package com.ifenghui.storybookapi.app.shop.dao;

import com.ifenghui.storybookapi.app.shop.entity.ShopGoods;
import com.ifenghui.storybookapi.app.shop.entity.ShopGoodsPrice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Transactional
public interface ShopGoodsPriceDao extends JpaRepository<ShopGoodsPrice, Integer> {


    @Query(" select  price from ShopGoodsPrice as price where price.id =:id and price.status = 1 ")
    ShopGoodsPrice findByIdAndStatus(@Param("id") Integer id);

    @Query(" from ShopGoodsPrice as price where price.goodsId =:goodsId and price.status = 1 order by price.orderBy desc")
    List<ShopGoodsPrice> findPriceListByGoodsId(@Param("goodsId") Integer goodsId);
}

package com.ifenghui.storybookapi.app.shop.dao;

import com.ifenghui.storybookapi.app.shop.entity.ShopGoods;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Transactional
public interface ShopGoodsDao extends JpaRepository<ShopGoods, Integer> {


    @Query("from ShopGoods as gooods where gooods.categoryId =:categoryId and gooods.status=1")
    List<ShopGoods> findGoodsListByCategoryId(@Param("categoryId") Integer categoryId);
}

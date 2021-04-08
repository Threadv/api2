package com.ifenghui.storybookapi.app.shop.service;

import com.ifenghui.storybookapi.app.shop.entity.ShopGoods;
import com.ifenghui.storybookapi.app.shop.entity.ShopGoodsImg;


import java.util.List;


public interface ShopGoodsService {

    /**
     * id查询商品
     * @param id
     * @return
     */
    ShopGoods findById(Integer id);

    /**
     * 根据商品类型查询商品
     * @param categoryId
     * @return
     */
    List<ShopGoods> findGoodsListByCategoryId(Integer categoryId);

    /**
     * 商品图片
     * @param goodsId
     * @return
     */
    List<ShopGoodsImg> findGoodsImgByGoodsId(Integer goodsId);
}

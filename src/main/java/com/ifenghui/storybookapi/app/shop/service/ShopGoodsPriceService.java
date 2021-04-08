package com.ifenghui.storybookapi.app.shop.service;


import com.ifenghui.storybookapi.app.shop.entity.ShopGoodsPrice;

import java.util.List;

public interface ShopGoodsPriceService {


    /**
     * 查询价格
     * @param id
     * @return
     */
    ShopGoodsPrice findPriceById(Integer id);

    /**
     * 商品价格列表 用户身份
     * @param userId
     * @param goodsId
     * @return
     */
    List<ShopGoodsPrice> findPriceListByGoodsIdAndUserID(Integer userId,Integer goodsId);


    /**
     * 商品价格列表 全部
     * @param goodsId
     * @return
     */
    List<ShopGoodsPrice> findPriceListByGoodsId(Integer goodsId);

}

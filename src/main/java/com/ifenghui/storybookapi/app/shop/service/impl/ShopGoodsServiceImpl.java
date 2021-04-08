package com.ifenghui.storybookapi.app.shop.service.impl;

import com.ifenghui.storybookapi.app.shop.dao.ShopGoodsDao;
import com.ifenghui.storybookapi.app.shop.dao.ShopGoodsImgDao;
import com.ifenghui.storybookapi.app.shop.entity.ShopGoods;
import com.ifenghui.storybookapi.app.shop.entity.ShopGoodsImg;
import com.ifenghui.storybookapi.app.shop.service.ShopGoodsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Component
@Transactional
public class ShopGoodsServiceImpl implements ShopGoodsService {

    @Autowired
    ShopGoodsDao shopGoodsDao;
    @Autowired
    ShopGoodsImgDao shopGoodsImgDao;


    @Override
    public ShopGoods findById(Integer id) {
        ShopGoods goods = shopGoodsDao.findOne(id);
        return goods;
    }

    @Override
    public List<ShopGoods> findGoodsListByCategoryId(Integer categoryId) {
        List<ShopGoods> list = shopGoodsDao.findGoodsListByCategoryId(categoryId);
        return list;
    }

    @Override
    public List<ShopGoodsImg> findGoodsImgByGoodsId(Integer goodsId) {

        List<ShopGoodsImg> shopGoodsImgList = shopGoodsImgDao.findGoodsImgByGoodsId(goodsId);
        return shopGoodsImgList;
    }
}

package com.ifenghui.storybookapi.app.presale.service.impl;

import com.ifenghui.storybookapi.app.presale.dao.PreSaleGoodsDao;
import com.ifenghui.storybookapi.app.presale.entity.PreSaleGoods;
import com.ifenghui.storybookapi.app.presale.service.PreSaleGoodsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;


@Transactional
@Component
public class PreSalePreSaleGoodsServiceImpl implements PreSaleGoodsService {

    @Autowired
    PreSaleGoodsDao preSaleGoodsDao;

    @Override
    public PreSaleGoods findGoodsById(Integer goodId) {

        PreSaleGoods preSaleGoods = preSaleGoodsDao.findOne(goodId);
        return preSaleGoods;
    }
    @Override
    public Page<PreSaleGoods> findAll(PreSaleGoods preSaleGoods, PageRequest pageRequest) {
        return preSaleGoodsDao.findAll(Example.of(preSaleGoods),pageRequest);
    }

    @Override
    public PreSaleGoods save(PreSaleGoods preSaleGoods) {
        return preSaleGoodsDao.save(preSaleGoods);
    }

    @Override
    public void delete(Integer id) {
        preSaleGoodsDao.delete(id);
    }
}

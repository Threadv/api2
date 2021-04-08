package com.ifenghui.storybookapi.app.presale.service;

import com.ifenghui.storybookapi.app.presale.entity.PreSaleGoods;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

public interface PreSaleGoodsService {

   PreSaleGoods findGoodsById(Integer goodId);


   Page<PreSaleGoods> findAll(PreSaleGoods preSaleGoods, PageRequest pageRequest);

   PreSaleGoods save(PreSaleGoods preSaleGoods);

   void delete(Integer id);

}

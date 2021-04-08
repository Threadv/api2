package com.ifenghui.storybookapi.app.shop.service.impl;

import com.ifenghui.storybookapi.app.shop.dao.ShopGoodsPriceDao;
import com.ifenghui.storybookapi.app.shop.entity.ShopGoodsPrice;
import com.ifenghui.storybookapi.app.shop.service.ShopGoodsPriceService;
import com.ifenghui.storybookapi.app.user.entity.User;
import com.ifenghui.storybookapi.app.user.service.UserService;
import com.ifenghui.storybookapi.style.SvipStyle;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Component
@Transactional
public class ShopGoodsPriceServiceImpl implements ShopGoodsPriceService {

    @Autowired
    ShopGoodsPriceDao shopGoodsPriceDao;

    @Autowired
    UserService userService;

    /**
     * 查询价格
     *
     * @param id
     * @return
     */
    @Override
    public ShopGoodsPrice findPriceById(Integer id) {

        ShopGoodsPrice shopGoodsPrice = shopGoodsPriceDao.findByIdAndStatus(id);
        return shopGoodsPrice;
    }

    @Override
    public List<ShopGoodsPrice> findPriceListByGoodsIdAndUserID(Integer userId, Integer goodsId) {

        List<ShopGoodsPrice> priceList = shopGoodsPriceDao.findPriceListByGoodsId(goodsId);
        List<ShopGoodsPrice> newPriceList = new ArrayList<>();
        //用户信息 token
        if (userId != 0) {
            User user = userService.getUser(userId.longValue());
            //会员价格
            if (user.getSvip() == SvipStyle.LEVEL_THREE.getId()) {
                //遍历获取会员价格
                for (ShopGoodsPrice p : priceList) {
                    if (p.getType() == 2 || p.getType() == 4 || p.getType() == 5) {
                        newPriceList.add(p);
                    }
                }
            }
            //非会员价格
            if (user.getSvip() != SvipStyle.LEVEL_THREE.getId()) {
                //遍历获取非会员商品价格
                for (ShopGoodsPrice p : priceList) {
                    if (p.getType() == 1 || p.getType() == 3 || p.getType() == 4) {
                        newPriceList.add(p);
                    }
                }
            }
        }
        //没有用户信息 无token
        if (userId == 0) {
            //遍历获取非会员商品价格
            for (ShopGoodsPrice p : priceList) {
                if (p.getType() == 1 || p.getType() == 3 || p.getType() == 4) {
                    newPriceList.add(p);
                }
            }
        }
        return newPriceList;
    }


    /**
     * 商品价格列表 全部
     * @param goodsId
     * @return
     */
    @Override
    public List<ShopGoodsPrice> findPriceListByGoodsId(Integer goodsId) {

        List<ShopGoodsPrice> priceList = shopGoodsPriceDao.findPriceListByGoodsId(goodsId);
        return priceList;
    }
}

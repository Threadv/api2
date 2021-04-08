package com.ifenghui.storybookapi.app.shop.controller;

import com.ifenghui.storybookapi.app.app.entity.Ads;
import com.ifenghui.storybookapi.app.app.entity.Ads2;
import com.ifenghui.storybookapi.app.app.service.AdService;
import com.ifenghui.storybookapi.app.shop.entity.ShopGoods;
import com.ifenghui.storybookapi.app.shop.entity.ShopGoodsCategory;
import com.ifenghui.storybookapi.app.shop.entity.ShopGoodsImg;
import com.ifenghui.storybookapi.app.shop.entity.ShopGoodsPrice;
import com.ifenghui.storybookapi.app.shop.response.ShopGoodsListResponse;
import com.ifenghui.storybookapi.app.shop.response.ShopGoodsResponse;
import com.ifenghui.storybookapi.app.shop.response.ShopIndexResponse;
import com.ifenghui.storybookapi.app.shop.service.ShopGoodsPriceService;
import com.ifenghui.storybookapi.app.shop.service.ShopGoodsService;
import com.ifenghui.storybookapi.app.user.service.UserService;
import com.ifenghui.storybookapi.app.wallet.service.WalletService;
import com.ifenghui.storybookapi.exception.ApiNotTokenException;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@Controller
@EnableAutoConfiguration
@Api(value = "飞船商城", description = "飞船商城")
@RequestMapping("/api/shop")
public class ShopController {



    @Autowired
    ShopGoodsService shopGoodsService;

    @Autowired
    AdService adService;

    @Autowired
    ShopGoodsPriceService shopGoodsPriceService;

    @Autowired
    WalletService walletService;

    @Autowired
    UserService userService;

    @CrossOrigin
    @ApiOperation(value = "商品详情")
    @RequestMapping(value = "/get_goods_detail", method = RequestMethod.GET)
    @ResponseBody
    ShopGoodsResponse getDetail(
            @RequestHeader(required = false) String ssToken,
            @ApiParam(value = "商品id") @RequestParam(required = false) Integer id
    ) {

        Long userId = 0L;
        Integer starCount = 0;
        Integer svip = 0;
        if (ssToken == null || ssToken.equals("")) {
        } else {
            userId = userService.checkAndGetCurrentUserId(ssToken);
            starCount = walletService.getWalletByUserId(userId).getStarCount();
            svip = userService.getUser(userId).getSvip();
        }
        ShopGoodsResponse response = new ShopGoodsResponse();

        if (svip == 3) {
            response.setSvipStatus(1);
        } else {
            response.setSvipStatus(0);
        }
        response.setStarCount(starCount);
        ShopGoods goods = shopGoodsService.findById(id);
        //商品价格列表 根据用户身份查询
        List<ShopGoodsPrice> priceList = shopGoodsPriceService.findPriceListByGoodsIdAndUserID(userId.intValue(), id);

        //商品价格列表 全部
        List<ShopGoodsPrice> priceListAll = shopGoodsPriceService.findPriceListByGoodsId(id);

        //商品图片列表
        List<ShopGoodsImg> goodsImgList = shopGoodsService.findGoodsImgByGoodsId(id);
        goods.setShopGoodsImgList(goodsImgList);
        goods.setShopGoodsPriceList(priceList);
        goods.setShopGoodsPriceListAll(priceListAll);

        response.setGoods(goods);
        return response;
    }

    @CrossOrigin
    @ApiOperation(value = "商成首页")
    @RequestMapping(value = "/get_index", method = RequestMethod.GET)
    @ResponseBody
    ShopIndexResponse getIndex(
            @RequestHeader(required = false) String ssToken
    ) throws ApiNotTokenException {

        ShopIndexResponse response = new ShopIndexResponse();

        Integer starCount;
        Long userId = 0L;
        if (ssToken == null || ssToken.equals("")) {
            starCount = 0;
        } else {
            userId = userService.checkAndGetCurrentUserId(ssToken);
            //我的星星值
            starCount = walletService.getWalletByUserId(userId.intValue()).getStarCount();
        }


        List<ShopGoodsCategory> list = new ArrayList<>();
        ShopGoodsCategory goodsCategory = new ShopGoodsCategory();
        ShopGoodsCategory goodsCategory2 = new ShopGoodsCategory();
        //广告列表
        List<Ads> adsList = adService.getAdsByStatusAndAdsPosition(1, 3);
        List<Ads2> ads2List = new ArrayList<>();
        for (Ads ads : adsList){
            Ads2 ads2 = new Ads2();
            ads2.setId(ads.getId());
            ads2.setTitle(ads.getTitle());
            ads2.setContent(ads.getContent());
            ads2.setIcon(ads.getIcon());
//            ads2.setBanner(ads.getBanner());
            ads2.setTargetType(ads.getTargetType());
            ads2.setTargetValue(ads.getTargetValue());
            ads2.setUrl(ads.getUrl());
            ads2.setStatus(ads.getStatus());
            ads2.setOrderBy(ads.getOrderBy());
            ads2.setCreateTime(ads.getCreateTime());
            ads2.setImgPath(ads.getImgPath());
            ads2.setAdsPosition(ads.getAdsPosition());

            ads2List.add(ads2);
        }
        response.setAdsList(ads2List);
        //查询分类商品
        List<ShopGoods> list1 = shopGoodsService.findGoodsListByCategoryId(1);
        List<ShopGoods> careGoodsList = new ArrayList<>();
        int size1 = (list1.size() < 4 ? list1.size() : 4);
        for (int i = 0; i < size1; i++) {
            ShopGoods s = list1.get(i);
            //商品图片
            List<ShopGoodsImg> goodsImgList = shopGoodsService.findGoodsImgByGoodsId(s.getId());
            //商品价格列表 全部
            List<ShopGoodsPrice> priceListAll = shopGoodsPriceService.findPriceListByGoodsId(s.getId());
            s.setShopGoodsPriceListAll(priceListAll);
            s.setShopGoodsImgList(goodsImgList);
            //精选商品列表
            careGoodsList.add(s);
        }
        goodsCategory.setId(1);
        goodsCategory.setName("精选商品");
        goodsCategory.setIsShow(1);
        goodsCategory.setShopGoodslist(careGoodsList);
        //查询分类商品
        List<ShopGoods> list2 = shopGoodsService.findGoodsListByCategoryId(2);
        List<ShopGoods> starGoodsList = new ArrayList<>();
        //判断list大小
        int size2 = list2.size() < 4 ? list2.size() : 4;
        for (int i = 0; i < size2; i++) {
            ShopGoods s = list2.get(i);
            //商品图片
            List<ShopGoodsImg> goodsImgList = shopGoodsService.findGoodsImgByGoodsId(s.getId());
            //商品价格列表 全部
            List<ShopGoodsPrice> priceListAll = shopGoodsPriceService.findPriceListByGoodsId(s.getId());
            s.setShopGoodsImgList(goodsImgList);
            s.setShopGoodsPriceListAll(priceListAll);
            //星值商品列表
            starGoodsList.add(s);
        }
        goodsCategory2.setId(2);
        goodsCategory2.setName("星值专区");
        goodsCategory2.setIsShow(0);
        goodsCategory2.setShopGoodslist(starGoodsList);
        //添加到list中 -category
        list.add(goodsCategory);
        list.add(goodsCategory2);
        //星星值
        response.setStarCount(starCount);
        response.setShopGoodsCategoryList(list);
        return response;
    }

    @CrossOrigin
    @ApiOperation(value = "分类商品")
    @RequestMapping(value = "/get_category_goods", method = RequestMethod.GET)
    @ResponseBody
    ShopGoodsListResponse getCategoryGoods(
            @ApiParam(value = "分类id") @RequestParam() Integer categoryId
    ) {

        ShopGoodsListResponse response = new ShopGoodsListResponse();

        if (categoryId == 1) {
            response.setCategory("精选商品");
            response.setIsShow(1);
        }

        if (categoryId == 2) {
            response.setCategory("星值专区");
            response.setIsShow(0);
        }

        List<ShopGoods> goodsList = shopGoodsService.findGoodsListByCategoryId(categoryId);
        List<ShopGoods> list = new ArrayList<>();
        for (ShopGoods s : goodsList) {
            //商品价格列表 全部
            List<ShopGoodsPrice> priceListAll = shopGoodsPriceService.findPriceListByGoodsId(s.getId());
            //商品图片
            List<ShopGoodsImg> goodsImgList = shopGoodsService.findGoodsImgByGoodsId(s.getId());
            s.setShopGoodsImgList(goodsImgList);
            s.setShopGoodsPriceListAll(priceListAll);
            list.add(s);
        }
        response.setGoodsList(list);
        return response;
    }


}

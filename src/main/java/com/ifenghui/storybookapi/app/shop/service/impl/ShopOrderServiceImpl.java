package com.ifenghui.storybookapi.app.shop.service.impl;

import com.alipay.api.AlipayApiException;
import com.alipay.api.AlipayClient;
import com.alipay.api.DefaultAlipayClient;
import com.alipay.api.domain.AlipayTradeWapPayModel;
import com.alipay.api.request.AlipayTradeWapPayRequest;
import com.alipay.api.response.AlipayTradeWapPayResponse;
import com.ifenghui.storybookapi.app.presale.exception.PreSaleNotFoundException;
import com.ifenghui.storybookapi.app.shop.dao.ShopGoodsDao;
import com.ifenghui.storybookapi.app.shop.dao.ShopOrderDao;
import com.ifenghui.storybookapi.app.shop.entity.ShopGoods;
import com.ifenghui.storybookapi.app.shop.entity.ShopGoodsPrice;
import com.ifenghui.storybookapi.app.shop.entity.ShopOrder;
import com.ifenghui.storybookapi.app.shop.response.ShopOrderListResponse;
import com.ifenghui.storybookapi.app.shop.service.ShopGoodsPriceService;
import com.ifenghui.storybookapi.app.shop.service.ShopOrderService;
import com.ifenghui.storybookapi.app.transaction.dao.ExchangeRecordDao;
import com.ifenghui.storybookapi.app.transaction.entity.goods.ExchangeRecord;
import com.ifenghui.storybookapi.app.transaction.entity.goods.Goods;
import com.ifenghui.storybookapi.app.transaction.service.CouponDeferredService;
import com.ifenghui.storybookapi.app.transaction.service.CouponService;
import com.ifenghui.storybookapi.app.transaction.service.CouponStoryExchangeUserService;
import com.ifenghui.storybookapi.app.transaction.service.StarShopService;
import com.ifenghui.storybookapi.app.user.service.UserTokenService;
import com.ifenghui.storybookapi.config.StarShopConfig;
import com.ifenghui.storybookapi.style.*;
import com.ifenghui.storybookapi.util.NumberUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Component
@Transactional
public class ShopOrderServiceImpl implements ShopOrderService {

    @Autowired
    private Environment env;
    @Autowired
    ShopOrderDao shopOrderDao;

    @Autowired
    ShopGoodsDao shopGoodsDao;

    @Autowired
    ExchangeRecordDao exchangeRecordDao;

    @Autowired
    StarShopService starShopService;

    @Autowired
    CouponStoryExchangeUserService couponStoryExchangeUserService;

    @Autowired
    ShopGoodsPriceService shopGoodsPriceService;

    @Autowired
    UserTokenService userTokenService;

    @Autowired
    HttpServletResponse httpResponse;

    @Autowired
    CouponService couponService;

    @Autowired
    CouponDeferredService couponDeferredService;


    /**
     * ???????????? ????????? ?????????
     * @return
     */

    @Override
    public List<ShopOrder> findAllOrder() {

        return  shopOrderDao.findAllOrder();
    }

    /**
     * ????????????
     *
     * @param userId
     * @param status
     * @param pageNo
     * @param pageSize
     * @return
     */
    @Override
    public ShopOrderListResponse getMyOrderList(Integer userId, Integer status, Integer pageNo, Integer pageSize) {


        ShopOrderListResponse response = new ShopOrderListResponse();

        List<ShopOrder> orderList = new ArrayList<>();
        if (status == 2) {
            Page<ShopOrder> orderPage = this.findOrderListByUserId(userId, pageNo, pageSize);
            for (ShopOrder o : orderPage.getContent()) {
                ShopGoods goods = shopGoodsDao.findOne(o.getGoodsId());
                ShopGoodsPrice price = shopGoodsPriceService.findPriceById(o.getPriceId());
                o.setShopGoodsPrice(price);
                o.setShopGoods(goods);
                o.setOrderCode( o.getId().toString());
                orderList.add(o);
            }
            response.setJpaPage(orderPage);
            response.setShopOrderList(orderList);
        }
        if (status == 0) {
            //???????????????status = 0
            Page<ShopOrder> orderPage = this.findOrderListByStatus(userId.intValue(), status, pageNo, pageSize);

            for (ShopOrder o : orderPage.getContent()) {
                ShopGoods goods = shopGoodsDao.findOne(o.getGoodsId());
                ShopGoodsPrice price = shopGoodsPriceService.findPriceById(o.getPriceId());
                o.setShopGoods(goods);
                o.setShopGoodsPrice(price);
                o.setOrderCode(o.getId().toString());
                orderList.add(o);
            }
            response.setJpaPage(orderPage);
            response.setShopOrderList(orderList);
        }
        if(status ==1 ){
            //???????????????status = 1 ??????????????????2
            Pageable pageable = new PageRequest(pageNo, pageSize, new Sort(Sort.Direction.DESC, "createTime", "id"));
            Page<ShopOrder> orderPage = shopOrderDao.findSuccessOrderList(userId.intValue(), pageable);
            for (ShopOrder o : orderPage.getContent()) {
                ShopGoods goods = shopGoodsDao.findOne(o.getGoodsId());
                ShopGoodsPrice price = shopGoodsPriceService.findPriceById(o.getPriceId());
                o.setShopGoodsPrice(price);
                o.setOrderCode(o.getId().toString());
                o.setShopGoods(goods);
                orderList.add(o);
            }
            response.setJpaPage(orderPage);
            response.setShopOrderList(orderList);
        }
        return response;
    }

    /**
     * ??????????????????
     *
     * @param userId
     * @param goodsId
     * @param num
     * @return
     */
    @Override
    public void addExchangeCoupon(Integer userId, Integer goodsId, Integer num) {

        ShopGoods goods = shopGoodsDao.findOne(goodsId);

        if (goods.getType().equals(StarShopConfig.BUY_TYPE_STORY_COUPON)) {//???????????????
            starShopService.addCouponStoryExchangeUser(userId.longValue(),Long.parseLong(goods.getValue()), num);
        }

        if (goods.getType().equals(StarShopConfig.BUY_TYPE_COUPON)) {//?????????
            starShopService.addExchangeRecordCoupon(userId.longValue(),goods.getGoodsName(),Long.parseLong(goods.getValue()), num);
        }

        if (goods.getType().equals(StarShopConfig.BUY_TYPE_COUPON_DEFERRED)) {//?????????
            starShopService.addExchangeRecordCouponDeferred(userId.longValue(),goods.getGoodsName(),Long.parseLong(goods.getValue()), num);
        }
    }

    /**
     * ????????????
     *
     * @param orderId
     * @return
     */
    @Override
    public ShopOrder updateShopPayOrderSuccess(Integer orderId, OrderPayStyle orderPayStyle) {
        ShopOrder shopOrder = this.findOrderById(orderId);
        if (shopOrder == null) {
            throw new PreSaleNotFoundException("??????????????????");
        }
        shopOrder.setStatus(OrderStatusStyle.PAY_SUCCESS);
        shopOrder.setBuyType(orderPayStyle.getId());
        shopOrder.setSuccessTime(new Date());
        return shopOrderDao.save(shopOrder);
    }

    /**
     * ????????????
     *
     * @param userId
     * @param goodsId
     * @param num
     * @param priceId
     * @param isTest
     * @param channel
     * @param remark
     * @return
     */
    @Override
    public ShopOrder addOrder(Integer userId, Integer goodsId, Integer num, Integer priceId, Integer isTest, String channel, String remark, OrderPayStyle orderPayStyle) {

        ShopGoods goods = shopGoodsDao.findOne(goodsId);



        //????????????
        ShopGoodsPrice shopGoodsPrice = shopGoodsPriceService.findPriceById(priceId);
        //?????????
        int totalAmount = shopGoodsPrice.getPrice() * num;
        //????????????
        int totalStart = shopGoodsPrice.getStarNumber() * num;

        ShopOrder shopOrder = new ShopOrder();
        shopOrder.setUserId(userId);
        shopOrder.setGoodsId(goodsId);
        shopOrder.setGoodsNumber(num);
        shopOrder.setTotalAmount(totalAmount);
        shopOrder.setTotalStar(totalStart);
        shopOrder.setStatus(OrderStatusStyle.WAIT_PAY);
        shopOrder.setPriceId(priceId);

        if(goods.getType()>0){//????????? ???????????????3
            shopOrder.setExpress_status(2);
        }else {
            shopOrder.setExpress_status(0);
        }

        shopOrder.setBuyType(orderPayStyle.getId());
        shopOrder.setIsDel(0);
        shopOrder.setIsTest(isTest);
        shopOrder.setChannel(channel);
        shopOrder.setRemark(remark);
        shopOrder.setCreateTime(new Date());
        shopOrder.setSuccessTime(null);
        return shopOrderDao.save(shopOrder);
    }

    /**
     * ????????????
     *
     * @param id
     */
    @Override
    public ShopOrder cancelOrder(Integer id) {
        ShopOrder order = shopOrderDao.findOne(id);
        order.setStatus(OrderStatusStyle.PAY_BACK);
        return shopOrderDao.save(order);
    }

    /**
     * ????????????
     *
     * @param id
     */
    @Override
    public void deleteOrder(Integer id) {

        ShopOrder order = shopOrderDao.findOne(id);
        //???????????? ???????????????1
        order.setIsDel(1);
        shopOrderDao.save(order);
    }

    /**
     * ????????????
     *
     * @param orderId
     * @return
     */
    @Override
    public ShopOrder findOrderById(Integer orderId) {
        return shopOrderDao.findOne(orderId);
    }

    /**
     * ????????????????????????
     *
     * @param userId
     * @param status
     * @return
     */
    @Override
    public Page<ShopOrder> findOrderListByStatus(Integer userId, Integer status, Integer pageNo, Integer pageSize) {

        Pageable pageable = new PageRequest(pageNo, pageSize, new Sort(Sort.Direction.DESC, "createTime", "id"));
        Page<ShopOrder> page = shopOrderDao.findOrderListByStatus(userId, status, pageable);
        return page;
    }

    @Override
    public Page<ShopOrder> findOrderListByUserId(Integer userId, Integer pageNo, Integer pageSize) {
        Pageable pageable = new PageRequest(pageNo, pageSize, new Sort(Sort.Direction.DESC, "createTime", "id"));
        Page<ShopOrder> page = shopOrderDao.findOrderListByUserId(userId, pageable);
        return page;
    }


    @Override
    public void addAliOrder(Integer orderId, Integer userId) throws Exception {

        ShopOrder shopOrder = this.findOrderById(orderId);
        if (shopOrder.getUserId() != userId.intValue()) {
            throw new RuntimeException("??????????????????");
        }
        ShopGoods shopGoods = shopGoodsDao.findOne(shopOrder.getGoodsId());
        Float totalAmount = (float) shopOrder.getTotalAmount() / 100;
        String totalAmountStr = totalAmount.toString();
        this.initAliWapPay(orderId, shopGoods.getGoodsName(), totalAmountStr, "???????????????????????????" + shopGoods.getGoodsName() + "???", httpResponse);
    }

    @Override
    public void initAliWapPay(Integer orderId, String subject, String totalAmount, String body, HttpServletResponse httpResponse) throws Exception {

        String orderPrefix = env.getProperty("shopOrder.prefix");
        String out_trade_no = orderPrefix + "_" + orderId;
        String timeout_express = "2m";
        String product_code = "QUICK_WAP_WAY";
        AlipayClient client = userTokenService.getAlipayClient();
        AlipayTradeWapPayRequest alipay_request = new AlipayTradeWapPayRequest();
        AlipayTradeWapPayModel model = new AlipayTradeWapPayModel();
        model.setOutTradeNo(out_trade_no);
        model.setSubject(subject);
        model.setTotalAmount(totalAmount);
        model.setBody(body);
        model.setTimeoutExpress(timeout_express);
        model.setProductCode(product_code);
        alipay_request.setBizModel(model);
        // ????????????????????????
        String notifyUrl = env.getProperty("shopAliPay.notify");
        alipay_request.setNotifyUrl(notifyUrl);
        // ??????????????????
        String returnUrl = env.getProperty("shopAliPay.return");
        alipay_request.setReturnUrl(returnUrl);
        String form = "";
        try {
            form = client.pageExecute(alipay_request).getBody();
            httpResponse.setContentType("text/html;charset=utf-8");
            httpResponse.getWriter().write(form);
            httpResponse.getWriter().flush();
            httpResponse.getWriter().close();
        } catch (AlipayApiException e) {
            e.printStackTrace();
        }
    }

}

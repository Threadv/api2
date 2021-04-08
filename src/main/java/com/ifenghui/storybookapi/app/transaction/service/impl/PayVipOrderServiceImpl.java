package com.ifenghui.storybookapi.app.transaction.service.impl;

import com.ifenghui.storybookapi.app.app.service.TemplateNoticeService;
import com.ifenghui.storybookapi.app.presale.entity.PreSaleCode;
import com.ifenghui.storybookapi.app.presale.service.PreSaleCodeService;
import com.ifenghui.storybookapi.app.shop.service.ShopExpressService;
import com.ifenghui.storybookapi.app.transaction.dao.CouponDao;
import com.ifenghui.storybookapi.app.transaction.dao.CouponUserDao;
import com.ifenghui.storybookapi.app.transaction.dao.PayVipOrderDao;
import com.ifenghui.storybookapi.app.transaction.dao.UserSvipDao;
import com.ifenghui.storybookapi.app.transaction.entity.OrderMix;
import com.ifenghui.storybookapi.app.transaction.entity.vip.PayVipOrder;
import com.ifenghui.storybookapi.app.transaction.entity.vip.UserSvip;
import com.ifenghui.storybookapi.app.transaction.response.*;
import com.ifenghui.storybookapi.app.transaction.service.CouponOrderRelateService;
import com.ifenghui.storybookapi.app.transaction.service.CouponsResult;
import com.ifenghui.storybookapi.app.transaction.service.PayVipOrderService;
import com.ifenghui.storybookapi.app.transaction.service.UserSvipService;
import com.ifenghui.storybookapi.app.transaction.service.order.OrderMixService;
import com.ifenghui.storybookapi.app.user.entity.User;
import com.ifenghui.storybookapi.app.user.service.UserService;
import com.ifenghui.storybookapi.app.wallet.entity.Wallet;
import com.ifenghui.storybookapi.app.wallet.response.SubscribeByCodeResponse;
import com.ifenghui.storybookapi.app.wallet.service.WalletService;
import com.ifenghui.storybookapi.config.StarConfig;
import com.ifenghui.storybookapi.exception.ApiNoPermissionDelException;
import com.ifenghui.storybookapi.exception.ApiNotFoundException;
import com.ifenghui.storybookapi.exception.ApiNotThisUserException;
import com.ifenghui.storybookapi.style.*;
import com.ifenghui.storybookapi.util.NumberUtil;
import com.ifenghui.storybookapi.util.VersionUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletRequest;
import java.text.SimpleDateFormat;
import java.util.*;

@Component
public class PayVipOrderServiceImpl implements PayVipOrderService {

    @Autowired
    PayVipOrderDao payVipOrderDao;

    @Autowired
    OrderMixService orderMixService;

    @Autowired
    UserService userService;

    @Autowired
    UserSvipDao userSvipDao;

    @Autowired
    CouponUserDao couponUserDao;

    @Autowired
    CouponDao couponDao;

    @Autowired
    HttpServletRequest request;

    @Autowired
    CouponOrderRelateService couponOrderRelateService;
    @Autowired
    WalletService walletService;

    @Autowired
    TemplateNoticeService templateNoticeService;

    @Autowired
    UserSvipService userSvipService;

    @Autowired
    PreSaleCodeService preSaleCodeService;

    @Autowired
    ShopExpressService shopExpressService;

    @Override
    public GetSvipPrivilegeResponse getSvipPrivilege(Long userId) {

        GetSvipPrivilegeResponse response = new GetSvipPrivilegeResponse();

        User user = userService.getUser(userId);

        Integer isSvip;
        if (user != null && (user.getSvip() == 3 || user.getSvip() == 4)) {
            isSvip = 1;
            response.setIsSvip(isSvip);
        } else {
            isSvip = 0;
            response.setIsSvip(isSvip);
        }

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        UserSvip lastestUserSvipRecord = userSvipService.getLastestUserSvipRecord(userId);
        String endTime = "";
        if (lastestUserSvipRecord != null) {
            endTime = sdf.format(lastestUserSvipRecord.getEndTime());
        }
        response.setTitle("故事飞船会员卡");
        response.setImg("http://storybook.oss-cn-hangzhou.aliyuncs.com/vip/card.png");
        response.setEndTime(endTime);

        //svip1
        SvipPrice svipPrice1 = new SvipPrice(
                VipPriceStyle.YEAR_VIP.getId(),
                VipPriceStyle.YEAR_VIP.getName(),
                VipPriceStyle.YEAR_VIP.getDays(),
                VipPriceStyle.YEAR_VIP.getPrice(),
                VipPriceStyle.YEAR_VIP.getPriceId()
        );
        //svip2
        SvipPrice svipPrice2 = new SvipPrice(
                VipPriceStyle.HALF_YEAR_VIP.getId(),
                VipPriceStyle.HALF_YEAR_VIP.getName(),
                VipPriceStyle.HALF_YEAR_VIP.getDays(),
                VipPriceStyle.HALF_YEAR_VIP.getPrice(),
                VipPriceStyle.HALF_YEAR_VIP.getPriceId()
        );
        //svip3
        SvipPrice svipPrice3 = new SvipPrice(
                VipPriceStyle.SEASON_VIP.getId(),
                VipPriceStyle.SEASON_VIP.getName(),
                VipPriceStyle.SEASON_VIP.getDays(),
                VipPriceStyle.SEASON_VIP.getPrice(),
                VipPriceStyle.SEASON_VIP.getPriceId()
        );
        //svip1 权益
        ReturnSvip returnSvip1 = new ReturnSvip();
        List<SvipGift> svipGiftList1 = new ArrayList<>();
        SvipGift svipGift11 = new SvipGift(1, 1, 1, isSvip);
        SvipGift svipGift12 = new SvipGift(2, 1, 1, isSvip);
        SvipGift svipGift13 = new SvipGift(3, 1, 1, isSvip);
        SvipGift svipGift14 = new SvipGift(4, 1, 1, isSvip);
        SvipGift svipGift15 = new SvipGift(5, 1, 1, isSvip);
        SvipGift svipGift17 = new SvipGift(7, 1, 1, isSvip);
        svipGiftList1.add(svipGift11);
        svipGiftList1.add(svipGift12);
        svipGiftList1.add(svipGift13);
        svipGiftList1.add(svipGift14);
        svipGiftList1.add(svipGift15);
        svipGiftList1.add(svipGift17);
        returnSvip1.setSvipPrice(svipPrice1);
        returnSvip1.setSvipGiftList(svipGiftList1);
        //svip2 权益
        ReturnSvip returnSvip2 = new ReturnSvip();
        List<SvipGift> svipGiftList2 = new ArrayList<>();
        SvipGift svipGift21 = new SvipGift(1, 2, 1, isSvip);
        SvipGift svipGift22 = new SvipGift(2, 2, 1, isSvip);
        SvipGift svipGift23 = new SvipGift(3, 2, 1, isSvip);
        SvipGift svipGift24 = new SvipGift(4, 2, 1, isSvip);
        SvipGift svipGift25 = new SvipGift(5, 2, 1, isSvip);
        SvipGift svipGift27 = new SvipGift(7, 2, 1, isSvip);
        svipGiftList2.add(svipGift21);
        svipGiftList2.add(svipGift22);
        svipGiftList2.add(svipGift23);
        svipGiftList2.add(svipGift24);
        svipGiftList2.add(svipGift25);
        svipGiftList2.add(svipGift27);
        returnSvip2.setSvipPrice(svipPrice2);
        returnSvip2.setSvipGiftList(svipGiftList2);
        //svip3 权益
        ReturnSvip returnSvip3 = new ReturnSvip();
        List<SvipGift> svipGiftList3 = new ArrayList<>();
        SvipGift svipGift31 = new SvipGift(1, 3, 1, isSvip);
        SvipGift svipGift32 = new SvipGift(2, 3, 1, isSvip);
        SvipGift svipGift33 = new SvipGift(3, 3, 1, isSvip);
        SvipGift svipGift34 = new SvipGift(4, 3, 1, isSvip);
        SvipGift svipGift35 = new SvipGift(5, 3, 1, isSvip);
        SvipGift svipGift37 = new SvipGift(7, 3, 1, isSvip);
        svipGiftList3.add(svipGift31);
        svipGiftList3.add(svipGift32);
        svipGiftList3.add(svipGift33);
        svipGiftList3.add(svipGift34);
        svipGiftList3.add(svipGift35);
        svipGiftList3.add(svipGift37);
        returnSvip3.setSvipPrice(svipPrice3);
        returnSvip3.setSvipGiftList(svipGiftList3);

        List<ReturnSvip> returnSvipList = new ArrayList<>();
        returnSvipList.add(returnSvip1);
        returnSvipList.add(returnSvip2);
        returnSvipList.add(returnSvip3);


        response.setReturnSvipList(returnSvipList);
        return response;
    }

    @Override
    @Transactional
    public PayVipOrder addPayVipOrder(User user, Integer originalPrice, Integer couponAmount, Integer amount, Integer priceId, Integer userDiscount, String channel, String code, OrderPayStyle orderPayStyle) {
        PayVipOrder payVipOrder = new PayVipOrder();
        payVipOrder.setUserId(user.getId().intValue());
        payVipOrder.setOriginalPrice(originalPrice);
        payVipOrder.setCouponAmount(couponAmount);
        payVipOrder.setAmount(amount);
        payVipOrder.setStatus(0);
        payVipOrder.setPriceId(priceId);
        payVipOrder.setCreateTime(new Date());
        payVipOrder.setUserDiscount(userDiscount);
        payVipOrder.setPayStyle(orderPayStyle);
        payVipOrder.setCode(code);
        payVipOrder.setIsDel(0);
        payVipOrder.setIsTest(user.getIsTest());
        payVipOrder.setChannel(channel);
        payVipOrder.setSuccessTime(new Date());
        payVipOrder.setRemark("");
        payVipOrder = payVipOrderDao.save(payVipOrder);
        OrderMix orderMix = orderMixService.addOrderMix(OrderStyle.VIP_ORDER, payVipOrder.getId(), user.getId().intValue());
        payVipOrder.setMixOrderId(orderMix.getId());
        return payVipOrder;
    }

    @Transactional
    @Override
    public PayVipOrder createPayVipOrder(VipGoodsStyle vipGoodsStyle, Long userId, List<Integer> couponIds) {

        User user = userService.getUser(userId);
        if (vipGoodsStyle.getVipPriceStyle().equals(VipPriceStyle.DEFAULT_NULL)) {
            throw new ApiNotFoundException("没有找到该商品！");
        }
        Integer originalPrice = vipGoodsStyle.getVipPriceStyle().getPrice();
        Integer userDiscount = 100;
        Float noCouponAmount = originalPrice * ((float) userDiscount / 100);

        /**
         * 获得使用兑换券后扣除金额
         */
        CouponsResult couponsResult = new CouponsResult();
        couponsResult.checkCoupons(couponIds, couponUserDao, couponDao, originalPrice);
        Integer couponAmount = couponsResult.getCouponAmount();

        Integer amount = noCouponAmount.intValue() - couponAmount;

        String channel = VersionUtil.getChannelInfo(request);

        PayVipOrder payVipOrder = this.addPayVipOrder(
                user,
                originalPrice,
                couponAmount,
                amount,
                vipGoodsStyle.getId(),
                userDiscount,
                channel,
                "",
                OrderPayStyle.DEFAULT_NULL
        );
        couponOrderRelateService.addCouponOrderByCouponIdsStr(couponIds, userId.intValue(), payVipOrder.getId(), OrderStyle.VIP_ORDER);
        return payVipOrder;
    }

    @Transactional
    @Override
    public PayVipOrder buyVipByBalance(Long userId, Integer orderId, OrderPayStyle payStyle, WalletStyle walletStyle) {
        PayVipOrder payVipOrder = payVipOrderDao.findOne(orderId);

        /**
         * 安卓余额订单需要重新计算订单折扣
         */
        Integer newAmount = walletService.getAndroidUserNewAmount(userId.intValue(), payVipOrder.getAmount(), payStyle, walletStyle);
        if (!newAmount.equals(payVipOrder.getAmount())) {
            Wallet wallet = walletService.getWalletByUserId(userId);
            payVipOrder.setAmount(newAmount);
            payVipOrder.setUserDiscount(wallet.getDiscount());
        }

        String intro = "";
        if (payVipOrder.getPriceId() == VipGoodsStyle.YEAR_VIP.getId() || payVipOrder.getPriceId() == VipGoodsStyle.GIFT_YEAR_VIP.getId() || payVipOrder.getPriceId() == VipGoodsStyle.EXPRESS_YEAR_VIP.getId() || payVipOrder.getPriceId() == VipGoodsStyle.GIFT_EXPRESS_YEAR_VIP.getId()) {

            intro = "开通VIP-全年";
        }
        if (payVipOrder.getPriceId() == VipGoodsStyle.HALF_YEAR_VIP.getId() || payVipOrder.getPriceId() == VipGoodsStyle.GIFT_HALF_YEAR_VIP.getId() || payVipOrder.getPriceId() == VipGoodsStyle.EXPRESS_HALF_YEAR_VIP.getId() || payVipOrder.getPriceId() == VipGoodsStyle.GIFT_EXPRESS_HALF_YEAR_VIP.getId()) {

            intro = " 开通VIP-半年";
        }
        if (payVipOrder.getPriceId() == VipGoodsStyle.SEASON_VIP.getId() || payVipOrder.getPriceId() == VipGoodsStyle.GIFT_SEASON_VIP.getId() || payVipOrder.getPriceId() == VipGoodsStyle.EXPRESS_SEASON_VIP.getId() || payVipOrder.getPriceId() == VipGoodsStyle.GIFT_EXPRESS_SEASON_VIP.getId()) {

            intro = "开通VIP-季度";
        }

        walletService.addAmountToWallet(userId.intValue(), walletStyle, RechargeStyle.BUY_SVIP, NumberUtil.unAbs(payVipOrder.getAmount()), "svip_" + orderId, intro);

        VipGoodsStyle vipGoodsStyle = VipGoodsStyle.getById(payVipOrder.getPriceId());

        if (vipGoodsStyle == null) {
            throw new ApiNotFoundException("未找到对应商品无法支付！");
        }

        userSvipService.createUserSvip(userId.intValue(), vipGoodsStyle.getVipPriceStyle());


        /**
         * 调用积分流水添加   购买vip会员卡
         */
        Integer starCount = StarConfig.STAR_USR_BUY * (payVipOrder.getAmount()) / 100;
        walletService.addStarToWallet(userId.intValue(), StarRechargeStyle.BUY_VIP, starCount, StarContentStyle.VIP_BUY.getName());

        payVipOrder.setSuccessTime(new Date());
        payVipOrder.setStatus(1);
        payVipOrder.setPayStyle(payStyle);
        payVipOrder = payVipOrderDao.save(payVipOrder);
        orderMixService.updateOrderMixStatus(OrderStyle.VIP_ORDER, payVipOrder.getId(), OrderStatusStyle.PAY_SUCCESS);
        /**
         * 发送系统消息
         */
        Map<String, String> contentMap = new HashMap<>();
        templateNoticeService.addNoticeToUserByUserIdAndMessage(NoticeStyle.BUY_SVIP, contentMap, userId.intValue());
        return payVipOrder;
    }

    @Override
    public PayVipOrder buyVipByCodeMethod(Long userId, VipGoodsStyle vipGoodsStyle, PreSaleCode preSaleCode) {

        PayVipOrder payVipOrder = this.createPayVipOrder(vipGoodsStyle, userId, new ArrayList<>());
        userSvipService.createUserSvip(userId.intValue(), vipGoodsStyle.getVipPriceStyle());

        payVipOrder.setSuccessTime(new Date());
        payVipOrder.setStatus(1);
        payVipOrder.setCode(preSaleCode.getCode());
        payVipOrder.setPayStyle(OrderPayStyle.CODE);
        payVipOrder = payVipOrderDao.save(payVipOrder);
        orderMixService.updateOrderMixStatus(OrderStyle.VIP_ORDER, payVipOrder.getId(), OrderStatusStyle.PAY_SUCCESS);
        /**
         * 发送系统消息
         */
        Map<String, String> contentMap = new HashMap<>();
        templateNoticeService.addNoticeToUserByUserIdAndMessage(NoticeStyle.BUY_SVIP, contentMap, userId.intValue());
        return payVipOrder;
    }

    @Override
    public PayVipOrder getPayVipOrderById(Integer id) {
        return payVipOrderDao.findOne(id);
    }

    @Transactional
    @Override
    public SubscribeByCodeResponse buyVipByCode(Long userId, VipGoodsStyle vipGoodsStyle, PreSaleCode preSaleCode, String receiver, String phone, String address, String area, Integer codeType) {
        PayVipOrder payVipOrder = this.buyVipByCodeMethod(userId, vipGoodsStyle, preSaleCode);

        if (codeType == 2) {
            shopExpressService.addExpress(payVipOrder.getId(), receiver, phone, address, area, ExpressStyle.DEFAULT_NULL, "", ExpressStatusStyle.HAS_NO_DELIVERY, OrderStyle.VIP_ORDER);
        }

        preSaleCodeService.usePreSaleCode(preSaleCode,userId.intValue());
        SubscribeByCodeResponse response = new SubscribeByCodeResponse();
        String intro = "恭喜您成功兑换故事飞船会员卡" + vipGoodsStyle.getVipPriceStyle().getName() + "版！";
        response.setTargetValue(0);
        response.setIntro(intro);
        response.setType(VipCodeStyle.VIP.getId());
        return response;
    }

    @Override
    public void cancelOrder(Integer orderId) {
        PayVipOrder payVipOrder = this.getPayVipOrderById(orderId);

        if (payVipOrder == null) {
            throw new ApiNoPermissionDelException("没有此订单！");
        }

        if (!payVipOrder.getUserId().equals(payVipOrder.getUserId())) {
            throw new ApiNotThisUserException("参数错误，用户与订单信息不符");
        }

        payVipOrder.setStatus(2);
        payVipOrderDao.save(payVipOrder);
        couponOrderRelateService.deleteCouponOrderByUserIdAndOrderId(payVipOrder.getUserId(), orderId, OrderStyle.VIP_ORDER);
    }

    @Override
    public void setDataToOrderMix(OrderMix orderMix) {
        PayVipOrder payVipOrder = this.getPayVipOrderById(orderMix.getOrderId());
        orderMix.setAmount(payVipOrder.getAmount());
        orderMix.setOriginalPrice(payVipOrder.getOriginalPrice());
        orderMix.setCreateTime(payVipOrder.getCreateTime());
        orderMix.setSuccessTime(payVipOrder.getSuccessTime());
        orderMix.setIsDel(payVipOrder.getIsDel());
        orderMix.setBuyType(payVipOrder.getType());
        VipGoodsStyle vipGoodsStyle = VipGoodsStyle.getById(payVipOrder.getPriceId());
        VipPriceStyle vipPriceStyle = vipGoodsStyle.getVipPriceStyle();
        SvipPrice svipPrice = new SvipPrice(
                vipPriceStyle.getId(),
                vipPriceStyle.getName(),
                vipPriceStyle.getDays(),
                vipPriceStyle.getPrice(),
                vipPriceStyle.getPriceId()
        );
        orderMix.setSvipPrice(svipPrice);
    }

    @Override
    public void deleteOrder(Integer orderId) {
        PayVipOrder payVipOrder = this.getPayVipOrderById(orderId);
        payVipOrder.setIsDel(1);
        payVipOrderDao.save(payVipOrder);
    }

    @Override
    public Integer getShareTradeAmount(Integer orderId) {
        PayVipOrder payVipOrder = this.getPayVipOrderById(orderId);
        if(
                payVipOrder.getStatus().equals(OrderStatusStyle.PAY_SUCCESS.getId()) &&
                        !payVipOrder.getType().equals(OrderPayStyle.CODE.getId()) &&
                        !payVipOrder.getType().equals(OrderPayStyle.DEFAULT_NULL.getId()) &&
                        !payVipOrder.getType().equals(OrderPayStyle.STORY_COUPON.getId())
                ){
            return payVipOrder.getAmount();
        }
        return 0;
    }
}

package com.ifenghui.storybookapi.app.transaction.service.impl;

import com.ifenghui.storybookapi.adminapi.controlleradmin.ability.AbilityQuery;
import com.ifenghui.storybookapi.api.notify.WeixinNotify;
import com.ifenghui.storybookapi.app.app.entity.Ads;
import com.ifenghui.storybookapi.app.app.service.TemplateNoticeService;
import com.ifenghui.storybookapi.app.express.entity.ExpressCenterOrder;
import com.ifenghui.storybookapi.app.express.service.ExpressCenterOrderService;
import com.ifenghui.storybookapi.app.presale.entity.PreSaleCode;
import com.ifenghui.storybookapi.app.presale.service.PreSaleCodeService;
import com.ifenghui.storybookapi.app.shop.entity.ShopExpress;
import com.ifenghui.storybookapi.app.shop.service.ShopExpressService;
import com.ifenghui.storybookapi.app.studyplan.dao.WeekPlanJoinDao;
import com.ifenghui.storybookapi.app.studyplan.dao.WeekPlanUserRecordDao;
import com.ifenghui.storybookapi.app.studyplan.entity.WeekPlanJoin;
import com.ifenghui.storybookapi.app.studyplan.service.WeekPlanJoinService;
import com.ifenghui.storybookapi.app.studyplan.service.WeekPlanUserRecordService;
import com.ifenghui.storybookapi.app.transaction.dao.*;
import com.ifenghui.storybookapi.app.transaction.entity.OrderMix;
import com.ifenghui.storybookapi.app.transaction.entity.abilityplan.AbilityPlanOrder;
import com.ifenghui.storybookapi.app.transaction.entity.abilityplan.AbilityPlanStyleAndPrice;
import com.ifenghui.storybookapi.app.transaction.entity.vip.PayVipOrder;
import com.ifenghui.storybookapi.app.transaction.service.*;
import com.ifenghui.storybookapi.app.transaction.service.lesson.PayLessonOrderService;
import com.ifenghui.storybookapi.app.transaction.service.order.OrderMixService;
import com.ifenghui.storybookapi.app.user.entity.User;
import com.ifenghui.storybookapi.app.user.entity.UserExtend;
import com.ifenghui.storybookapi.app.user.service.UserExtendService;
import com.ifenghui.storybookapi.app.user.service.UserService;
import com.ifenghui.storybookapi.app.wallet.entity.Wallet;
import com.ifenghui.storybookapi.app.wallet.response.SubscribeByCodeResponse;
import com.ifenghui.storybookapi.app.wallet.service.WalletService;
import com.ifenghui.storybookapi.config.StarConfig;
import com.ifenghui.storybookapi.exception.ApiNotFoundException;
import com.ifenghui.storybookapi.style.*;
import com.ifenghui.storybookapi.util.NumberUtil;
import com.ifenghui.storybookapi.util.VersionUtil;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import javax.servlet.http.HttpServletRequest;
import java.util.*;

/**
 * @Date: 2018/11/8 18:40
 * @Description:
 */

@Component
public class AbilityPlanOrderServiceImpl implements AbilityPlanOrderService {

    Logger logger = Logger.getLogger(WeixinNotify.class);

    @Autowired
    WeekPlanJoinService weekPlanJoinService;

    @Autowired
    UserExtendService userExtendService;

    @Autowired
    AbilityPlanOrderDao abilityPlanOrderDao;
    @Autowired
    PayVipOrderDao payVipOrderDao;

    @Autowired
    WeekPlanJoinDao weekPlanJoinDao;

    @Autowired
    TemplateNoticeService templateNoticeService;

    @Autowired
    WalletService walletService;
    @Autowired
    UserSvipService userSvipService;

    @Autowired
    UserAbilityPlanRelateService userAbilityPlanRelateService;

    @Autowired
    WeekPlanUserRecordService weekPlanUserRecordService;
    @Autowired
    WeekPlanUserRecordDao weekPlanUserRecordDao;

    @Autowired
    OrderMixService orderMixService;
    @Autowired
    OrderMixDao orderMixDao;

    @Autowired
    ShopExpressService shopExpressService;
    @Autowired
    PreSaleCodeService preSaleCodeService;

    @Autowired
    UserService userService;

    @Autowired
    CouponUserDao couponUserDao;

    @Autowired
    CouponDao couponDao;

    @Autowired
    HttpServletRequest request;

    @Autowired
    CouponOrderRelateService couponOrderRelateService;

    @Autowired
    ExpressCenterOrderService expressCenterOrderService;

    @Autowired
    PayLessonOrderService payLessonOrderService;

    @Override
    public void setPlanTypeExpire(Integer userId) {

        List<AbilityPlanOrder> orderList = abilityPlanOrderDao.getAbilityPlanOrdersByUserIdAndStatus(userId, 1);
        for (AbilityPlanOrder o:orderList) {
            o.setAbilityPlanStyle(AbilityPlanStyle.DEFAULT);
            abilityPlanOrderDao.save(o);
        }

    }

    @Override
    public boolean isHasSuccessOrder(Integer userId) {

        boolean flag = false;
        List<AbilityPlanOrder> list24 = abilityPlanOrderDao.findAbilityPlanOrdersByUserIdAndStatusAndPlaneType(userId, 1, 1);
        List<AbilityPlanOrder> list46 = abilityPlanOrderDao.findAbilityPlanOrdersByUserIdAndStatusAndPlaneType(userId, 1, 2);
        if (list24.size() > 0 || list46.size() > 0) {
            flag=true;
        }
        return flag;
    }

    @Override
    public AbilityPlanOrder getAbilityPlanOrderByUserIdAndPlanType(Integer userId) {

        List<AbilityPlanOrder> orderList = abilityPlanOrderDao.findAbilityPlanOrdersByUserIdAndStatusAndPlaneType(userId, 1, 0);
        if (orderList != null && orderList.size() > 0) {
            return orderList.get(0);
        }
        return null;
    }

    @Override
    public AbilityPlanOrder getAbilityPlanOrderByUserId(Integer userId) {
        List<AbilityPlanOrder> orderList = abilityPlanOrderDao.findAbilityPlanOrdersByUserIdAndPlaneType(userId, 0);
        if (orderList != null && orderList.size() > 0) {
            return orderList.get(0);
        }
        return null;
    }

    @Override
    public AbilityPlanOrder getAbilityPlanOrder(Integer orderId) {
        return abilityPlanOrderDao.findOne(orderId);
    }

    @Override
    public AbilityPlanStyleAndPrice getAbilityPlanStyleAndPriceId(Integer planStyle, Integer buyNum) {

        AbilityPlanStyleAndPrice abilityPlanStyleAndPrice = new AbilityPlanStyleAndPrice();
        AbilityPlanCodeStyle abilityPlanStyle = AbilityPlanCodeStyle.TWO_FOUR_YEAR;
        int priceId = 40;
        if (buyNum == 0) {
            if (planStyle == 1) {
                abilityPlanStyle = AbilityPlanCodeStyle.TWO_FOUR_YEAR;
                priceId = 40;
            } else if (planStyle == 2) {
                abilityPlanStyle = AbilityPlanCodeStyle.FOUR_SIX_YEAR;
                priceId = 41;
            }
            abilityPlanStyleAndPrice.setPriceId(priceId);
            abilityPlanStyleAndPrice.setAbilityPlanCodeStyle(abilityPlanStyle);
        } else {
            if (planStyle == 1) {
                if (buyNum == 52) {
                    abilityPlanStyle = AbilityPlanCodeStyle.TWO_FOUR_YEAR;
                    priceId = 40;
                } else if (buyNum == 48) {
                    abilityPlanStyle = AbilityPlanCodeStyle.TWO_FOUR_OTHER;
                    priceId = 50;
                } else if (buyNum == 47) {
                    abilityPlanStyle = AbilityPlanCodeStyle.TWO_FOUR_OTHER47;
                    priceId = 54;
                } else if (buyNum == 4) {
                    abilityPlanStyle = AbilityPlanCodeStyle.TWO_FOUR_MONTH;
                    priceId = 48;
                } else {
                    throw new ApiNotFoundException("没有这种类型商品");
                }
                abilityPlanStyleAndPrice.setPriceId(priceId);
                abilityPlanStyleAndPrice.setAbilityPlanCodeStyle(abilityPlanStyle);
            } else if (planStyle == 2) {
                if (buyNum == 52) {
                    abilityPlanStyle = AbilityPlanCodeStyle.FOUR_SIX_YEAR;
                    priceId = 41;
                } else if (buyNum == 48) {
                    abilityPlanStyle = AbilityPlanCodeStyle.FOUR_SIX_OTHER;
                    priceId = 51;
                } else if (buyNum == 47) {
                    abilityPlanStyle = AbilityPlanCodeStyle.FOUR_SIX_OTHER47;
                    priceId = 55;
                } else if (buyNum == 4) {
                    abilityPlanStyle = AbilityPlanCodeStyle.FOUR_SIX_MONTH;
                    priceId = 49;
                } else {
                    throw new ApiNotFoundException("没有这种类型商品");
                }
                abilityPlanStyleAndPrice.setPriceId(priceId);
                abilityPlanStyleAndPrice.setAbilityPlanCodeStyle(abilityPlanStyle);
            }
        }

        return abilityPlanStyleAndPrice;
    }

    private List<AbilityPlanOrder> getAbilityPlanOrderByUserIdAndPriceId(Long userId, Integer priceId) {

        List<AbilityPlanOrder> orderList = abilityPlanOrderDao.findAbilityPlanOrdersByUserIdAndPriceIdAndStatus(userId.intValue(), priceId, 1);
        return orderList;
    }

    @Override
    public Boolean isBuyMonthAbilityPlan(Long userId, Integer priceId) {

        List<AbilityPlanOrder> orders = this.getAbilityPlanOrderByUserIdAndPriceId(userId, priceId);
        if (orders.size() > 0) {
            return true;
        }
        return false;
    }

    @Transactional
    @Override
    public AbilityPlanOrder createAbilityPlanOrder(Integer priceId, AbilityPlanCodeStyle abilityPlanCodeStyle, Long userId, List<Integer> couponIdsStr) {

        /**
         * 是否购买过单月
         */
        Boolean isBuyMonth = false;
        if (priceId == 48) {
            isBuyMonth = this.isBuyMonthAbilityPlan(userId, priceId);
        } else if (priceId == 49) {
            isBuyMonth = this.isBuyMonthAbilityPlan(userId, priceId);
        }
        if (isBuyMonth) {
            throw new ApiNotFoundException("您已开通过月度优宝宝会读");
        }

        User user = userService.getUser(userId);
        Integer originalPrice = abilityPlanCodeStyle.getRealPrice();
        Integer userDiscount = 100;
        Float noCouponAmount = originalPrice * ((float) userDiscount / 100);

        /**
         * 获得使用兑换券后扣除金额
         */
        CouponsResult couponsResult = new CouponsResult();
        couponsResult.checkCoupons(couponIdsStr, couponUserDao, couponDao, originalPrice);
        Integer couponAmount = couponsResult.getCouponAmount();
        Integer amount = noCouponAmount.intValue() - couponAmount;
        String channel = VersionUtil.getChannelInfo(request);

        AbilityPlanOrder abilityPlanOrder = this.addAbilityPlanOrder(
                user,
                priceId,
                abilityPlanCodeStyle.getAbilityPlanStyle(),
                originalPrice,
                couponAmount,
                amount,
                userDiscount,
                channel,
                OrderPayStyle.DEFAULT_NULL,
                0
        );
        couponOrderRelateService.addCouponOrderByCouponIdsStr(couponIdsStr, userId.intValue(), abilityPlanOrder.getId(), OrderStyle.ABILITY_PLAN_ORDER);
        return abilityPlanOrder;
    }

    @Override
    public AbilityPlanOrder createAbilityPlanOrder(Integer price, Integer month, Integer baobaoWeek, Long userId, List<Integer> couponIds,Integer onlineOnly) {
        /**
         * 是否购买过单月
         */
        if(month<=4){
//            boolean isBuyMonth = false;
            boolean isBuyMonth46 = this.isBuyMonthAbilityPlan(userId, AbilityPlanCodeStyle.FOUR_SIX_MONTH.getPriceId());
            boolean isBuyMonth24 = this.isBuyMonthAbilityPlan(userId, AbilityPlanCodeStyle.TWO_FOUR_MONTH.getPriceId());
            if (isBuyMonth46||isBuyMonth24) {
                throw new ApiNotFoundException("您已开通过月度优宝宝会读");
            }
        }
//        Boolean isBuyMonth = false;
//        if (priceId == 48) {
//            isBuyMonth = this.isBuyMonthAbilityPlan(userId, priceId);
//        } else if (priceId == 49) {
//            isBuyMonth = this.isBuyMonthAbilityPlan(userId, priceId);
//        }
//        if (isBuyMonth) {
//            throw new ApiNotFoundException("您已开通过月度优宝宝会读");
//        }
        AbilityPlanStyle abilityPlanStyle;
        int priceId=AbilityPlanCodeStyle.TWO_FOUR_MONTH.getPriceId();
        if(month==12){
            priceId=AbilityPlanCodeStyle.TWO_FOUR_YEAR.getPriceId();
        }

        User user = userService.getUser(userId);
        Integer originalPrice = price;
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

        AbilityPlanOrder abilityPlanOrder = this.addAbilityPlanOrder(
                user,
                priceId,
                AbilityPlanStyle.DEFAULT,
                originalPrice,
                couponAmount,
                amount,
                userDiscount,
                channel,
                OrderPayStyle.DEFAULT_NULL,
                onlineOnly
        );
        couponOrderRelateService.addCouponOrderByCouponIdsStr(couponIds, userId.intValue(), abilityPlanOrder.getId(), OrderStyle.ABILITY_PLAN_ORDER);
        return abilityPlanOrder;
    }

    @Override
    @Transactional
    public AbilityPlanOrder addAbilityPlanOrder(User user, Integer priceId, AbilityPlanStyle abilityPlanStyle, Integer originalPrice, Integer couponAmount, Integer amount, Integer userDiscount, String channel, OrderPayStyle orderPayStyle,Integer onlineOnly) {

        AbilityPlanOrder abilityPlanOrder = new AbilityPlanOrder();

        abilityPlanOrder.setUserId(user.getId().intValue());
        abilityPlanOrder.setPriceId(priceId);
        abilityPlanOrder.setAbilityPlanStyle(abilityPlanStyle);
        abilityPlanOrder.setOriginalPrice(originalPrice);
        abilityPlanOrder.setCouponAmount(couponAmount);
        abilityPlanOrder.setAmount(amount);
        abilityPlanOrder.setSuccessTime(new Date());
        abilityPlanOrder.setStatus(0);
        abilityPlanOrder.setCode("");
        abilityPlanOrder.setCreateTime(new Date());
        abilityPlanOrder.setUserDiscount(userDiscount);
        abilityPlanOrder.setPayStyle(orderPayStyle);
        abilityPlanOrder.setIsDel(0);
        abilityPlanOrder.setIsTest(user.getIsTest());
        abilityPlanOrder.setChannel(channel);
        abilityPlanOrder.setRemark("");
        abilityPlanOrder.setOnlineOnly(onlineOnly);
        abilityPlanOrder = abilityPlanOrderDao.save(abilityPlanOrder);

        OrderMix orderMix = orderMixService.addOrderMix(OrderStyle.ABILITY_PLAN_ORDER, abilityPlanOrder.getId(), user.getId().intValue());
        abilityPlanOrder.setMixOrderId(orderMix.getId());
        return abilityPlanOrder;
    }

    @Transactional
    @Override
    public AbilityPlanOrder buyAbilityPlanByBalance(Long userId, Integer orderId, OrderPayStyle payStyle, WalletStyle walletStyle) {

        AbilityPlanOrder abilityPlanOrder = abilityPlanOrderDao.findOne(orderId);
        UserExtend userExtend = userExtendService.findUserExtendByUserId(userId);
        Integer type = userExtend.getWeekPlanTypeNew();
        Integer priceId = abilityPlanOrder.getPriceId();
        if (type == 1) {
            //2-4岁
            priceId = 40;
        } else if (type == 2) {
            //4-6岁
            priceId = 41;
        }
        /**
         * 安卓余额订单需要重新计算订单折扣
         */
        Integer newAmount = walletService.getAndroidUserNewAmount(userId.intValue(), abilityPlanOrder.getAmount(), payStyle, walletStyle);
        if (!newAmount.equals(abilityPlanOrder.getAmount())) {
            Wallet wallet = walletService.getWalletByUserId(userId);
            abilityPlanOrder.setAmount(newAmount);
            abilityPlanOrder.setUserDiscount(wallet.getDiscount());
        }

        walletService.addAmountToWallet(userId.intValue(), walletStyle, RechargeStyle.BUY_ABILITY_PLAN, NumberUtil.unAbs(abilityPlanOrder.getAmount()), "abilityPlan_" + orderId, "宝宝会读订单");
        AbilityPlanStyle abilityPlanStyle = AbilityPlanStyle.getById(userExtend.getWeekPlanType());
        if (abilityPlanStyle == null) {
            throw new ApiNotFoundException("未找到对应商品无法支付！");
        }
        //获得优能计划购买类型
        AbilityPlanCodeStyle abilityPlanCodeStyle = AbilityPlanCodeStyle.getByPriceId(priceId);
        userAbilityPlanRelateService.createUserAbilityPlanRelate(userId.intValue(), abilityPlanCodeStyle);
        /**
         * 调用积分流水添加 宝宝会读（优能计划）
         */
        Integer starCount = StarConfig.STAR_USR_BUY * (abilityPlanOrder.getAmount()) / 100;
        walletService.addStarToWallet(userId.intValue(), StarRechargeStyle.BUY_ABILITY_PLAN, starCount, StarContentStyle.BUY_ABILITY_PLAN.getName());

        abilityPlanOrder.setSuccessTime(new Date());
        abilityPlanOrder.setStatus(1);
        abilityPlanOrder.setPayStyle(payStyle);
        abilityPlanOrder = abilityPlanOrderDao.save(abilityPlanOrder);
        orderMixService.updateOrderMixStatus(OrderStyle.ABILITY_PLAN_ORDER, abilityPlanOrder.getId(), OrderStatusStyle.PAY_SUCCESS);
        /**
         * 取消其他未支付订单
         */
        List<OrderMix> orderMixList = orderMixDao.findListByUserIdAndStatusAndOrderType(userId.intValue(), 0, RechargeStyle.BUY_ABILITY_PLAN.getId());
        for (OrderMix o : orderMixList) {
            try {
                orderMixService.cancelOrder(userId.intValue(), o.getOrderId(), RechargeStyle.BUY_ABILITY_PLAN);
            } catch (Exception e) {
                logger.info(e);
            }
        }
        /**
         * 参加宝宝会读（优能计划） 211 购买完成不推送
         */
        if (type > 0) {
            abilityPlanOrder.setPriceId(priceId);
            abilityPlanOrder.setAbilityPlanStyle(AbilityPlanStyle.getById(type));
            abilityPlanOrderDao.save(abilityPlanOrder);
            /**创建记录和增加buyNum*/
            weekPlanJoinService.createWeekPlanJoin(userId.intValue(), WeekPlanStyle.getById(type));
            weekPlanJoinService.updateWeekPlanJoinBuyNum(orderId,userId.intValue(), WeekPlanStyle.getById(type), abilityPlanOrder.getPriceId());
            //推送一周内容
            weekPlanUserRecordService.sendWeekPlanUserRecordByUserIdAndPlanTypeToSomeone(userId.intValue(), WeekPlanStyle.getById(type));
        }

        /**
         * 发送系统消息
         */
        Map<String, String> contentMap = new HashMap<>();
        templateNoticeService.addNoticeToUserByUserIdAndMessage(NoticeStyle.BUY_ABILITY, contentMap, userId.intValue());


        //兑换码兑换，增加课程阅读标记
        payLessonOrderService.addBuyLessonItemRecordAndOrderLesson(userId.intValue(), 1, 0, 0, 50,1);
        payLessonOrderService.addBuyLessonItemRecordAndOrderLesson(userId.intValue(), 2, 0, 0, 50,1);

        return abilityPlanOrder;
    }

//    @Override
//    public AbilityPlanOrder getAbilityPlanOrderById(Integer orderId) {
//
//        return abilityPlanOrderDao.findOne(orderId);
//    }

    @Override
    public SubscribeByCodeResponse buyAbilityPlanByCode(Integer ver, Long userId, Integer priceId, AbilityPlanCodeStyle abilityPlanCodeStyle, PreSaleCode preSaleCode, String receiver, String phone, String address, String area, Integer codeType) {
        AbilityPlanOrder abilityPlanOrder;
        if (ver == 210) {
            abilityPlanOrder = this.buyAbilityPlanByCodeMethod210(userId, priceId, abilityPlanCodeStyle, preSaleCode);
        } else {
            abilityPlanOrder = this.buyAbilityPlanByCodeMethod(userId, priceId, abilityPlanCodeStyle, preSaleCode);
        }

        if (codeType == 2) {
            shopExpressService.addExpress(abilityPlanOrder.getId(), receiver, phone, address, area, ExpressStyle.DEFAULT_NULL, "", ExpressStatusStyle.HAS_NO_DELIVERY, OrderStyle.ABILITY_PLAN_ORDER);
        }

        preSaleCodeService.usePreSaleCode(preSaleCode,userId.intValue());
        SubscribeByCodeResponse response = new SubscribeByCodeResponse();
        String intro = "恭喜您成功兑换故事飞船宝宝会读！";
        response.setTargetValue(0);
        response.setIntro(intro);
        if (abilityPlanCodeStyle.getAbilityPlanStyle() == AbilityPlanStyle.TWO_FOUR) {
            response.setType(VipCodeStyle.ABILITY_PLAN_TWO_FOUR.getId());
        } else {
            response.setType(VipCodeStyle.ABILITY_PLAN_FOUR_SIX.getId());
        }

        //兑换码兑换，增加课程阅读标记
        payLessonOrderService.addBuyLessonItemRecordAndOrderLesson(userId.intValue(), 1, 0, 0, 50,1);
        payLessonOrderService.addBuyLessonItemRecordAndOrderLesson(userId.intValue(), 2, 0, 0, 50,1);
        return response;
    }

    @Override
    public AbilityPlanOrder buyAbilityPlanByCodeMethod210(Long userId, Integer priceId, AbilityPlanCodeStyle abilityPlanCodeStyle, PreSaleCode preSaleCode) {

        AbilityPlanOrder abilityPlanOrder = this.createAbilityPlanOrder(priceId, abilityPlanCodeStyle, userId, new ArrayList<>());
        userAbilityPlanRelateService.createUserAbilityPlanRelate(userId.intValue(), abilityPlanCodeStyle);

        abilityPlanOrder.setSuccessTime(new Date());
        abilityPlanOrder.setStatus(1);
        abilityPlanOrder.setCode(preSaleCode.getCode());
        abilityPlanOrder.setPayStyle(OrderPayStyle.CODE);
        if(abilityPlanCodeStyle.getOnlineOnly()==1){
            abilityPlanOrder.setOnlineOnly(1);
        }

        abilityPlanOrder = abilityPlanOrderDao.save(abilityPlanOrder);
        orderMixService.updateOrderMixStatus(OrderStyle.ABILITY_PLAN_ORDER, abilityPlanOrder.getId(), OrderStatusStyle.PAY_SUCCESS);
        /**
         * 取消其他未支付订单
         */
        List<OrderMix> orderMixList = orderMixDao.findListByUserIdAndStatusAndOrderType(userId.intValue(), 0, RechargeStyle.BUY_ABILITY_PLAN.getId());
        for (OrderMix o : orderMixList) {
            try {
                orderMixService.cancelOrder(userId.intValue(), o.getOrderId(), RechargeStyle.BUY_ABILITY_PLAN);
            } catch (Exception e) {
                logger.info(e);
            }
        }
        /**
         * 参加宝宝会读（优能计划）
         */
        weekPlanJoinService.createWeekPlanJoin(userId.intValue(), WeekPlanStyle.getById(abilityPlanOrder.getPlaneType()));
        weekPlanJoinService.updateWeekPlanJoinBuyNum(abilityPlanOrder.getId(),userId.intValue(), WeekPlanStyle.getById(abilityPlanOrder.getPlaneType()), abilityPlanOrder.getPriceId());
        //推送一周内容
        weekPlanUserRecordService.sendWeekPlanUserRecordByUserIdAndPlanType(userId.intValue(), WeekPlanStyle.getById(abilityPlanOrder.getPlaneType()));
        /**
         * 发送系统消息
         */
        Map<String, String> contentMap = new HashMap<>();
        templateNoticeService.addNoticeToUserByUserIdAndMessage(NoticeStyle.BUY_ABILITY, contentMap, userId.intValue());
        return abilityPlanOrder;
    }


    @Override
    public AbilityPlanOrder buyAbilityPlanByCodeMethod(Long userId, Integer priceId, AbilityPlanCodeStyle abilityPlanCodeStyle, PreSaleCode preSaleCode) {

        AbilityPlanOrder abilityPlanOrder = this.createAbilityPlanOrder(priceId, abilityPlanCodeStyle, userId, new ArrayList<>());
        userAbilityPlanRelateService.createUserAbilityPlanRelate(userId.intValue(), abilityPlanCodeStyle);

        abilityPlanOrder.setSuccessTime(new Date());
        abilityPlanOrder.setStatus(1);
        abilityPlanOrder.setCode(preSaleCode.getCode());
        abilityPlanOrder.setPayStyle(OrderPayStyle.CODE);
        abilityPlanOrder = abilityPlanOrderDao.save(abilityPlanOrder);
        if(abilityPlanCodeStyle.getOnlineOnly()==1){
            abilityPlanOrder.setOnlineOnly(1);
        }
        orderMixService.updateOrderMixStatus(OrderStyle.ABILITY_PLAN_ORDER, abilityPlanOrder.getId(), OrderStatusStyle.PAY_SUCCESS);
        /**
         * 取消其他未支付订单
         */
        List<OrderMix> orderMixList = orderMixDao.findListByUserIdAndStatusAndOrderType(userId.intValue(), 0, RechargeStyle.BUY_ABILITY_PLAN.getId());
        for (OrderMix o : orderMixList) {
            try {
                orderMixService.cancelOrder(userId.intValue(), o.getOrderId(), RechargeStyle.BUY_ABILITY_PLAN);
            } catch (Exception e) {
                logger.info(e);
            }
        }

        //修复用户状态已选择年龄，但订单没选择的补丁
        UserExtend userExtend=userExtendService.findUserExtendByUserId(userId);
        if(abilityPlanOrder.getPlaneType()==0&&userExtend.getWeekPlanType()!=0){
            abilityPlanOrder.setAbilityPlanStyle(AbilityPlanStyle.getById(userExtend.getWeekPlanType()));
        }

        /**
         * 参加宝宝会读（优能计划）
         */
        if (abilityPlanOrder.getPlaneType() > 0) {

            /**创建记录和增加buyNum*/
            weekPlanJoinService.createWeekPlanJoin(userId.intValue(), WeekPlanStyle.getById(abilityPlanOrder.getPlaneType()));
            weekPlanJoinService.updateWeekPlanJoinBuyNum(abilityPlanOrder.getId(),userId.intValue(), WeekPlanStyle.getById(abilityPlanOrder.getPlaneType()), abilityPlanOrder.getPriceId());

            weekPlanUserRecordService.sendWeekPlanUserRecordByUserIdAndPlanTypeToSomeone(userId.intValue(), WeekPlanStyle.getById(abilityPlanOrder.getPlaneType()));
        }
        /**
         * 发送系统消息
         */
        Map<String, String> contentMap = new HashMap<>();
        templateNoticeService.addNoticeToUserByUserIdAndMessage(NoticeStyle.BUY_ABILITY, contentMap, userId.intValue());
        return abilityPlanOrder;
    }


    @Override
    public AbilityPlanOrder getAbilityPlanOrderByPlanTypeAndUserIdAndStatus(WeekPlanStyle weekPlanStyle, Integer userId, Integer status) {

        Sort sort = new Sort(Sort.Direction.ASC, "priceId");
        List<AbilityPlanOrder> orderList = abilityPlanOrderDao.getAbilityPlanOrdersByUserIdAndPlanType(userId, weekPlanStyle.getId(), status, sort);
        if (orderList != null && orderList.size() > 0) {
            return orderList.get(0);
        }
        return null;
    }

//    @Override
//    public void addRelateByVip(List<User> userList) {
//
//        for (User u : userList) {
//            //查询是否有优能计划定单
//            List<AbilityPlanOrder> orderList = abilityPlanOrderDao.getAbilityPlanOrdersByUserIdAndStatus(u.getId().intValue(), 1);
//            if (orderList != null && orderList.size() > 0) {
//                UserAbilityPlanRelate record = userAbilityPlanRelateService.getLastestUserAbilityPlanRelateRecord(u.getId());
//                if (record != null) {
//                    continue;
//                }
//                AbilityPlanOrder order = orderList.get(0);
//                UserSvip userSvip = userSvipService.getLastestUserSvipRecord(u.getId());
//                //添加svip时间段
//                Date sTime = new Date();
//                Date eTime = new Date();
//                if (userSvip != null) {
//                    sTime = userSvip.getStartTime();
//                    eTime = userSvip.getEndTime();
//                }
//                logger.info("++++++++++++vvvvvvvvvvvvip----通过relate user" + u.getId());
//                userAbilityPlanRelateService.addUserAbilityPlanRelate(u.getId().intValue(), AbilityPlanStyle.getById(order.getPlaneType()), sTime, eTime);
//
//            }
//        }
//
//
//    }
//1
//    @Override
//    public void addOrderByVip(List<User> userList) {
//
//        for (User u : userList) {
//
//            UserExtend userExtend = userExtendService.findUserExtendByUserId(u.getId());
//            //查询是否有优能计划定单
//            Sort sort = new Sort(Sort.Direction.ASC, "priceId");
//            List<AbilityPlanOrder> orderList = abilityPlanOrderDao.getAbilityPlanOrdersByUserIdAndPlanType(u.getId().intValue(), userExtend.getWeekPlanType(), 1, sort);
//            if (orderList != null && orderList.size() > 0) {
//                continue;
//            }
//            //没有添加新成功订单 根据user_extend所选优能计划类型
//            AbilityPlanStyle abilityPlanStyle = AbilityPlanStyle.getById(userExtend.getWeekPlanType());
//            if (abilityPlanStyle != null) {
//                logger.info("vvvvvvvvvvvvip----通过VIP补充用户订单 user" + u.getId());
//                this.addOrderByVip(u.getId().intValue(), abilityPlanStyle);
//            }
//        }
//    }

//    private AbilityPlanOrder addOrderByVip(Integer userId, AbilityPlanStyle abilityPlanStyle) {
//
//        AbilityPlanOrder abilityPlanOrder = new AbilityPlanOrder();
//        abilityPlanOrder.setUserId(userId);
//        //40 2-4  41  4-6
//        if (abilityPlanStyle == AbilityPlanStyle.TWO_FOUR) {
//            abilityPlanOrder.setPriceId(40);
//        } else if (abilityPlanStyle == AbilityPlanStyle.FOUR_SIX) {
//            abilityPlanOrder.setPriceId(41);
//        }
//        abilityPlanOrder.setOriginalPrice(79800);
//        abilityPlanOrder.setCouponAmount(0);
//        abilityPlanOrder.setAmount(79800);
//        abilityPlanOrder.setSuccessTime(new Date());
//        abilityPlanOrder.setCreateTime(new Date());
//        abilityPlanOrder.setUserDiscount(0);
//        abilityPlanOrder.setPayStyle(OrderPayStyle.DEFAULT_NULL);
//        abilityPlanOrder.setCode("");
//        abilityPlanOrder.setAbilityPlanStyle(abilityPlanStyle);
//        abilityPlanOrder.setStatus(1);
//        abilityPlanOrder.setIsDel(0);
//        abilityPlanOrder.setIsTest(0);
//        abilityPlanOrder.setChannel("通过 VIP 补充的订单");
//        abilityPlanOrder.setRemark("");
//
//        return abilityPlanOrderDao.save(abilityPlanOrder);
//    }


//    @Override
//    public void addRecordByVip(List<User> userList) {
//
//        for (User u : userList) {
//
//            UserExtend userExtend = userExtendService.findUserExtendByUserId(u.getId());
//
//            Pageable pageable = new PageRequest(0, 1, new Sort(Sort.Direction.DESC, "weekPlanId"));
//            List<WeekPlanUserRecord> weekPlanUserRecords = weekPlanUserRecordDao.getListTwoFourByUserId(userExtend.getUserId(), pageable);
//            if (weekPlanUserRecords == null || weekPlanUserRecords.size() == 0) {
//                continue;
//            } else {
//                WeekPlanUserRecord weekPlanUserRecord = weekPlanUserRecords.get(0);
//                if (userExtend.getWeekPlanType() == 1 && weekPlanUserRecord.getWeekPlanId() < 10) {
//                    logger.info("rrrrrrrrrecordp--2-4--通过VIP补充推送记录2-4s岁 user" + u.getId());
//                    weekPlanUserRecordService.sendWeekPlanUserRecordByUserIdAndPlanType(userExtend.getUserId(), WeekPlanStyle.getById(userExtend.getWeekPlanType()));
//                }
//                if (userExtend.getWeekPlanType() == 2 && weekPlanUserRecord.getWeekPlanId() < 62) {
//                    logger.info("rrrrrrrrrecordp--4-6--通过VIP补充推送记录4-6s岁 user" + u.getId());
//                    weekPlanUserRecordService.sendWeekPlanUserRecordByUserIdAndPlanType(userExtend.getUserId(), WeekPlanStyle.getById(userExtend.getWeekPlanType()));
//                }
//            }
//        }
//    }

    @Override
    public Page<AbilityPlanOrder> getOrdersByTypeAndStatus(Integer payType, Integer status, Pageable pageable) {

        return abilityPlanOrderDao.getAbilityPlanOrdersByTypeAndStatus(payType, status, pageable);

    }

    @Override
    public void updateBuyNum(List<AbilityPlanOrder> orderList) {

        for (AbilityPlanOrder o : orderList) {

            Sort sort = new Sort(Sort.Direction.DESC, "originalPrice");
            List<PayVipOrder> vipOrderList = payVipOrderDao.findPayVipOrdersByUserId(o.getUserId(), sort);
            PayVipOrder vipOrder = null;
            if (vipOrderList != null && vipOrderList.size() > 0) {
                vipOrder = vipOrderList.get(0);
            }
            if (vipOrder != null) {
                int priceId = vipOrder.getPriceId();
                int buyNum = 5;
                if (priceId == 28 || priceId == 31 || priceId == 34 || priceId == 37) {
                    buyNum = 52;
                } else if (priceId == 29 || priceId == 32 || priceId == 35 || priceId == 38) {
                    buyNum = 26;
                } else if (priceId == 30 || priceId == 33 || priceId == 36 || priceId == 39) {
                    buyNum = 13;
                }
                WeekPlanJoin join = weekPlanJoinService.getWeekPlanJoinByUserIdAndType(o.getUserId(), WeekPlanStyle.getById(o.getPlaneType()));
                join.setBuyNum(buyNum);
                logger.info("uuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuu-s岁 user" + join.getUserId() + "buy_num = " + buyNum);
                weekPlanJoinDao.save(join);
            } else {
                WeekPlanJoin join = weekPlanJoinService.getWeekPlanJoinByUserIdAndType(o.getUserId(), WeekPlanStyle.getById(o.getPlaneType()));
                join.setBuyNum(5);
                logger.info("uuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuu-s岁 user" + join.getUserId() + "buy_num = 4");
                weekPlanJoinDao.save(join);
            }
        }

    }

    @Override
    public Page<AbilityPlanOrder> findAll(AbilityPlanOrder abilityPlanOrder, Pageable pageable) {
        return abilityPlanOrderDao.findAll(Example.of(abilityPlanOrder),pageable);
    }

    @Override
    public Page<AbilityPlanOrder> findAll(AbilityQuery abilityQuery, Pageable pageable) {
        return abilityPlanOrderDao.findAll(abilitySepc(abilityQuery),pageable);
    }

    /**
     * 广告混合条件搜索
     * @param /
     * @return
     */
    private Specification<AbilityPlanOrder> abilitySepc(AbilityQuery abilityQuery){
        return new Specification<AbilityPlanOrder>() {

            @Override
            public Predicate toPredicate(Root<AbilityPlanOrder> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder criteriaBuilder) {
                List<Predicate> predicates=new ArrayList<Predicate>();
                if(abilityQuery.getId()!=null){
                    predicates.add(criteriaBuilder.equal(root.get("id"),abilityQuery.getId()));
                }
                if(abilityQuery.getStatus()!=null){
                    predicates.add(criteriaBuilder.equal(root.get("status"),abilityQuery.getStatus()));
                }
                if(abilityQuery.getIsTest()!=null){
                    predicates.add(criteriaBuilder.equal(root.get("isTest"),abilityQuery.getIsTest()));
                }
                if(abilityQuery.getIsDel()!=null){
                    predicates.add(criteriaBuilder.equal(root.get("isDel"),abilityQuery.getIsDel()));
                }
                if(abilityQuery.getIsCode()!=null){
                    predicates.add(criteriaBuilder.equal(root.get("isCode"),abilityQuery.getIsCode()));
                }
                if(abilityQuery.getUserId()!=null){
                    predicates.add(criteriaBuilder.equal(root.get("userId"),abilityQuery.getUserId()));
                }
                if(abilityQuery.getCode()!=null&&!abilityQuery.getCode().equals("")){
                    predicates.add(criteriaBuilder.equal(root.get("code"),abilityQuery.getCode()));
                }
                if(abilityQuery.getBeginCreateTime()!=null){
                    predicates.add(criteriaBuilder.greaterThanOrEqualTo(root.get("createTime"),abilityQuery.getBeginCreateTime()));
                }
                if(abilityQuery.getEndCreateTime()!=null){
                    predicates.add(criteriaBuilder.lessThanOrEqualTo(root.get("createTime"),abilityQuery.getEndCreateTime()));
                }

                if(abilityQuery.getBeginSuccessTime()!=null){
                    predicates.add(criteriaBuilder.greaterThanOrEqualTo(root.get("successTime"),abilityQuery.getBeginSuccessTime()));
                }
                if(abilityQuery.getEndSuccessTime()!=null){
                    predicates.add(criteriaBuilder.lessThanOrEqualTo(root.get("successTime"),abilityQuery.getEndSuccessTime()));
                }

                if(abilityQuery.getIsBaobao()!=null){
                    if(abilityQuery.getIsBaobao()>0){
                        predicates.add(criteriaBuilder.greaterThanOrEqualTo(root.get("id"),1103));
                    }
                    if(abilityQuery.getIsBaobao()==0){

                        predicates.add(criteriaBuilder.lessThan(root.get("id"),1103));
                    }
                }

                return criteriaBuilder.and(predicates.toArray(new Predicate[]{}));
            }
        };
    }
}

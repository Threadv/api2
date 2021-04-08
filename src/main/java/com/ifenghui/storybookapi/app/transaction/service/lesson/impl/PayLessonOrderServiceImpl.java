package com.ifenghui.storybookapi.app.transaction.service.lesson.impl;

import com.ifenghui.storybookapi.app.app.service.TemplateNoticeService;
import com.ifenghui.storybookapi.app.presale.entity.PreSaleCode;
import com.ifenghui.storybookapi.app.presale.entity.SaleGoodsStyle;
import com.ifenghui.storybookapi.app.presale.service.PreSaleCodeService;
import com.ifenghui.storybookapi.app.story.entity.Lesson;
import com.ifenghui.storybookapi.app.story.entity.LessonItem;
import com.ifenghui.storybookapi.app.story.service.LessonItemService;
import com.ifenghui.storybookapi.app.story.service.LessonService;
import com.ifenghui.storybookapi.app.transaction.controller.OrderController;
import com.ifenghui.storybookapi.app.transaction.dao.CouponDao;
import com.ifenghui.storybookapi.app.transaction.dao.CouponUserDao;
import com.ifenghui.storybookapi.app.transaction.dao.lesson.PayLessonOrderDao;
import com.ifenghui.storybookapi.app.transaction.dao.lesson.PayLessonPriceDao;
import com.ifenghui.storybookapi.app.transaction.entity.OrderMix;
import com.ifenghui.storybookapi.app.transaction.entity.coupon.CouponStoryExchangeUser;
import com.ifenghui.storybookapi.app.transaction.entity.lesson.CouponLessonOrder;
import com.ifenghui.storybookapi.app.transaction.entity.lesson.PayLessonOrder;
import com.ifenghui.storybookapi.app.transaction.entity.lesson.PayLessonPrice;
import com.ifenghui.storybookapi.app.transaction.entity.serial.PaySerialStoryOrder;
import com.ifenghui.storybookapi.app.transaction.response.lesson.GetShareMagazineStatusResponse;
import com.ifenghui.storybookapi.app.transaction.service.CouponStoryExchangeOrderService;
import com.ifenghui.storybookapi.app.transaction.service.CouponStoryExchangeUserService;
import com.ifenghui.storybookapi.app.transaction.service.CouponsResult;
import com.ifenghui.storybookapi.app.transaction.service.PayAfterOrderService;
import com.ifenghui.storybookapi.app.transaction.service.impl.BuySerialServiceImpl;
import com.ifenghui.storybookapi.app.transaction.service.lesson.*;
import com.ifenghui.storybookapi.app.transaction.service.order.OrderMixService;
import com.ifenghui.storybookapi.app.user.dao.UserDao;
import com.ifenghui.storybookapi.app.user.entity.User;
import com.ifenghui.storybookapi.app.user.service.UserService;
import com.ifenghui.storybookapi.app.wallet.entity.Wallet;
import com.ifenghui.storybookapi.app.wallet.response.SubscribeByCodeResponse;
import com.ifenghui.storybookapi.app.wallet.service.PayRechargeOrderService;
import com.ifenghui.storybookapi.app.wallet.service.WalletService;
import com.ifenghui.storybookapi.config.StarConfig;
import com.ifenghui.storybookapi.exception.*;
import com.ifenghui.storybookapi.style.*;
import com.ifenghui.storybookapi.util.ListUtil;
import com.ifenghui.storybookapi.util.NumberUtil;
import com.ifenghui.storybookapi.util.VersionUtil;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Component;
import sun.security.krb5.internal.PAData;

import javax.servlet.http.HttpServletRequest;
import javax.transaction.Transactional;
import java.beans.Transient;
import java.util.*;

@Component
public class PayLessonOrderServiceImpl implements PayLessonOrderService {

    Logger logger= Logger.getLogger(BuySerialServiceImpl.class);

    @Autowired
    CouponLessonOrderService couponLessonOrderService;

    @Autowired
    PayLessonOrderDao payLessonOrderDao;

    @Autowired
    BuyLessonItemRecordService buyLessonItemRecordService;

    @Autowired
    LessonService lessonService;

    @Autowired
    PayLessonPriceDao payLessonPriceDao;

    @Autowired
    UserDao userDao;

    @Autowired
    PayLessonPriceService payLessonPriceService;

    @Autowired
    WalletService walletService;

    @Autowired
    HttpServletRequest request;

    @Autowired
    CouponUserDao couponUserDao;

    @Autowired
    CouponDao couponDao;

    @Autowired
    PayRechargeOrderService payRechargeOrderService;

    @Autowired
    PayAfterOrderService payAfterOrderService;

    @Autowired
    UserService userService;

    @Autowired
    CouponStoryExchangeOrderService couponStoryExchangeOrderService;

    @Autowired
    CouponStoryExchangeUserService couponStoryExchangeUserService;

    @Autowired
    LessonItemService lessonItemService;

    @Autowired
    OrderLessonService orderLessonService;

    @Autowired
    TemplateNoticeService templateNoticeService;

    @Autowired
    PreSaleCodeService preSaleCodeService;

    @Autowired
    OrderMixService orderMixService;




    @Transactional
    @Override
    public PayLessonOrder createPayLessonOrder(Integer lessonId, Integer priceId, Integer lessonNum, Long userId,List<Integer> couponIds, Integer realPrice) throws ApiException {
        couponIds= ListUtil.removeNull(couponIds);
        /**
         * 检测是否有数量剩余创建订单
         */
        this.checkIsCanCreatePayLessonOrder(userId.intValue(),priceId,lessonId, lessonNum);


        /**
         * 获得可以购买的数量
         */
        Lesson lesson = lessonService.getLessonById(lessonId);
        PayLessonPrice payLessonPrice = payLessonPriceDao.findOne(priceId);
        payLessonPrice.setLessonId(lessonId);
        if(payLessonPrice.getLessonItemCount().equals(0)){
            Integer hasLessonItemCount = buyLessonItemRecordService.getBuyLessonItemCount(userId.intValue(),lessonId);
            Integer buyLessonItemCount = lesson.getLessonItemCount() - hasLessonItemCount;
            payLessonPrice.setLessonNum(buyLessonItemCount);
            payLessonPrice.setOrderDiscount(payLessonPriceService.getRemainPayLessonPriceDiscount(buyLessonItemCount));
        } else {
            payLessonPrice.setLessonNum(payLessonPrice.getLessonItemCount());
            payLessonPrice.setOrderDiscount(payLessonPrice.getDiscount());
        }
        User user = userDao.findOne(userId);

        Integer orignalPrice = lesson.getPerPrice() * payLessonPrice.getLessonNum();
        Integer userDiscount = 100;
        Integer orderDiscount = payLessonPrice.getOrderDiscount();

        Float noCouponAmount = orignalPrice * ((float)orderDiscount /100) * ((float)userDiscount / 100);
        if (!realPrice.equals(noCouponAmount.intValue())){
            throw new ApiDuplicateException("订单金额错误！");
        }

        /**
         * 获得使用兑换券后扣除金额
         */
        CouponsResult couponsResult=new CouponsResult();
        couponsResult.checkCoupons(couponIds,couponUserDao,couponDao, orignalPrice);
        Integer couponAmount = couponsResult.getCouponAmount();

        Integer amount = noCouponAmount.intValue() - couponAmount;

        String channel = VersionUtil.getChannelInfo(request);

        PayLessonOrder payLessonOrder = this.addPayLessonOrder(
                user,
                orignalPrice,
                couponAmount,
                amount,
                priceId,
                payLessonPrice.getOrderDiscount(),
                userDiscount,
                channel,
                payLessonPrice.getLessonNum(),
                lessonId,
                "",
                OrderPayStyle.DEFAULT_NULL
                );

        couponLessonOrderService.addCouponOrderByCouponIdsStr(couponIds,userId.intValue(),payLessonOrder.getId());


        return payLessonOrder;
    }

    @Transactional
    @Override
    public PayLessonOrder addPayLessonOrder(User user, Integer orignalPrice, Integer couponAmount, Integer amount, Integer priceId, Integer orderDiscount, Integer userDiscount, String channel, Integer num, Integer lessonId, String code, OrderPayStyle orderPayStyle) {
        PayLessonOrder payLessonOrder = new PayLessonOrder();
        payLessonOrder.setUserId(user.getId().intValue());
        payLessonOrder.setOriginalPrice(orignalPrice);
        payLessonOrder.setCouponAmount(couponAmount);
        payLessonOrder.setAmount(amount);
        payLessonOrder.setStatus(0);
        payLessonOrder.setLessonId(lessonId);
        payLessonOrder.setPriceId(priceId);
        payLessonOrder.setCreateTime(new Date());
        payLessonOrder.setOrderDiscount(orderDiscount);
        payLessonOrder.setUserDiscount(userDiscount);
        payLessonOrder.setPayStyle(orderPayStyle);
        payLessonOrder.setCode(code);
        payLessonOrder.setIsDel(0);
        payLessonOrder.setIsTest(user.getIsTest());
        payLessonOrder.setChannel(channel);
        payLessonOrder.setNum(num);
        payLessonOrder.setRemark("");
        Integer couponNum = 0;
        if(orderPayStyle.equals(OrderPayStyle.STORY_COUPON)){
            couponNum = num * 3;
        }
        payLessonOrder.setCouponNum(couponNum);

        payLessonOrder = payLessonOrderDao.save(payLessonOrder);

        OrderMix orderMix = orderMixService.addOrderMix(OrderStyle.LESSON_ORDER,payLessonOrder.getId(),user.getId().intValue());
        payLessonOrder.setMixOrderId(orderMix.getId());
        return payLessonOrder;
    }

    @Override
    public void checkIsCanCreatePayLessonOrder(Integer userId, Integer priceId, Integer lessonId, Integer lessonNum) {

        Integer canBuy = this.getCanBuyLessonNum(userId, lessonId);

        PayLessonPrice payLessonPrice = payLessonPriceDao.findOne(priceId);
        payLessonPrice.setLessonId(lessonId);
        if(
            (!payLessonPrice.getLessonItemCount().equals(0) && canBuy < payLessonPrice.getLessonItemCount()) ||
            (payLessonPrice.getLessonItemCount().equals(0) && !canBuy.equals(lessonNum))
        ){
            List<PayLessonOrder> payLessonOrderList = this.getPayLessonOrderList(userId,0, lessonId);
            if(payLessonOrderList.size() > 0){
                PayLessonOrder payLessonOrder = payLessonOrderList.get(0);
                throw new ApiStoryOrderRepeatException(payLessonOrder,"您有待支付订单！");
            } else {
                throw new ApiDuplicateException("价格信息已更新，请刷新！");
            }
        }
    }


    @Override
    public Integer getCanBuyLessonNum(Integer userId, Integer lessonId){

        Integer hasBuyLessonNum = buyLessonItemRecordService.getBuyLessonItemCount(userId, lessonId);
        Lesson lesson = lessonService.getLessonById(lessonId);
        Integer canBuyLessonNum = lesson.getLessonItemCount() - hasBuyLessonNum;
        if(hasBuyLessonNum >= lesson.getLessonItemCount()){
            throw new ApiDuplicateException("您已经购买全部课程！");
        }
        Integer lessonOrderSumLessonNum = this.getLessonOrderCountLessonNum(userId, 0, lessonId);

        Integer canBuy = canBuyLessonNum - lessonOrderSumLessonNum;

        return  canBuy;
    }

    @Override
    public Integer getLessonOrderCountLessonNum(Integer userId, Integer status, Integer lessonId) {

        Integer lessonNum = payLessonOrderDao.getPayLessonOrderLessonNumSum(userId,status,lessonId);
        if(lessonNum == null){
            lessonNum = 0;
        }
        return lessonNum;

    }

    @Override
    public List<PayLessonOrder> getPayLessonOrderList(Integer userId, Integer status, Integer lessonId){
        PayLessonOrder payLessonOrder = new PayLessonOrder();
        payLessonOrder.setStatus(status);
        payLessonOrder.setLessonId(lessonId);
        payLessonOrder.setUserId(userId);
        List<PayLessonOrder> payLessonOrderList = payLessonOrderDao.findAll(Example.of(payLessonOrder));
        return payLessonOrderList;
    }

    @Transactional
    @Override
    public void cancelPayLessonOrder(Integer userId, Integer orderId) {
        PayLessonOrder payLessonOrder = payLessonOrderDao.findOne(orderId);

        if(payLessonOrder == null){
            throw new ApiNoPermissionDelException("没有此订单！");
        }

        if(!payLessonOrder.getUserId().equals(userId)){
            throw new ApiNotThisUserException("参数错误，用户与订单信息不符");
        }

        payLessonOrder.setStatus(2);
        payLessonOrderDao.save(payLessonOrder);
        couponLessonOrderService.deleteCouponOrderByUserIdAndOrderId(userId,orderId);

    }

    @Override
    public void cancelOrder(Integer orderId) {
        PayLessonOrder payLessonOrder = payLessonOrderDao.findOne(orderId);

        if(payLessonOrder == null){
            throw new ApiNoPermissionDelException("没有此订单！");
        }

        if(!payLessonOrder.getUserId().equals(payLessonOrder.getUserId())){
            throw new ApiNotThisUserException("参数错误，用户与订单信息不符");
        }

        payLessonOrder.setStatus(2);
        payLessonOrderDao.save(payLessonOrder);
        couponLessonOrderService.deleteCouponOrderByUserIdAndOrderId(payLessonOrder.getUserId(),orderId);
    }

    @Transactional
    @Override
    public PayLessonOrder buyLessonByStoryCoupon(Integer userId, Integer lessonNum, Integer lessonId, Integer priceId) {

        this.checkIsCanCreatePayLessonOrder(userId, priceId, lessonId, lessonNum);

        User user = userService.getUser(userId);
        Lesson lesson = lessonService.getLessonById(lessonId);
        /**
         * 检测下故事兑换券是否足够并且获得需要使用的兑换券
         */
        List<CouponStoryExchangeUser> couponStoryExchangeUserList = couponStoryExchangeUserService.checkStoryCouponAndGetCouponNum(userId, lessonNum * 3);
        /**
         * 创建订单
         */
        Integer orignalPrice = lesson.getPerPrice() * lessonNum;
        String channel = VersionUtil.getChannelInfo(request);
        PayLessonOrder payLessonOrder = this.addPayLessonOrder(
            user,
            orignalPrice,
            0,0,
            priceId,
            100,100,
            channel,
            lessonNum,
            lessonId,"",
            OrderPayStyle.STORY_COUPON
        );
        /**
         * 将故事兑换券与订单关联 激活故事兑换券
         */
        couponStoryExchangeOrderService.addCouponStoryExchangeOrderByList(couponStoryExchangeUserList, payLessonOrder.getId(), OrderStyle.LESSON_ORDER);

        Integer maxLessonNum = buyLessonItemRecordService.getMaxLessonNumByLessonIdAndUserId(lessonId, userId);
        this.addBuyLessonItemRecordAndOrderLesson(userId, lessonId, maxLessonNum, payLessonOrder.getId(), payLessonOrder.getNum(),0);

        payLessonOrder.setStatus(1);
        payLessonOrder.setSuccessTime(new Date());
        orderMixService.updateOrderMixStatus(OrderStyle.LESSON_ORDER, payLessonOrder.getId(), OrderStatusStyle.PAY_SUCCESS);
        return payLessonOrderDao.save(payLessonOrder);
    }

    @Transactional
    @Override
    public SubscribeByCodeResponse buyLessonByCode(Integer userId, SaleGoodsStyle saleGoodsStyle, PreSaleCode preSaleCode) {

        Integer lessonId = saleGoodsStyle.getTargetValue();

        List<PayLessonOrder> payLessonOrderList = this.getPayLessonOrderList(userId,0, lessonId);
        if(payLessonOrderList.size() > 0){
            for(PayLessonOrder item : payLessonOrderList){
                item.setStatus(2);
                item.setRemark("系统内部取消订单");
                payLessonOrderDao.save(item);
            }
        }

        Integer canBuy = this.getCanBuyLessonNum(userId, lessonId);

        Integer lessonBuyNum = 0;
        Integer priceId = 0;
        if(canBuy < saleGoodsStyle.getBuyLessonNum() && !canBuy.equals(0)){
            lessonBuyNum = canBuy;
            priceId = 5;
        } else {
            lessonBuyNum = saleGoodsStyle.getBuyLessonNum();
            priceId = saleGoodsStyle.getLessonPriceId();
        }
        this.checkIsCanCreatePayLessonOrder(userId, priceId, lessonId, lessonBuyNum);

        User user = userService.getUser(userId);
        Lesson lesson = lessonService.getLessonById(lessonId);
        Integer orignalPrice = lesson.getPerPrice() * lessonBuyNum;
        String channel = VersionUtil.getChannelInfo(request);
        PayLessonOrder payLessonOrder = this.addPayLessonOrder(
                user,
                orignalPrice,
                0,0,
                priceId,
                100,100,
                channel,
                lessonBuyNum,
                lessonId,"",
                OrderPayStyle.CODE
        );
        Integer maxLessonNum = buyLessonItemRecordService.getMaxLessonNumByLessonIdAndUserId(lessonId, userId);
        this.addBuyLessonItemRecordAndOrderLesson(userId, lessonId, maxLessonNum, payLessonOrder.getId(), payLessonOrder.getNum(),0);
        payLessonOrder.setStatus(1);
        payLessonOrder.setCode(preSaleCode.getCode());
        payLessonOrder.setSuccessTime(new Date());
        payLessonOrderDao.save(payLessonOrder);
        orderMixService.updateOrderMixStatus(OrderStyle.LESSON_ORDER, payLessonOrder.getId(), OrderStatusStyle.PAY_SUCCESS);
        preSaleCodeService.usePreSaleCode(preSaleCode,userId);

        SubscribeByCodeResponse response = new SubscribeByCodeResponse();
        String intro = "恭喜您成功兑换《" + lesson.getName() + "》课程！";
        response.setTargetValue(lessonId);
        response.setIntro(intro);
        response.setType(VipCodeStyle.LESSON.getId());
        return response;
    }

    @Transactional
    @Override
    public PayLessonOrder buyLessonByBalance(Long userId, Integer orderId, OrderPayStyle payStyle, WalletStyle walletStyle) throws ApiException {

        PayLessonOrder payLessonOrder = payLessonOrderDao.findOne(orderId);

        /**
         * 安卓余额订单需要重新计算订单折扣
         */
        if(payStyle.equals(OrderPayStyle.ANDRIOD_BLANCE) && walletStyle.equals(WalletStyle.ANDROID_WALLET)){
            Wallet wallet = walletService.getWalletByUserId(userId.intValue());
            Float newAmount = ((float)wallet.getDiscount() / 100) * (float) payLessonOrder.getAmount();
            payLessonOrder.setAmount(newAmount.intValue());
            payLessonOrder.setUserDiscount(wallet.getDiscount());
        }

        walletService.addAmountToWallet(userId.intValue(),walletStyle,RechargeStyle.SERIAL, NumberUtil.unAbs(payLessonOrder.getAmount()),"lesson_"+orderId,"购买课程");

        Integer maxLessonNum = buyLessonItemRecordService.getMaxLessonNumByLessonIdAndUserId(payLessonOrder.getLessonId(), userId.intValue());
        this.addBuyLessonItemRecordAndOrderLesson(userId.intValue(), payLessonOrder.getLessonId(), maxLessonNum, payLessonOrder.getId(), payLessonOrder.getNum(),0);

        /**
         * 调用积分流水添加   课程购买
         */
        Integer starCount = StarConfig.STAR_USR_BUY*(payLessonOrder.getAmount())/100;
        walletService.addStarToWallet(userId.intValue(), StarRechargeStyle.BUY_LESSON,starCount,StarContentStyle.LESSON_BUY.getName());

        payLessonOrder.setSuccessTime(new Date());
        payLessonOrder.setStatus(1);
        payLessonOrder.setPayStyle(payStyle);
        payLessonOrder = payLessonOrderDao.save(payLessonOrder);
        orderMixService.updateOrderMixStatus(OrderStyle.LESSON_ORDER, payLessonOrder.getId(), OrderStatusStyle.PAY_SUCCESS);
        /**
         * 发送系统消息
         */
        Lesson lesson = lessonService.getLessonById(payLessonOrder.getLessonId());
        Map<String,String> contentMap = new HashMap<>();
        contentMap.put("lessonNum", payLessonOrder.getNum().toString());
        contentMap.put("starCount", starCount.toString());
        contentMap.put("name", lesson.getName());
        templateNoticeService.addNoticeToUserByUserIdAndMessage(NoticeStyle.BUY_LESSON, contentMap, userId.intValue());
        return payLessonOrder;
    }

    /**
     *  添加课程订单成功后，相关数据
     * @param userId
     * @param lessonId
     * @param maxLessonNum
     * @param payLessonOrderId
     * @param lessonCount
     */
    @Transactional
    @Override
    public void addBuyLessonItemRecordAndOrderLesson(Integer userId, Integer lessonId, Integer maxLessonNum, Integer payLessonOrderId, Integer lessonCount,Integer isBaobao){
        List<LessonItem> lessonItemList = lessonItemService.getNeedBuyLessonItemList(maxLessonNum,lessonCount,lessonId);
        if(lessonItemList.size() > 0){
            for(LessonItem item : lessonItemList){
                buyLessonItemRecordService.addBuyLessonItemRecord(userId,lessonId,item.getId(),isBaobao);
                if(isBaobao==0){
                    orderLessonService.addOrderLesson(userId,payLessonOrderId,lessonId,item.getId());
                }

            }
        }
    }

    @Override
    public PayLessonOrder getPayLessonOrderById(Integer id){
        return payLessonOrderDao.findOne(id);
    }

    @Override
    public void setDataToOrderMix(OrderMix orderMix) {
        PayLessonOrder payLessonOrder = this.getPayLessonOrderById(orderMix.getOrderId().intValue());
        PayLessonPrice payLessonPrice = payLessonPriceService.getPayLessonPrice(payLessonOrder.getPriceId(), payLessonOrder.getNum());
        orderMix.setPayLessonPrice(payLessonPrice);
        orderMix.setAmount(payLessonOrder.getAmount());
        orderMix.setOriginalPrice(payLessonOrder.getOriginalPrice());
        orderMix.setCreateTime(payLessonOrder.getCreateTime());
        orderMix.setSuccessTime(payLessonOrder.getSuccessTime());
        orderMix.setIsDel(payLessonOrder.getIsDel());
        orderMix.setBuyType(payLessonOrder.getType());
    }

    @Override
    public void deleteOrder(Integer orderId){
        PayLessonOrder payLessonOrder = payLessonOrderDao.findOne(orderId);
        payLessonOrder.setIsDel(1);
        payLessonOrderDao.save(payLessonOrder);
    }

    @Override
    public void checkCanBuyPriceIdSix(Integer userId) {
        if(buyLessonItemRecordService.isReadFreeLesson(userId)){
            List<PayLessonOrder> payLessonOrderList = payLessonOrderDao.getPriceIdSixLessonOrder(userId);
            for(PayLessonOrder item : payLessonOrderList){
                if(item.getStatus().equals(1)){
                    throw new ApiBeyondLimitException("你已经购买过了优惠课程！");
                } else if(item.getStatus().equals(0)){
                    orderMixService.cancelOrder(userId, item.getId(), RechargeStyle.LESSON);
                }
            }
        } else {
            throw new ApiNotFoundException("你不具备购买优惠价格条件！");
        }
    }

    @Override
    public GetShareMagazineStatusResponse getShareMagazineStatus(Integer userId) {
        GetShareMagazineStatusResponse response = new GetShareMagazineStatusResponse();
        List<PayLessonOrder> payLessonOrderList = payLessonOrderDao.getSuccessPriceIdSixLessonOrder(userId);
        if(payLessonOrderList != null && payLessonOrderList.size() > 0){
            response.setBuyStatus(1);
        } else {
            response.setBuyStatus(0);
        }
        Integer hasBuyEnlightenLesson = buyLessonItemRecordService.getMaxLessonNumByLessonIdAndUserId(1, userId);
        Integer hasBuyGrowthLesson = buyLessonItemRecordService.getMaxLessonNumByLessonIdAndUserId(2, userId);
        Integer canBuyEnlightenLesson = 50 - hasBuyEnlightenLesson;
        Integer canBuyGrowthLesson = 50 - hasBuyGrowthLesson;
        response.setEnlightenLessonCanBuy(canBuyEnlightenLesson);
        response.setGrowthLessonCanBuy(canBuyGrowthLesson);
        return response;
    }

    @Override
    public Integer getShareTradeAmount(Integer orderId) {
        PayLessonOrder payLessonOrder = this.getPayLessonOrderById(orderId);
        if(
                payLessonOrder.getStatus().equals(OrderStatusStyle.PAY_SUCCESS.getId()) &&
                        !payLessonOrder.getType().equals(OrderPayStyle.CODE.getId()) &&
                        !payLessonOrder.getType().equals(OrderPayStyle.DEFAULT_NULL.getId()) &&
                        !payLessonOrder.getType().equals(OrderPayStyle.STORY_COUPON.getId())
                ){

            return payLessonOrder.getAmount();
        }
        return 0;
    }
}

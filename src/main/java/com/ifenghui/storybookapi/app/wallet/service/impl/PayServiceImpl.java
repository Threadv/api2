package com.ifenghui.storybookapi.app.wallet.service.impl;

/**
 * Created by jia on 2016/12/28.
 */
import com.alipay.api.AlipayApiException;
import com.alipay.api.internal.util.AlipaySignature;
import com.ifenghui.storybookapi.app.transaction.entity.coupon.*;
import com.ifenghui.storybookapi.app.transaction.entity.serial.PaySerialStoryOrder;
import com.ifenghui.storybookapi.app.transaction.entity.subscription.PaySubscriptionOrder;
import com.ifenghui.storybookapi.app.transaction.entity.subscription.PaySubscriptionPrice;
import com.ifenghui.storybookapi.app.transaction.entity.subscription.SubscriptionRecord;
import com.ifenghui.storybookapi.app.transaction.service.PayAfterOrderService;
import com.ifenghui.storybookapi.app.wallet.dao.*;
import com.ifenghui.storybookapi.app.wallet.entity.*;
import com.ifenghui.storybookapi.app.wallet.response.SubscribeByBalanceResponse;
import com.ifenghui.storybookapi.app.story.dao.MagazineDao;
import com.ifenghui.storybookapi.app.story.dao.SerialStoryDao;
import com.ifenghui.storybookapi.app.story.dao.StoryDao;
import com.ifenghui.storybookapi.app.story.entity.Story;
import com.ifenghui.storybookapi.app.story.service.StoryService;
import com.ifenghui.storybookapi.app.transaction.dao.*;
import com.ifenghui.storybookapi.app.transaction.entity.*;
import com.ifenghui.storybookapi.app.transaction.service.BuyMagazineRecordService;
import com.ifenghui.storybookapi.app.transaction.service.BuyStoryRecordService;
import com.ifenghui.storybookapi.app.transaction.service.PaySubscriptionPriceService;
import com.ifenghui.storybookapi.app.user.dao.UserDao;
import com.ifenghui.storybookapi.app.wallet.service.*;
import com.ifenghui.storybookapi.config.MyEnv;
import com.ifenghui.storybookapi.config.StarConfig;
import com.ifenghui.storybookapi.config.StoryConfig;
import com.ifenghui.storybookapi.app.user.entity.User;
import com.ifenghui.storybookapi.exception.*;
import com.ifenghui.storybookapi.style.*;
import com.ifenghui.storybookapi.util.NumberUtil;
import com.ifenghui.storybookapi.util.VersionUtil;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletRequest;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;


@Transactional
@Component
public class PayServiceImpl  implements PayService {

    private static Logger logger = Logger.getLogger(PayServiceImpl.class);


    @Autowired
    BuyStoryRecordDao buyStoryRecordDao;


    @Autowired
    PayCallbackRecordDao payCallbackRecordDao;
    @Autowired
    PaySubscriptionPriceDao paySubscriptionPriceDao;
    @Autowired
    SubscriptionRecordDao subscriptionRecordDao;
    @Autowired
    PaySubscriptionOrderDao paySubscriptionOrderDao;
    @Autowired
    PaySerialStoryOrderDao paySerialStoryOrderDao;

    @Autowired
    MagazineDao magazineDao;
    @Autowired
    BuyMagazineRecordDao buyMagazineRecordDao;
    @Autowired
    BuySerialStoryRecordDao buySerialStoryRecordDao;

    @Autowired
    PayStoryOrderDao payStoryOrderDao;
    @Autowired
    VPayOrderDao vPayOrderDao;
//    @Autowired
//    ShoppingCartDao shoppingCartDao;
    @Autowired
    WalletDao walletDao;
    @Autowired
    UserDao userDao;
    @Autowired
    StoryDao storyDao;

    @Autowired
    SerialStoryDao serialStoryDao;

    @Autowired
    OrderStoryDao orderStoryDao;
    @Autowired
    PayAfterOrderDao payAfterOrderDao;
    @Autowired
    PayRechargeOrderDao payRechargeOrderDao;
    @Autowired
    PayRechargePriceDao payRechargePriceDao;
    @Autowired
    CouponDeferredDao couponDeferredDao;
    @Autowired
    CouponDeferredUserDao couponDeferredUserDao;
    @Autowired
    CouponDeferredSubscriptionDao couponDeferredSubscriptionDao;
    @Autowired
    CouponDao couponDao;
    @Autowired
    CouponUserDao couponUserDao;
    @Autowired
    CouponSubscriptionDao couponSubscriptionDao;
    @Autowired
    CouponSerialStoryOrderDao couponSerialStoryOrderDao;
    @Autowired
    UserAccountRecordService userAccountRecordService;
    @Autowired
    UserStarRecordService userStarRecordService;
    @Autowired
    StoryService storyService;
    @Autowired
    HttpServletRequest request;

    @Autowired
    ShoppingCartDao shoppingCartDao;

    @Autowired
    BuyStoryRecordService buyStoryRecordService;

    @Autowired
    BuyMagazineRecordService buyMagazineRecordService;

    @Autowired
    private Environment env;

    @Autowired
    PaySubscriptionPriceService paySubscriptionPriceService;

    @Autowired
    PayRechargeOrderService payRechargeOrderService;

    @Autowired
    WalletService walletService;

    @Autowired
    PayAfterOrderService payAfterOrderService;


    @Override
    public PayRechargeOrder getPayRechargeOrder(Long rechargeOrderId) {
        return payRechargeOrderDao.findOne(rechargeOrderId);
    }

    @Override
    public SubscribeByBalanceResponse subscribeByBalance(Long orderId, Long userId, OrderPayStyle orderPayStyle, WalletStyle walletStyle)throws ApiException {
        logger.info("--------------------subscribeByBalance------------");
        logger.info(">>input data:orderCode:"+orderId+"_userId:"+userId+"_buySourceType:"+orderPayStyle);
        //????????????id
//        Long orderId=  Long.parseLong(orderCode.split("_")[1]);
        logger.info("--------------------subscribeByBalance------orderId------"+orderId);
        // ??????????????????
        PaySubscriptionOrder paySubscriptionOrder = paySubscriptionOrderDao.findOne(orderId.longValue());
        //????????????id
        Long priceId = paySubscriptionOrder.getPriceId();

        //????????????????????????
        PaySubscriptionPrice paySubscriptionPrice = paySubscriptionPriceDao.findOne(priceId);

        /**
         * V 1.7.0 ?????????????????? ???????????????????????????
         * ???????????????????????????
         */
        Integer isNeedBuyWeek = paySubscriptionPriceService.getPaySubscriptionPriceNeedWeek(userId);

        if(paySubscriptionPrice.getMonth().equals(0) && isNeedBuyWeek.equals(0)){
            throw new ApiDuplicateException("?????????????????????????????????");
        }

        //?????????????????????
        Wallet wallet = walletDao.getWalletByUserId(userId.intValue());
        //????????????????????????
        if (wallet.getBalance() < paySubscriptionOrder.getAmount()){
            logger.info("--------------------subscribeByBalance---------yu e bu zu---");
            //????????????
            throw new ApiLackBalanceException("????????????");
        }
        //??????????????????
//        wallet.setBalance(wallet.getBalance()-paySubscriptionOrder.getAmount());
//        walletDao.save(wallet);
        //??????????????????????????????
        Integer waterType = 2;//??????
        Integer buyType = 2;//1??????2??????
        String intro = "??????????????????";

//        userAccountRecordService.addUserAcountRecord(userId,paySubscriptionOrder.getAmount(), AddStyle.DOWN,RechargeStyle.SUBSCRIPTION,intro);//1??????2??????

        walletService.addAmountToWallet(userId.intValue(),walletStyle,RechargeStyle.SUBSCRIPTION, NumberUtil.unAbs(paySubscriptionOrder.getAmount()),"subscribe_"+orderId,intro);


        logger.info("--------------------subscribeByBalance-----add water-------");
        //??????????????????
        SubscriptionRecord subscriptionRecord = new SubscriptionRecord();
        User user = userDao.findOne(userId);
        logger.info("--------------------subscribeByBalance-----tain jia ding yue ji lu-------");
        //????????????????????????
        CouponDeferredSubscription couponDeferredSubscription = couponDeferredSubscriptionDao.getByOrderId(orderId.longValue());//??????????????????????????????
        Integer month = 0;
        if(couponDeferredSubscription != null){
            CouponDeferredUser couponDeferredUser = couponDeferredUserDao.findOne(couponDeferredSubscription.getCouponUserId());
            if(couponDeferredUser != null){
                CouponDeferred couponDeferred = couponDeferredDao.findOne(couponDeferredUser.getCouponId());
                if(couponDeferred != null){
                    month = couponDeferred.getValidTime();
                }
            }
        }

        /**
         * ???????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????
         * ????????????????????????????????????????????????
         */
        Integer pageNo=0;
        Integer pageSize=1;//????????????????????????
        Pageable pageable = new PageRequest(pageNo, pageSize, new Sort(Sort.Direction.DESC,"id"));
        Page<SubscriptionRecord> subscriptionRecords = subscriptionRecordDao.getSubscriptionRecordsByUserId(user,pageable);
        Date preEndTime = new Date();
        for (SubscriptionRecord item : subscriptionRecords.getContent()){
            preEndTime = item.getEndTime();//??????????????????
        }
        Date nowDate = new Date();
        long  nowTimeStemp = nowDate.getTime();//???????????????
        long endTimeStemp = preEndTime.getTime();//??????????????????????????????
        long dv = nowTimeStemp-endTimeStemp;


        SimpleDateFormat f = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Calendar c = Calendar.getInstance();

        if(paySubscriptionPrice.getMonth().equals(0)){
            subscriptionRecord.setStartTime(new Date());
            subscriptionRecord.setEndTime(new Date());
        } else {
            if (subscriptionRecords.getContent().size() == 0 || dv >0){
                //????????????????????????????????????????????????????????????????????????????????????????????????
                subscriptionRecord.setStartTime(new Date());
                //????????????????????????
                c.add(Calendar.MONTH, paySubscriptionPrice.getMonth() + month);//?????????+???????????????
                Date time =c.getTime();
                Long endTimeStr=time.getTime();
                Date date = new Date(endTimeStr);

                subscriptionRecord.setEndTime(date);
            }else{
                //????????????????????????????????????
                subscriptionRecord.setStartTime(preEndTime);
                //????????????????????????
                c.setTime(preEndTime);//????????????????????????????????????????????????
                c.add(Calendar.MONTH, paySubscriptionPrice.getMonth() + month);//???????????????????????????+???????????????
                Date time =c.getTime();
                Date date = new Date(time.getTime());

                subscriptionRecord.setEndTime(date);
            }
        }

        subscriptionRecord.setUser(user);
        subscriptionRecord.setStatus(1);
        subscriptionRecord.setCreateTime(new Date());
        subscriptionRecord=subscriptionRecordDao.save(subscriptionRecord);

        Long subscriptionRecordId=subscriptionRecord.getId();

        /**
         * ????????????????????????????????????
         * ??????????????????????????????????????????????????????????????????
         */
        buyMagazineRecordService.addIsNowBuyMagazineRecordAndBuyStoryRecord(user.getId(), StoryConfig.BUY_STORY_RECORD_SUBSCRIPTION);

        //????????????????????????story_pay_subscription_order
        logger.info("--------------------subscribeByBalance-------tain jia ding yue ding dan-----");
        paySubscriptionOrder.setUser(user);

        paySubscriptionOrder.setSuccessTime(new Date());
        paySubscriptionOrder.setStatus(1);
        paySubscriptionOrder.setSubscriptionId(subscriptionRecordId);
        paySubscriptionOrder.setPayStyle(orderPayStyle);//????????????

        paySubscriptionOrder = paySubscriptionOrderDao.save(paySubscriptionOrder);

        //????????????????????????
//        Integer buyType2 = 17;//??????????????????
        String intro2 = "????????????";
        Integer starCount = StarConfig.STAR_USR_BUY*paySubscriptionOrder.getOriginalPrice()/100;
//        userStarRecordService.addUserStarRecord(user.getId(), starCount,AddStyle.UP , StarRechargeStyle.SUBSCRIPTION,intro2);
        walletService.addStarToWallet(userId.intValue(),StarRechargeStyle.SUBSCRIPTION,starCount,StarContentStyle.SUBSCRIBE.getName());
        logger.info("--------------------subscribeByBalance-----end-------");

        SubscribeByBalanceResponse response = new SubscribeByBalanceResponse();
        //??????????????????????????????
//        List<TaskFinish> taskFinishes = new ArrayList<>();
//
//        intro2 = "????????????\n??????"+ starCount +"????????????";
//
//        TaskFinish taskFinish = new TaskFinish();
//        taskFinish.setIntro(intro2);
//        taskFinishes.add(taskFinish);
//        response.setTaskFinishInfo(taskFinishes);
        return response;
    }

//    @Override
//    public SubscribeByBalanceResponse subscribeByBalance(PayAfterOrder afterOrder, Long userId, Integer buySourceType) throws ApiException {
//
//        return this.subscribeByBalance(afterOrder.getPayOrderId(),userId,buySourceType);
//    }

    @Override
    public String subscribeByCode(Long userId,String code,Integer month)throws ApiException {
        /**
         * ??????????????????
         */
        User user = userDao.findOne(userId);
        /**
         * ????????????????????????story_pay_subscription_order
         */
        PaySubscriptionOrder paySubscriptionOrder = new PaySubscriptionOrder();
        paySubscriptionOrder.setUser(user);
        paySubscriptionOrder.setOriginalPrice(0);//??????
        paySubscriptionOrder.setAmount(0);//??????????????????
        paySubscriptionOrder.setOrderCode("");//?????????
        paySubscriptionOrder.setSuccessTime(new Date());
        paySubscriptionOrder.setStatus(0);
        paySubscriptionOrder.setSubscriptionId(0L);
        paySubscriptionOrder.setDiscount(1);
        paySubscriptionOrder.setPriceId(0L);
        paySubscriptionOrder.setPayStyle(OrderPayStyle.CODE);//???????????????
        paySubscriptionOrder.setIsDel(0);
        paySubscriptionOrder.setCode(code);
        paySubscriptionOrder.setMonth(month);
        paySubscriptionOrder.setIsTest(user.getIsTest());
        String channel = VersionUtil.getChannelInfo(request);

        paySubscriptionOrder.setChannel(channel);
        paySubscriptionOrder.setCreateTime(new Date());
        paySubscriptionOrder = paySubscriptionOrderDao.save(paySubscriptionOrder);

        //????????????id???????????????order_id

        String prefix=env.getProperty("order.prefix");
        String orderStr = prefix+"_" + paySubscriptionOrder.getId();
        paySubscriptionOrder.setOrderCode(orderStr);//?????????
        paySubscriptionOrder.setSuccessTime(new Date());
        paySubscriptionOrder = paySubscriptionOrderDao.save(paySubscriptionOrder);


        //??????????????????????????????
//        Integer waterType = 2;//??????
//        Integer buyType = 2;//1??????2??????
//        String intro = "?????????????????????";
//        userAccountRecordService.addUserAcountRecord(userId,paySubscriptionPrice.getPrice(),waterType,buyType,intro);//1??????2??????
        //??????????????????
        SubscriptionRecord subscriptionRecord = new SubscriptionRecord();
        //???????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????
        // ????????????????????????????????????????????????
        Integer pageNo=0;
        Integer pageSize=1;//????????????????????????
        Pageable pageable = new PageRequest(pageNo, pageSize, new Sort(Sort.Direction.DESC,"id"));
        Page<SubscriptionRecord> subscriptionRecords = subscriptionRecordDao.getSubscriptionRecordsByUserId(user,pageable);
        Date preEndTime = new Date();
        for (SubscriptionRecord item : subscriptionRecords.getContent()){
            preEndTime = item.getEndTime();//??????????????????
        }
        Date nowDate = new Date();
        long  nowTimeStemp = nowDate.getTime();//???????????????
        long endTimeStemp = preEndTime.getTime();//??????????????????????????????
        long dv = nowTimeStemp-endTimeStemp;


        SimpleDateFormat f = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Calendar c = Calendar.getInstance();
        if (subscriptionRecords.getContent().size() == 0 || dv >0){
            //????????????????????????????????????????????????????????????????????????????????????????????????
            subscriptionRecord.setStartTime(new Date());
            //????????????????????????
            c.add(Calendar.MONTH, month);//?????????,??????????????????????????????
            Date time =c.getTime();
            Long endTimeStr=time.getTime();
            Date date = new Date(endTimeStr);

            subscriptionRecord.setEndTime(date);
        }else{
            //????????????????????????????????????
            subscriptionRecord.setStartTime(preEndTime);
            //????????????????????????
            c.setTime(preEndTime);//????????????????????????????????????????????????
            c.add(Calendar.MONTH, month);//???????????????????????????
            Date time =c.getTime();
            Date date = new Date(time.getTime());

            subscriptionRecord.setEndTime(date);
        }
        subscriptionRecord.setUser(user);

        subscriptionRecord.setStatus(1);
        subscriptionRecord.setCreateTime(new Date());
        subscriptionRecord=subscriptionRecordDao.save(subscriptionRecord);
        Long subscriptionRecordId=subscriptionRecord.getId();

        /**
         * ????????????????????????????????????
         * ??????????????????????????????????????????????????????????????????
         */
        buyMagazineRecordService.addIsNowBuyMagazineRecordAndBuyStoryRecord(user.getId(), StoryConfig.BUY_STORY_RECORD_SUBSCRIPTION);

        /**
         * ????????????????????????story_pay_subscription_order
         */
        paySubscriptionOrder.setUser(user);
        paySubscriptionOrder.setSuccessTime(new Date());
        paySubscriptionOrder.setStatus(1);
        paySubscriptionOrder.setSubscriptionId(subscriptionRecordId);
        paySubscriptionOrder.setPayStyle(OrderPayStyle.CODE);//????????????
        paySubscriptionOrder.setSuccessTime(new Date());
        paySubscriptionOrder = paySubscriptionOrderDao.save(paySubscriptionOrder);

        String intro = "";
        if (month==3) {
            intro ="????????????????????????????????????\n????????????????????????";
        } else if(month == 1) {
            intro ="?????????????????????????????????\n????????????????????????";
        } else if(month == 6) {
            intro ="????????????????????????????????????\n????????????????????????";
        } else {
            intro ="?????????????????????????????????\n????????????????????????";
        }

        return intro;
    }


    /**
     * ??????????????????,??????????????????????????????
     */
    private int checkByStoryOrderAndGetPrice(List<Long> cartIdsStrArray,Long userId) throws ApiStoryOrderRepeatException {
        int price = 0;
        Story story;
//        Long storyId;
        VPayOrder vPayOrder;
        for (Long storyId:cartIdsStrArray) {
//            storyId = Long.parseLong(cartIdsStrArray[i]);
            story = storyDao.findOne(storyId);
            price = price + story.getPrice();
//        }
//        //????????????????????????
            List<OrderStory> orderStories = orderStoryDao.getOrderStoriesByUserIdAndStoryId(userId, storyId);

            for(OrderStory orderStory:orderStories){
                PayStoryOrder payStoryOrder=payStoryOrderDao.findOne(orderStory.getOrderId());
                if(payStoryOrder.getStatus()==1){
                    ApiStoryOrderRepeatException apiStoryOrderRepeatException = new ApiStoryOrderRepeatException(orderStory);
                    vPayOrder = vPayOrderDao.getOrderByOrderIdAndType(orderStory.getOrderId(),1);
                    apiStoryOrderRepeatException.setvPayOrder(vPayOrder);
                    throw apiStoryOrderRepeatException;
                }
            }

        }

        return price;
    }
    private List<Long> getLongsByStrs(String storyIdsStr){
        String[] cartIdsStrArray = storyIdsStr.split(",");
        List<Long> ids=new ArrayList<>();
        for (String idStr:cartIdsStrArray){
            long id = Long.parseLong(idStr);
            ids.add(id);
//            story = storyDao.findOne(storyId);
////            price = price + story.getPrice();
//            OrderStory orderStory = new OrderStory();
//            orderStory.setUser(user);
//            orderStory.setUserId(userId);
//            orderStory.setStory(story);
//            orderStory.setOrderId(payStoryOrder.getId());
//            orderStory.setCreateTime(new Date());
//            orderStoryDao.save(orderStory);
        }
        return ids;
    }

    @Override
    public Page<PayRechargePrice> getRechargePrice(WalletStyle walletStyle) {
        Integer pageNo = 0 ;
        Integer pageSize =20;
        Pageable pageable = new PageRequest(pageNo, pageSize, new Sort(Sort.Direction.DESC,"orderBy","id"));
        return payRechargePriceDao.getPayRechargePricesByStatusAndType(1, walletStyle.getId(), pageable);
    }

    @Override
    public PayRechargeOrder getPayRechargeOrderByTradeNo(String tradeNo) {
        PayRechargeOrder payRechargeOrder = payRechargeOrderDao.getByTradeNo(tradeNo);
        return payRechargeOrder;
    }

    @Override
    public PayRechargeOrder addPayRechargeOrder(Long userId, Long payOrderId, RechargeStyle type, Long priceId, String channel,String app_id,String private_key,String alipay_notify_url,String appName) throws ApiException {

        Integer amount = payRechargeOrderService.getAmountByRechargeStyle(type, payOrderId.intValue(), priceId);

        String prefix =env.getProperty("order.prefix");

        User user = userDao.findOne(userId);
        /**
         * ??????????????????story_pay_recharge_order
         */

        if (channel == null) {
            channel = "";
        }

        PayRechargeOrder payRechargeOrder = payRechargeOrderService.addPayRechargeOrder(
                userId.intValue(),
                amount,
                RechargePayStyle.DEFAULT_NULL,
                type, channel, appName
        );

        String orderStr = prefix + "_" + payRechargeOrder.getId();
        payRechargeOrder.setOrderCode(orderStr);
        payRechargeOrderDao.save(payRechargeOrder);
        payRechargeOrder.setUser(user);
        payRechargeOrderService.setPayRechargeOrderNotifyAddress(payRechargeOrder,alipay_notify_url);

        /**
         * ???????????????????????????
         */
        payAfterOrderService.addPayAfterOrder(userId,payRechargeOrder.getId(), type, payOrderId);
        logger.info("--------------------getPayRechargeOrder----add after order-----");

        String orderString = this.getAlipayStr(amount,payRechargeOrder,app_id,private_key,alipay_notify_url);
        payRechargeOrder.setOrderString(orderString);
        payRechargeOrderDao.save(payRechargeOrder);
        logger.info("--------------------getPayRechargeOrder----orderString-----"+orderString);
        return payRechargeOrder;
    }

    /**
     * ??????alipay??????????????????????????????????????????????????????
     * @return
     */
    private String getAlipayStr(int amount,PayRechargeOrder payRechargeOrder,String app_id,String private_key,String notify_url){
        String prefix=env.getProperty("order.prefix");
        String orderStr = prefix + "_" + payRechargeOrder.getId();
        //??????orderStr???sign
//        String app_id="2017042506959253";
        String charset="utf-8";
//        String private_key="MIICdgIBADANBgkqhkiG9w0BAQEFAASCAmAwggJcAgEAAoGBALZZdto/uKevvu0JYsMInulvoMx2yzgqDXQr/8Ja+NygsuNi6Z7a3YthvsBfpmVzQk4IsMjg+P3256ovbQGmKESGc2KeP+rJIO5G2bfTKAZ8WhXZ4sQwSpBwHZbCuVeFMkcdYQ1oMIxUyRJsE0AMg5BSRWb0YpvdFNKYSD7ktMU/AgMBAAECgYAnpAVBZs7WrU79Kqgsq+gX6St0p3KAMCwikBoWfz5QgCLDadJNtViqH0KfgWuj7E2Ct0LvOHEIjK9KAOtai2t/M/VK4wqiQbm5+e//7VHML1X1i1ldS3b8/770U8Y4+F8NKIS6cJeewO0qioWXbOOU4vL4HAHLBpKa1ppshkj5CQJBAOZkkXCisw2+L58QZp6pZFPnQkGVQuc+DnVXxMmCr+GJJ12q3f0JmpQN659WHVJf22H93XAPKHNbGNUhwjxivHsCQQDKneeUQorUqwCSFKT4KSW0VQ34PT/vr3m4AHyXVACXdaJaY2LgCZPI4+oEa+HVYUDsVDQ85vSMJGX9DwkWjKkNAkEA5QLibwvK38ZEn+A1oVDPoXcmrPopXqKYzJtJyORW3+DteHX34yZAuRp9NAztaIxQDb/C9TicBM5wiKrd4BhlMQJAdzUb8LCNub26IjgfLxoWYti/1VND8KLO/CDdLLNxfarqED/1BlbcKg9duag3QDFt3x1TdplzO5iWoKJd+HRdaQJAemcoVxX9mfYId2dPofbq1QCbc+RFTth9L3fyZbwYEjrLFCBq3zNQdQnNXY2STHJivu2kGPQH2jkx+fzN5NfiWg==";
//        String alipay_public_key = "MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQDDI6d306Q8fIfCOaTXyiUeJHkrIvYISRcc73s3vF1ZT7XN8RNPwJxo8pWaJMmvyTn9N4HQ632qJBVHf8sxHi/fEsraprwCtzvzQETrNRwVxLO5jVmRGi60j8Ue1efIlzPXV9je9mkjzOmdssymZkh2QhUrCmZYI/FCEa3/cNMW0QIDAQAB\n";
        String sign_type="RSA";


        Date now = new Date();
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");//?????????????????????????????????
        String timestamp = dateFormat.format( now );


//        String notify_url = env.getProperty("iosalipay.notify");....
        String method = "alipay.trade.app.pay";//?
        String version = "1.0";
//        String gateway = "https://openapi.alipay.com/gateway.do";//???????????????

        float size = (float)amount/100;
        DecimalFormat df = new DecimalFormat("0.00");//??????????????????????????????0
        String total_amount = df.format(size);//????????????String?????????

        String out_trade_no = prefix+"_"+payRechargeOrder.getId();
        String biz_content = "{\"timeout_express\":\"30m\",\"seller_id\":\"\",\"product_code\":\"QUICK_MSECURITY_PAY\",\"total_amount\":"+total_amount+",\"subject\":\"???????????????\",\"body\":\"???????????????????????????\",\"out_trade_no\":"+"\""+out_trade_no+"\""+"}";
        String formatStr = "json";
        String originalStr;//???????????????
        originalStr = "app_id="+app_id+"&biz_content="+biz_content+"&charset="+charset+"&format="+formatStr+"&method="+method+"&notify_url="+notify_url+"&sign_type="+sign_type+"&timestamp="+timestamp+"&version="+version;

        logger.info("--------------------getPayRechargeOrder----biz_content-----"+biz_content);
        logger.info("--------------------getPayRechargeOrder----originalStr-----"+originalStr);

//        AlipayClient alipayClient = new DefaultAlipayClient(gateway,app_id,private_key,"json",charset,alipay_public_key,sign_type);

        String sign = null;//??????
        try {
            sign = AlipaySignature.rsaSign(originalStr,private_key,charset,sign_type);
            app_id = URLEncoder.encode(app_id,charset);
            biz_content = URLEncoder.encode(biz_content,charset);

            formatStr = URLEncoder.encode(formatStr,charset);
            method = URLEncoder.encode(method,charset);
            notify_url = URLEncoder.encode(notify_url,charset);
            sign_type = URLEncoder.encode(sign_type,charset);
            timestamp = URLEncoder.encode(timestamp,charset);
            version = URLEncoder.encode(version,charset);
            sign = URLEncoder.encode(sign,charset);
            charset = URLEncoder.encode(charset,charset);
        } catch (AlipayApiException e) {
            e.printStackTrace();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        String orderString = "app_id="+app_id+"&biz_content="+biz_content+"&charset="+charset+"&format="+formatStr+"&method="+method+"&notify_url="+notify_url+"&sign_type="+sign_type+"&timestamp="+timestamp+"&version="+version+"&sign="+sign;
//        payRechargeOrder.setOrderString(orderString);
//        payRechargeOrderDao.save(payRechargeOrder);
        logger.info("--------------------getPayRechargeOrder----orderString-----"+orderString);
        return orderString;
    }

    @Override
    public PayRechargePrice getPayRechargePriceById(Long id) {

        PayRechargePrice payRechargePrice = payRechargePriceDao.findOne(id);

        return payRechargePrice;
    }

    @Override
    public PayRechargePrice getPayRechargePriceByIap(String iap) {
        PayRechargePrice payRechargePrice = payRechargePriceDao.findOneByIap(iap);

        return payRechargePrice;
    }
}

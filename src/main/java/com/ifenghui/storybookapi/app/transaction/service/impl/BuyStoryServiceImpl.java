package com.ifenghui.storybookapi.app.transaction.service.impl;

import com.ifenghui.storybookapi.app.story.dao.StoryDao;
import com.ifenghui.storybookapi.app.story.entity.Story;
import com.ifenghui.storybookapi.app.story.service.StoryService;
import com.ifenghui.storybookapi.app.transaction.dao.*;
import com.ifenghui.storybookapi.app.transaction.entity.*;
import com.ifenghui.storybookapi.app.transaction.entity.coupon.CouponStoryExchangeUser;
import com.ifenghui.storybookapi.app.transaction.entity.single.ShoppingTrolley;
import com.ifenghui.storybookapi.app.transaction.response.BuyOrderByBalanceResponse;
import com.ifenghui.storybookapi.app.transaction.response.StandardOrder;
import com.ifenghui.storybookapi.app.transaction.service.*;
import com.ifenghui.storybookapi.app.transaction.service.order.OrderMixService;
import com.ifenghui.storybookapi.app.transaction.service.order.OrderStoryService;
import com.ifenghui.storybookapi.app.transaction.service.order.PayStoryOrderService;
import com.ifenghui.storybookapi.app.user.entity.User;
import com.ifenghui.storybookapi.app.user.service.UserService;
import com.ifenghui.storybookapi.app.wallet.entity.Wallet;
import com.ifenghui.storybookapi.app.wallet.response.BuySerialStoryResponse;
import com.ifenghui.storybookapi.app.wallet.service.WalletService;
import com.ifenghui.storybookapi.config.StarConfig;
import com.ifenghui.storybookapi.exception.ApiException;
import com.ifenghui.storybookapi.exception.ApiNotFoundException;
import com.ifenghui.storybookapi.exception.ApiStoryOrderRepeatException;
import com.ifenghui.storybookapi.style.*;
import com.ifenghui.storybookapi.util.NumberUtil;
import com.ifenghui.storybookapi.util.VersionUtil;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Component
public class BuyStoryServiceImpl implements BuyStoryService{

    @Autowired
    BuyStoryRecordDao buyStoryRecordDao;

    @Autowired
    StoryService storyService;

    @Autowired
    UserService userService;

    @Autowired
    StoryDao storyDao;

    @Autowired
    WalletService walletService;

    @Autowired
    HttpServletRequest request;

    @Autowired
    PayStoryOrderDao payStoryOrderDao;

    @Autowired
    private Environment env;

    @Autowired
    OrderStoryDao orderStoryDao;

    @Autowired
    ShoppingCartDao shoppingCartDao;

    @Autowired
    VPayOrderDao vPayOrderDao;

    Logger logger= Logger.getLogger(BuyStoryServiceImpl.class);

    @Autowired
    PayStoryOrderService payStoryOrderService;

    @Autowired
    ShoppingCartService shoppingCartService;

    @Autowired
    OrderStoryService orderStoryService;

    @Autowired
    CouponUserDao couponUserDao;

    @Autowired
    CouponDao couponDao;

    @Autowired
    CouponStoryOrderService couponStoryOrderService;

    @Autowired
    BuyStoryRecordService buyStoryRecordService;

    @Autowired
    CouponStoryExchangeUserService couponStoryExchangeUserService;

    @Autowired
    CouponStoryExchangeOrderService couponStoryExchangeOrderService;

    @Autowired
    OrderMixService orderMixService;

    @Transactional
    @Override
    public Story buyStoryByCode(Long userId,String code)throws ApiException {
        //????????????????????????????????????????????????????????????????????????,????????????
        logger.info("--------------------buyStoryByCode-------buy-----");

        //??????????????????
        Story story= storyDao.getStoryByRank(userId);
//        List<Story> storys = storyDao.getStoryByRank(userId);
//        Story story = storys.get(0);
        if(story == null){
            throw new ApiNotFoundException("???????????????????????????????????????");
        }
        Long storyId = story.getId();
        User user = userService.getUser(userId);


        //????????????????????????
        List<BuyStoryRecord> buyStoryRecordLists;
        Integer type = 6;//1??????2??????3?????????????????????4????????????5???????????????6?????????

        //????????????????????????
        BuyStoryRecord buyStoryRecord = new BuyStoryRecord();//??????new??????????????????????????????,?????????????????????
        buyStoryRecordLists = buyStoryRecordDao.getBuyStoryRecordsByUserIdAndStoryId(userId,storyId);
        if (buyStoryRecordLists.size()==0){//??????????????????????????????
            buyStoryRecord.setUserId(userId);
            buyStoryRecord.setType(type);//1??????2??????3???????????????4????????????5???????????????6?????????
            buyStoryRecord.setStory(story);
            buyStoryRecord.setIsTest(user.getIsTest());
            buyStoryRecord.setStoryId(story.getId());
            buyStoryRecord.setCreateTime(new Date());
            buyStoryRecordDao.save(buyStoryRecord);
        }
        //??????????????????
//        payStoryOrder.setStatus(1);
//        payStoryOrder.setSuccessTime(new Date());
//        payStoryOrderDao.save(payStoryOrder);
        //????????????????????????
//        Integer buyType2 = 21;//???????????????????????????
        String intro2 = "??????????????????";
        Integer starCount = StarConfig.STAR_USR_BUY*(story.getPrice())/100;
//        userStarRecordService.addUserStarRecord(user.getId(),starCount,AddStyle.UP,StarRechargeStyle.GIVE_STORY,intro2);
        walletService.addStarToWallet(user.getId().intValue(), StarRechargeStyle.GIVE_STORY,starCount,StarContentStyle.CHANGE_SINGLE.getName());
        BuySerialStoryResponse response = new BuySerialStoryResponse();
        return story;
    }

    @Transactional
    public List<Long> getLongsByStrs(String storyIdsStr){
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

    @Transactional
    @Override
    public PayStoryOrder createBuyStorysOrder(Long userId, String storyIdsStr, List<Integer> couponIds)throws ApiException {
        /**
         * ??????????????????
         * ????????????????????????
         */

        /**
         * (1)???????????????
         */
        logger.info("--------------------createPayStoryOrder-----------");
        List<Long> storyIds = this.getLongsByStrs(storyIdsStr);
        int length = storyIds.size();
        int price = this.checkRepeatStoryOrderAndGetPrice(storyIds,userId);

        /**
         * ???????????????????????????
         */
        CouponsResult couponsResult = new CouponsResult();
        couponsResult.checkCoupons(couponIds,couponUserDao,couponDao, price);

        /**
         * ????????????
         */
        String channel = VersionUtil.getChannelInfo(request);
        logger.info("--------------------createPayStoryOrder----userAgent---channel----"+channel);
        logger.info("--------------------createPayStoryOrder----getprice-------");

        /**
         * ?????????????????????
         */
        Wallet wallet = walletService.getWalletByUserId(userId.intValue());

        /**
         * ???????????????????????????story_pay_story_order
         * ?????????????????????
         */
        Integer couponAmount = couponsResult.getCouponAmount();
        Integer amount = price - couponsResult.getCouponAmount();
        User user = userService.getUser(userId);
        PayStoryOrder payStoryOrder = payStoryOrderService.addPayStoryOrder(user, price, couponAmount, amount, length, OrderPayStyle.DEFAULT_NULL, 100, channel);

        logger.info("--------------------createPayStoryOrder----getbanlance-------");
        /**
         * ??????????????????????????????story_order_story
         */
        orderStoryService.addOrderStoryList(storyIds, user, payStoryOrder.getId());
        logger.info("--------------------createPayStoryOrder----add --story_order_story-----");
        /**
         * ???????????????????????????
         */
        shoppingCartService.clearStoryInShoppingCart(storyIds, userId);

        /**
         * ????????????????????????????????????
         */
        couponStoryOrderService.addCouponOrderByCouponIdsStr(couponIds,userId.intValue(),payStoryOrder.getId().intValue());

        user.setBalance(wallet.getBalance());
        payStoryOrder.setUser(user);
        return payStoryOrder;
    }


    @Transactional
    @Override
    public BuyOrderByBalanceResponse buyStorysByStoryCoupon(Long userId, String storyIdsStr) {


        /**
         * (1)???????????????
         */
        logger.info("--------------------createPayStoryOrder-----------");
        List<Long> storyIds = this.getLongsByStrs(storyIdsStr);
        int length = storyIds.size();
        int price = this.checkRepeatStoryOrderAndGetPrice(storyIds,userId);

        /**
         * ????????????????????????????????????
         */
        List<CouponStoryExchangeUser> couponStoryExchangeUserList = couponStoryExchangeUserService.checkStoryCouponAndGetCouponNum(userId.intValue(), length);

        /**
         * ????????????
         */
        PayStoryOrder payStoryOrder = this.createBuyStorysOrder(userId,storyIdsStr,new ArrayList<>());

        /**
         * ????????????????????????????????????
         */
        this.addBuyStoryRecordAndClearShoppingCart(payStoryOrder.getId(), userId);

        /**
         * ????????????????????????????????? ?????????????????????
         */
        couponStoryExchangeOrderService.addCouponStoryExchangeOrderByList(couponStoryExchangeUserList,payStoryOrder.getId().intValue(), OrderStyle.STORY_ORDER);

        payStoryOrder.setPayStyle(OrderPayStyle.STORY_COUPON);
        payStoryOrder.setCouponNum(payStoryOrder.getNum());
        payStoryOrder.setStatus(1);
        payStoryOrder.setSuccessTime(new Date());
        payStoryOrder = payStoryOrderDao.save(payStoryOrder);

        orderMixService.updateOrderMixStatus(OrderStyle.STORY_ORDER, payStoryOrder.getId().intValue(), OrderStatusStyle.PAY_SUCCESS);

        BuyOrderByBalanceResponse response = new BuyOrderByBalanceResponse();
        response.setStandardOrder(new StandardOrder(payStoryOrder));
        return response;
    }

    /**
     * ???????????????????????????????????????????????????
     */
    @Transactional
    public int checkRepeatStoryOrderAndGetPrice(List<Long> cartIdsStrArray,Long userId) throws ApiStoryOrderRepeatException {
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
                if(payStoryOrder==null){
                    continue;
                }
                if(payStoryOrder.getStatus()==1 || payStoryOrder.getStatus()==0){
                    ApiStoryOrderRepeatException apiStoryOrderRepeatException = new ApiStoryOrderRepeatException(orderStory);
                    vPayOrder = vPayOrderDao.getOrderByOrderIdAndType(orderStory.getOrderId(),1);
                    apiStoryOrderRepeatException.setvPayOrder(vPayOrder);
                    throw apiStoryOrderRepeatException;
                }
            }

        }
        return price;
    }

    @Transactional
    @Override
    public BuyOrderByBalanceResponse buyStorysByBalance(Long userId, Long orderId, OrderPayStyle payStyle, WalletStyle walletStyle)throws ApiException {

        logger.info("--------------------buyStorysByBalance-------buy-----");
        /**
         * ????????????id ??????????????????
         */
        PayStoryOrder payStoryOrder = payStoryOrderDao.findOne(orderId);

        if(walletStyle.equals(WalletStyle.ANDROID_WALLET)) {
            Wallet wallet = walletService.getWalletByUserId(userId.intValue());
            Float newAmount = ((float)wallet.getDiscount() / 100) * (float) payStoryOrder.getAmount();
            payStoryOrder.setAmount(newAmount.intValue());
            payStoryOrder.setUserDiscount(wallet.getDiscount());
        }

        walletService.addAmountToWallet(userId.intValue(),walletStyle,RechargeStyle.BUY_STORY,NumberUtil.unAbs(payStoryOrder.getAmount()),"buystory_"+orderId,"????????????");
        logger.info("--------------------buyStorysByBalance-------kou chu qianbao yu e-----");
        User user = userService.getUser(userId);

        this.addBuyStoryRecordAndClearShoppingCart(orderId, userId);

        /**
         * ??????????????????
         */
        payStoryOrder.setPayStyle(payStyle);
        payStoryOrder.setStatus(1);
        payStoryOrder.setSuccessTime(new Date());
        payStoryOrder = payStoryOrderDao.save(payStoryOrder);
        orderMixService.updateOrderMixStatus(OrderStyle.STORY_ORDER, payStoryOrder.getId().intValue(), OrderStatusStyle.PAY_SUCCESS);
        String intro2 = "??????????????????";
        Integer starCount = StarConfig.STAR_USR_BUY*(payStoryOrder.getAmount())/100;
        walletService.addStarToWallet(user.getId().intValue(),StarRechargeStyle.BUYSTORY,starCount,StarContentStyle.SINGLE_STORY_BUY.getName());

        BuyOrderByBalanceResponse response = new BuyOrderByBalanceResponse();
        response.setStandardOrder(new StandardOrder(payStoryOrder));
        return response;
    }

    @Transactional
    void addBuyStoryRecordAndClearShoppingCart(Long orderId, Long userId){

        PayStoryOrder payStoryOrder = payStoryOrderDao.findOne(orderId);

        /**
         * ??????????????????????????????
         */
        Integer pageNo = 0;
        Integer pageSize = 200;//????????????????????????????????????200
        Pageable pageable = new PageRequest(pageNo, pageSize, new Sort(Sort.Direction.DESC,"id"));
        Page<OrderStory> orderStoryPage = orderStoryDao.getOrderStoryByOrderId(orderId,pageable);

        /**
         * ????????????????????????
         */
        List<BuyStoryRecord> buyStoryRecordLists;
        Page<ShoppingTrolley> shoppingTrolley;
        pageSize = 1;
        pageable = new PageRequest(pageNo, pageSize, new Sort(Sort.Direction.DESC,"id"));


        for (OrderStory item :orderStoryPage.getContent()) {
            /**
             * ????????????????????????
             */
            buyStoryRecordLists = buyStoryRecordDao.getBuyStoryRecordsByUserIdAndStoryId(userId,item.getStoryId());
            if (buyStoryRecordLists.size()==0){//??????????????????????????????
                buyStoryRecordService.addBuyStoryRecord(userId, item.getStoryId(),1);

            }else{
                Story one = storyDao.findOne(item.getStoryId());
                throw new ApiStoryOrderRepeatException("??????????????????"+one.getName()+":"+item.getStoryId()+"+??????????????????");
            }
            /**
             * ????????????????????????????????????????????????????????????
             */
            shoppingTrolley = shoppingCartDao.getShoppingTrolleyByUserIdAndStoryId(userId,item.getStoryId(),pageable);
            if (shoppingTrolley.getContent().size()>0){
                for (ShoppingTrolley cartItem :shoppingTrolley.getContent()) {
                    shoppingCartDao.delete(cartItem.getId());
                }
            }
        }
    }
}

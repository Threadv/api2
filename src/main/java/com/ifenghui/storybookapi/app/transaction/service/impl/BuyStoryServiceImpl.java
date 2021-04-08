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
        //随机兑换一个故事（未购买，已发布，未删除，非当期,非音乐）
        logger.info("--------------------buyStoryByCode-------buy-----");

        //随机获取故事
        Story story= storyDao.getStoryByRank(userId);
//        List<Story> storys = storyDao.getStoryByRank(userId);
//        Story story = storys.get(0);
        if(story == null){
            throw new ApiNotFoundException("没有找到符合兑换条件的故事");
        }
        Long storyId = story.getId();
        User user = userService.getUser(userId);


        //添加购买故事记录
        List<BuyStoryRecord> buyStoryRecordLists;
        Integer type = 6;//1购买2订阅3使用故事兑换券4活动送的5故事集购买6兑换码

        //先判断是否已购买
        BuyStoryRecord buyStoryRecord = new BuyStoryRecord();//必须new一个新的，不然会覆盖,只添加最后一条
        buyStoryRecordLists = buyStoryRecordDao.getBuyStoryRecordsByUserIdAndStoryId(userId,storyId);
        if (buyStoryRecordLists.size()==0){//添加过的数据不用添加
            buyStoryRecord.setUserId(userId);
            buyStoryRecord.setType(type);//1购买2订阅3积分兑换券4活动送的5故事集购买6兑换码
            buyStoryRecord.setStory(story);
            buyStoryRecord.setIsTest(user.getIsTest());
            buyStoryRecord.setStoryId(story.getId());
            buyStoryRecord.setCreateTime(new Date());
            buyStoryRecordDao.save(buyStoryRecord);
        }
        //修改订单状态
//        payStoryOrder.setStatus(1);
//        payStoryOrder.setSuccessTime(new Date());
//        payStoryOrderDao.save(payStoryOrder);
        //调用积分流水添加
//        Integer buyType2 = 21;//故事集购买获得积分
        String intro2 = "兑换单本故事";
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
         * 生成故事订单
         * 生成后删除购物车
         */

        /**
         * (1)获取总价格
         */
        logger.info("--------------------createPayStoryOrder-----------");
        List<Long> storyIds = this.getLongsByStrs(storyIdsStr);
        int length = storyIds.size();
        int price = this.checkRepeatStoryOrderAndGetPrice(storyIds,userId);

        /**
         * 检测兑换券使用情况
         */
        CouponsResult couponsResult = new CouponsResult();
        couponsResult.checkCoupons(couponIds,couponUserDao,couponDao, price);

        /**
         * 获取渠道
         */
        String channel = VersionUtil.getChannelInfo(request);
        logger.info("--------------------createPayStoryOrder----userAgent---channel----"+channel);
        logger.info("--------------------createPayStoryOrder----getprice-------");

        /**
         * 查询此用户余额
         */
        Wallet wallet = walletService.getWalletByUserId(userId.intValue());

        /**
         * 添加单本支付订单表story_pay_story_order
         * 计算出实付金额
         */
        Integer couponAmount = couponsResult.getCouponAmount();
        Integer amount = price - couponsResult.getCouponAmount();
        User user = userService.getUser(userId);
        PayStoryOrder payStoryOrder = payStoryOrderService.addPayStoryOrder(user, price, couponAmount, amount, length, OrderPayStyle.DEFAULT_NULL, 100, channel);

        logger.info("--------------------createPayStoryOrder----getbanlance-------");
        /**
         * 添加订单与故事关联表story_order_story
         */
        orderStoryService.addOrderStoryList(storyIds, user, payStoryOrder.getId());
        logger.info("--------------------createPayStoryOrder----add --story_order_story-----");
        /**
         * 从购物车中清除数据
         */
        shoppingCartService.clearStoryInShoppingCart(storyIds, userId);

        /**
         * 添加故事订单和优惠券关联
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
         * (1)获取总价格
         */
        logger.info("--------------------createPayStoryOrder-----------");
        List<Long> storyIds = this.getLongsByStrs(storyIdsStr);
        int length = storyIds.size();
        int price = this.checkRepeatStoryOrderAndGetPrice(storyIds,userId);

        /**
         * 检测下故事兑换券是否足够
         */
        List<CouponStoryExchangeUser> couponStoryExchangeUserList = couponStoryExchangeUserService.checkStoryCouponAndGetCouponNum(userId.intValue(), length);

        /**
         * 创建订单
         */
        PayStoryOrder payStoryOrder = this.createBuyStorysOrder(userId,storyIdsStr,new ArrayList<>());

        /**
         * 添加购买记录和清理购物车
         */
        this.addBuyStoryRecordAndClearShoppingCart(payStoryOrder.getId(), userId);

        /**
         * 将故事兑换券与订单关联 激活故事兑换券
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
     * 提交订单验证是否有添加过订单的故事
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
//        //处理重复订单数据


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
         * 根据订单id 获取订单数据
         */
        PayStoryOrder payStoryOrder = payStoryOrderDao.findOne(orderId);

        if(walletStyle.equals(WalletStyle.ANDROID_WALLET)) {
            Wallet wallet = walletService.getWalletByUserId(userId.intValue());
            Float newAmount = ((float)wallet.getDiscount() / 100) * (float) payStoryOrder.getAmount();
            payStoryOrder.setAmount(newAmount.intValue());
            payStoryOrder.setUserDiscount(wallet.getDiscount());
        }

        walletService.addAmountToWallet(userId.intValue(),walletStyle,RechargeStyle.BUY_STORY,NumberUtil.unAbs(payStoryOrder.getAmount()),"buystory_"+orderId,"购买故事");
        logger.info("--------------------buyStorysByBalance-------kou chu qianbao yu e-----");
        User user = userService.getUser(userId);

        this.addBuyStoryRecordAndClearShoppingCart(orderId, userId);

        /**
         * 修改订单状态
         */
        payStoryOrder.setPayStyle(payStyle);
        payStoryOrder.setStatus(1);
        payStoryOrder.setSuccessTime(new Date());
        payStoryOrder = payStoryOrderDao.save(payStoryOrder);
        orderMixService.updateOrderMixStatus(OrderStyle.STORY_ORDER, payStoryOrder.getId().intValue(), OrderStatusStyle.PAY_SUCCESS);
        String intro2 = "购买单本故事";
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
         * 获取所有订单关联故事
         */
        Integer pageNo = 0;
        Integer pageSize = 200;//一个订单一般数量不会超过200
        Pageable pageable = new PageRequest(pageNo, pageSize, new Sort(Sort.Direction.DESC,"id"));
        Page<OrderStory> orderStoryPage = orderStoryDao.getOrderStoryByOrderId(orderId,pageable);

        /**
         * 添加购买故事记录
         */
        List<BuyStoryRecord> buyStoryRecordLists;
        Page<ShoppingTrolley> shoppingTrolley;
        pageSize = 1;
        pageable = new PageRequest(pageNo, pageSize, new Sort(Sort.Direction.DESC,"id"));


        for (OrderStory item :orderStoryPage.getContent()) {
            /**
             * 先判断是否已购买
             */
            buyStoryRecordLists = buyStoryRecordDao.getBuyStoryRecordsByUserIdAndStoryId(userId,item.getStoryId());
            if (buyStoryRecordLists.size()==0){//添加过的数据不用添加
                buyStoryRecordService.addBuyStoryRecord(userId, item.getStoryId(),1);

            }else{
                Story one = storyDao.findOne(item.getStoryId());
                throw new ApiStoryOrderRepeatException("重复购买故事"+one.getName()+":"+item.getStoryId()+"+，请重新选择");
            }
            /**
             * 如果是购物车，则购买成功后删除购物车数据
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

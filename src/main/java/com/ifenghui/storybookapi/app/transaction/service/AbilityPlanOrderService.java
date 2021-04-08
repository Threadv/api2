package com.ifenghui.storybookapi.app.transaction.service;

import com.ifenghui.storybookapi.adminapi.controlleradmin.ability.AbilityQuery;
import com.ifenghui.storybookapi.app.presale.entity.PreSaleCode;
import com.ifenghui.storybookapi.app.transaction.entity.abilityplan.AbilityPlanOrder;
import com.ifenghui.storybookapi.app.transaction.entity.abilityplan.AbilityPlanStyleAndPrice;
import com.ifenghui.storybookapi.app.user.entity.User;
import com.ifenghui.storybookapi.app.wallet.response.SubscribeByCodeResponse;
import com.ifenghui.storybookapi.style.*;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

/**
 * @Date: 2018/11/8 18:14
 * @Description: 宝宝会读（优能计划）
 */
public interface AbilityPlanOrderService {


    /**
     * 宝宝会读过期 设置状态为未选择年龄段状态
     * @param userId
     */
    void setPlanTypeExpire(Integer userId);

    /***
     * 是否有2-4 或 4-6 成功的订单
     * @param userId
     * @return
     */
    boolean isHasSuccessOrder(Integer userId);
    /**
     * 查找
     * @param userId
     * @return
     */
    AbilityPlanOrder getAbilityPlanOrderByUserIdAndPlanType(Integer userId);

    /**
     * 查找订单
     * @param userId
     * @return
     */
    AbilityPlanOrder getAbilityPlanOrderByUserId(Integer userId);

    /**
     * 查找
     * @param orderId
     * @return
     */
    AbilityPlanOrder getAbilityPlanOrder(Integer orderId);

    /**
     * 获得类型和priceId
     *
     * @param planStyle
     * @param buyNum
     * @return
     */
    AbilityPlanStyleAndPrice getAbilityPlanStyleAndPriceId(Integer planStyle, Integer buyNum);

    /**
     * 是否购买过单月宝宝会读（优能计划）
     *
     * @param userId
     * @param priceId
     * @return
     */
    Boolean isBuyMonthAbilityPlan(Long userId, Integer priceId);

    AbilityPlanOrder createAbilityPlanOrder(Integer priceId, AbilityPlanCodeStyle abilityPlanStyle, Long userId, List<Integer> couponIds);

    /**
     * 12.13版本增加
     * 价格部分已经改到了数据库管理
     * 只用于创建年订阅
     * @param price 交易价格
     * @param month 月数
     * @param baobaoWeek 宝宝会读的周数
     * @param userId 用户id
     * @param couponIds 优惠券列表
     * @return
     */
    AbilityPlanOrder createAbilityPlanOrder(Integer price,Integer month,Integer baobaoWeek,Long userId, List<Integer> couponIds,Integer onlineOnly);

    /**
     * 增加宝宝会读订单
     * 2019-3-19增加注释
     * @param user
     * @param priceId
     * @param abilityPlanStyle
     * @param originalPrice
     * @param couponAmount
     * @param amount
     * @param userDiscount
     * @param channel
     * @param orderPayStyle
     * @param onlineOnly
     * @return
     */
    AbilityPlanOrder addAbilityPlanOrder(User user, Integer priceId, AbilityPlanStyle abilityPlanStyle, Integer originalPrice, Integer couponAmount, Integer amount, Integer userDiscount, String channel, OrderPayStyle orderPayStyle,Integer onlineOnly);

    /**
     * 余额购买宝宝会读（优能计划）
     *
     * @param userId
     * @param orderId
     * @param payStyle
     * @param walletStyle
     * @return
     */
    AbilityPlanOrder buyAbilityPlanByBalance(Long userId, Integer orderId, OrderPayStyle payStyle, WalletStyle walletStyle);

//    AbilityPlanOrder getAbilityPlanOrderById(Integer orderId);


    /**
     * 兑换码兑换宝宝会读（优能计划）
     *
     * @param userId
     * @param abilityPlanCodeStyle
     * @param preSaleCode
     * @param receiver
     * @param phone
     * @param address
     * @param area
     * @param codeType
     * @return
     */
    SubscribeByCodeResponse buyAbilityPlanByCode(Integer ver,Long userId, Integer priceId, AbilityPlanCodeStyle abilityPlanCodeStyle, PreSaleCode preSaleCode, String receiver, String phone, String address, String area, Integer codeType);

    public AbilityPlanOrder buyAbilityPlanByCodeMethod210(Long userId, Integer priceId, AbilityPlanCodeStyle abilityPlanCodeStyle, PreSaleCode preSaleCode);
    AbilityPlanOrder buyAbilityPlanByCodeMethod(Long userId, Integer priceId, AbilityPlanCodeStyle abilityPlanCodeStyle, PreSaleCode preSaleCode);

    AbilityPlanOrder getAbilityPlanOrderByPlanTypeAndUserIdAndStatus(WeekPlanStyle weekPlanStyle, Integer userId, Integer status);

//    /**
//     * 补充vip用户宝宝会读（优能计划）relate
//     * @param userList
//     */
//    void addRelateByVip(List<User> userList);
//    /**
//     * 补充vip用户宝宝会读（优能计划）订单
//     * @param userList
//     */
//    void addOrderByVip(List<User> userList);

//    /**
//     *  补充vip用户推送记录
//     * @param userList
//     */
//    void addRecordByVip(List<User> userList);


    Page<AbilityPlanOrder> getOrdersByTypeAndStatus(Integer payType, Integer status, Pageable pageable);


    void  updateBuyNum(List<AbilityPlanOrder> orderList);

    Page<AbilityPlanOrder> findAll(AbilityPlanOrder abilityPlanOrder, Pageable pageable);

    Page<AbilityPlanOrder> findAll(AbilityQuery abilityQuery, Pageable pageable);

}

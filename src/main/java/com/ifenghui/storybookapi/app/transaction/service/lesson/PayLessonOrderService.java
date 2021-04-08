package com.ifenghui.storybookapi.app.transaction.service.lesson;

import com.ifenghui.storybookapi.app.presale.entity.PreSaleCode;
import com.ifenghui.storybookapi.app.presale.entity.SaleGoodsStyle;
import com.ifenghui.storybookapi.app.transaction.entity.lesson.PayLessonOrder;
import com.ifenghui.storybookapi.app.transaction.response.lesson.GetShareMagazineStatusResponse;
import com.ifenghui.storybookapi.app.transaction.service.order.OrderPayProcess;
import com.ifenghui.storybookapi.app.user.entity.User;
import com.ifenghui.storybookapi.app.wallet.response.SubscribeByCodeResponse;
import com.ifenghui.storybookapi.exception.ApiException;
import com.ifenghui.storybookapi.style.OrderPayStyle;
import com.ifenghui.storybookapi.style.WalletStyle;

import java.util.List;

public interface PayLessonOrderService  extends OrderPayProcess {

    PayLessonOrder createPayLessonOrder(Integer lessonId, Integer priceId, Integer lessonNum, Long userId, List<Integer> couponIds, Integer realPrice) throws ApiException;

    /**
     * 增加课程订单
     * @param user
     * @param orignalPrice
     * @param amount
     * @param priceId
     * @param orderDiscount
     * @param userDiscount
     * @param channel
     * @param num
     * @param lessonId
     * @param code
     * @return
     */
    PayLessonOrder addPayLessonOrder(User user, Integer orignalPrice, Integer couponAmount, Integer amount, Integer priceId, Integer orderDiscount, Integer userDiscount, String channel, Integer num, Integer lessonId, String code, OrderPayStyle orderPayStyle);

    void checkIsCanCreatePayLessonOrder(Integer userId, Integer priceId, Integer lessonId, Integer lessonNum);

    Integer getCanBuyLessonNum(Integer userId, Integer lessonId);

    Integer getLessonOrderCountLessonNum(Integer userId, Integer status, Integer lessonId);

    public List<PayLessonOrder> getPayLessonOrderList(Integer userId, Integer status, Integer lessonId);

    /**
     * 取消课程订单
     * @param userId
     * @param orderId
     */
    void cancelPayLessonOrder(Integer userId, Integer orderId);

    /**
     * 购买课程通过故事兑换券
     * @param userId
     * @param lessonNum
     * @param lessonId
     * @param priceId
     */
    PayLessonOrder buyLessonByStoryCoupon(Integer userId, Integer lessonNum, Integer lessonId, Integer priceId);

    /**
     * 通过 课程兑换码 兑换课程
     * @param userId
     * @param saleGoodsStyle
     * @param preSaleCode
     * @return
     */
    SubscribeByCodeResponse buyLessonByCode(Integer userId, SaleGoodsStyle saleGoodsStyle, PreSaleCode preSaleCode);

    /**
     * 余额购买课程
     * @param userId
     * @param orderId
     * @param payStyle
     * @param walletStyle
     * @throws ApiException
     */
    PayLessonOrder buyLessonByBalance(Long userId, Integer orderId, OrderPayStyle payStyle, WalletStyle walletStyle) throws ApiException;

    /**
     * 获取课程id
     * @param id
     * @return
     */
    public PayLessonOrder getPayLessonOrderById(Integer id);

    /**
     * 添加课程购买相关记录
     * @param userId
     * @param lessonId
     * @param maxLessonNum
     * @param payLessonOrderId
     * @param lessonCount
     */
    void addBuyLessonItemRecordAndOrderLesson(Integer userId, Integer lessonId, Integer maxLessonNum, Integer payLessonOrderId, Integer lessonCount,Integer isBaobao);

    /**
     * 查看是否能够购买优惠价格
     * @param userId
     */
    void checkCanBuyPriceIdSix(Integer userId);

    /**
     *  查看用户分享杂志情况
     * @param userId
     * @return
     */
    GetShareMagazineStatusResponse getShareMagazineStatus(Integer userId);

}

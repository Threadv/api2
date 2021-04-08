package com.ifenghui.storybookapi.app.shop.service;

import com.alipay.api.AlipayApiException;
import com.ifenghui.storybookapi.app.shop.entity.ShopGoods;
import com.ifenghui.storybookapi.app.shop.entity.ShopOrder;
import com.ifenghui.storybookapi.app.shop.response.ShopOrderListResponse;
import com.ifenghui.storybookapi.app.transaction.entity.goods.ExchangeRecord;
import com.ifenghui.storybookapi.app.transaction.entity.goods.Goods;
import com.ifenghui.storybookapi.style.OrderPayStyle;
import com.ifenghui.storybookapi.style.OrderStatusStyle;
import com.ifenghui.storybookapi.style.RechargePayStyle;
import org.springframework.data.domain.Page;

import javax.servlet.http.HttpServletResponse;
import java.util.List;

public interface ShopOrderService {


    /**
     * 全部订单 未删除 未支付
     * @return
     */
    List<ShopOrder> findAllOrder();

    /**
     * 我的订单
     * @param userId
     * @param status
     * @param pageNo
     * @param pageSize
     * @return
     */
    ShopOrderListResponse getMyOrderList(Integer userId, Integer status, Integer pageNo, Integer pageSize);

    /**
     * 添加兑换记录
     * @param userId
     * @param goodsId
     * @param num
     * @return
     */
    void addExchangeCoupon(Integer userId, Integer goodsId, Integer num);

    /**
     * 修改订单
     * @param orderId
     * @return
     */
    ShopOrder updateShopPayOrderSuccess(Integer orderId, OrderPayStyle orderPayStyle);

    /**
     * 添加订单
     * @param userId
     * @param goodsId
     * @param num
     * @param priceId
     * @param isTest
     * @param channel
     * @param remark
     * @return
     */
    ShopOrder addOrder(Integer userId,Integer goodsId,Integer num,Integer priceId,Integer isTest,String channel, String remark,OrderPayStyle orderPayStyle);

    /**
     * 取消订单
     * @param id
     */
    ShopOrder cancelOrder(Integer id);
    /**
     * 删除订单
     * @param id
     */
    void  deleteOrder(Integer id);

    /**
     * 查找订单
     * @param orderId
     * @return
     */
    ShopOrder findOrderById(Integer orderId);

    /**
     * 根据状态查询订单
     * @param userId
     * @param status
     * @return
     */
    Page<ShopOrder> findOrderListByStatus(Integer userId, Integer status, Integer pageNo, Integer pageSize);
    /**
     * 订单列表
     * @param userId
     * @return
     */
    Page<ShopOrder> findOrderListByUserId(Integer userId ,Integer pageNo,Integer pageSize);


    /**
     * 支付宝订单
     */
    void  addAliOrder(Integer orderId,Integer userId)throws Exception;

    //支付宝下单
    public void initAliWapPay(Integer orderId,String subject,String totalAmount,String body,HttpServletResponse httpResponse) throws Exception;

}

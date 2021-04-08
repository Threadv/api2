package com.ifenghui.storybookapi.app.transaction.service;

import com.ifenghui.storybookapi.app.transaction.response.GetLogisticsResponse;
import com.ifenghui.storybookapi.app.transaction.entity.goods.ExchangeRecord;
import com.ifenghui.storybookapi.app.transaction.entity.goods.ExpressRecord;
import com.ifenghui.storybookapi.app.transaction.entity.goods.Goods;
import org.springframework.data.domain.Page;

public interface StarShopService {


    /**
     *  获取商品分页列表
     * @param pageNo
     * @param pageSize
     * @return
     */
    @Deprecated
    Page<Goods> getGoodsPage(Integer pageNo, Integer pageSize,String ver);

    /**
     * 获取商品详情
     * @param goodsId
     * @return
     */
    @Deprecated
    Goods getGoodsById(Long goodsId);
    @Deprecated
    Page<ExchangeRecord> getExchangeRecordPageByUserId(Long userId,Integer pageNo,Integer pageSize);

    /**
     *  添加兑换记录
     * @param userId
     * @param goodsId
     * @param buyNumber
     * @param amount
     */
    @Deprecated
    ExchangeRecord addExchangeRecord(Long userId,Long goodsId,Integer buyNumber,Integer amount, String receiver,String phone, String address);

    /**
     *  购买实体书
     * @param exchangeRecord
     * @param goods
     */
    @Deprecated
    ExchangeRecord addExchangeRecordRealBook(ExchangeRecord exchangeRecord,Goods goods, String receiver, String phone, String address);

    /**
     * 购买虚拟单本故事
     * @param exchangeRecord
     * @param goods
     */
    void addExchangeRecordUnRealBook(ExchangeRecord exchangeRecord,Goods goods);

    /**
     * 购买代金券
     */
    void addExchangeRecordCoupon(Long userId, String goodsName,Long value,Integer buyNumber);

    /**
     * 购买赠阅券
     */
    void addExchangeRecordCouponDeferred(Long userId, String goodsName,Long value,Integer buyNumber);

    /**
     * 购买兑换码
     * @param exchangeRecord
     * @param goods
     */
    @Deprecated
    ExchangeRecord addExchangeRecordVipCode(Long userId,ExchangeRecord exchangeRecord,Goods goods,Integer buyNumber);
    @Deprecated
    ExpressRecord addExpressRecord(Goods goods, Long recordId, String receiver, String phone, String address);

    /**
     * 获取物流信息
     * @param userId,orderId
     */
    GetLogisticsResponse getLogistics(Long userId,Long orderId);

    /**
     * 购买故事兑换券
     * @param buyNumber
     */
    void addCouponStoryExchangeUser(Long userId,Long value,Integer buyNumber);


}

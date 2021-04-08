package com.ifenghui.storybookapi.app.presale.service;


import com.ifenghui.storybookapi.app.presale.entity.PreSaleGift;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
public interface PreSaleGiftService {


    /**
     * 查找礼物信息列表
     * @param userId
     * @param activityId
     * @return
     */
    @Transactional
    List<PreSaleGift> getGiftListByUserId(Integer userId, Integer activityId);


    /**
     * 填写地址信息
     * @param payId
     * @param receiver
     * @param phone
     * @param address
     */
    @Transactional
    PreSaleGift  setGiftInfo(Integer payId,Integer activityId,String receiver,String phone,String address,Integer userType);


    /**
     * 通过userId查询
     * @param userId
     * @return
     */
    @Deprecated
    PreSaleGift getGiftByUserId(Integer userId,Integer activityId);
    /**
     * 添加礼品物流空信息
     * @return
     */
    @Transactional
    PreSaleGift addGiftByPayId(Integer payId);

    /**
     * 填写地址信息
     * @param payId
     * @param receiver
     * @param phone
     * @param address
     */
    @Transactional
    PreSaleGift  setAddress(Integer payId,String receiver,String phone,String address);

    /**
     * 填写物流信息
     * @param payId
     * @param express_company_name
     * @param express_code
     * @param express_status
     * @return
     */
    @Transactional
    PreSaleGift setExpress(Integer payId,String express_company_name,String express_code,Integer express_status);

    /**
     * 通过userId获得列表 活动1
     * @param userId
     * @return
     */
    @Deprecated
    Page<PreSaleGift> findGiftListByUserId(Integer userId, Integer pageNo, Integer pageSize);

    /**
     * 获得礼物领取详情
     * @param Id
     * @return
     */
    PreSaleGift getGiftById(Integer Id);

    /**
     * 通过payId获得礼物领取详情
     * @param payId
     * @return
     */
    PreSaleGift getGiftByPayId(Integer payId);


    /**
     * 查询所有地址
     * @param preSaleGift
     * @param pageRequest
     * @return
     */
    Page<PreSaleGift> findAll(PreSaleGift preSaleGift, PageRequest pageRequest);
}

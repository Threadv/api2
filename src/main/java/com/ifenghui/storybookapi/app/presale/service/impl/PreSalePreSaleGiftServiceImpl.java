package com.ifenghui.storybookapi.app.presale.service.impl;


import com.ifenghui.storybookapi.app.presale.dao.PreSaleGiftDao;
import com.ifenghui.storybookapi.app.presale.dao.PreSaleGoodsDao;
import com.ifenghui.storybookapi.app.presale.dao.PreSalePayDao;
import com.ifenghui.storybookapi.app.presale.entity.PreSaleGift;
import com.ifenghui.storybookapi.app.presale.entity.PreSaleGoods;
import com.ifenghui.storybookapi.app.presale.entity.PreSalePay;
import com.ifenghui.storybookapi.app.presale.service.PreSaleGiftService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

@Transactional
@Component
public class PreSalePreSaleGiftServiceImpl implements PreSaleGiftService {

    @Autowired
    PreSaleGiftDao giftDao;

    @Autowired
    PreSalePayDao preSalePayDao;

    @Autowired
    PreSaleGoodsDao goodsDao;


    /**
     * 查找礼物信息列表
     * @param userId
     * @param activityId
     * @return
     */
    @Override
    public List<PreSaleGift> getGiftListByUserId(Integer userId, Integer activityId) {

        List<PreSaleGift> giftList = giftDao.getGiftListByUserIdAndActivityId(userId,activityId);
        return giftList;
    }

    /**
     * 填写地址信息 activity2
     *
     * @param payId
     * @param receiver
     * @param phone
     * @param address
     */
    @Override
    public PreSaleGift setGiftInfo(Integer payId, Integer activityId, String receiver, String phone, String address,Integer userType) {

        PreSalePay pay = preSalePayDao.findOne(payId);
        PreSaleGift preSaleGift = new PreSaleGift();
        int userId = pay.getUserId();
        int goodsId = pay.getGoodsId();
        PreSaleGoods goods = goodsDao.findOne(goodsId);
        preSaleGift.setUserId(userId);
        preSaleGift.setGoodsId(goodsId);
        preSaleGift.setPayId(payId);
        preSaleGift.setActivityId(activityId);
        preSaleGift.setName(goods.getContent());
        preSaleGift.setReceiver(receiver);
        preSaleGift.setPhone(phone);
        preSaleGift.setAddress(address);
        preSaleGift.setStatus(1);
        preSaleGift.setUserType(userType);
        preSaleGift.setCreateTime(new Date());
        preSaleGift.setExpressCompanyName("暂无");
        preSaleGift.setExpressStatus(0);
        preSaleGift.setExpressCode("暂无");
        giftDao.save(preSaleGift);
        return preSaleGift;
    }

    /**
     * 活动2
     * 通过userId查询
     *
     * @param userId
     * @return
     */
    @Override
    public PreSaleGift getGiftByUserId(Integer userId, Integer activityId) {

        PreSaleGift gift = new PreSaleGift();
        gift.setUserId(userId);
        gift.setActivityId(activityId);
        PreSaleGift saleGift = giftDao.findOne(Example.of(gift));
        return saleGift;
    }

    /**
     * 添加礼品待领取信息
     *
     * @return
     */
    @Override
    public PreSaleGift addGiftByPayId(Integer payId) {

        List<PreSaleGift> all = giftDao.findAll();
        for (PreSaleGift g : all) {
            if (g.getPayId() == payId.intValue()) {
                return g;
            }
        }
        PreSalePay pay = preSalePayDao.findOne(payId);
        PreSaleGoods goods = goodsDao.findOne(pay.getGoodsId());
        PreSaleGift preSaleGift = new PreSaleGift();
        preSaleGift.setPayId(payId);
        preSaleGift.setUserId(pay.getUserId());
        preSaleGift.setGoodsId(pay.getGoodsId());
        preSaleGift.setActivityId(pay.getActivityId());
        preSaleGift.setUserType(pay.getUserType());
        preSaleGift.setReceiver("");
        preSaleGift.setPhone("");
        preSaleGift.setAddress("");
        preSaleGift.setName(goods.getContent());
        preSaleGift.setStatus(0);
        preSaleGift.setExpressCompanyName("暂无");
        preSaleGift.setExpressStatus(0);
        preSaleGift.setExpressCode("暂无");
        preSaleGift.setCreateTime(new Date());
        giftDao.save(preSaleGift);
        return preSaleGift;
    }

    /**
     * 填写地址信息
     *
     * @param payId
     * @param receiver
     * @param phone
     * @param address
     */
    @Override
    public PreSaleGift setAddress(Integer payId, String receiver, String phone, String address) {


        PreSalePay pay = preSalePayDao.findOne(payId);
        int userId = pay.getUserId();
        int goodsId = pay.getGoodsId();

        PreSaleGift preSaleGift = this.getGiftByPayId(payId);

        if(preSaleGift==null){
            preSaleGift=new PreSaleGift();
            preSaleGift.setGoodsId(pay.getGoodsId());
            preSaleGift.setPayId(pay.getId());
            preSaleGift.setUserType(pay.getUserType());
            preSaleGift.setUserId(pay.getUserId());
            preSaleGift.setCreateTime(new Date());
            preSaleGift.setActivityId(pay.getActivityId());
            preSaleGift.setStatus(0);
            preSaleGift.setExpressCode("");
            preSaleGift.setExpressCompanyName("");
            preSaleGift.setPhone("");
            preSaleGift.setReceiver("");
            preSaleGift.setExpressStatus(0);
            preSaleGift.setName("");
//            preSaleGift.setm
        }
        preSaleGift.setUserId(userId);
        preSaleGift.setGoodsId(goodsId);
        preSaleGift.setReceiver(receiver);
        preSaleGift.setPhone(phone);
        preSaleGift.setAddress(address);
        preSaleGift.setStatus(1);
        preSaleGift.setCreateTime(new Date());

        giftDao.save(preSaleGift);

        pay.setIsExpress(1);
        preSalePayDao.save(pay);
        return preSaleGift;
    }

    /**
     * 填写物流信息
     *
     * @param payId
     * @param express_company_name
     * @param express_code
     * @param express_status
     * @return
     */
    @Override
    public PreSaleGift setExpress(Integer payId, String express_company_name, String express_code, Integer express_status) {

        PreSaleGift preSaleGift = this.getGiftByPayId(payId);
        preSaleGift.setExpressCompanyName(express_company_name);
        preSaleGift.setExpressCode(express_code);
        preSaleGift.setExpressStatus(express_status);

        giftDao.save(preSaleGift);
        return preSaleGift;
    }

    @Override
    public Page<PreSaleGift> findGiftListByUserId(Integer userId, Integer pageNo, Integer pageSize) {

        PreSaleGift preSaleGift = new PreSaleGift();
        preSaleGift.setUserId(userId);
        preSaleGift.setActivityId(1);
        Pageable p = new PageRequest(pageNo - 1, pageSize, new Sort(Sort.Direction.DESC, "id"));
        Page<PreSaleGift> giftPage = giftDao.findAll(Example.of(preSaleGift), p);
        return giftPage;
    }

    @Override
    public PreSaleGift getGiftById(Integer Id) {
        PreSaleGift preSaleGift = giftDao.findOne(Id);
        return preSaleGift;
    }

    /**
     * 通过payId获得礼物领取详情
     *
     * @param payId
     * @return
     */
    @Override
    public PreSaleGift getGiftByPayId(Integer payId) {

        PreSaleGift preSaleGift = giftDao.getGiftByPayId(payId);
        return preSaleGift;
    }

    @Override
    public Page<PreSaleGift> findAll(PreSaleGift preSaleGift, PageRequest pageRequest) {
        return giftDao.findAll(Example.of(preSaleGift),pageRequest);
    }
}


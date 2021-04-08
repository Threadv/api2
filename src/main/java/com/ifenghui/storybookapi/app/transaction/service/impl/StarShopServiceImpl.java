package com.ifenghui.storybookapi.app.transaction.service.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ifenghui.storybookapi.app.story.dao.StoryDao;
import com.ifenghui.storybookapi.app.transaction.dao.*;
import com.ifenghui.storybookapi.app.transaction.response.GetFenXiaoVipCodeResponse;
import com.ifenghui.storybookapi.app.transaction.response.GetLogisticsResponse;
import com.ifenghui.storybookapi.app.transaction.response.LogisticsNew;
import com.ifenghui.storybookapi.app.transaction.response.SetLogisticsData;
import com.ifenghui.storybookapi.app.transaction.service.StarShopService;
import com.ifenghui.storybookapi.app.user.dao.UserDao;

import com.ifenghui.storybookapi.app.wallet.service.WalletService;
import com.ifenghui.storybookapi.config.MyEnv;
import com.ifenghui.storybookapi.config.StarShopConfig;
import com.ifenghui.storybookapi.app.transaction.entity.BuyStoryRecord;
import com.ifenghui.storybookapi.app.transaction.entity.goods.ExchangeRecord;
import com.ifenghui.storybookapi.app.transaction.entity.goods.ExchangeRecordVipcode;
import com.ifenghui.storybookapi.app.transaction.entity.goods.ExpressRecord;
import com.ifenghui.storybookapi.app.transaction.entity.goods.Goods;
import com.ifenghui.storybookapi.app.user.entity.User;
import com.ifenghui.storybookapi.exception.ApiLackBalanceException;
import com.ifenghui.storybookapi.exception.ApiNotFoundException;
import com.ifenghui.storybookapi.app.transaction.service.CouponDeferredService;
import com.ifenghui.storybookapi.app.transaction.service.CouponService;
import com.ifenghui.storybookapi.app.transaction.service.CouponStoryExchangeUserService;
import com.ifenghui.storybookapi.app.wallet.service.UserStarRecordService;
import com.ifenghui.storybookapi.style.StarContentStyle;
import com.ifenghui.storybookapi.style.StarRechargeStyle;
import com.ifenghui.storybookapi.util.HttpRequest;
import com.ifenghui.storybookapi.util.HttpUtils;
import com.ifenghui.storybookapi.util.NumberUtil;
import org.apache.http.HttpResponse;
import org.apache.http.util.EntityUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Component;
import org.springframework.core.env.Environment;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.*;

@Component
public class StarShopServiceImpl implements StarShopService {

    @Autowired
    private Environment env;

    @Autowired
    GoodsDao goodsDao;

    @Autowired
    ExchangeRecordDao exchangeRecordDao;

    @Autowired
    ExpressRecordDao expressRecordDao;

    @Autowired
    UserStarRecordService userStarRecordService;

    @Autowired
    BuyStoryRecordDao buyStoryRecordDao;

    @Autowired
    ExchangeRecordVipcodeDao exchangeRecordVipcodeDao;

    @Autowired
    StoryDao storyDao;

    @Autowired
    UserDao userDao;


    @Autowired
    CouponService couponService;

    @Autowired
    WalletService walletService;

    @Autowired
    CouponDeferredService couponDeferredService;

    @Autowired
    CouponStoryExchangeUserService couponStoryExchangeUserService;
    private static Logger logger = Logger.getLogger(StarShopServiceImpl.class);
    @Override
    public Page<Goods> getGoodsPage(Integer pageNo, Integer pageSize,String ver) {
        Goods goods = new Goods();
        goods.setStatus(1);
        Pageable pageable  = new PageRequest(pageNo, pageSize,new Sort(Sort.Direction.DESC,"orderBy","id"));;
        Page<Goods> goodsPage;
        logger.info("--------------------getGoodsPage--------ver----"+ver);
//        ver = "1.6.0";
        if(ver.compareTo("1.0.0") >= 0){

            if(ver.compareTo("1.6.0")>=0){
                logger.info("--------------------getGoodsPage--------ver--->=160-");
                goodsPage = goodsDao.findAll(Example.of(goods),pageable);
            } else {
                logger.info("--------------------getGoodsPage--------ver---<160-");
                //之前版本不返回商品type为5的商品
                goodsPage = goodsDao.getGoodsByPage(pageable);
            }

        } else {
            goodsPage = goodsDao.findAll(Example.of(goods),pageable);
        }


        for(Goods item:goodsPage.getContent()){
            item.setGoodsImg(MyEnv.env.getProperty("oss.url")+"goods/"+item.getGoodsImg());
        }
        return goodsPage;
    }

    @Override
    public Goods getGoodsById(Long goodsId) {
        Goods goods = goodsDao.findOne(goodsId);
        goods.setGoodsImg(MyEnv.env.getProperty("oss.url")+"goods/"+goods.getGoodsImg());
        return goods;
    }

    @Override
    public Page<ExchangeRecord> getExchangeRecordPageByUserId(Long userId,Integer pageNo, Integer pageSize) {
        ExchangeRecord exchangeRecord = new ExchangeRecord();
        exchangeRecord.setUserId(userId);
        Pageable pageable = new PageRequest(pageNo, pageSize,new Sort(Sort.Direction.DESC,"createTime","id"));
        Page<ExchangeRecord> exchangeRecordPage = exchangeRecordDao.findAll(Example.of(exchangeRecord),pageable);
        ExchangeRecordVipcode exchangeRecordVipcodefind = new ExchangeRecordVipcode();
        List<ExchangeRecordVipcode> exchangeRecordVipcodeList;
        Date endTime;
        SimpleDateFormat f = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Calendar c = Calendar.getInstance();
        for (ExchangeRecord item:exchangeRecordPage.getContent()){
            if(item.getType().intValue() == 2){
                //兑换码，返回兑换码列表
                exchangeRecordVipcodefind.setOrderId(item.getId());
                exchangeRecordVipcodeList = exchangeRecordVipcodeDao.findAll(Example.of(exchangeRecordVipcodefind));
                //获取激活截止日期，订单时间+3月
//                SimpleDateFormat f = new SimpleDateFormat("yyyy-MM-dd,hh:mm:ss");
//                f.format(item.getCreateTime());
//
//                Calendar c = Calendar.getInstance();
//                //c.set(2011, 1, 1);
////                System.out.println(f.format(c.getTime()));
//                c.add(Calendar.DAY_OF_MONTH, 3);
//                endTime = c.getTime();

                c.add(Calendar.MONTH, 3);//几个月
                Date time =c.getTime();
                Long endTimeStr=time.getTime();
                 endTime = new Date(endTimeStr);
                for(ExchangeRecordVipcode vItem:exchangeRecordVipcodeList){
                    vItem.setExpiryDate(endTime);
                }
                item.setVipcodes(exchangeRecordVipcodeList);
            }
        }

        return exchangeRecordPage;
    }

    @Override
    public ExchangeRecord addExchangeRecord(Long userId, Long goodsId, Integer buyNumber, Integer amount, String receiver, String phone, String address) {


        Integer starCount = walletService.getWalletByUserId(userId).getStarCount();

        if(starCount < amount){
            throw new ApiLackBalanceException("星星值不足！");
        }

        Goods goods = goodsDao.findOne(goodsId);
        ExchangeRecord exchangeRecord = new ExchangeRecord();
        exchangeRecord.setUserId(userId);
        exchangeRecord.setAmount(amount);
        exchangeRecord.setGoodsId(goodsId);
        exchangeRecord.setType(goods.getType());
        exchangeRecord.setBuyNumber(buyNumber);
        exchangeRecord.setGoodsName(goods.getGoodsName());
        exchangeRecord.setCreateTime(new Date());
        exchangeRecord.setStatus(2);// 0未发货 1已发货 2即时兑换
        exchangeRecord = exchangeRecordDao.save(exchangeRecord);

        if (goods.getType().equals(StarShopConfig.BUY_TYPE_REAL_BOOK)) {
            exchangeRecord = this.addExchangeRecordRealBook(exchangeRecord,goods,receiver,phone,address);
        }

//        if (goods.getType().equals(StarShopConfig.BUY_TYPE_UNREAL_BOOK)) {
//            this.addExchangeRecordUnRealBook(exchangeRecord,goods);
//        }

        if(goods.getType().equals(StarShopConfig.BUY_TYPE_STORY_COUPON)){
            this.addCouponStoryExchangeUser(exchangeRecord.getUserId(),Long.parseLong(goods.getValue()),buyNumber);
            exchangeRecord.setIntro("故事兑换券已自动添加到“个人中心--我的优惠券”");
        }


        if (goods.getType().equals(StarShopConfig.BUY_TYPE_COUPON)) {
            this.addExchangeRecordCoupon(exchangeRecord.getUserId(),goods.getGoodsName(),Long.parseLong(goods.getValue()),buyNumber);
            exchangeRecord.setIntro("代金券已自动添加在“个人中心--我的优惠券”");
        }

        if (goods.getType().equals(StarShopConfig.BUY_TYPE_COUPON_DEFERRED)) {
            this.addExchangeRecordCouponDeferred(exchangeRecord.getUserId(),goods.getGoodsName(),Long.parseLong(goods.getValue()),buyNumber);
            exchangeRecord.setIntro("赠阅券已自动添加在“个人中心--我的优惠券”");
        }

        if(goods.getType().equals(StarShopConfig.BUY_TYPE_VIP_CODE)) {
            this.addExchangeRecordVipCode(userId,exchangeRecord,goods,buyNumber);
        }

        String intro = "购买" + goods.getGoodsName();
//        userStarRecordService.addUserStarRecord(userId,amount, AddStyle.DOWN, StarRechargeStyle.getById(goods.getType()),intro);//阅读星流水

        //购买故事
        walletService.addStarToWallet(userId.intValue(),StarRechargeStyle.getById(goods.getType()), NumberUtil.unAbs(amount), StarContentStyle.SINGLE_STORY_BUY.getName());
        //获取商品图片
         exchangeRecord.setGoodsImg(MyEnv.env.getProperty("oss.url")+"goods/"+goods.getGoodsImg());
        return exchangeRecord;

    }

    @Override
    public ExchangeRecord addExchangeRecordRealBook(ExchangeRecord exchangeRecord, Goods goods, String receiver, String phone, String address) {
        exchangeRecord.setStatus(0);
        exchangeRecord = exchangeRecordDao.save(exchangeRecord);
        ExpressRecord expressRecord = this.addExpressRecord(goods,exchangeRecord.getId(),receiver,phone,address);
        exchangeRecord.setExpressRecord(expressRecord);
        return exchangeRecord;
    }

    @Override
    public void addExchangeRecordUnRealBook(ExchangeRecord exchangeRecord, Goods goods) {
        User user = userDao.findOne(exchangeRecord.getUserId());
        BuyStoryRecord buyStoryRecord = new BuyStoryRecord();
        buyStoryRecord.setCreateTime(new Date());
        buyStoryRecord.setStoryId(Long.parseLong(goods.getValue()));
        buyStoryRecord.setUserId(exchangeRecord.getUserId());
        buyStoryRecord.setIsTest(user.getIsTest());
        buyStoryRecord.setType(1);
        buyStoryRecordDao.save(buyStoryRecord);
    }

    @Override
    public void addExchangeRecordCoupon(Long userId, String goodsName,Long value,Integer buyNumber) {
        String channel = "兑换" + goodsName;
        for (int i=0;i<buyNumber;i++){
            couponService.collectCoupon(userId,value,channel);
        }
    }

    @Override
    public void addExchangeRecordCouponDeferred(Long userId, String goodsName,Long value,Integer buyNumber) {
        String channel = "兑换" + goodsName;
        for (int i=0;i<buyNumber;i++) {
            couponDeferredService.collectDeferredCoupon(userId, value, channel);
        }
    }
    @Transactional
    @Override
    public ExchangeRecord addExchangeRecordVipCode(Long userId,ExchangeRecord exchangeRecord, Goods goods,Integer buyNumber) {
        String url = env.getProperty("fenxiao.url") + "public/index.php/api/order/createStarShopVipCode";
        String param = "month="+ goods.getValue();
        String codes;
        ObjectMapper objectMapper = new ObjectMapper();
        GetFenXiaoVipCodeResponse getCodesResponse = new GetFenXiaoVipCodeResponse();
        GetFenXiaoVipCodeResponse response;
        for (int i=0;i<buyNumber;i++) {
            codes = HttpRequest.sendGet(url, param);
            try {
                response = objectMapper.readValue(codes, GetFenXiaoVipCodeResponse.class);
                if(response.getStatus().intValue() == 1){
                    this.addExchangeRecordVipcode(userId,exchangeRecord.getId(),response.getCode());//添加vipcode和订单关联
                } else {
                    throw new ApiNotFoundException("vip码生成失败！无法购买",2);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        //获取插入的数据
        ExchangeRecordVipcode exchangeRecordVipcodefind = new ExchangeRecordVipcode();
        exchangeRecordVipcodefind.setOrderId(exchangeRecord.getId());
        List<ExchangeRecordVipcode> exchangeRecordVipcodeList = exchangeRecordVipcodeDao.findAll(Example.of(exchangeRecordVipcodefind));
        exchangeRecord.setVipcodes(exchangeRecordVipcodeList);//vipcode列表


        return exchangeRecord;
    }
    private void addExchangeRecordVipcode(Long userId,Long orderId,String vipCode){
        ExchangeRecordVipcode exchangeRecordVipcode = new ExchangeRecordVipcode();
        exchangeRecordVipcode.setOrderId(orderId);
        exchangeRecordVipcode.setUserId(userId);
        exchangeRecordVipcode.setVipCode(vipCode);
        exchangeRecordVipcode.setCreateTime(new Date());
        exchangeRecordVipcodeDao.save(exchangeRecordVipcode);
    }
    @Override
    public ExpressRecord addExpressRecord(Goods goods, Long recordId, String receiver, String phone, String address) {
        ExpressRecord expressRecord = new ExpressRecord();
        expressRecord.setRecordId(recordId);
        expressRecord.setGoodsId(goods.getId());
        expressRecord.setGoodsName(goods.getGoodsName());
        expressRecord.setReceiver(receiver);
        expressRecord.setPhone(phone);
        expressRecord.setAddress(address);
        expressRecord.setExpressCompanyId(0);
        expressRecord.setExpressOrderId("");
        expressRecord.setStatus(0);
        expressRecord.setCreateTime(new Date());
        expressRecordDao.save(expressRecord);
        return expressRecord;
    }
    @Override
    public GetLogisticsResponse getLogistics(Long userId, Long orderId){
        GetLogisticsResponse getLogisticsResponse = new GetLogisticsResponse();
        //orderId,获取物流信息
        ExpressRecord expressRecordFind = new ExpressRecord();
        expressRecordFind.setRecordId(orderId);
        List<ExpressRecord> expressRecords = expressRecordDao.findAll(Example.of(expressRecordFind));
        if(expressRecords.size() == 0){
            return  getLogisticsResponse;
        }
        Integer expressCompanyId = expressRecords.get(0).getExpressCompanyId();
        String address = expressRecords.get(0).getAddress();
        String type = "";
        String type2 = "";
        if(expressCompanyId.intValue() == 1){
            type = "YTO";
            type2 = "圆通速递";
        }else if(expressCompanyId.intValue() == 2){
            type = "EMS";
            type2 = "EMS";
        }else{
            type = "auto";
        }

        String host = "http://jisukdcx.market.alicloudapi.com";
        String path = "/express/query";
        String method = "GET";
        String appcode = "075e3442a30745969e73e6f2de67c889";
        Map<String, String> headers = new HashMap<String, String>();
        //最后在header中的格式(中间是英文空格)为Authorization:APPCODE 83359fd73fe94948385f570e3c139105
        headers.put("Authorization", "APPCODE " + appcode);
        Map<String, String> querys = new HashMap<String, String>();
        querys.put("number", expressRecords.get(0).getExpressOrderId());
        querys.put("type", type);

        try {
            /**
             * 重要提示如下:
             * HttpUtils请从
             * https://github.com/aliyun/api-gateway-demo-sign-java/blob/master/src/main/java/com/aliyun/api/gateway/demo/util/HttpUtils.java
             * 下载
             *
             * 相应的依赖请参照
             * https://github.com/aliyun/api-gateway-demo-sign-java/blob/master/pom.xml
             */
            HttpResponse response = HttpUtils.doGet(host, path, method, headers, querys);
            String a = response.toString();
            System.out.println(response.toString());
            //获取response的body
            String resString = EntityUtils.toString(response.getEntity());
//            System.out.println(EntityUtils.toString(response.getEntity()));

            //jackson json转换工具
            ObjectMapper objectMapper = new ObjectMapper();
            SetLogisticsData setLogisticsData = new SetLogisticsData();

            SetLogisticsData resp = objectMapper.readValue(resString, SetLogisticsData.class);

            LogisticsNew logisticsNew = new LogisticsNew();
            logisticsNew.setNumber(resp.getResult().getNumber());
            logisticsNew.setType(type2);
            logisticsNew.setAddress(address);
            logisticsNew.setList(resp.getResult().getList());
            logisticsNew.setIssign(resp.getResult().getIssign());
            logisticsNew.setDeliverystatus(resp.getResult().getDeliverystatus());
//            SetLogisticsNewData setLogisticsNewData = new SetLogisticsNewData();
//            setLogisticsNewData.setResult(logisticsNew);
            getLogisticsResponse.setLogistics(logisticsNew);
            //获取对应商品信息
            ExchangeRecord exchangeRecord = new ExchangeRecord();
            exchangeRecord = exchangeRecordDao.findOne(orderId);
            Goods goods = goodsDao.findOne(exchangeRecord.getGoodsId());
            goods.setGoodsImg(MyEnv.env.getProperty("oss.url")+"goods/"+goods.getGoodsImg());
            getLogisticsResponse.setGoods(goods);
        } catch (Exception e) {
            e.printStackTrace();
        }


        return getLogisticsResponse;
    }

    @Override
    public void addCouponStoryExchangeUser(Long userId,Long value,Integer buyNumber) {
        for (int i=0;i<buyNumber;i++) {
            couponStoryExchangeUserService.addCouponStoryExchangeUser(value.intValue(),userId.intValue());
        }
    }
}

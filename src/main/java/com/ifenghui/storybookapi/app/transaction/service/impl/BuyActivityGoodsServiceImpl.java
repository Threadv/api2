package com.ifenghui.storybookapi.app.transaction.service.impl;

import com.ifenghui.storybookapi.app.presale.controller.PreSalePayController;
import com.ifenghui.storybookapi.app.presale.entity.PreSalePay;
import com.ifenghui.storybookapi.app.presale.response.AppCallBackeResponse;
import com.ifenghui.storybookapi.app.presale.service.PreSalePayService;

import com.ifenghui.storybookapi.app.transaction.response.BuyOrderByBalanceResponse;
import com.ifenghui.storybookapi.app.transaction.service.*;
import com.ifenghui.storybookapi.app.user.service.UserService;
import com.ifenghui.storybookapi.app.wallet.service.WalletService;
import com.ifenghui.storybookapi.exception.ApiException;
import com.ifenghui.storybookapi.style.*;
import com.ifenghui.storybookapi.util.MD5Util;
import com.ifenghui.storybookapi.util.NumberUtil;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import java.io.ByteArrayOutputStream;

@Component
public class BuyActivityGoodsServiceImpl implements BuyActivityGoodsService{



    @Autowired
    UserService userService;



    @Autowired
    WalletService walletService;

    @Autowired
    HttpServletRequest request;


    @Autowired
    private Environment env;

    @Autowired
    PreSalePayService preSalePayService;

    @Autowired
    PreSalePayController preSalPayController;


    Logger logger= Logger.getLogger(BuyActivityGoodsServiceImpl.class);

    @Value("${sale.api.uri}")
    String saleApi;


    @Override
    public BuyOrderByBalanceResponse buyActivityGoodsByBalance(Long userId, Long orderId, OrderPayStyle payStyle, WalletStyle walletStyle) throws ApiException {



        //给activity接口回调

        Runnable runnable=new Runnable() {
            @Override
            public void run() {
                Integer amount=0;
//                PayResponse payResponse=null;
//              1,请求订单接口,查询价格




//                    ByteArrayOutputStream baos = new ByteArrayOutputStream();
//                    HttpUtils.doGet(saleApi+"/pay/get_pay?id="+orderId,null,"GET",new HashMap<>(),new HashMap<>()).getEntity().writeTo(baos);
//                    String str=new String(baos.toByteArray(),"utf-8");

                    PreSalePay preSalePay= preSalePayService.findPayById(orderId.intValue());
//                    ObjectMapper mapper = new ObjectMapper();
//                    payResponse=mapper.readValue(str,PayResponse.class);
                    if(preSalePay.getStatus()!=0){
                        return;//已经是成功订单不再处理
                    }

                    walletService.addAmountToWallet(userId.intValue(),walletStyle,RechargeStyle.ACTIVITY_GOODS,NumberUtil.unAbs(preSalePay.getPrice()),"activity_goods_pay_"+preSalePay.getId(),"活动购买");



//                2，回调接口设置成功状态
                String sign=MD5Util.getMD5(""+preSalePay.getPrice()+orderId+"vista688");


//                Map<String,String> bodys=new HashMap<>();
//                bodys.put("price",preSalePay.getPrice()+"");
//                bodys.put("orderId",preSalePay.getId()+"");
//                bodys.put("walletStyle", walletStyle.toString());
//                bodys.put("sign",sign+"");

                for(int i=0;i<50;i++){
                    try {
//                        String saleApi="http://test.ifenghui.com/sale";
                        ByteArrayOutputStream baos = new ByteArrayOutputStream();
                        AppCallBackeResponse resp= preSalPayController.appNotify(preSalePay.getPrice(),preSalePay.getId(),walletStyle,sign);
//                        HttpUtils.doPost(saleApi+"/pay/appNotify",null,"POST",new HashMap<>(),new HashMap<>(),bodys).getEntity().writeTo(baos);
//                        String str=new String(baos.toByteArray(),"utf-8");

//                        ObjectMapper mapper = new ObjectMapper();
//                        payResponse=mapper.readValue(str,PayResponse.class);

                        if(resp.getStatus().getCode()==1){
                            return;//已经是成功订单不再处理
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }


                    //前10秒，每秒请求一次，10秒后每5秒请求一次，请求范围：10+200秒
                    try {
                        if(i<10){
                                Thread.sleep(1000);

                        }else{
                                Thread.sleep(5000);
                        }
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }

                }
            }
        };
        Thread thread =new Thread(runnable);
        thread.start();
        return null;

    }



//    public static void main(String[] args){
//        try {
//            String aa= MD5Util.getMD5("abc");
//            aa=aa;
////            String saleApi="http://test.ifenghui.com/sale";
////            ByteArrayOutputStream baos = new ByteArrayOutputStream();
////            HttpUtils.doGet(saleApi+"/pay/get_pay?id=694",null,"GET",new HashMap<>(),new HashMap<>()).getEntity().writeTo(baos);
////            String str=new String(baos.toByteArray(),"utf-8");
////            ObjectMapper mapper = new ObjectMapper();
////            PayResponse payResponse=mapper.readValue(str,PayResponse.class);
////            str=str;
////
////            JsonMapper
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//    }


}

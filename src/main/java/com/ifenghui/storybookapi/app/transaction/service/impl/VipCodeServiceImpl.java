package com.ifenghui.storybookapi.app.transaction.service.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ifenghui.storybookapi.app.presale.entity.CodeRemindIntro;
import com.ifenghui.storybookapi.app.presale.entity.PreSaleCode;
import com.ifenghui.storybookapi.app.presale.entity.PreSaleGoods;
import com.ifenghui.storybookapi.app.presale.entity.SaleGoodsStyle;
import com.ifenghui.storybookapi.app.presale.service.PreSaleCodeService;
import com.ifenghui.storybookapi.app.presale.service.PreSaleGoodsService;
import com.ifenghui.storybookapi.app.story.entity.Lesson;
import com.ifenghui.storybookapi.app.story.entity.SerialStory;
import com.ifenghui.storybookapi.app.story.entity.Story;
import com.ifenghui.storybookapi.app.story.service.LessonService;
import com.ifenghui.storybookapi.app.story.service.SerialStoryService;
import com.ifenghui.storybookapi.app.transaction.response.GetActiveCodeResultResponse;
import com.ifenghui.storybookapi.app.transaction.service.BuySerialService;
import com.ifenghui.storybookapi.app.transaction.service.BuyStoryService;
import com.ifenghui.storybookapi.app.transaction.service.VipCodeService;
import com.ifenghui.storybookapi.app.user.entity.UserAccount;
import com.ifenghui.storybookapi.app.user.entity.UserExtend;
import com.ifenghui.storybookapi.app.user.service.UserAccountService;
import com.ifenghui.storybookapi.app.user.service.UserExtendService;
import com.ifenghui.storybookapi.app.wallet.response.GetVipCodeDetailResponse;
import com.ifenghui.storybookapi.app.wallet.response.SubscribeByCodeResponse;
import com.ifenghui.storybookapi.app.wallet.service.PayService;
import com.ifenghui.storybookapi.config.VipcodeConfig;
import com.ifenghui.storybookapi.exception.ApiActiveCodeFailException;
import com.ifenghui.storybookapi.exception.ApiNotFoundException;

import com.ifenghui.storybookapi.style.VipCodeStyle;
import com.ifenghui.storybookapi.style.VipGoodsStyle;

import com.ifenghui.storybookapi.util.HttpRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;


import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Component
public class VipCodeServiceImpl implements VipCodeService {

    @Autowired
    private Environment env;

    @Autowired
    UserAccountService userAccountService;

    @Autowired
    BuyStoryService buyStoryService;

    @Autowired
    BuySerialService buySerialService;

    @Autowired
    PayService payService;

    @Autowired
    SerialStoryService serialStoryService;

    @Autowired
    PreSaleCodeService preSaleCodeService;

    @Autowired
    LessonService lessonService;

    @Autowired
    UserExtendService userExtendService;

    @Autowired
    PreSaleGoodsService preSaleGoodsService;

    @Override
    public SubscribeByCodeResponse subscribeByCode(Long userId, String code) {
        SubscribeByCodeResponse subscribeByCodeResponse = new SubscribeByCodeResponse();

//      Integer buyType = 4;//???????????????vfg
//      Void res = payService.subscribeByBalance(orderCode,userId,);//
        //??????????????????????????????
        Integer srcType = 1;

        UserAccount userAccount = userAccountService.getUserAccountByUserIdAndSrcType(userId, srcType);
        String unionid = "";
        if (userAccount != null) {
            unionid = userAccount.getSrcId();
        }
        //????????????code????????????
//        HttpRequest httpRequest = new HttpRequest();
//        String url = "http://wx.storybook.ifenghui.com/public/index.php/api/order/vipActive";
        String url = env.getProperty("fenxiao.url") + "public/index.php/api/order/vipActiveNew";

        String vipActiveResult;
        String param = "app_uid=" + userId + "&code=" + code + "&unionid=" + unionid;
//        param    = "app_uid="+userId;
        vipActiveResult = HttpRequest.sendPost(url, param);
        //jackson json????????????
        ObjectMapper objectMapper = new ObjectMapper();
        GetActiveCodeResultResponse resp = new GetActiveCodeResultResponse();
        try {
            resp = objectMapper.readValue(vipActiveResult, GetActiveCodeResultResponse.class);
        } catch (IOException e) {
            e.printStackTrace();
        }
        if (resp.getCode() != 0) {
            //????????????0??????
            throw new ApiActiveCodeFailException("?????????????????????");
        }
        Integer type = null;//1?????????????????????2???????????????3?????????????????????
        String intro = "";
        if (resp.getCode().equals(0) && resp.getType().equals(0)) {//??????=0?????????
            Integer month = resp.getMonth();
            intro = payService.subscribeByCode(userId, code, month);
            type = 1;
        } else if (resp.getCode().equals(0) && resp.getType().equals(3)) {
            //?????????????????????//?????????????????????????????????????????????
            type = 3;
            Story story = buyStoryService.buyStoryByCode(userId, code);
            intro = "?????????" + story.getName() + "???\n????????????????????????";
            subscribeByCodeResponse.setTargetValue(story.getId().intValue());
        } else if (resp.getCode().equals(0) && resp.getType().equals(1)) {
            //???????????????,resp.getType()??????????????????id
            intro = buySerialService.buySerialStoryByCode(userId, code, VipcodeConfig.STORY_SERIAL_WULALA);
            type = 2;
            subscribeByCodeResponse.setTargetValue(VipcodeConfig.STORY_SERIAL_WULALA);
        } else if (resp.getCode().equals(0) && resp.getType().equals(2)) {
            intro = buySerialService.buySerialStoryByCode(userId, code, VipcodeConfig.STORY_SERIAL_GULI);
            type = 2;
            subscribeByCodeResponse.setTargetValue(VipcodeConfig.STORY_SERIAL_GULI);
        }

        subscribeByCodeResponse.setType(type);
        subscribeByCodeResponse.setIntro(intro);
        subscribeByCodeResponse.getStatus().setMsg("????????????");
        return subscribeByCodeResponse;
    }

//    @Override
//    @Deprecated
//    public GetVipCodeDetailResponse getVipcodeDetail(String code) {
//        GetVipCodeDetailResponse response = new GetVipCodeDetailResponse();
//
////        HttpRequest httpRequest = new HttpRequest();
//        String url = env.getProperty("fenxiao.url") + "public/index.php/api/order/getVipcodeDetail";
//        String param = "code=" + code;
//        String vipCodeDetail;
//        vipCodeDetail = HttpRequest.sendGet(url, param);
//        //jackson json????????????
//        ObjectMapper objectMapper = new ObjectMapper();
//        GetActiveCodeResultResponse result = new GetActiveCodeResultResponse();
//        try {
//            result = objectMapper.readValue(vipCodeDetail, GetActiveCodeResultResponse.class);
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//        //??????????????????????????????????????????
//        if (result.getCode() != 0) {
//            if (result.getCode().equals(1)) {
//                throw new ApiActiveCodeFailException("??????????????????!");
//            } else {
//                throw new ApiActiveCodeFailException("???????????????!");
//            }
//        }
//        String intro = "";
//        if (result.getCode().equals(0) && result.getType().equals(0)) {
//            if (result.getMonth().equals(1)) {
//                intro = "??????????????????";
//            } else if (result.getMonth().equals(3)) {
//                intro = "???????????????";
//            } else if (result.getMonth().equals(6)) {
//                intro = "???????????????";
//            } else if (result.getMonth().equals(12)) {
//                intro = "???????????????";
//            }
//        } else if (result.getCode().equals(0) && result.getType().equals(VipcodeConfig.STORY_SERIAL_WULALA)) {
//            SerialStory serialStoryOne = serialStoryService.getSerialStory(VipcodeConfig.STORY_SERIAL_WULALA);
//            intro = "???" + serialStoryOne.getName() + "???";
//        } else if (result.getCode().equals(0) && result.getType().equals(VipcodeConfig.STORY_SERIAL_GULI)) {
//            SerialStory serialStoryTwo = serialStoryService.getSerialStory(VipcodeConfig.STORY_SERIAL_GULI);
//            intro = "???" + serialStoryTwo.getName() + "???";
//        } else if (result.getCode().equals(0) && result.getType().equals(3)) {
//            intro = "??????????????????";
//        }
//        response.setCodeType(1);
//        response.setIntro(intro);
//        return response;
//    }

    @Override
    public GetVipCodeDetailResponse getSaleLessonVipCodeDetail(Long userId, String code) {
        PreSaleCode preSaleCode = preSaleCodeService.getCodeByCode(code);
        if (preSaleCode == null) {
            throw new ApiNotFoundException("???????????????????????????");
        }
        String intro = "";

        //?????????????????????????????????????????????????????????
        List<CodeRemindIntro> introList = null;

        this.checkSaleLessonVipCode(preSaleCode);
        VipGoodsStyle vipGoodsStyle = VipGoodsStyle.getById(preSaleCode.getCodeType());
//        PreSaleGoods preSaleGoods = preSaleGoodsService.findGoodsById(preSaleCode.getGoodsId());
//        VipGoodsStyle vipGoodsStyle = VipGoodsStyle.getById(preSaleGoods.getCodeType());
        SaleGoodsStyle saleGoodsStyle = vipGoodsStyle.getSaleGoodsStyle();
        GetVipCodeDetailResponse response = new GetVipCodeDetailResponse();
        if (vipGoodsStyle.getSaleGoodsStyle() != null) {
            Lesson lesson = lessonService.getLessonById(saleGoodsStyle.getTargetValue());
            intro = "???" + lesson.getName() + "?????????";
            response.setCodeType(vipGoodsStyle.getCodeType());
            response.setType(VipCodeStyle.LESSON.getId());
        }

        //????????????????????????????????????????????????
        if (vipGoodsStyle.getAbilityPlanCodeStyle() != null) {
            /**
             * ????????????(2.10.0??????)
             */
            introList =new ArrayList<>();
            CodeRemindIntro codeRemindIntro1 = new CodeRemindIntro();
            CodeRemindIntro codeRemindIntro2 = new CodeRemindIntro();

            codeRemindIntro1.setId(1);
            codeRemindIntro2.setId(2);
            codeRemindIntro1.setTitle("2-4???" );
            codeRemindIntro2.setTitle("4-6???" );

            introList.add(codeRemindIntro1);
            introList.add(codeRemindIntro2);
            response.setIntroList(introList);
            response.setCodeType(vipGoodsStyle.getCodeType());
            response.setContent("????????????????????????????????????????????????????????????");
            //????????????????????????6??????
            response.setType(VipCodeStyle.ABILITY_PLAN_TWO_FOUR.getId());
            //2.11.0 ?????????????????????
            intro = "????????????????????????("+vipGoodsStyle.getAbilityPlanCodeStyle().getTitle()+")";
        }
        if (vipGoodsStyle.getVipPriceStyle() != null) {
            intro = "????????????????????????" + vipGoodsStyle.getVipPriceStyle().getName();
            response.setCodeType(vipGoodsStyle.getCodeType());
            response.setContent("??????????????????????????????????????????????????????");
            response.setType(VipCodeStyle.VIP.getId());
        }
        if (vipGoodsStyle.getSerialStoryCodeStyle() != null) {

            intro = "????????????" + vipGoodsStyle.getSerialStoryCodeStyle().getName()+"???";
            response.setCodeType(vipGoodsStyle.getCodeType());
            response.setContent("");
            response.setType(VipCodeStyle.SERIAL.getId());
        }

        response.setIntro(intro);
        return response;
    }

    @Override
    public void checkSaleLessonVipCode(PreSaleCode preSaleCode) {
        if (preSaleCode.getStatus().equals(1)) {
            throw new ApiActiveCodeFailException("????????????????????????");
        }
        if (preSaleCode.getIsExpire().equals(1)) {
            throw new ApiActiveCodeFailException("?????????????????????");
        }
    }
}

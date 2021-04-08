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

//      Integer buyType = 4;//兑换码订阅vfg
//      Void res = payService.subscribeByBalance(orderCode,userId,);//
        //获取用户微信账号信息
        Integer srcType = 1;

        UserAccount userAccount = userAccountService.getUserAccountByUserIdAndSrcType(userId, srcType);
        String unionid = "";
        if (userAccount != null) {
            unionid = userAccount.getSrcId();
        }
        //调用微信code激活接口
//        HttpRequest httpRequest = new HttpRequest();
//        String url = "http://wx.storybook.ifenghui.com/public/index.php/api/order/vipActive";
        String url = env.getProperty("fenxiao.url") + "public/index.php/api/order/vipActiveNew";

        String vipActiveResult;
        String param = "app_uid=" + userId + "&code=" + code + "&unionid=" + unionid;
//        param    = "app_uid="+userId;
        vipActiveResult = HttpRequest.sendPost(url, param);
        //jackson json转换工具
        ObjectMapper objectMapper = new ObjectMapper();
        GetActiveCodeResultResponse resp = new GetActiveCodeResultResponse();
        try {
            resp = objectMapper.readValue(vipActiveResult, GetActiveCodeResultResponse.class);
        } catch (IOException e) {
            e.printStackTrace();
        }
        if (resp.getCode() != 0) {
            //激活失败0成功
            throw new ApiActiveCodeFailException("兑换码激活失败");
        }
        Integer type = null;//1跳转书架订阅区2跳故事集区3跳转购买单本区
        String intro = "";
        if (resp.getCode().equals(0) && resp.getType().equals(0)) {//类型=0是订阅
            Integer month = resp.getMonth();
            intro = payService.subscribeByCode(userId, code, month);
            type = 1;
        } else if (resp.getCode().equals(0) && resp.getType().equals(3)) {
            //兑换单本故事，//随机送一个此用户没购买过的故事
            type = 3;
            Story story = buyStoryService.buyStoryByCode(userId, code);
            intro = "故事《" + story.getName() + "》\n已成功添加至书架";
            subscribeByCodeResponse.setTargetValue(story.getId().intValue());
        } else if (resp.getCode().equals(0) && resp.getType().equals(1)) {
            //兑换故事集,resp.getType()值对应故事集id
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
        subscribeByCodeResponse.getStatus().setMsg("兑换成功");
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
//        //jackson json转换工具
//        ObjectMapper objectMapper = new ObjectMapper();
//        GetActiveCodeResultResponse result = new GetActiveCodeResultResponse();
//        try {
//            result = objectMapper.readValue(vipCodeDetail, GetActiveCodeResultResponse.class);
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//        //判断有无此兑换码或是否已激活
//        if (result.getCode() != 0) {
//            if (result.getCode().equals(1)) {
//                throw new ApiActiveCodeFailException("兑换码已失效!");
//            } else {
//                throw new ApiActiveCodeFailException("无此兑换码!");
//            }
//        }
//        String intro = "";
//        if (result.getCode().equals(0) && result.getType().equals(0)) {
//            if (result.getMonth().equals(1)) {
//                intro = "一个月兑换码";
//            } else if (result.getMonth().equals(3)) {
//                intro = "季度兑换码";
//            } else if (result.getMonth().equals(6)) {
//                intro = "半年兑换码";
//            } else if (result.getMonth().equals(12)) {
//                intro = "全年兑换码";
//            }
//        } else if (result.getCode().equals(0) && result.getType().equals(VipcodeConfig.STORY_SERIAL_WULALA)) {
//            SerialStory serialStoryOne = serialStoryService.getSerialStory(VipcodeConfig.STORY_SERIAL_WULALA);
//            intro = "《" + serialStoryOne.getName() + "》";
//        } else if (result.getCode().equals(0) && result.getType().equals(VipcodeConfig.STORY_SERIAL_GULI)) {
//            SerialStory serialStoryTwo = serialStoryService.getSerialStory(VipcodeConfig.STORY_SERIAL_GULI);
//            intro = "《" + serialStoryTwo.getName() + "》";
//        } else if (result.getCode().equals(0) && result.getType().equals(3)) {
//            intro = "兑换一本故事";
//        }
//        response.setCodeType(1);
//        response.setIntro(intro);
//        return response;
//    }

    @Override
    public GetVipCodeDetailResponse getSaleLessonVipCodeDetail(Long userId, String code) {
        PreSaleCode preSaleCode = preSaleCodeService.getCodeByCode(code);
        if (preSaleCode == null) {
            throw new ApiNotFoundException("没有找到该兑换码！");
        }
        String intro = "";

        //宝宝会读（优能计划）兑换码提示信息列表
        List<CodeRemindIntro> introList = null;

        this.checkSaleLessonVipCode(preSaleCode);
        VipGoodsStyle vipGoodsStyle = VipGoodsStyle.getById(preSaleCode.getCodeType());
//        PreSaleGoods preSaleGoods = preSaleGoodsService.findGoodsById(preSaleCode.getGoodsId());
//        VipGoodsStyle vipGoodsStyle = VipGoodsStyle.getById(preSaleGoods.getCodeType());
        SaleGoodsStyle saleGoodsStyle = vipGoodsStyle.getSaleGoodsStyle();
        GetVipCodeDetailResponse response = new GetVipCodeDetailResponse();
        if (vipGoodsStyle.getSaleGoodsStyle() != null) {
            Lesson lesson = lessonService.getLessonById(saleGoodsStyle.getTargetValue());
            intro = "《" + lesson.getName() + "》课程";
            response.setCodeType(vipGoodsStyle.getCodeType());
            response.setType(VipCodeStyle.LESSON.getId());
        }

        //如果是宝宝会读（优能计划）兑换码
        if (vipGoodsStyle.getAbilityPlanCodeStyle() != null) {
            /**
             * 提示信息(2.10.0版本)
             */
            introList =new ArrayList<>();
            CodeRemindIntro codeRemindIntro1 = new CodeRemindIntro();
            CodeRemindIntro codeRemindIntro2 = new CodeRemindIntro();

            codeRemindIntro1.setId(1);
            codeRemindIntro2.setId(2);
            codeRemindIntro1.setTitle("2-4岁" );
            codeRemindIntro2.setTitle("4-6岁" );

            introList.add(codeRemindIntro1);
            introList.add(codeRemindIntro2);
            response.setIntroList(introList);
            response.setCodeType(vipGoodsStyle.getCodeType());
            response.setContent("宝宝会读权限包含实物礼品，请完善收货信息");
            //兑换码提示信息用6表示
            response.setType(VipCodeStyle.ABILITY_PLAN_TWO_FOUR.getId());
            //2.11.0 兑换码提示文案
            intro = "故事飞船宝宝会读("+vipGoodsStyle.getAbilityPlanCodeStyle().getTitle()+")";
        }
        if (vipGoodsStyle.getVipPriceStyle() != null) {
            intro = "故事飞船会员——" + vipGoodsStyle.getVipPriceStyle().getName();
            response.setCodeType(vipGoodsStyle.getCodeType());
            response.setContent("会员权限包含实物礼品，请完善收货信息");
            response.setType(VipCodeStyle.VIP.getId());
        }
        if (vipGoodsStyle.getSerialStoryCodeStyle() != null) {

            intro = "故事集《" + vipGoodsStyle.getSerialStoryCodeStyle().getName()+"》";
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
            throw new ApiActiveCodeFailException("该内容已被兑换！");
        }
        if (preSaleCode.getIsExpire().equals(1)) {
            throw new ApiActiveCodeFailException("兑换码已过期！");
        }
    }
}

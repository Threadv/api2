package com.ifenghui.storybookapi.app.transaction.controller;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.ifenghui.storybookapi.api.response.exception.ExceptionResponse;
import com.ifenghui.storybookapi.app.presale.entity.PreSaleCode;
import com.ifenghui.storybookapi.app.presale.entity.PreSaleGoods;
import com.ifenghui.storybookapi.app.presale.entity.SaleGoodsStyle;
import com.ifenghui.storybookapi.app.presale.service.PreSaleCodeService;
import com.ifenghui.storybookapi.app.presale.service.PreSaleGoodsService;
import com.ifenghui.storybookapi.app.story.service.LessonService;
import com.ifenghui.storybookapi.app.story.service.SerialStoryService;
import com.ifenghui.storybookapi.app.story.service.StoryService;
import com.ifenghui.storybookapi.app.transaction.response.GetCodesFenxiaoResponse;
import com.ifenghui.storybookapi.app.transaction.service.*;
import com.ifenghui.storybookapi.app.transaction.service.lesson.PayLessonOrderService;
import com.ifenghui.storybookapi.app.transaction.service.order.OrderService;
import com.ifenghui.storybookapi.app.user.entity.UserAccount;
import com.ifenghui.storybookapi.app.user.entity.UserExtend;
import com.ifenghui.storybookapi.app.user.service.UserAccountService;
import com.ifenghui.storybookapi.app.user.service.UserExtendService;
import com.ifenghui.storybookapi.app.user.service.UserService;
import com.ifenghui.storybookapi.app.wallet.response.*;
import com.ifenghui.storybookapi.app.wallet.service.PayService;
import com.ifenghui.storybookapi.exception.ApiActiveCodeFailException;
import com.ifenghui.storybookapi.exception.ApiException;
import com.ifenghui.storybookapi.exception.ApiNotFoundException;
import com.ifenghui.storybookapi.style.*;
import com.ifenghui.storybookapi.util.HttpRequest;
import io.swagger.annotations.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

@Controller
@EnableAutoConfiguration
@RequestMapping("/api/vipcode")
@Api(value = "?????????", description = "?????????")
public class VipCodeController {
    @Autowired
    CouponService couponService;

    @Autowired
    UserService userService;

    @Autowired
    CouponDeferredService couponDeferredService;

    @Autowired
    CouponStoryExchangeUserService couponStoryExchangeUserService;

    @Autowired
    private Environment env;

    @Autowired
    StoryService storyService;


    @Autowired
    PaySubscriptionPriceService paySubscriptionPriceService;

    @Autowired
    HttpServletRequest request;


    @Autowired
    CouponService couponSerivce;
    @Autowired
    OrderService orderService;

    @Autowired

    PayService payService;
    @Autowired
    UserAccountService userAccountService;

    @Autowired
    BuyStoryService buyStoryService;

    @Autowired
    BuySerialService buySerialService;

    @Autowired
    SerialStoryService serialStoryService;

    @Autowired
    PreSaleCodeService preSaleCodeService;

    @Autowired
    VipCodeService vipCodeService;

    @Autowired
    LessonService lessonService;

    @Autowired
    PayLessonOrderService payLessonOrderService;

    @Autowired
    PreSaleGoodsService preSaleGoodsService;

    @Autowired
    PayVipOrderService payVipOrderService;

    @Autowired
    AbilityPlanOrderService abilityPlanOrderService;
    @Autowired
    UserExtendService userExtendService;


    @RequestMapping(value = "/getCodes", method = RequestMethod.POST)
    @ResponseBody
    @ApiOperation(value = "?????????????????????", notes = "")
//    @ApiResponses({@ApiResponse(code=204,message="????????????",response = ExceptionResponse.class)})
    public GetCodesResponse getCodes(
            @ApiParam(value = "??????token") @RequestParam String token
    ) throws ApiException {
        Long userId;
        userId = userService.checkAndGetCurrentUserId(token);
        //????????????openid(??????????????????????????????srcid??????openid)
        Integer srcType = 1;//????????????
        UserAccount userAccount = userAccountService.getUserAccountByUserIdAndSrcType(userId, srcType);
        if (userAccount == null) {
            throw new ApiNotFoundException("??????????????????");
        }
        //??????????????????
        String url = env.getProperty("fenxiao.url") + "public/index.php/api/order/getVipCodeByWx";

        String codes;
        String param = "unionid=" + userAccount.getSrcId();
        codes = HttpRequest.sendGet(url, param);
//        JSONArray json = JSONArray.fromObject(codes);
        //jackson json????????????
        ObjectMapper objectMapper = new ObjectMapper();
        GetCodesResponse getCodesResponse = new GetCodesResponse();

        try {
            GetCodesFenxiaoResponse resp = objectMapper.readValue(codes, GetCodesFenxiaoResponse.class);

            getCodesResponse.setCodes(resp.getBody());

        } catch (IOException e) {
            e.printStackTrace();
        }


        return getCodesResponse;
    }

    @RequestMapping(value = "/subscribeByCode", method = RequestMethod.POST)
    @ResponseBody
    @ApiOperation(value = "?????????????????????/???????????????/?????????????????????", notes = "")
    @ApiResponses({@ApiResponse(code = 208, message = "code????????????", response = ExceptionResponse.class)})
    public SubscribeByCodeResponse subscribeByCode(
            @ApiParam(value = "??????token") @RequestParam String token,
            @ApiParam(value = "?????????") @RequestParam String code,
            @ApiParam(value = "?????????(1:2-4???, 2:4-6???)") @RequestParam(required = false) Integer ageType,
            @ApiParam(value = "?????????") @RequestParam(required = false) String receiver,
            @ApiParam(value = "????????????") @RequestParam(required = false) String phone,
            @ApiParam(value = "??????") @RequestParam(required = false) String address,
            @ApiParam(value = "??????") @RequestParam(required = false) String area
    ) throws ApiException {
        Long userId = userService.checkAndGetCurrentUserId(token);
        if (code != null) {
            code = code.trim();
        }
        if (!code.endsWith("x")) {
            PreSaleCode preSaleCode = preSaleCodeService.getCodeByCode(code);
            vipCodeService.checkSaleLessonVipCode(preSaleCode);
            PreSaleGoods preSaleGoods = preSaleGoodsService.findGoodsById(preSaleCode.getCodeType());
            VipGoodsStyle vipGoodsStyle = VipGoodsStyle.getById(preSaleGoods.getCodeType());

            if (vipGoodsStyle == null) {
                throw new ApiActiveCodeFailException("?????????????????????");
            }

            if (vipGoodsStyle.getCodeType() == 2) {
                if (receiver == null || receiver.equals("") || phone == null || phone.equals("") || address == null || address.equals("") || area == null || address.equals("")) {
                    throw new ApiActiveCodeFailException("????????????????????????");
                }
            }

            if (vipGoodsStyle.getVipPriceStyle() != null) {
                return payVipOrderService.buyVipByCode(userId, vipGoodsStyle, preSaleCode, receiver, phone, address, area, vipGoodsStyle.getCodeType());
            } else if (vipGoodsStyle.getSaleGoodsStyle() != null) {
                return payLessonOrderService.buyLessonByCode(userId.intValue(), vipGoodsStyle.getSaleGoodsStyle(), preSaleCode);
            } else if (vipGoodsStyle.getSerialStoryCodeStyle() != null) {
                //??????????????????
                String intro = buySerialService.buySerialStoryByCode(userId, preSaleCode.getCode(), vipGoodsStyle.getSerialStoryCodeStyle().getId());
                SubscribeByCodeResponse response = new SubscribeByCodeResponse();
                response.setTargetValue(vipGoodsStyle.getSerialStoryCodeStyle().getId());
                response.setIntro(intro);
                response.setType(VipCodeStyle.SERIAL.getId());
                return response;
            } else if (vipGoodsStyle.getAbilityPlanCodeStyle() != null) {

                UserExtend userExtend = userExtendService.findUserExtendByUserId(userId);
                int weekPlan = 0;


                if (ageType != null && ageType != 1 && ageType != 2) {
                    throw new ApiActiveCodeFailException("????????????????????????");
                } else {
                    weekPlan = userExtend.getWeekPlanType();
                }
                /**
                 * ??????????????????????????????????????????,???????????????????????????vipGoodsStyle?????????,?????????????????????????????????????????????????????????
                 */
                if (vipGoodsStyle.getId() == 40 || vipGoodsStyle.getId() == 41) {
                    if (ageType != null) {
                        if (ageType == 1) {
                            vipGoodsStyle = VipGoodsStyle.ABILITY_PLAN_YEAR_TWO_FOUR;
                        } else {
                            vipGoodsStyle = VipGoodsStyle.ABILITY_PLAN_YEAR_FOUR_SIX;
                        }
                    } else {
                        if (weekPlan == 1) {
                            vipGoodsStyle = VipGoodsStyle.ABILITY_PLAN_YEAR_TWO_FOUR;
                        } else {
                            vipGoodsStyle = VipGoodsStyle.ABILITY_PLAN_YEAR_FOUR_SIX;
                        }
                    }
                } else if (vipGoodsStyle.getId() == 42 || vipGoodsStyle.getId() == 43) {
                    if (ageType != null) {
                        if (ageType == 1) {
                            vipGoodsStyle = VipGoodsStyle.ABILITY_PLAN_YEAR_TWO_FOUR_CODE;
                        } else {
                            vipGoodsStyle = VipGoodsStyle.ABILITY_PLAN_YEAR_FOUR_SIX_CODE;
                        }
                    } else {
                        if (weekPlan == 1) {
                            vipGoodsStyle = VipGoodsStyle.ABILITY_PLAN_YEAR_TWO_FOUR_CODE;
                        } else {
                            vipGoodsStyle = VipGoodsStyle.ABILITY_PLAN_YEAR_FOUR_SIX_CODE;
                        }
                    }

                } else if (vipGoodsStyle.getId() == 44 || vipGoodsStyle.getId() == 45) {
                    if (ageType != null) {
                        if (ageType == 1) {
                            vipGoodsStyle = VipGoodsStyle.ABILITY_PLAN_HALF_YEAR_TWO_FOUR_CODE;
                        } else {
                            vipGoodsStyle = VipGoodsStyle.ABILITY_PLAN_HALF_YEAR_FOUR_SIX_CODE;
                        }
                    } else {
                        if (weekPlan == 1) {
                            vipGoodsStyle = VipGoodsStyle.ABILITY_PLAN_HALF_YEAR_TWO_FOUR_CODE;
                        } else {
                            vipGoodsStyle = VipGoodsStyle.ABILITY_PLAN_HALF_YEAR_FOUR_SIX_CODE;
                        }
                    }

                } else if (vipGoodsStyle.getId() == 46 || vipGoodsStyle.getId() == 47) {
                    if (ageType != null) {
                        if (ageType == 1) {
                            vipGoodsStyle = VipGoodsStyle.ABILITY_PLAN_SEASON_TWO_FOUR_CODE;
                        } else {
                            vipGoodsStyle = VipGoodsStyle.ABILITY_PLAN_SEASON_FOUR_SIX_CODE;
                        }
                    } else {
                        if (weekPlan == 1) {
                            vipGoodsStyle = VipGoodsStyle.ABILITY_PLAN_SEASON_TWO_FOUR_CODE;
                        } else {
                            vipGoodsStyle = VipGoodsStyle.ABILITY_PLAN_SEASON_FOUR_SIX_CODE;
                        }
                    }
                } else if (vipGoodsStyle.getId() == 48 || vipGoodsStyle.getId() == 49) {
                    if (ageType != null) {
                        if (ageType == 1) {
                            vipGoodsStyle = VipGoodsStyle.ABILITY_PLAN_MONTH_TWO_FOUR_CODE;
                        } else {
                            vipGoodsStyle = VipGoodsStyle.ABILITY_PLAN_MONTH_FOUR_SIX_CODE;
                        }
                    } else {
                        if (weekPlan == 1) {
                            vipGoodsStyle = VipGoodsStyle.ABILITY_PLAN_MONTH_TWO_FOUR_CODE;
                        } else {
                            vipGoodsStyle = VipGoodsStyle.ABILITY_PLAN_MONTH_FOUR_SIX_CODE;
                        }
                    }
                } else if (vipGoodsStyle.getId() == 50 || vipGoodsStyle.getId() == 51) {
                    if (ageType != null) {
                        if (ageType == 1) {
                            vipGoodsStyle = VipGoodsStyle.ABILITY_PLAN_OTHER_TWO_FOUR_CODE;
                        } else {
                            vipGoodsStyle = VipGoodsStyle.ABILITY_PLAN_OTHER_FOUR_SIX_CODE;
                        }
                    } else {
                        if (weekPlan == 1) {
                            vipGoodsStyle = VipGoodsStyle.ABILITY_PLAN_OTHER_TWO_FOUR_CODE;
                        } else {
                            vipGoodsStyle = VipGoodsStyle.ABILITY_PLAN_OTHER_FOUR_SIX_CODE;
                        }
                    }
                }
                return abilityPlanOrderService.buyAbilityPlanByCode(210,userId, vipGoodsStyle.getId(), vipGoodsStyle.getAbilityPlanCodeStyle(), preSaleCode, receiver, phone, address, area, vipGoodsStyle.getCodeType());
            }
            return null;
        } else {
            return vipCodeService.subscribeByCode(userId, code);
        }
    }

    
    @RequestMapping(value = "/subscribeByCode211")
    @ResponseBody
    @ApiOperation(value = "?????????????????????/???????????????/????????????????????? v2.11.0", notes = "")
    @ApiResponses({@ApiResponse(code = 208, message = "code????????????", response = ExceptionResponse.class)})
    public SubscribeByCodeResponse subscribeByCode211(
            @RequestHeader(value = "ssToken",required = false) String ssToken,
            @ApiParam(value = "token") @RequestParam(required = false) String token,
            @ApiParam(value = "?????????") @RequestParam String code,
            @ApiParam(value = "?????????") @RequestParam(required = false) String receiver,
            @ApiParam(value = "????????????") @RequestParam(required = false) String phone,
            @ApiParam(value = "??????") @RequestParam(required = false) String address,
            @ApiParam(value = "??????") @RequestParam(required = false) String area
    ) throws ApiException {

        Long userId;
        if(ssToken!=null){
            userId = userService.checkAndGetCurrentUserId(ssToken);
        }else if(token != null){
            userId = userService.checkAndGetCurrentUserId(token);
        }else {
            throw new ApiNotFoundException("?????????????????????");
        }
        if (code != null) {
            code = code.trim();
        }
        if (!code.endsWith("x")) {
            PreSaleCode preSaleCode = preSaleCodeService.getCodeByCode(code);
            vipCodeService.checkSaleLessonVipCode(preSaleCode);
//            PreSaleGoods preSaleGoods = preSaleGoodsService.findGoodsById(preSaleCode.getGoodsId());
            VipGoodsStyle vipGoodsStyle = VipGoodsStyle.getById(preSaleCode.getCodeType());

            if (vipGoodsStyle == null) {
                throw new ApiActiveCodeFailException("?????????????????????");
            }

            if (vipGoodsStyle.getCodeType() == 2) {
                if (receiver == null || receiver.equals("") || phone == null || phone.equals("") || address == null || address.equals("") || area == null || address.equals("")) {
                    throw new ApiActiveCodeFailException("????????????????????????");
                }
            }

            if (vipGoodsStyle.getVipPriceStyle() != null) {
                return payVipOrderService.buyVipByCode(userId, vipGoodsStyle, preSaleCode, receiver, phone, address, area, vipGoodsStyle.getCodeType());
            } else if (vipGoodsStyle.getSaleGoodsStyle() != null) {
                return payLessonOrderService.buyLessonByCode(userId.intValue(), vipGoodsStyle.getSaleGoodsStyle(), preSaleCode);
            } else if (vipGoodsStyle.getSerialStoryCodeStyle() != null) {
                //??????????????????
                String intro = buySerialService.buySerialStoryByCode(userId, preSaleCode.getCode(), vipGoodsStyle.getSerialStoryCodeStyle().getId());
                SubscribeByCodeResponse response = new SubscribeByCodeResponse();
                response.setTargetValue(vipGoodsStyle.getSerialStoryCodeStyle().getId());
                response.setIntro(intro);
                response.setType(VipCodeStyle.SERIAL.getId());
                preSaleCodeService.usePreSaleCode(preSaleCode,userId.intValue());
                return response;
            } else if (vipGoodsStyle.getAbilityPlanCodeStyle() != null) {

                UserExtend userExtend = userExtendService.findUserExtendByUserId(userId);
                boolean hasSuccessOrder = abilityPlanOrderService.isHasSuccessOrder(userId.intValue());
                int  weekPlan;
                if(hasSuccessOrder){
                    weekPlan = userExtend.getWeekPlanType();
                }else {
                    weekPlan = userExtend.getWeekPlanTypeNew();
                }

                /**
                 * ??????????????????????????????????????????,???????????????????????????vipGoodsStyle?????????,?????????????????????????????????????????????????????????
                 */
                if (vipGoodsStyle.getId() == 40 || vipGoodsStyle.getId() == 41) {
                    if (weekPlan == 1) {
                        vipGoodsStyle = VipGoodsStyle.ABILITY_PLAN_YEAR_TWO_FOUR;
                    } else if (weekPlan == 2) {
                        vipGoodsStyle = VipGoodsStyle.ABILITY_PLAN_YEAR_FOUR_SIX;
                    } else if (weekPlan == 0) {
                        throw new ApiNotFoundException("????????????????????????");
                    }
                } else if (vipGoodsStyle.getId() == 42 || vipGoodsStyle.getId() == 43) {
                    if (weekPlan == 1) {
                        vipGoodsStyle = VipGoodsStyle.ABILITY_PLAN_YEAR_TWO_FOUR_CODE;
                    } else if (weekPlan == 2) {
                        vipGoodsStyle = VipGoodsStyle.ABILITY_PLAN_YEAR_FOUR_SIX_CODE;
                    } else if (weekPlan == 0) {
                        vipGoodsStyle = VipGoodsStyle.ABILITY_PLAN_YEAR_DEFAULT;
                    }
                } else if (vipGoodsStyle.getId() == 44 || vipGoodsStyle.getId() == 45) {
                    if (weekPlan == 1) {
                        vipGoodsStyle = VipGoodsStyle.ABILITY_PLAN_HALF_YEAR_TWO_FOUR_CODE;
                    } else if (weekPlan == 2) {
                        vipGoodsStyle = VipGoodsStyle.ABILITY_PLAN_HALF_YEAR_FOUR_SIX_CODE;
                    } else if (weekPlan == 0) {
                        throw new ApiNotFoundException("????????????????????????");
                    }
                } else if (vipGoodsStyle.getId() == 46 || vipGoodsStyle.getId() == 47) {
                    if (weekPlan == 1) {
                        vipGoodsStyle = VipGoodsStyle.ABILITY_PLAN_SEASON_TWO_FOUR_CODE;
                    } else if (weekPlan == 2) {
                        vipGoodsStyle = VipGoodsStyle.ABILITY_PLAN_SEASON_FOUR_SIX_CODE;
                    } else if (weekPlan == 0) {
                        throw new ApiNotFoundException("????????????????????????");
                    }
                } else if (vipGoodsStyle.getId() == 48 || vipGoodsStyle.getId() == 49) {
                    if (weekPlan == 1) {
                        vipGoodsStyle = VipGoodsStyle.ABILITY_PLAN_MONTH_TWO_FOUR_CODE;
                    } else if (weekPlan == 2) {
                        vipGoodsStyle = VipGoodsStyle.ABILITY_PLAN_MONTH_FOUR_SIX_CODE;
                    } else if (weekPlan == 0) {
                        vipGoodsStyle = VipGoodsStyle.ABILITY_PLAN_MONTH_DEFAULT;
                    }
                } else if (vipGoodsStyle.getId() == 50 || vipGoodsStyle.getId() == 51) {
                    if (weekPlan == 1) {
                        vipGoodsStyle = VipGoodsStyle.ABILITY_PLAN_OTHER_TWO_FOUR_CODE;
                    } else if (weekPlan == 2) {
                        vipGoodsStyle = VipGoodsStyle.ABILITY_PLAN_OTHER_FOUR_SIX_CODE;
                    } else if (weekPlan == 0) {
                        throw new ApiNotFoundException("????????????????????????");
                    }
                }
                return abilityPlanOrderService.buyAbilityPlanByCode(211,userId, vipGoodsStyle.getId(), vipGoodsStyle.getAbilityPlanCodeStyle(), preSaleCode, receiver, phone, address, area, vipGoodsStyle.getCodeType());
            }
            return null;
        } else {
            return vipCodeService.subscribeByCode(userId, code);
        }
    }


    @RequestMapping(value = "/vip_code_detail", method = RequestMethod.GET)
    @ResponseBody
    @ApiOperation(value = "???????????????", notes = "???????????????")
    public GetVipCodeDetailResponse getVipcodeDetail(
            @RequestHeader("ssToken") String ssToken,
            @ApiParam(value = "?????????") @RequestParam String code
    ) throws ApiException {

        Long userId;
        if (ssToken == null || ssToken.length() <= 0) {
            userId = 0L;
        } else {
            userId = userService.checkAndGetCurrentUserId(ssToken);
        }

        if (code != null) {
            code = code.trim();
        }
//        if (!code.endsWith("x")) {
            //?????????
            return vipCodeService.getSaleLessonVipCodeDetail(userId, code);
//        } else {
//            return vipCodeService.getVipcodeDetail(code);
//        }
    }


}

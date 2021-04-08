package com.ifenghui.storybookapi.app.transaction.controller;

import com.ifenghui.storybookapi.api.response.exception.ExceptionResponse;
import com.ifenghui.storybookapi.app.app.response.BaseResponse;
import com.ifenghui.storybookapi.app.app.service.ConfigService;
import com.ifenghui.storybookapi.app.express.entity.ExpressCenterOrder;
import com.ifenghui.storybookapi.app.express.service.ExpressCenterOrderService;
import com.ifenghui.storybookapi.app.shop.entity.ShopExpress;
import com.ifenghui.storybookapi.app.shop.service.ShopExpressService;
import com.ifenghui.storybookapi.app.studyplan.entity.WeekPlanJoin;
import com.ifenghui.storybookapi.app.studyplan.service.WeekPlanJoinService;
import com.ifenghui.storybookapi.app.studyplan.service.WeekPlanUserRecordService;
import com.ifenghui.storybookapi.app.system.service.GeoipService;
import com.ifenghui.storybookapi.app.transaction.dao.AbilityPlanOrderDao;
import com.ifenghui.storybookapi.app.transaction.dao.UserAbilityPlanRelateDao;
import com.ifenghui.storybookapi.app.transaction.entity.abilityplan.AbilityPlanOrder;
import com.ifenghui.storybookapi.app.transaction.entity.abilityplan.AbilityPlanStyleAndPrice;
import com.ifenghui.storybookapi.app.transaction.entity.abilityplan.UserAbilityPlanRelate;
import com.ifenghui.storybookapi.app.transaction.response.*;
import com.ifenghui.storybookapi.app.transaction.service.AbilityPlanOrderService;
import com.ifenghui.storybookapi.app.transaction.service.UserAbilityPlanRelateService;
import com.ifenghui.storybookapi.app.user.dao.UserExtendDao;
import com.ifenghui.storybookapi.app.user.entity.User;
import com.ifenghui.storybookapi.app.user.entity.UserExtend;
import com.ifenghui.storybookapi.app.user.service.UserExtendService;
import com.ifenghui.storybookapi.app.user.service.UserService;
import com.ifenghui.storybookapi.exception.ApiDuplicateException;
import com.ifenghui.storybookapi.exception.ApiException;
import com.ifenghui.storybookapi.exception.ApiNotFoundException;
import com.ifenghui.storybookapi.style.*;
import com.ifenghui.storybookapi.util.ListUtil;
import com.ifenghui.storybookapi.util.VersionUtil;
import io.swagger.annotations.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;


/**
 * @Date: 2018/11/8 18:10
 * @Description:
 */
@Controller
@EnableAutoConfiguration
@RequestMapping("/api/abilityPlanBuy")
@Api(value = "购买宝宝会读（优能计划）", description = "购买宝宝会读（优能计划）")
public class AbilityPlanBuyController {

    @Autowired
    UserService userService;
    @Autowired
    ShopExpressService shopExpressService;
    @Autowired
    AbilityPlanOrderService abilityPlanOrderService;
    @Autowired
    UserExtendDao userExtendDao;
    @Autowired
    AbilityPlanOrderDao abilityPlanOrderDao;
    @Autowired
    WeekPlanJoinService weekPlanJoinService;
    @Autowired
    WeekPlanUserRecordService weekPlanUserRecordService;
    @Autowired
    UserExtendService userExtendService;
    @Autowired
    HttpServletRequest request;
    @Autowired
    ConfigService configService;
    @Autowired
    GeoipService geoipService;
    @Autowired
    UserAbilityPlanRelateDao userAbilityPlanRelateDao;
    @Autowired
    UserAbilityPlanRelateService userAbilityPlanRelateService;

    @Autowired
    ExpressCenterOrderService expressCenterOrderService;


    org.apache.log4j.Logger logger = org.apache.log4j.Logger.getLogger(AbilityPlanBuyController.class);

    /**
     * 2.13修改：
     * wsl 2019-3-19 价格区分了线上内容和线上线下全内容
     * 价格改成从数据库获取
     * @param onlineOnly
     * @return
     */
    @RequestMapping(value = "/get_ability_plan_price_211", method = RequestMethod.GET)
    @ResponseBody
    @ApiOperation(value = "宝宝会读价格 v2.11.0")
    public GetAbilityPlanPrice211Response getAbilityPlanPrice211(
            //是否是只有线上可用
            Integer onlineOnly) {

        Integer iosIsCheck = VersionUtil.getIosIsCheck(request, configService,geoipService);
        logger.info("--------iosIsCheck == " + iosIsCheck);
        GetAbilityPlanPrice211Response response = new GetAbilityPlanPrice211Response();

//        Integer price = 46000;



        Integer baobaoMagPrice=Integer.parseInt(configService.getConfigByKey("baobao_mag_price").getVal());
        Integer baobaoOnlyPrice=Integer.parseInt(configService.getConfigByKey("baobao_only_price").getVal());
        if(onlineOnly==null||onlineOnly==0){

            response.setInformation("若15号前完成购买，杂志当月发货，否则从购买后次月开始邮寄\n"+"有问题可咨询客服微信（storyship_01）");
            response.setPrice(baobaoMagPrice);
            response.setOnlineOnly(0);
        }else{
            response.setPrice(baobaoOnlyPrice);
            response.setOnlineOnly(1);
        }

        if (iosIsCheck == 1) {
            response.setPrice(79800);
        }

        response.setTargetValue(0);
        response.setIosSubPrice("subscription_auto_a_year");
        return response;

    }

    @RequestMapping(value = "/get_ability_plan_baobaoprice", method = RequestMethod.GET)
    @ResponseBody
    @ApiOperation(value = "宝宝会读权益 v2.11.0")
    public GetAbilityPlanPrivilegeResponse getBaobaoPrice() {
        GetAbilityPlanPrivilegeResponse response = new GetAbilityPlanPrivilegeResponse();

        //返回宝宝会读的两个产品价格
        Integer baobaoMagPrice=Integer.parseInt(configService.getConfigByKey("baobao_mag_price").getVal());
        Integer baobaoOnlyPrice=Integer.parseInt(configService.getConfigByKey("baobao_only_price").getVal());

        BaobaoPrice baobaoMagPlanPrice=new BaobaoPrice();
        baobaoMagPlanPrice.setPrice(baobaoMagPrice);
        baobaoMagPlanPrice.setTitle("宝宝会读");
        baobaoMagPlanPrice.setContent("每月两期，每年2月份只有上半月刊，下半月休刊一期（价值460）元");
        baobaoMagPlanPrice.setOnlineOnly(0);


        BaobaoPrice baobaoOnlyPlanPrice=new BaobaoPrice();
        baobaoOnlyPlanPrice.setPrice(baobaoOnlyPrice);
        baobaoOnlyPlanPrice.setTitle("线上专享权益（不含纸质绘本）");
        baobaoOnlyPlanPrice.setContent("[宝宝会读]全年52周学习计划（价值199元）[互动故事]100+（价值300）元");
        baobaoOnlyPlanPrice.setOnlineOnly(1);

        response.setBaobaoAllPrice(baobaoMagPlanPrice);
        response.setBaobaoOnlinePrice(baobaoOnlyPlanPrice);

        if(configService.isIosReview(request)){
            //如果是ios审核中的版本 只返回460
            response.setBaobaoOnlinePrice(null);
            baobaoMagPlanPrice=new BaobaoPrice();
            baobaoMagPlanPrice.setPrice(79800);
            baobaoMagPlanPrice.setTitle("宝宝会读");
            baobaoMagPlanPrice.setContent("每月两期，每年2月份只有上半月刊，下半月休刊一期（价值798）元");
            baobaoMagPlanPrice.setOnlineOnly(0);
            response.setBaobaoAllPrice(baobaoMagPlanPrice);
        }
        return response;
    }


        @RequestMapping(value = "/get_ability_plan_privilege", method = RequestMethod.GET)
    @ResponseBody
    @ApiOperation(value = "宝宝会读权益 v2.11.0")
    public GetAbilityPlanPrivilegeResponse getAbilityPlanPrivilege(
            @RequestHeader(value = "ssToken") String ssToken
    ) {
        //购买须知文案
        String productIntro = "1.本虚拟商品一经售出，不可退款、转让，敬请谅解。\n" +
                "2.如果遇到其他问题，请添加【故事飞船】客服微信号：storyship_01，咨询在线客服，感谢您的理解";


        GetAbilityPlanPrivilegeResponse response = new GetAbilityPlanPrivilegeResponse();
        Long userId = userService.checkAndGetCurrentUserId(ssToken);
        Integer iosIsCheck = VersionUtil.getIosIsCheck(request, configService,geoipService);
        logger.info("iosIsCheck == " + iosIsCheck);
        User user = userService.getUser(userId);
            Sort sort = new Sort(Sort.Direction.DESC, "id");
            List<UserAbilityPlanRelate> relateList = userAbilityPlanRelateDao.findUserAbilityPlanRelatesByUserId(userId.intValue(), sort);
            UserAbilityPlanRelate relate = null;
            if(relateList.size()>0){
                 relate = relateList.get(0);
            }
//        UserAbilityPlanRelate relate = userAbilityPlanRelateService.getLastestUserAbilityPlanRelateRecord(userId);
            Date startTime = null;
            Date endTime = null;
        if(relate != null){
            startTime = userAbilityPlanRelateService.getUserAbilityPlanRelateStartTime(userId, relate.getStartTime());
            endTime = relate.getEndTime();
        }

        //返回宝宝会读的两个产品价格
            Integer baobaoMagPrice=Integer.parseInt(configService.getConfigByKey("baobao_mag_price").getVal());
            Integer baobaoOnlyPrice=Integer.parseInt(configService.getConfigByKey("baobao_only_price").getVal());
            BaobaoPrice baobaoMagPlanPrice=new BaobaoPrice();
            baobaoMagPlanPrice.setPrice(baobaoMagPrice);
            baobaoMagPlanPrice.setTitle("宝宝会读套餐");
            baobaoMagPlanPrice.setContent("线上+线下宝宝阅读大提升");
            baobaoMagPlanPrice.setOnlineOnly(0);


            BaobaoPrice baobaoOnlyPlanPrice=new BaobaoPrice();
            baobaoOnlyPlanPrice.setPrice(baobaoOnlyPrice);
            baobaoOnlyPlanPrice.setTitle("线上畅读权益");
            baobaoOnlyPlanPrice.setContent("不含全年纸质绘本杂志");
            baobaoOnlyPlanPrice.setOnlineOnly(1);


            boolean isIosReview=configService.isIosReview(request);
        // 权益说明
            List<RightIntro> intros = this.getRightIntros(isIosReview);

        response.setTargetValue(0);
        response.setIosIsCheck(iosIsCheck);
        response.setPrice(46000);
        response.setUser(user);
        response.setContent("购买宝宝会读，轻松培养孩子阅读能力");
        response.setStarTime(startTime);
        response.setEndTime(endTime);
        response.setIntros(intros);
//        response.setIntrosOnline(introsOnline);
        response.setProductIntro(productIntro);
        response.setBaobaoAllPrice(baobaoMagPlanPrice);
        response.setBaobaoOnlinePrice(baobaoOnlyPlanPrice);

            if(configService.isIosReview(request)){
                //如果是ios审核中的版本 只返回460
                response.setBaobaoOnlinePrice(null);
                baobaoMagPlanPrice=new BaobaoPrice();
                baobaoMagPlanPrice.setPrice(79800);
                baobaoMagPlanPrice.setTitle("宝宝会读");
                baobaoMagPlanPrice.setContent("每月两期，每年2月份只有上半月刊，下半月休刊一期（价值798）元");
                baobaoMagPlanPrice.setOnlineOnly(0);
                response.setBaobaoAllPrice(baobaoMagPlanPrice);
            }
        return response;
    }

    @ApiOperation(value = "选择学习计划类型2.11.0")
    @RequestMapping(value = "/add_planType", method = RequestMethod.POST)
    @ResponseBody
    @Transactional
    BaseResponse addPlanType(
            @RequestHeader(value = "ssToken") String ssToken,
            @ApiParam(value = "选择的计划类型") @RequestParam WeekPlanStyle weekPlanStyle
    ) {

        Long userId = userService.checkAndGetCurrentUserId(ssToken);
        User user = userService.getUser(userId);
        int priceId = 0;
        if (WeekPlanStyle.TWO_FOUR == weekPlanStyle) {
            priceId = 40;
        } else if (WeekPlanStyle.FOUR_SIX == weekPlanStyle) {
            priceId = 41;
        }
        UserExtend userExtend = userExtendService.findUserExtendByUserId(userId);
        userExtend.setWeekPlanType(weekPlanStyle.getId());
        userExtend.setWeekPlanTypeNew(weekPlanStyle.getId());
        userExtendDao.save(userExtend);
        //更改订单状态
        Sort sort = new Sort(Sort.Direction.ASC, "priceId");
        List<AbilityPlanOrder> orderList = abilityPlanOrderDao.getAbilityPlanOrdersByUserIdAndPlanType(userId.intValue(), WeekPlanStyle.DEFAULT.getId(), 1, sort);
        for (AbilityPlanOrder o : orderList) {
            o.setAbilityPlanStyle(AbilityPlanStyle.getById(weekPlanStyle.getId()));
            o.setPriceId(priceId);
            abilityPlanOrderDao.save(o);
        }
        //plantype=0成功订单（未推送记录）
        if (orderList.size() > 0 && user.getIsAbilityPlan() == 1) {
            this.pushRecord(orderList.get(0).getId(),userId.intValue(), weekPlanStyle, priceId);
        }
        return new BaseResponse();
    }

    /***
     * 创建参加记录和推送记录
     */
    private void pushRecord(Integer orderId,Integer userId, WeekPlanStyle weekPlanStyle, Integer priceId) {

        //创建参加记录，推送到此时间的记录
        weekPlanJoinService.createWeekPlanJoin(userId, weekPlanStyle);
        weekPlanJoinService.updateWeekPlanJoinBuyNum(orderId,userId, weekPlanStyle, priceId);
        //推送到至今的记录
        UserAbilityPlanRelate relate = userAbilityPlanRelateService.getUserAbilityPlanRelateByUserIdAndType(userId, 0);
        if (relate == null) {
            throw new ApiNotFoundException("记录不存在");
        }
        long weeks = (System.currentTimeMillis() - relate.getStartTime().getTime()) / (1000 * 60 * 60 * 24 * 7) + 1;
        logger.info(weeks);
        for (int i = 0; i < weeks; i++) {
            weekPlanUserRecordService.sendWeekPlanUserRecordByUserIdAndPlanTypeToSomeone(userId, weekPlanStyle);
        }
        //更改关联de 计划类型
        userAbilityPlanRelateService.updateAbilityRelateByUserId(userId, weekPlanStyle);
    }


    @ApiOperation(value = "查看是否购买（弹出学习计划选择）v2.11.0")
    @RequestMapping(value = "/get_ability_opt", method = RequestMethod.GET)
    @ResponseBody
    StudyPlanOptionResponse getAbilityOpt(
            @RequestHeader(value = "ssToken") String ssToken
    ) {

        Long userId = userService.checkAndGetCurrentUserId(ssToken);
        UserExtend userExtend = userExtendService.findUserExtendByUserId(userId);
        StudyPlanOptionResponse response = new StudyPlanOptionResponse();
        boolean isHasSuccessOrder = abilityPlanOrderService.isHasSuccessOrder(userId.intValue());
        List<WeekPlanStyle> weekPlanStyles = new ArrayList<>();
        //有(2-4或4-6)成功订单 或者选择过年龄段
        if ( (isHasSuccessOrder || userExtend.getWeekPlanTypeNew() > 0) && userExtend.getWeekPlanType() >0){
            response.setIsOption(1);
        } else {
            response.setIsOption(0);
            weekPlanStyles.add(WeekPlanStyle.TWO_FOUR);
            weekPlanStyles.add(WeekPlanStyle.FOUR_SIX);
            response.setWeekPlanStyles(weekPlanStyles);
        }
        return response;
    }

    @RequestMapping(value = "/get_plan_buy", method = RequestMethod.GET)
    @ResponseBody
    @ApiOperation(value = "获取是否购买宝宝会读（优能计划）")
    public GetPlanBuyResponse getCanBuy(
            @RequestHeader(value = "ssToken") String ssToken
    ) {

        GetPlanBuyResponse response = new GetPlanBuyResponse();

        Long userId = userService.checkAndGetCurrentUserId(ssToken);
        WeekPlanJoin planJoin24 = weekPlanJoinService.getWeekPlanJoinByUserIdAndType(userId.intValue(), WeekPlanStyle.TWO_FOUR);
        WeekPlanJoin planJoin46 = weekPlanJoinService.getWeekPlanJoinByUserIdAndType(userId.intValue(), WeekPlanStyle.FOUR_SIX);

        if (planJoin24 == null || planJoin24.getBuyNum() < 52) {
            response.setCanBuyTwoFour(1);
        } else {
            response.setCanBuyTwoFour(0);
        }
        if (planJoin46 == null || planJoin46.getBuyNum() < 52) {
            response.setCanBuyFourSix(1);
        } else {
            response.setCanBuyFourSix(0);
        }

        return response;
    }

    @RequestMapping(value = "/get_ability_plan_price_list", method = RequestMethod.GET)
    @ResponseBody
    @ApiOperation(value = "宝宝会读（优能计划）价格列表 v2.10.0")
    public GetAbilityPlanPriceListResponse getAbilityPlanPriceList(
            @RequestHeader(value = "ssToken") String ssToken,
            @ApiParam(value = "类型 1 2-4  2 4-6") @RequestParam() WeekPlanStyle weekPlanStyle
    ) {

        GetAbilityPlanPriceListResponse response = new GetAbilityPlanPriceListResponse();

        Long userId = userService.checkAndGetCurrentUserId(ssToken);
        Integer iosIsCheck = VersionUtil.getIosIsCheck(request, configService,geoipService);

        logger.info("iosIsCheck == " + iosIsCheck);
        response.setIosIsCheck(iosIsCheck);
        List<AbilityPlanPrice> abilityPlanPrices = new ArrayList<>();
        Boolean isBuyMonth = false;
        //判断是否购买过单月四周2-4
        if (weekPlanStyle == null) {
            isBuyMonth = false;
        } else if (weekPlanStyle == WeekPlanStyle.TWO_FOUR) {
            isBuyMonth = abilityPlanOrderService.isBuyMonthAbilityPlan(userId, 48);
        } else if (weekPlanStyle == WeekPlanStyle.FOUR_SIX) {
            isBuyMonth = abilityPlanOrderService.isBuyMonthAbilityPlan(userId, 49);
        }
        if (isBuyMonth) {

            int price = AbilityPlanCodeStyle.TWO_FOUR_OTHER.getRealPrice();
            if (iosIsCheck == 1) {
                price = 79800;
            }
            WeekPlanJoin planJoin = weekPlanJoinService.getWeekPlanJoinByUserIdAndType(userId.intValue(), weekPlanStyle);
            int otherNum = 52 - planJoin.getBuyNum();
            String content = "剩余<font size='19' color='red'>" + otherNum + "</font>周";
            AbilityPlanPrice abilityPlanOtherPrice = new AbilityPlanPrice(content, "剩余" + otherNum + "周", "赠送11个月APP畅读权限", AbilityPlanCodeStyle.TWO_FOUR_OTHER.getDays(), AbilityPlanCodeStyle.TWO_FOUR_OTHER.getPrice(), weekPlanStyle.getId(), iosIsCheck, price, otherNum, 0,0);
            abilityPlanOtherPrice.setPre("剩余");
            abilityPlanOtherPrice.setLast("周");

            abilityPlanPrices.add(abilityPlanOtherPrice);
        } else {
            int price = AbilityPlanCodeStyle.FOUR_SIX_YEAR.getRealPrice();
            if (iosIsCheck == 1) {
                price = 79800;
            }
            String content = "全年<font size='19' color='red'>52</font>周";
            AbilityPlanPrice abilityPlanYearPrice = new AbilityPlanPrice(content, "全年52周", "赠送1年APP畅读权限", AbilityPlanCodeStyle.FOUR_SIX_YEAR.getDays(), AbilityPlanCodeStyle.FOUR_SIX_YEAR.getPrice(), weekPlanStyle.getId(), iosIsCheck, price, 52, 0,0);

            abilityPlanYearPrice.setPre("全年");
            abilityPlanYearPrice.setLast("周");
            abilityPlanPrices.add(abilityPlanYearPrice);

        }
        response.setAbilityPlanPriceList(abilityPlanPrices);

        User user = userService.getUser(userId);
        if (user.getIsAbilityPlan() == 1) {
            response.setIsBuyAbilityPlan(1);
        } else {
            response.setIsBuyAbilityPlan(0);
        }

        response.setTargetValue(0);
        response.setWeekPlanStyle(weekPlanStyle.getId());
        return response;
    }

    @RequestMapping(value = "/get_ability_plan_price", method = RequestMethod.GET)
    @ResponseBody
    @ApiOperation(value = "宝宝会读（优能计划）价格 v2.9.0")
    public GetAbilityPlanPriceResponse getAbilityPlanPrice(
            @RequestHeader(value = "ssToken") String ssToken
    ) {

        GetAbilityPlanPriceResponse response = new GetAbilityPlanPriceResponse();

        Long userId = userService.checkAndGetCurrentUserId(ssToken);
        UserExtend userExtend = userExtendService.findUserExtendByUserId(userId);
        WeekPlanStyle weekPlanStyle = WeekPlanStyle.getById(userExtend.getWeekPlanType());
        if (weekPlanStyle == WeekPlanStyle.DEFAULT) {
            weekPlanStyle = WeekPlanStyle.TWO_FOUR;
        }
        Integer iosIsCheck = VersionUtil.getIosIsCheck(request, configService,geoipService);

        AbilityPlanPrice abilityPlanYearPrice = new AbilityPlanPrice("全年52周", "全年52周", "赠送一年APP畅读权限", AbilityPlanCodeStyle.FOUR_SIX_YEAR.getDays(), AbilityPlanCodeStyle.FOUR_SIX_YEAR.getPrice(), weekPlanStyle.getId(), iosIsCheck, AbilityPlanCodeStyle.FOUR_SIX_YEAR.getRealPrice(), 52, 0,0);
        response.setAbilityPlanPrice(abilityPlanYearPrice);

        User user = userService.getUser(userId);
        if (user.getIsAbilityPlan() == 1) {
            response.setIsBuyAbilityPlan(1);
        } else {
            response.setIsBuyAbilityPlan(0);
        }
        return response;
    }

    @RequestMapping(value = "/create_ability_plan_order_211", method = RequestMethod.POST)
    @ResponseBody
    @ApiResponses({
            @ApiResponse(code = 209, message = "优惠券已使用", response = ExceptionResponse.class),
            @ApiResponse(code = 210, message = "优惠券已过期", response = ExceptionResponse.class)
    })
    @ApiOperation(value = "创建购买优能计划订单号2.11.0")
    public GetOneOrderResponse getAbilityPlanOrder211(
            @RequestHeader(value = "ssToken") String ssToken,
            @ApiParam(value = "收货人") @RequestParam(required = false) String receiver,
            @ApiParam(value = "电话号码") @RequestParam(required = false) String phone,
            @ApiParam(value = "地址") @RequestParam(required = false) String address,
            @ApiParam(value = "区域") @RequestParam(required = false) String area,
            @ApiParam(value = "优惠券id（多个id逗号分割）", defaultValue = "", required = false) @RequestParam(required = false, defaultValue = "") List<Integer> couponIdsStr,
            @ApiParam(value = "是否只包含线上部分，1：只包含线上") @RequestParam(required = false) Integer onlineOnly
    ) {
        couponIdsStr = ListUtil.removeNull(couponIdsStr);
        GetOneOrderResponse response = new GetOneOrderResponse();
        Long userId = userService.checkAndGetCurrentUserId(ssToken);

        if(onlineOnly==null){
            onlineOnly=0;
        }

        Integer baobaoPrice=Integer.parseInt(configService.getConfigByKey("baobao_mag_price").getVal());
        if(onlineOnly==1){
            baobaoPrice=Integer.parseInt(configService.getConfigByKey("baobao_only_price").getVal());
        }

        int month=12;
        int planWeek=52;

        AbilityPlanOrder abilityPlanOrder = abilityPlanOrderService.createAbilityPlanOrder(baobaoPrice,month, planWeek, userId, couponIdsStr,onlineOnly);
        if(onlineOnly==null||onlineOnly==0){
            shopExpressService.addExpress(abilityPlanOrder.getId(), receiver, phone, address, area, ExpressStyle.DEFAULT_NULL, "", ExpressStatusStyle.HAS_NO_DELIVERY, OrderStyle.ABILITY_PLAN_ORDER);
        }
        StandardOrder standardOrder = new StandardOrder(abilityPlanOrder);
        response.setStandardOrder(standardOrder);
        return response;
    }

    @RequestMapping(value = "/create_ability_plan_order", method = RequestMethod.POST)
    @ResponseBody
    @ApiResponses({
            @ApiResponse(code = 209, message = "优惠券已使用", response = ExceptionResponse.class),
            @ApiResponse(code = 210, message = "优惠券已过期", response = ExceptionResponse.class)
    })
    @ApiOperation(value = "创建购买优能计划订单号")
    public GetOneOrderResponse getAbilityPlanOrder(
            @RequestHeader(value = "ssToken") String ssToken,
            @ApiParam(value = "计划类型 1 2-4  2 4-6 ") @RequestParam Integer planStyle,
            @ApiParam(value = "购买周数") @RequestParam(required = false) Integer buyNum,
            @ApiParam(value = "收货人") @RequestParam String receiver,
            @ApiParam(value = "电话号码") @RequestParam String phone,
            @ApiParam(value = "地址") @RequestParam String address,
            @ApiParam(value = "区域") @RequestParam String area,
            @ApiParam(value = "优惠券id（多个id逗号分割）", defaultValue = "", required = false) @RequestParam(required = false, defaultValue = "") List<Integer> couponIdsStr
    ) {
        couponIdsStr = ListUtil.removeNull(couponIdsStr);
        GetOneOrderResponse response = new GetOneOrderResponse();
        Long userId = userService.checkAndGetCurrentUserId(ssToken);

        if (buyNum == null) {
            buyNum = 0;
        }
        AbilityPlanStyleAndPrice abilityPlanStyleAndPrice = abilityPlanOrderService.getAbilityPlanStyleAndPriceId(planStyle, buyNum);

        AbilityPlanOrder abilityPlanOrder = abilityPlanOrderService.createAbilityPlanOrder(abilityPlanStyleAndPrice.getPriceId(), abilityPlanStyleAndPrice.getAbilityPlanCodeStyle(), userId, couponIdsStr);

        shopExpressService.addExpress(abilityPlanOrder.getId(), receiver, phone, address, area, ExpressStyle.DEFAULT_NULL, "", ExpressStatusStyle.HAS_NO_DELIVERY, OrderStyle.ABILITY_PLAN_ORDER);
        StandardOrder standardOrder = new StandardOrder(abilityPlanOrder);
        response.setStandardOrder(standardOrder);
        return response;
    }

    @RequestMapping(value = "/balance_buy_ability_plan", method = RequestMethod.POST)
    @ResponseBody
    @ApiOperation(value = "余额购买宝宝会读（优能计划）")
    public BuyOrderByBalanceResponse buyAbilityPlanByBalance(
            @RequestHeader(value = "ssToken") String ssToken,
            @ApiParam(value = "订单号") @RequestParam Integer orderId,
            @ApiParam(value = "钱包类型") @RequestParam WalletStyle walletStyle
    ) throws ApiException {

        Long userId = userService.checkAndGetCurrentUserId(ssToken);

        BuyOrderByBalanceResponse response = new BuyOrderByBalanceResponse();

        OrderPayStyle orderPayStyle = OrderPayStyle.IOS_BLANCE;

        if (walletStyle.equals(WalletStyle.ANDROID_WALLET)) {
            orderPayStyle = OrderPayStyle.ANDRIOD_BLANCE;
        }
        AbilityPlanOrder abilityPlanOrder = abilityPlanOrderService.buyAbilityPlanByBalance(userId, orderId, orderPayStyle, walletStyle);
        response.setStandardOrder(new StandardOrder(abilityPlanOrder));
        return response;
    }

//    /***
//     * 获得权益列表宝宝会读
//     * @return
//     */
//    private List<RightIntro> getRightIntros(boolean isOnline) {
//
//        List<RightIntro> intros = new ArrayList<>();
//        RightIntro r1 = new RightIntro("全年23本精品绘本", "每月15号左右邮寄，1次两刊（2月份为单期）", 46000, "","http://storybook.oss-cn-hangzhou.aliyuncs.com/abilityplan/privilege/books.png");
//        RightIntro r2 = new RightIntro("全年学习计划", "52周学习计划，科学提升宝宝阅读能力", 19900, "","http://storybook.oss-cn-hangzhou.aliyuncs.com/abilityplan/privilege/plan.png");
//        RightIntro r3 = new RightIntro("精品互动故事 100+", "交互式阅读新体验，让孩子从小爱上阅读", 30000, "","http://storybook.oss-cn-hangzhou.aliyuncs.com/abilityplan/privilege/interactive.png");
//        RightIntro r4 = new RightIntro("故事音频 100+", "精品故事免费听，宝妈哄睡好帮手", 9900, "","http://storybook.oss-cn-hangzhou.aliyuncs.com/abilityplan/privilege/audio.png");
//        RightIntro r5 = new RightIntro("飞船阅读课", "以故事为中心，延展搭建的科学课程体系", 29800, "","http://storybook.oss-cn-hangzhou.aliyuncs.com/abilityplan/privilege/spacecraft.png");
//        RightIntro r6 = new RightIntro("大咖专家讲座", "知名幼教专家，传授专业育儿知识", 9900, "","http://storybook.oss-cn-hangzhou.aliyuncs.com/abilityplan/privilege/lecture.png");
//        if(!isOnline){
//            intros.add(r1);
//        }
//
//        intros.add(r2);
//        intros.add(r3);
//        intros.add(r4);
//        intros.add(r5);
//        intros.add(r6);
//        return intros;
//    }

    /***
     * 获得权益列表宝宝会读
     * @return
     */
    private List<RightIntro> getRightIntros(boolean iosReview) {

        List<RightIntro> intros = new ArrayList<>();
        RightIntro r1 = new RightIntro("精品绘本（460元套餐专享）", "每月15号左右邮寄，1次两刊（2月份为单期）", 46000, "","http://storybook.oss-cn-hangzhou.aliyuncs.com/abilityplan/privilege/books.png");
        RightIntro r2 = new RightIntro("全年学习计划", "52周学习计划，科学提升宝宝阅读能力", 19900, "","http://storybook.oss-cn-hangzhou.aliyuncs.com/abilityplan/privilege/plan.png");
        RightIntro r3 = new RightIntro("精品互动故事 100+", "交互式阅读新体验，让孩子从小爱上阅读", 30000, "","http://storybook.oss-cn-hangzhou.aliyuncs.com/abilityplan/privilege/interactive.png");
        RightIntro r4 = new RightIntro("故事音频 100+", "精品故事免费听，宝妈哄睡好帮手", 9900, "","http://storybook.oss-cn-hangzhou.aliyuncs.com/abilityplan/privilege/audio.png");
        RightIntro r5 = new RightIntro("飞船阅读课", "以故事为中心，延展搭建的科学课程体系", 29800, "","http://storybook.oss-cn-hangzhou.aliyuncs.com/abilityplan/privilege/spacecraft.png");
        RightIntro r6 = new RightIntro("大咖专家讲座", "知名幼教专家，传授专业育儿知识", 9900, "","http://storybook.oss-cn-hangzhou.aliyuncs.com/abilityplan/privilege/lecture.png");
        if(iosReview){
            intros.add(r1);
        }

        intros.add(r2);
        intros.add(r3);
        intros.add(r4);
        intros.add(r5);
        intros.add(r6);
        return intros;
    }

//    @RequestMapping(value = "/ability_express_output_expresscenter", method = RequestMethod.POST)
//    @ResponseBody
//    @ApiOperation(value = "宝宝会读订单导入到物流中心")
//    public BaseResponse buyAbilityPlanToExpressCenter(
//            @RequestHeader(value = "ssToken") String ssToken
//    ) throws ApiException {
//
//        AbilityPlanOrder finder=new AbilityPlanOrder();
//        //查询已成交的含有物流的订单
//        finder.setStatus(1);
//        finder.setOnlineOnly(0);
//        finder.setIsDel(0);
//        finder.setIsTest(0);
//        Page<AbilityPlanOrder> page= abilityPlanOrderService.findAll(finder,new PageRequest(0,20,new Sort(Sort.Direction.ASC,"id")));
//        outputExpressByOrder(page.getContent());
//        for(int i=1;i<page.getTotalPages();i++){
//            page= abilityPlanOrderService.findAll(finder,new PageRequest(i,20,new Sort(Sort.Direction.ASC,"id")));
//            outputExpressByOrder(page.getContent());
//        }
//
//
//        return new BaseResponse();
//    }

//    private void outputExpressByOrder(List<AbilityPlanOrder> abilityPlanOrders){
//        //查询物流
//        for(AbilityPlanOrder abilityPlanOrder:abilityPlanOrders){
//            ShopExpress shopExpress= shopExpressService.findShopExpressByOrderId(abilityPlanOrder.getId(),OrderStyle.ABILITY_PLAN_ORDER);
//            if(shopExpress!=null){
//                ExpressCenterOrder order=new ExpressCenterOrder(abilityPlanOrder,shopExpress);
//                try{
//                    expressCenterOrderService.addExpressCenterOrder(order);
//                }catch (ApiDuplicateException e){
//                    logger.info(e.getMessage());
//                }
//
//            }
//        }
//    }
}

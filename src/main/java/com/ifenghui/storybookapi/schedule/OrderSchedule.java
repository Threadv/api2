package com.ifenghui.storybookapi.schedule;

import com.ifenghui.storybookapi.adminapi.controlleradmin.code.entity.SmsMessageDetail;
import com.ifenghui.storybookapi.adminapi.controlleradmin.code.service.SmsMessageDetailService;
import com.ifenghui.storybookapi.app.app.response.BaseResponse;
import com.ifenghui.storybookapi.app.presale.entity.PreSaleCode;
import com.ifenghui.storybookapi.app.presale.service.PreSaleCodeService;
import com.ifenghui.storybookapi.app.studyplan.service.WeekPlanUserRecordService;
import com.ifenghui.storybookapi.app.system.service.ElasticService;
import com.ifenghui.storybookapi.app.system.service.MessageService;
import com.ifenghui.storybookapi.app.transaction.service.CouponStoryExchangeUserService;
import com.ifenghui.storybookapi.app.transaction.service.UserAbilityPlanRelateService;
import com.ifenghui.storybookapi.app.transaction.service.UserSvipService;
import com.ifenghui.storybookapi.app.transaction.service.order.OrderMixService;
import com.ifenghui.storybookapi.app.user.entity.User;
import com.ifenghui.storybookapi.exception.ApiException;
import com.ifenghui.storybookapi.exception.ApiNotTokenException;
import com.ifenghui.storybookapi.style.VipGoodsStyle;
import io.swagger.annotations.ApiOperation;
import net.javacrumbs.shedlock.core.SchedulerLock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.text.SimpleDateFormat;
import java.util.*;


@Component
@EnableScheduling
public class OrderSchedule {
    @Autowired
    OrderMixService orderMixService;

    @Autowired
    UserAbilityPlanRelateService userAbilityPlanRelateService;

    @Autowired
    UserSvipService userSvipService;

    @Autowired
    WeekPlanUserRecordService weekPlanUserRecordService;

    @Autowired
    CouponStoryExchangeUserService couponStoryExchangeUserService;

    @Autowired
    ElasticService elasticService;

    @Autowired
    PreSaleCodeService preSaleCodeService;

    @Autowired
    MessageService messageService;

    @Autowired
    SmsMessageDetailService smsMessageDetailService;
    /**
     * ????????????????????????????????????????????????
     */
//    @ApiOperation(value = "?????????????????????????????????????????????")
    @Scheduled(cron = "0 0 1 * * ?")
//    @Scheduled(cron = "0/30 * * * * ? ")
    @SchedulerLock(name = "userSvipServiceCheckCoupons",lockAtLeastFor = 1*1000,lockAtMostFor = 3*1000)
    void userSvipServiceCheckCoupons() {
        System.out.println(">>>>>>>>>>>>> Scheduled_CheckUserAbilityPlan ... ");
        try {
            userAbilityPlanRelateService.resetAbilityPlanUser();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    /**
     * ???????????????????????????????????????
     */
//    @ApiOperation(value = "????????????svip")
//    @RequestMapping(value = "/unset", method = RequestMethod.GET)
    @Scheduled(cron = "0 0 12 * * ?") // ????????????????????????
//    @Scheduled(cron = "0/30 * * * * ? ")
//    @ResponseBody
    @SchedulerLock(name = "userSvipServiceCoupons",lockAtLeastFor = 1*1000,lockAtMostFor = 3*1000)
    void userSvipServiceCoupons() throws ApiNotTokenException {
        System.out.println(">>>>>>>>>>>>> Scheduled_CheckExpiredStoryCoupons ... ");
        try {
            userSvipService.resetSvipUser();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

//    @ApiOperation(value = "????????????????????????????????????????????????")
//    @RequestMapping(value = "/dayTaskSendWeekPlanUserRecord", method = RequestMethod.GET)
//    @ResponseBody
    @Scheduled(cron = "0 1 0 * * ?")
//    @Scheduled(cron = "* * * * * ?")
    @SchedulerLock(name = "dayTaskSendWeekPlanUserRecord",lockAtLeastFor = 1*1000,lockAtMostFor = 3*1000)
    void dayTaskSendWeekPlanUserRecord() throws ApiNotTokenException {
        System.out.println(">>>>>>>>>>>>> dayTaskSendWeekPlanUserRecord ... ");
        /**
         * ????????????????????????
         */
        Date now = new Date();
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(now);
        int dayNumber = calendar.get(Calendar.DAY_OF_WEEK);
        if (dayNumber == 2) {
            try {
                weekPlanUserRecordService.dayTaskSendWeekPlanUserRecord();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    /**???5????????????*/
    @Scheduled(cron = "0 */2 * * * ?")
    @ApiOperation(value = "??????????????????", notes = "")
    @SchedulerLock(name = "timingCancelOrder",lockAtLeastFor = 1*1000,lockAtMostFor = 3*1000)
    @RequestMapping(value="/timingCancelOrder",method = RequestMethod.GET)
    public void timingCancelOrder(
    )throws ApiException {

        orderMixService.timingCancelOrder();
    }

    /**
     * ???????????????????????????????????????
     */
    @Scheduled(cron = "0 0 12 * * ?") // ????????????????????????
    @SchedulerLock(name = "dayTaskCheckExpireStoryCoupons",lockAtLeastFor = 1*1000,lockAtMostFor = 3*1000)
    void dayTaskCheckExpireStoryCoupons() throws ApiNotTokenException {
        System.out.println(">>>>>>>>>>>>> Scheduled_CheckExpiredStoryCoupons ... ");
        try {
            couponStoryExchangeUserService.dayTaskCheckExpireStoryCoupon();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    @Scheduled(cron = "30 0 0 * * ?") // ????????????????????????
    @SchedulerLock(name = "dayTaskClearStatusisZeroStory",lockAtLeastFor = 1*1000,lockAtMostFor = 3*1000)
    void dayTaskClearStatusisZeroStory() throws ApiNotTokenException {
        System.out.println(">>>>>>>>>>>>> Scheduled_CheckExpiredStoryCoupons ... ");
        try {
            elasticService.cleanStoryByStatus0();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * ???????????????????????????
     */
        @Scheduled(cron = "0 0 1 * * ?")
//    @Scheduled(cron = "*/5 * * * * ?")
        @SchedulerLock(name = "codeResetTime",lockAtLeastFor = 1*1000,lockAtMostFor = 3*1000)
    public void codeResetTime() {
            preSaleCodeService.setExpire();
    }

    /**
     * ?????????????????????????????????????????????
     */
//    @Scheduled(cron = "0 0 9 * * ?") //??????????????????
//    public void sendReminderSMS(){
//        Date date = new Date();
//        Calendar cal = Calendar.getInstance();
//        cal.add(Calendar.DATE,2);
//        List<PreSaleCode> codes = new ArrayList<>();
//        codes = preSaleCodeService.getCodesByEndTime(VipGoodsStyle.ABILITY_PLAN_YEAR_DEFAULT.getId() ,cal.getTime());
//        List<PreSaleCode> codes1 = preSaleCodeService.getCodesByEndTime(VipGoodsStyle.ABILITY_PLAN_15_MONTH_DEFAULT.getId() ,cal.getTime());
//        if (codes1.size()>0){
//            for (PreSaleCode c:codes1){
//                codes.add(c);
//            }
//        }
//        if (codes.size() >0){
//            for (PreSaleCode c:codes){
//                SmsMessageDetail smsMessageDetail=smsMessageDetailService.findOneByCode(c.getCode());
//                if (smsMessageDetail != null && smsMessageDetail.getPhone() != null){
//                    Map<String,String> map=new HashMap<>();
//                    map.put("code",c.getCode());
//                    SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd");
//                    String endTime = sf.format(c.getEndTime());
//                    map.put("endTime",endTime);
//                    messageService.sendSms(smsMessageDetail.getPhone(),"SMS_163433555",map);
//                }
//            }
//        }
//
//    }
}

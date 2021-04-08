package com.ifenghui.storybookapi.app.transaction.controller;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.ifenghui.storybookapi.api.response.exception.ExceptionResponse;
import com.ifenghui.storybookapi.app.presale.entity.PreSaleCode;
import com.ifenghui.storybookapi.app.presale.entity.SaleGoodsStyle;
import com.ifenghui.storybookapi.app.presale.service.PreSaleCodeService;
import com.ifenghui.storybookapi.app.story.service.LessonService;
import com.ifenghui.storybookapi.app.story.service.SerialStoryService;
import com.ifenghui.storybookapi.app.story.service.StoryService;
import com.ifenghui.storybookapi.app.transaction.dao.FixPayOrderDao;
import com.ifenghui.storybookapi.app.transaction.dao.OrderMixDao;
import com.ifenghui.storybookapi.app.transaction.dao.VPayOrderDao;
import com.ifenghui.storybookapi.app.transaction.entity.FixPayOrder;
import com.ifenghui.storybookapi.app.transaction.entity.OrderMix;
import com.ifenghui.storybookapi.app.transaction.entity.VPayOrder;
import com.ifenghui.storybookapi.app.transaction.response.GetCodesFenxiaoResponse;
import com.ifenghui.storybookapi.app.transaction.service.*;
import com.ifenghui.storybookapi.app.transaction.service.lesson.PayLessonOrderService;
import com.ifenghui.storybookapi.app.transaction.service.order.OrderMixService;
import com.ifenghui.storybookapi.app.transaction.service.order.OrderService;
import com.ifenghui.storybookapi.app.user.entity.UserAccount;
import com.ifenghui.storybookapi.app.user.service.UserAccountService;
import com.ifenghui.storybookapi.app.user.service.UserService;
import com.ifenghui.storybookapi.app.wallet.response.GetCodesResponse;
import com.ifenghui.storybookapi.app.wallet.response.GetVipCodeDetailResponse;
import com.ifenghui.storybookapi.app.wallet.response.SubscribeByCodeResponse;
import com.ifenghui.storybookapi.app.wallet.service.PayService;
import com.ifenghui.storybookapi.exception.ApiActiveCodeFailException;
import com.ifenghui.storybookapi.exception.ApiException;
import com.ifenghui.storybookapi.exception.ApiNotFoundException;
import com.ifenghui.storybookapi.style.OrderStyle;
import com.ifenghui.storybookapi.util.HttpRequest;
import io.swagger.annotations.*;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.core.env.Environment;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.Date;
import java.util.List;

@Controller
@EnableAutoConfiguration
@RequestMapping("/api/fix/code")
@Api(value="兑换码",description = "数据修复")
public class FixController {

    Logger logger = Logger.getLogger(FixController.class);

    public int test = 1;

    @Autowired
    private Environment env;


    @Autowired
    FixPayOrderDao vPayOrderDao;

    @Autowired
    OrderMixDao orderMixDao;

    @RequestMapping(value = "/fixOrderMix", method = RequestMethod.GET)
    @ResponseBody
    @ApiOperation(value = "数据修复", notes = "")
//    @ApiResponses({@ApiResponse(code=204,message="余额不足",response = ExceptionResponse.class)})
    public GetCodesResponse fixOrderMix(
    ) throws ApiException {

        List<FixPayOrder> lessonOrderList = vPayOrderDao.getVPayOrdersByIdBetween(600000000L, 700000000L);
        this.viewTomix(lessonOrderList);
        List<FixPayOrder> serialOrderList = vPayOrderDao.getVPayOrdersByIdBetween(700000000L, 800000000L);
        this.viewTomix(serialOrderList);
        List<FixPayOrder> subscriptionList = vPayOrderDao.getVPayOrdersByIdBetween(800000000L, 900000000L);
        this.viewTomix(subscriptionList);

        List<FixPayOrder> storyOrderList1 = vPayOrderDao.getVPayOrdersByIdBetween(900000000L, 900001000L);
        this.viewTomix(storyOrderList1);

        List<FixPayOrder> storyOrderList2 = vPayOrderDao.getVPayOrdersByIdBetween(900001000L, 900002000L);
        this.viewTomix(storyOrderList2);

        List<FixPayOrder> storyOrderList3 = vPayOrderDao.getVPayOrdersByIdBetween(900002000L, 900003000L);
        this.viewTomix(storyOrderList3);

        List<FixPayOrder> storyOrderList4 = vPayOrderDao.getVPayOrdersByIdBetween(900003000L, 900004000L);
        this.viewTomix(storyOrderList4);

        List<FixPayOrder> storyOrderList5 = vPayOrderDao.getVPayOrdersByIdBetween(900004000L, 900005000L);
        this.viewTomix(storyOrderList5);

        List<FixPayOrder> storyOrderList6 = vPayOrderDao.getVPayOrdersByIdBetween(900005000L, 900006000L);
        this.viewTomix(storyOrderList6);

        List<FixPayOrder> storyOrderList7 = vPayOrderDao.getVPayOrdersByIdBetween(900006000L, 900007000L);
        this.viewTomix(storyOrderList7);

        List<FixPayOrder> storyOrderList8 = vPayOrderDao.getVPayOrdersByIdBetween(900007000L, 900008000L);
        this.viewTomix(storyOrderList8);

        List<FixPayOrder> storyOrderList9 = vPayOrderDao.getVPayOrdersByIdBetween(900008000L, 900009000L);
        this.viewTomix(storyOrderList9);

        List<FixPayOrder> storyOrderList10 = vPayOrderDao.getVPayOrdersByIdBetween(900009000L, 900010000L);
        this.viewTomix(storyOrderList10);
        return null;
    }

    private void viewTomix(List<FixPayOrder> vPayOrders){

        OrderMix orderMix;
        for(FixPayOrder vPayOrder:vPayOrders){
            logger.info("类型：" + vPayOrder.getType() + "订单号：" + vPayOrder.getOrderId());

            orderMix=orderMixDao.findOneByOrderIdAndType(vPayOrder.getOrderId(),vPayOrder.getType());
            test ++;
            logger.info("循环次数=" + test);
            if(orderMix==null){
                orderMix=new OrderMix();
                orderMix.setStatus(vPayOrder.getStatus());
                orderMix.setIsDel(vPayOrder.getIsDel());
                if(vPayOrder.getIsDel()==null){
                    orderMix.setIsDel(0);
                }
                orderMix.setCreateTime(vPayOrder.getCreateTime());
                orderMix.setUserId(vPayOrder.getUserId());
                orderMix.setCreateTime(vPayOrder.getCreateTime());
                if(vPayOrder.getCreateTime()==null){
                    continue;
                }
                orderMix.setOrderId(vPayOrder.getOrderId());
                orderMix.setOrderType(vPayOrder.getType());
            }
            orderMixDao.save(orderMix);
        }
    }


}

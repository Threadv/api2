package com.ifenghui.storybookapi.app.presale.controller;




import com.ifenghui.storybookapi.app.app.response.BaseResponse;
import com.ifenghui.storybookapi.app.presale.entity.PreSaleLog;
import com.ifenghui.storybookapi.app.presale.service.LogService;
import com.ifenghui.storybookapi.util.IpUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;

@CrossOrigin(origins = "*", maxAge = 3600)//内部开发暂时支持跨域
@Controller
@EnableAutoConfiguration
@Api(value = "预售商品", description = "预售商品")
@RequestMapping("/sale/pre_sale/log")
public class ActionLogController {



    @Autowired
    LogService logService;

    @Autowired
    HttpServletRequest request;

    private static Logger logger = Logger.getLogger(ActionLogController.class);

    /**
     * 1 记录日志
     *
     * @return
     */
    @RequestMapping(value = "/add_log", method = RequestMethod.GET)
    @ResponseBody
    @ApiOperation(value = "记录日志", notes = "记录日志")
    BaseResponse getGoods(
            @ApiParam(value = "活动id") @RequestParam Integer activityId,
            @ApiParam(value = "关键词1，可以用于分组筛选") @RequestParam String name,
            @ApiParam(value = "关键词2，可以用于分组筛选") @RequestParam String content,
            @ApiParam(value = "关键词2，可以用于分组筛选") @RequestParam(required = false) String unionId
    ) {
        String ip= IpUtil.getIpAddr(request);
        PreSaleLog log=new PreSaleLog();
        log.setActivityId(activityId);
        log.setName(name);
        log.setContent(content);
        log.setCreateTime(new Date());
        log.setIp(ip);
        log.setUnionId(unionId);
        BaseResponse response = new BaseResponse();
        logService.add(log);
        return response;
    }

}

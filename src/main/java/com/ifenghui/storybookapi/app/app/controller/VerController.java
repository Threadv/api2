package com.ifenghui.storybookapi.app.app.controller;

import com.ifenghui.storybookapi.app.app.response.GetVerResponse;
import com.ifenghui.storybookapi.app.app.entity.Ver;
import com.ifenghui.storybookapi.app.app.service.VerService;
import com.ifenghui.storybookapi.util.VersionUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

@Controller
@EnableAutoConfiguration
@Api(value = "版本渠道管理", description = "版本渠道管理")
@RequestMapping("/api/ver")
public class VerController {


    @Autowired
    VerService verService;
    @Autowired
    HttpServletRequest request;
    private static Logger logger = Logger.getLogger(VerController.class);

    @CrossOrigin
    @ApiOperation(value = "获取版本信息",notes = "")
    @RequestMapping(value = "/getVer",method = RequestMethod.GET)
    @ResponseBody
    GetVerResponse getVer(@ApiParam(value = "渠道名称")@RequestParam(required = false) String channelName,
//                                            @ApiParam(value = "当前版本号")@RequestParam(required = false) String ver,
                                            @ApiParam(value = "类型（1 安卓，2 ios）")@RequestParam(required = false) Integer type
    ) {

        if(channelName==null){
            channelName="_main";
        }
        String ver= VersionUtil.getVerionInfo(request);
        if(ver==null){
            ver="0";
        }
        if(type==null){
            type=0;
        }
        String userAgent=request.getHeader("User-Agent");
        logger.info("------------new--------getVer------userAgent--" + userAgent);
        int appId = 0;
        if(userAgent != null && userAgent.contains("appname:childstory")) {//存在
            logger.info("------------new--------getVer------majiabao--" );
            appId = 1;//儿童包，包多了加map
        } else {
            appId = 0;
        }
        GetVerResponse getVerResponse=new GetVerResponse();
        Ver verRow=this.verService.getVer(channelName,ver,type,appId);
        getVerResponse.setVer(verRow);

        return getVerResponse;
    }

}

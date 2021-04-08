package com.ifenghui.storybookapi.app.app.controller;

import com.ifenghui.storybookapi.app.app.response.GetAdsResponse;
import com.ifenghui.storybookapi.app.app.response.GetIndexFirstAdsResponse;
import com.ifenghui.storybookapi.app.app.response.IndexAds;
import com.ifenghui.storybookapi.app.app.service.AdService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
//@EnableAutoConfiguration
@Api(value = "广告管理", description = "广告管理")
@RequestMapping("/api/ads")
public class AdsController {

    @Autowired
    AdService adService;


    @ApiOperation(value = "获取第一屏广告",notes = "")
    @RequestMapping(value = "/getIndexAds",method = RequestMethod.GET)
    @ResponseBody
    GetIndexFirstAdsResponse getIndexFirstAds(){
        GetIndexFirstAdsResponse response = new GetIndexFirstAdsResponse();
        IndexAds indexAds = adService.getIndexAds();
        response.setIndexAds(indexAds);
        return response;
    }

    @ApiOperation(value = "获取某个广告",notes = "")
    @RequestMapping(value = "/ads_detail",method = RequestMethod.GET)
    @ResponseBody
    GetAdsResponse getAdsById(
            @ApiParam(value = "id")@RequestParam Integer id
    ) {
        GetAdsResponse response = new GetAdsResponse();
        response.setAds(adService.getAdsById(id));
        return response;
    }

}

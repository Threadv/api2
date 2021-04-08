package com.ifenghui.storybookapi.adminapi.controlleradmin;


import com.ifenghui.storybookapi.adminapi.manager.service.CmsLogService;
import com.ifenghui.storybookapi.app.app.entity.Ads;
import com.ifenghui.storybookapi.app.app.response.BaseResponse;
import com.ifenghui.storybookapi.app.app.response.GetAdsListResponse;
import com.ifenghui.storybookapi.app.app.response.GetAdsResponse;
import com.ifenghui.storybookapi.app.app.service.AdService;
import com.ifenghui.storybookapi.app.story.entity.SerialStory;
import com.ifenghui.storybookapi.app.story.response.GetUserSerialStorysResponse;
import com.ifenghui.storybookapi.app.story.service.IpLabelService;
import com.ifenghui.storybookapi.app.story.service.SerialStoryService;
import com.ifenghui.storybookapi.app.story.service.StoryService;
import com.ifenghui.storybookapi.exception.ApiNotTokenException;
import com.ifenghui.storybookapi.style.AdPositionStyle;
import com.ifenghui.storybookapi.style.AdminResponseType;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;

@CrossOrigin(origins = "*", maxAge = 3600)//内部开发暂时支持跨域
@Controller
@EnableAutoConfiguration
@Api(value = "广告管理", description = "广告管理api")
@RequestMapping("/adminapi/ad")
public class AdAdminController {

    @Autowired
    StoryService storyService;

    @Autowired
    AdService adService;

    @Autowired
    IpLabelService ipLabelService;

    @Autowired
    HttpServletRequest request;

    @Autowired
    CmsLogService cmsLogService;


    @ApiOperation(value = "获得广告列表")
    @RequestMapping(value = "/get_ad_list",method = RequestMethod.GET)
    @ResponseBody
    GetAdsListResponse getAdList(
        @ApiParam(value = "title") @RequestParam(required = false) String title,
        @ApiParam(value = "targetType") @RequestParam(required = false) Integer targetType,
        @ApiParam(value = "adPositionStyle") @RequestParam(required = false) AdPositionStyle adPositionStyle,
        @ApiParam(value = "orderBy") @RequestParam(required = false,defaultValue = "1") Integer orderBy,
        @ApiParam(value = "status") @RequestParam(required = false) Integer status,
        @ApiParam(value = "pageNo") @RequestParam(required = false) Integer pageNo,
        @ApiParam(value = "pageSize") @RequestParam Integer pageSize
    ) throws ApiNotTokenException {
        if(pageNo==null){
            pageNo=0;
        }
        GetAdsListResponse response=new GetAdsListResponse();
        Ads ads=new Ads();
        if (title != null && !"".equals(title)){
            ads.setTitle(title);
        }
        if (targetType != null){
           ads.setTargetType(targetType);
        }
        if (adPositionStyle != null){
            ads.setAdsPosition(adPositionStyle.getId());
        }
        if(status!=null){
            ads.setStatus(status);
        }
        PageRequest pageRequest = new PageRequest(pageNo, pageSize, new Sort(Sort.Direction.DESC,"id"));
        if (orderBy == 0){
            pageRequest = new PageRequest(pageNo, pageSize, new Sort(Sort.Direction.DESC,"orderBy"));
        }
        Page<Ads> adsPage = adService.getAdsList(ads,pageRequest);

        response.setAds(adsPage.getContent());
        response.setJpaPage(adsPage);
        return response;
    }

    @ApiOperation(value = "获得广告")
    @RequestMapping(value = "/get_ad",method = RequestMethod.GET)
    @ResponseBody
    GetAdsResponse getAd(
             Integer id
    ) throws ApiNotTokenException {
        GetAdsResponse response=new GetAdsResponse();
        Ads ads=adService.findOneAds(id.longValue());
        response.setAds(ads);
        return response;
    }

    @ApiOperation(value = "添加广告")
    @RequestMapping(value = "/add_ad",method = RequestMethod.POST)
    @ResponseBody
    BaseResponse addAd(
            @ApiParam(value = "title") @RequestParam(required = false) String title,
            @ApiParam(value = "content") @RequestParam(required = false) String content,
            @ApiParam(value = "targetType") @RequestParam(required = false) Integer targetType,
            @ApiParam(value = "targetValue") @RequestParam(required = false) Integer targetValue,
            @ApiParam(value = "url") @RequestParam(required = false) String url,
            @ApiParam(value = "orderBy") @RequestParam(required = false) Integer orderBy,
            @ApiParam(value = "status") @RequestParam(required = false) Integer status,
            @ApiParam(value = "isIos") @RequestParam(required = false) Integer isIos,
            @ApiParam(value = "adPositionStyle") @RequestParam(required = false) AdPositionStyle adPositionStyle,
            @ApiParam(value = "imgPath") @RequestParam String imgPath,
            @ApiParam(value = "icon") @RequestParam String iconDate,
            HttpServletRequest request
    ) throws ApiNotTokenException {
        BaseResponse response=new BaseResponse();
        Ads ads = new Ads();
        if (title != null & !"".equals(title)){
            ads.setTitle(title);
        }
        if (content != null && !"".equals(content)){
            ads.setContent(content);
        }
        if (targetType != null){
            ads.setTargetType(targetType);
        }
        if (targetValue != null){
            ads.setTargetValue(targetValue);
        }
        if (url != null){
            ads.setUrl(url);
        }
        if (isIos != null){
            ads.setIsIosVisual(isIos);
        }
        if (adPositionStyle != null){
            ads.setAdsPosition(adPositionStyle.getId());
        }
        if (imgPath != null){
            String str = imgPath.replace("banner/","");
            ads.setImgPath(str);
        }
        if (iconDate != null){
            String str = iconDate.replace("banner/","");
            ads.setIcon(str);
        }
        if (orderBy != null){
            ads.setOrderBy(orderBy);
        }
        if (status != null){
            ads.setStatus(status);
        }
        ads.setCreateTime(new Date());
        adService.save(ads);
        //添加操作日志
        cmsLogService.save("Ads",ads.getId().intValue(),1,request);
        return response;
    }

    @ApiOperation(value = "编辑广告")
    @RequestMapping(value = "/edit_ad",method = RequestMethod.POST)
    @ResponseBody
    BaseResponse editAd(
            @ApiParam(value = "id") @RequestParam(required = false) Integer id,
            @ApiParam(value = "title") @RequestParam(required = false) String title,
            @ApiParam(value = "content") @RequestParam(required = false) String content,
            @ApiParam(value = "targetType") @RequestParam(required = false) Integer targetType,
            @ApiParam(value = "targetValue") @RequestParam(required = false) Integer targetValue,
            @ApiParam(value = "url") @RequestParam(required = false) String url,
            @ApiParam(value = "orderBy") @RequestParam(required = false) Integer orderBy,
            @ApiParam(value = "status") @RequestParam(required = false) Integer status,
            @ApiParam(value = "isIos") @RequestParam(required = false) Integer isIos,
            @ApiParam(value = "adPositionStyle") @RequestParam(required = false) AdPositionStyle adPositionStyle,
            @ApiParam(value = "imgPath") @RequestParam String imgPath,
            @ApiParam(value = "icon") @RequestParam String iconDate,
            HttpServletRequest request
    ) throws ApiNotTokenException {
        BaseResponse response = new BaseResponse();
        Ads ads = adService.getAdsById(id);
        if (ads == null){
            response = new BaseResponse(AdminResponseType.ERROR);
            return response;
        }

        if (title != null & !"".equals(title)){
            ads.setTitle(title);
        }
        if (content != null && !"".equals(content)){
            ads.setContent(content);
        }
        if (targetType != null){
            ads.setTargetType(targetType);
        }
        if (targetValue != null){
            ads.setTargetValue(targetValue);
        }
        if (url != null){
            ads.setUrl(url);
        }
        if (isIos != null){
            ads.setIsIosVisual(isIos);
        }
        if (adPositionStyle != null){
            ads.setAdsPosition(adPositionStyle.getId());
        }
        if (imgPath != null){
            String str = imgPath.replace("banner/","");
            ads.setImgPath(str);
        }
        if (iconDate != null){
            StringBuffer sb = new StringBuffer(iconDate);
            String str = iconDate.replace("banner/","");
            ads.setIcon(str);
        }
        if (orderBy != null){
            ads.setOrderBy(orderBy);
        }
        if (status != null){
            ads.setStatus(status);
        }
        adService.save(ads);
        //添加操作日志
        cmsLogService.save("Ads",ads.getId().intValue(),2,request);
        return response;
    }


    @ApiOperation(value = "删除广告")
    @RequestMapping(value = "/del_ad",method = RequestMethod.GET)
    @ResponseBody
    BaseResponse ditAd(
            Integer id,
            HttpServletRequest request
    ) throws ApiNotTokenException {
        BaseResponse response=new BaseResponse();
        adService.del(id);
        //添加操作日志
        cmsLogService.save("Ads",id,3,request);
        return response;
    }

    @ApiOperation(value = "取消/发布")
    @RequestMapping(value = "/update_status",method = RequestMethod.GET)
    @ResponseBody
    BaseResponse updateStatus(
            Integer id
    ) throws ApiNotTokenException {
        BaseResponse response=new BaseResponse();
        Ads ads = adService.getAdsById(id);
        if (ads == null){
            response = new BaseResponse(AdminResponseType.ERROR);
            return response;
        }
        if (ads.getStatus() == 0){
            ads.setStatus(1);
        } else if (ads.getStatus() == 1){
            ads.setStatus(0);
        }
        adService.save(ads);
        return response;
    }
}

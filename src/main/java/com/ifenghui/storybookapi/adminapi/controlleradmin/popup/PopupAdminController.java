package com.ifenghui.storybookapi.adminapi.controlleradmin.popup;


import com.ifenghui.storybookapi.app.app.entity.Popup;
import com.ifenghui.storybookapi.app.app.response.BaseResponse;
import com.ifenghui.storybookapi.app.app.response.popup.PopupResponse;
import com.ifenghui.storybookapi.app.app.response.popup.PopupsResponse;
import com.ifenghui.storybookapi.app.app.service.PopupService;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.Date;


@CrossOrigin(origins = "*", maxAge = 3600)//内部开发暂时支持跨域
@Controller
@RestController
@RequestMapping(value = "/adminapi/popup", name = "弹框管理")
public class PopupAdminController {



    @Autowired
    PopupService popupService;



    @RequestMapping(value = "/getPopups", method = RequestMethod.GET)
    @ApiOperation("获得所有弹框")
    PopupsResponse getAdminPopups(

            @ApiParam(value = "pageNo") @RequestParam(required = false,defaultValue = "0") Integer pageNo,
            @ApiParam(value = "pageSize") @RequestParam(required = false,defaultValue = "20") Integer pageSize
    ) {
//        if(type==null)
        Popup popup=new Popup();

        PopupsResponse response=new PopupsResponse();
        Page<Popup> popups= popupService.findAll(popup,new PageRequest(pageNo,pageSize,new Sort(Sort.Direction.DESC,"id")));
        response.setPopups(popups.getContent());
        response.setJpaPage(popups);
        return response ;
    }
    @RequestMapping(value = "/getPopup", method = RequestMethod.GET)
    @ApiOperation("获取da单个广告")
    PopupResponse getAdminPopup(
            @ApiParam(value = "id") @RequestParam Integer id
    ) {
        PopupResponse response = new PopupResponse();
        Popup popup=popupService.findOne(id);
        response.setPopup(popup);
        return response ;
    }

    @RequestMapping(value = "/addPopup", method = RequestMethod.POST)
    @ApiOperation("增加popup")
    BaseResponse addPopup(
            @ApiParam(value = "title") @RequestParam(required = false) String title,
            @ApiParam(value = "beginTime") @RequestParam(required = false) @DateTimeFormat(pattern = "yyyy-MM-dd") Date beginTime,
            @ApiParam(value = "endTime") @RequestParam(required = false) @DateTimeFormat(pattern = "yyyy-MM-dd") Date endTime,
            @ApiParam(value = "img") @RequestParam(required = false) String img,
//                    @ApiParam(value = "className") @RequestParam(required = false) String className,
            @ApiParam @RequestParam(required = false) Integer imgHeight,
            @ApiParam @RequestParam(required = false) Integer imgWidth,
//            @ApiParam @RequestParam(required = false) Integer isShowBtn,
//            @ApiParam @RequestParam(required = false) String btnName,
            @ApiParam @RequestParam(required = false) String redirectUrl,
            @ApiParam @RequestParam(required = false) Integer status
//            @ApiParam @RequestParam(required = false) String btnColor,
//            @ApiParam @RequestParam(required = false) String btnTextColor,
//            @ApiParam @RequestParam(required = false) Integer btnBottom
    ) {

        Popup popup=new Popup();
        popup.setTitle(title.trim());
        popup.setBeginTime(beginTime);
        popup.setCreateTime(new Date());
        popup.setEndTime(endTime);
        popup.setImg(img);
        popup.setStatus(status);
//        popup.setIsShowBtn(isShowBtn);
//        popup.setBtnName(btnName);
        popup.setRedirectUrl(redirectUrl);
//        popup.setBtnColor(btnColor);
//        popup.setBtnTextColor(btnTextColor);
//        popup.setBtnBottom(btnBottom);
        if(imgHeight!=null){
            popup.setImgHeight(imgHeight);
        }
        if(imgWidth!=null){
            popup.setImgWidth(imgWidth);
        }
//        popup.setClassName(className);
        popupService.add(popup);
        BaseResponse response = new BaseResponse();
        return response ;
    }

    @RequestMapping(value = "/updatePopup", method = RequestMethod.PUT)
    @ApiOperation("修改弹出框")
    BaseResponse updatePopup(
            @ApiParam(value = "id") @RequestParam Integer id,
            @ApiParam(value = "title") @RequestParam(required = false) String title,
            @ApiParam(value = "beginTime") @RequestParam(required = false) @DateTimeFormat(pattern = "yyyy-MM-dd") Date beginTime,
            @ApiParam(value = "endTime") @RequestParam(required = false) @DateTimeFormat(pattern = "yyyy-MM-dd") Date endTime,
            @ApiParam(value = "img") @RequestParam(required = false) String img,
            @ApiParam @RequestParam(required = false) Integer status,
//            @ApiParam(value = "className") @RequestParam(required = false) String className,
            @ApiParam @RequestParam(required = false) Integer imgHeight,
            @ApiParam @RequestParam(required = false) Integer imgWidth,
//            @ApiParam @RequestParam(required = false) Integer isShowBtn,
//            @ApiParam @RequestParam(required = false) String btnName,
            @ApiParam @RequestParam(required = false) String redirectUrl
//            @ApiParam @RequestParam(required = false) String btnColor,
//            @ApiParam @RequestParam(required = false) String btnTextColor,
//            @ApiParam @RequestParam(required = false) Integer btnBottom
    ) {

        Popup popup=popupService.findOne(id);
        if(title!=null){
            popup.setTitle(title.trim());
        }
        if(beginTime!=null){
            popup.setBeginTime(beginTime);
        }
        if(endTime!=null){
            popup.setEndTime(endTime);
        }
        if(img!=null){
            popup.setImg(img);
        }
        if(status!=null){
            popup.setStatus(status);
        }
//        if(className!=null){
//            popup.setClassName(className);
//        }
        if(imgHeight!=null){
            popup.setImgHeight(imgHeight);
        }
        if(imgWidth!=null){
            popup.setImgWidth(imgWidth);
        }
//        if(isShowBtn!=null){
//            popup.setIsShowBtn(isShowBtn);
//        }
//        if(btnName!=null){
//            popup.setBtnName(btnName);
//        }
        if(redirectUrl!=null){
            popup.setRedirectUrl(redirectUrl);
        }
//        if(btnColor!=null){
//            popup.setBtnColor(btnColor);
//        }
//        if(btnTextColor!=null){
//            popup.setBtnTextColor(btnTextColor);
//        }
//        if(btnBottom!=null){
//            popup.setBtnBottom(btnBottom);
//        }

        popupService.update(popup);
        BaseResponse response = new BaseResponse();
        return response ;
    }

    @RequestMapping(value = "/delPopup", method = RequestMethod.DELETE)
    @ApiOperation("保存单个广告")
    BaseResponse delPopup(
            @ApiParam(value = "id") @RequestParam Integer id
    ) {
        popupService.delete(id);
        BaseResponse response = new BaseResponse();
        return response ;
    }


}

package com.ifenghui.storybookapi.adminapi.controlleradmin.code;

import com.ifenghui.storybookapi.adminapi.controlleradmin.code.entity.SmsMessage;
import com.ifenghui.storybookapi.adminapi.controlleradmin.code.resp.*;
import com.ifenghui.storybookapi.app.app.response.BaseResponse;
import com.ifenghui.storybookapi.app.presale.entity.PreSaleCode;
import com.ifenghui.storybookapi.app.presale.service.PreSaleCodeService;
import com.ifenghui.storybookapi.style.VipCodeStyle;
import com.ifenghui.storybookapi.style.VipGoodsStyle;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.List;

@CrossOrigin(origins = "*", maxAge = 3600)//支持跨域
@Controller
@Api(value = "兑换码类型", description = "兑换码类型")
@RequestMapping("/adminapi/code")
public class CodeAdminController {


    @Autowired
    PreSaleCodeService preSaleCodeService;



    @ApiOperation(value = "通过枚举获得兑换码类型",notes = "")
    @RequestMapping(value = "/getCodeType",method = RequestMethod.GET)
    @ResponseBody
    public VipCodesResponse getCodeTypes(){


        VipCodesResponse response = new VipCodesResponse();
        List<CodeTypeItem> codeTypeItemList=new ArrayList<>();
       for(VipGoodsStyle vipGoodsStyle: VipGoodsStyle.values()){
           codeTypeItemList.add(new CodeTypeItem(vipGoodsStyle));
       }
        response.setCodeTypeItems(codeTypeItemList);
        return response;
    }


    @ApiOperation(value = "获得code列表",notes = "")
    @RequestMapping(value = "/getCodes",method = RequestMethod.GET)
    @ResponseBody
    PreSaleCodesResponse getCodes(String code,Integer pageNo, Integer pageSize){

        PreSaleCodesResponse response=new PreSaleCodesResponse();

        PreSaleCode preSaleCode=new PreSaleCode();
        if(code!=null&&!code.equals("")){
            preSaleCode.setCode(code);
        }
        Page<PreSaleCode> smsMessagePage= preSaleCodeService.findAllCodes(preSaleCode,new PageRequest(pageNo,pageSize,new Sort(Sort.Direction.DESC,"id")));

        response.setPreSaleCodes(smsMessagePage.getContent());

        response.setJpaPage(smsMessagePage);
        return response;
    }

    @ApiOperation(value = "addcode列表",notes = "")
    @RequestMapping(value = "/addCodes",method = RequestMethod.POST)
    @ResponseBody
    BaseResponse addCodes(Integer count,Integer saleType,String name, VipGoodsStyle vipGoodsStyle){

        PreSaleCodesResponse response=new PreSaleCodesResponse();

        PreSaleCode code=new PreSaleCode();
        for(int i=0;i<count;i++){
//            code=new PreSaleCode();

            code=preSaleCodeService.addCode(0,1,4,vipGoodsStyle.getId());
            code.setName(name);
            code.setSaleType(saleType);
            preSaleCodeService.update(code);
        }


        return new BaseResponse();
    }

    @ApiOperation(value = "增加一个兑换码",notes = "")
    @RequestMapping(value = "/addCode",method = RequestMethod.POST)
    @ResponseBody
    public PreSaleCodeResponse addCode(Integer saleType,String name, VipGoodsStyle vipGoodsStyle,Integer activityId){

        PreSaleCodeResponse response=new PreSaleCodeResponse();
        PreSaleCode code=preSaleCodeService.addCode(0,0,activityId,vipGoodsStyle.getId());
//        if(activityId!=null){
//            code.setActivity_id(activityId);
//        }
        code.setName(name);
        code.setSaleType(saleType);
        preSaleCodeService.update(code);
        response.setPreSaleCode(code);


        return response;
    }

    @ApiOperation(value = "增加一个兑换码",notes = "")
    @RequestMapping(value = "/addCodeByType",method = RequestMethod.POST)
    @ResponseBody
    public PreSaleCodeResponse addCodeByType(Integer saleType,String name, Integer vipGoodsStyleId,Integer activityId){

        PreSaleCodeResponse response=new PreSaleCodeResponse();
        PreSaleCode code=preSaleCodeService.addCode(0,1,4,vipGoodsStyleId);
        if(activityId!=null){
            code.setActivity_id(activityId);
        }
        preSaleCodeService.update(code);
        response.setPreSaleCode(code);


        return response;
    }

    /**
     * 兑换码是否激活
     *
     * @return
     */
    @RequestMapping(value = "/is_use", method = RequestMethod.GET)
    @ResponseBody
    @ApiOperation(value = "兑换码是否激活", notes = "兑换码是否激活")
    PreSaleCodeResponse isUse(
            @ApiParam(value = "code") @RequestParam String code
    ) {

        PreSaleCodeResponse response = new PreSaleCodeResponse();

        PreSaleCode saleCode = preSaleCodeService.getCodeByCode(code);
        response.setPreSaleCode(saleCode);
        return response;
    }

    /**
     * 兑换码设置过期
     */
    @RequestMapping(value = "/set_expire", method = RequestMethod.GET)
    @ResponseBody
    @ApiOperation(value = "兑换码设置过期", notes = "兑换码设置过期")
    BaseResponse setExpire(
            @ApiParam(value = "id") @RequestParam Integer id
    ) {
        BaseResponse response = new BaseResponse();
        PreSaleCode saleCode = preSaleCodeService.findCode(id);
        saleCode.setIsExpire(1);
        preSaleCodeService.update(saleCode);
        return response;
    }
}

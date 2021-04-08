package com.ifenghui.storybookapi.adminapi.controlleradmin.code;

import com.ifenghui.storybookapi.adminapi.controlleradmin.code.entity.SmsMessage;
import com.ifenghui.storybookapi.adminapi.controlleradmin.code.entity.SmsMessageDetail;
import com.ifenghui.storybookapi.adminapi.controlleradmin.code.resp.SmsMessageDetailsResponse;
import com.ifenghui.storybookapi.adminapi.controlleradmin.code.resp.SmsMessagesResponse;
import com.ifenghui.storybookapi.adminapi.controlleradmin.code.service.SmsMessageDetailService;
import com.ifenghui.storybookapi.adminapi.controlleradmin.code.service.SmsMessageService;
import com.ifenghui.storybookapi.app.app.response.BaseResponse;
import com.ifenghui.storybookapi.app.presale.entity.PreSaleCode;
import com.ifenghui.storybookapi.app.presale.service.PreSaleCodeService;
import com.ifenghui.storybookapi.app.system.service.MessageService;
import com.ifenghui.storybookapi.style.VipGoodsStyle;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.sound.midi.SysexMessage;
import java.text.SimpleDateFormat;
import java.util.*;

@CrossOrigin(origins = "*", maxAge = 3600)//支持跨域
@Controller
@Api(value = "兑换码类型", description = "兑换码类型")
@RequestMapping("/adminapi/smsmessage")
public class SmsMessageAdminController {

    @Autowired
    SmsMessageService smsMessageService;

    @Autowired
    SmsMessageDetailService smsMessageDetailService;

    @Autowired
    HttpServletRequest request;

    @Autowired
    PreSaleCodeService preSaleCodeService;

    @Autowired
    MessageService messageService;

    @ApiOperation(value = "获得消息列表",notes = "")
    @RequestMapping(value = "/getSmsMessages",method = RequestMethod.GET)
    @ResponseBody
    SmsMessagesResponse getSmsMessages(Integer pageNo, Integer pageSize){

        SmsMessagesResponse response=new SmsMessagesResponse();
        Page<SmsMessage> smsMessagePage= smsMessageService.findAllSmsMessage(new SmsMessage(),new PageRequest(pageNo,pageSize,new Sort(Sort.Direction.DESC,"id")));

        response.setSmsMessages(smsMessagePage.getContent());

        response.setJpaPage(smsMessagePage);
        return response;
    }

    @ApiOperation(value = "增加消息列表",notes = "")
    @RequestMapping(value = "/addSmsMessages",method = RequestMethod.POST)
    @ResponseBody
    SmsMessagesResponse addSmsMessages(String title, String phones, VipGoodsStyle vipGoodsStyle){

        String[] phonesArr=phones.split("\n");
        SmsMessagesResponse response=new SmsMessagesResponse();
        SmsMessage smsMessage=new SmsMessage();
        smsMessage.setTitle(title);
        smsMessage.setCount(phonesArr.length);
        smsMessage.setCreateTime(new Date());
        smsMessage.setIsSend(0);
        smsMessage.setPhones(phones);
        smsMessage.setTemplateId(vipGoodsStyle.getTemplateCode());
        smsMessage= smsMessageService.add(smsMessage);


        //增加单条记录
        SmsMessageDetail messageDetail;
        for(String phone:phonesArr){
            messageDetail=new SmsMessageDetail();
            messageDetail.setMessageId(smsMessage.getId());
            PreSaleCode preSaleCode=preSaleCodeService.addCode(0,1,0,vipGoodsStyle.getId());
            preSaleCode.setName("短信兑换码");
            preSaleCodeService.update(preSaleCode);

            messageDetail.setCode(preSaleCode.getCode());
            messageDetail.setCreateTime(new Date());
            messageDetail.setIsSend(0);
            messageDetail.setPhone(phone);
            messageDetail.setTemplateId(vipGoodsStyle.getTemplateCode());
            smsMessageDetailService.add(messageDetail);
        }

        return response;
    }

    @ApiOperation(value = "修复短信",notes = "")
    @RequestMapping(value = "/fixSmsMessages",method = RequestMethod.POST)
    @ResponseBody
    SmsMessagesResponse addSmsMessages(Integer id){
        SmsMessagesResponse response=new SmsMessagesResponse();
        if(id!=22&&id!=208){
            return response;
        }

        SmsMessage smsMessage=smsMessageService.findOne(id);

        String[] phonesArr=smsMessage.getPhones().split("\n");
//        VipGoodsStyle.getById(smsMessage.getTemplateId());
        //增加单条记录
        SmsMessageDetail messageDetail;
        for(String phone:phonesArr){
            messageDetail=new SmsMessageDetail();
            messageDetail.setMessageId(smsMessage.getId());
            messageDetail.setCreateTime(new Date());
            messageDetail.setIsSend(0);
            messageDetail.setPhone(phone.trim());
            messageDetail.setTemplateId("SMS_154500203");
            messageDetail=smsMessageDetailService.add(messageDetail);
            if(messageDetail!=null&&(messageDetail.getCode()==null||messageDetail.getCode().length()<5)){
                PreSaleCode preSaleCode=preSaleCodeService.addCode(0,1,0,57);
                preSaleCode.setName("短信兑换码");
                preSaleCodeService.update(preSaleCode);

                messageDetail.setCode(preSaleCode.getCode());
                smsMessageDetailService.update(messageDetail);
            }
        }

        return response;
    }

    @ApiOperation(value = "获得发送记录",notes = "")
    @RequestMapping(value = "/getSmsMessageDetails",method = RequestMethod.GET)
    @ResponseBody
    SmsMessageDetailsResponse getSmsMessageDetails(Integer messageId,String phone,String code,Integer pageNo, Integer pageSize){

        SmsMessageDetailsResponse response=new SmsMessageDetailsResponse();

        SmsMessageDetail smsMessageDetail=new SmsMessageDetail();

        if(messageId!=null&&!messageId.equals("")){
            smsMessageDetail.setMessageId(messageId);
        }
        if(phone!=null&&!phone.equals("")){
            smsMessageDetail.setPhone(phone);
        }
        if(code!=null&&!code.equals("")){
            smsMessageDetail.setCode(code);
        }

        Page<SmsMessageDetail> smsMessagePage= smsMessageDetailService.findAllSmsMessage(smsMessageDetail,new PageRequest(pageNo,pageSize,new Sort(Sort.Direction.DESC,"id")));

        for(SmsMessageDetail smsMessageDetail1:smsMessagePage.getContent()){
            smsMessageDetail.setSmsMessage(smsMessageService.findOne(smsMessageDetail1.getMessageId()));
        }

        response.setSmsMessageDetails(smsMessagePage.getContent());

        response.setJpaPage(smsMessagePage);
        return response;
    }

    @ApiOperation(value = "批量发送",notes = "")
    @RequestMapping(value = "/pushMessages",method = RequestMethod.POST)
    @ResponseBody
    BaseResponse pushByMessage(Integer messageId){

        BaseResponse response=new BaseResponse();

        SmsMessageDetail smsMessageDetail=new SmsMessageDetail();
        smsMessageDetail.setMessageId(messageId);
        //查询所有单挑内容
        Page<SmsMessageDetail> smsMessagePage= smsMessageDetailService.findAllSmsMessage(smsMessageDetail,new PageRequest(0,9999,new Sort(Sort.Direction.DESC,"id")));

        //逐个发送并改发送状态
        for(SmsMessageDetail smsMessageDetail1:smsMessagePage.getContent()){
            Map<String,String> map=new HashMap<>();
            map.put("code",smsMessageDetail1.getCode());
            PreSaleCode preSaleCode=preSaleCodeService.getCodeByCode(smsMessageDetail1.getCode());
            SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd");
            String endTime = sf.format(preSaleCode.getEndTime());
            map.put("endTime",endTime);
            messageService.sendSms(smsMessageDetail1.getPhone(),smsMessageDetail1.getTemplateId(),map);
            smsMessageDetail1.setIsSend(1);
            smsMessageDetailService.update(smsMessageDetail1);
        }
        //修改总发送状态
        SmsMessage smsMessage= smsMessageService.findOne(messageId);
        smsMessage.setIsSend(1);
        smsMessageService.update(smsMessage);
        return response;
    }

    @ApiOperation(value = "单条发送",notes = "")
    @RequestMapping(value = "/pushMessage",method = RequestMethod.POST)
    @ResponseBody
    BaseResponse pushByMessageDetail(Integer messageDetailId){

        BaseResponse response=new BaseResponse();

        SmsMessageDetail smsMessageDetail=smsMessageDetailService.findOne(messageDetailId);
        Map<String,String> map=new HashMap<>();
        map.put("code",smsMessageDetail.getCode());
        PreSaleCode preSaleCode=preSaleCodeService.getCodeByCode(smsMessageDetail.getCode());
        SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd");
        String endTime = sf.format(preSaleCode.getEndTime());
        map.put("endTime",endTime);
        messageService.sendSms(smsMessageDetail.getPhone(),smsMessageDetail.getTemplateId(),map);
        smsMessageDetail.setIsSend(1);
        smsMessageDetailService.update(smsMessageDetail);
        return response;
    }

}

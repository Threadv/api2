package com.ifenghui.storybookapi.adminapi.controlleradmin.code;

import com.aliyun.oss.OSSClient;
import com.aliyun.oss.model.PutObjectRequest;
import com.aliyun.oss.model.PutObjectResult;
import com.ifenghui.storybookapi.adminapi.controlleradmin.code.entity.SmsMessageDetail;
import com.ifenghui.storybookapi.adminapi.controlleradmin.code.service.SmsMessageDetailService;
import com.ifenghui.storybookapi.adminapi.service.ExpressCenterExcelService;
import com.ifenghui.storybookapi.app.app.response.BaseResponse;
import com.ifenghui.storybookapi.app.express.entity.ExpressCenterTrack;
import com.ifenghui.storybookapi.style.AdminResponseType;
import com.ifenghui.storybookapi.util.ExcelUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.text.SimpleDateFormat;
import java.util.List;

@CrossOrigin(origins = "*", maxAge = 3600)//支持跨域
@Controller
@Api(value = "兑换码短信Excel", description = "excel导出")
@RequestMapping("//adminapi/smsmessage/excel")
public class SmsMessageExcelController {

    @Value("${oss.accountendpoint}")
    String endpoint;

    @Value("${oss.accesskeyid}")
    String accessKeyId;

    @Value("${oss.accesskeysecret}")
    String accessKeySecret;

    @Value("${oss.backet}")
    String backet;

    @Value("${oss.url}")
    String ossUrl;


    @Autowired
    SmsMessageDetailService smsMessageDetailService;

    @ApiOperation(value = "短信发送记录导出excel表", notes = "")
    @RequestMapping(value = "/export", method = RequestMethod.GET)
    @ResponseBody
    BaseResponse excelExport(Integer messageId,String phone,String code) {
        BaseResponse response = new BaseResponse();
        //查询短信发送记录
        SmsMessageDetail smsMessageDetail=new SmsMessageDetail();

        if(messageId == null){
            response.getStatus().setCode(0);
            response.getStatus().setMsg("信息不全");
            return response;
        }
        smsMessageDetail.setMessageId(messageId);
        if(phone!=null&&!phone.equals("")){
            smsMessageDetail.setPhone(phone);
        }
        if(code!=null&&!code.equals("")){
            smsMessageDetail.setCode(code);
        }
        List<SmsMessageDetail> smsMessageDetails = smsMessageDetailService.findAllSmsMessageBy(smsMessageDetail);
        if (smsMessageDetails.size() > 0){

            //excel标题
            String[] title = {"id","手机号","兑换码"};
            //excel文件名
            String fileName = "短信发送记录表" + System.currentTimeMillis() + ".xls";
            //sheet名
            String sheetName = "短信发送记录表";
            String[][] content = new String[smsMessageDetails.size()][];

            for (int i = 0; i < smsMessageDetails.size(); i++) {
                content[i] = new String[title.length];
                SmsMessageDetail obj = smsMessageDetails.get(i);
                content[i][0] = obj.getId().toString();
                content[i][1] = obj.getPhone();
                content[i][2] = "code_"+obj.getCode();
            }
            //创建HSSFWorkbook
            HSSFWorkbook wb = ExcelUtil.getHSSFWorkbook(sheetName, title, content, null);
            try {
                ByteArrayOutputStream ba= new ByteArrayOutputStream();
                wb.write(ba);
                ba.flush();
                ba.close();
                //将字节数组转换成输入流
                ByteArrayInputStream bio = new ByteArrayInputStream(ba.toByteArray());
                String endpoint =this.endpoint;
                String accessId =accessKeyId;
                String accessKey = accessKeySecret;
                String bucket = backet;
                OSSClient client = new OSSClient(endpoint, accessId, accessKey);
                PutObjectResult result = client.putObject(new PutObjectRequest(bucket, "exl/outpot/"+fileName, bio));
                if(result!=null){
                    System.out.println("成功");
                    response.getStatus().setMsg(ossUrl+"exl/outpot/"+fileName);
                }
            }catch (Exception e) {
            }
        }
        return response;
    }

}

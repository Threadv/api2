package com.ifenghui.storybookapi.adminapi.controlleradmin.express;

import com.aliyun.oss.OSSClient;
import com.aliyun.oss.model.PutObjectRequest;
import com.aliyun.oss.model.PutObjectResult;
import com.ifenghui.storybookapi.adminapi.service.ExpressCenterExcelService;
import com.ifenghui.storybookapi.app.app.response.BaseResponse;
import com.ifenghui.storybookapi.app.express.entity.ExpressCenterOrder;
import com.ifenghui.storybookapi.app.express.entity.ExpressCenterTrack;
import com.ifenghui.storybookapi.app.express.service.ExpressCenterOrderService;
import com.ifenghui.storybookapi.app.express.service.ExpressCenterTrackService;
import com.ifenghui.storybookapi.util.ExcelUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;
import java.text.SimpleDateFormat;
import java.util.List;

@CrossOrigin(origins = "*", maxAge = 3600)//支持跨域
@Controller
@Api(value = "物流和订单", description = "excel导入")
@RequestMapping("/adminapi/expresscenter/excel")
public class ExpressCenterExcelController {

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

    //    @Value("${ueditor.basepath}")
    String ueditorBase="/data/www";

    @Autowired
    ExpressCenterExcelService excelService;

    @Autowired
    ExpressCenterOrderService orderService;

    @Autowired
    ExpressCenterTrackService trackService;

    @ApiOperation(value = "物流导入excel表", notes = "")
    @RequestMapping(value = "/track/import", method = RequestMethod.GET)
    @ResponseBody
    BaseResponse trackExcelImport(String fileName) {
        BaseResponse response = new BaseResponse();
        try {
            URL httpurl = new URL(ossUrl + fileName);
            URLConnection urlConnection = httpurl.openConnection();
            InputStream is = urlConnection.getInputStream();
            Integer statue = excelService.trackImport(fileName, is);
            if (statue == 0) {
                response.getStatus().setCode(0);
                response.getStatus().setMsg("上传文件格式不对");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return response;
    }

    @ApiOperation(value = "物流导出excel表", notes = "")
    @RequestMapping(value = "/track/export", method = RequestMethod.GET)
    @ResponseBody
    BaseResponse trackExcelExport(Integer orderId,
                                  String trackNo,
                                  String phone,
                                  String fullname,
                                  Integer year,
                                  String srcMark,
                                  Integer month) {
        BaseResponse response = new BaseResponse();

        ExpressCenterTrack track = new ExpressCenterTrack();
        if (orderId != null){
            track.setCenterOrderId(orderId);
        }
        if (trackNo != null && !"".equals(trackNo)){
            track.setTrackNo(trackNo);
        }
        if (fullname != null && !"".equals(fullname)){
            track.setFullname(fullname);
        }
        if (phone != null && !"".equals(phone)){
            track.setPhone(phone);
        }
        if (srcMark != null && !"".equals(srcMark)){
            track.setSrcMark(srcMark);
        }
        track.setYear(year);
        track.setMonth(month);


        List<ExpressCenterTrack> tracks = trackService.findAllTracks(track);

        //excel标题
        String[] title = {"物流id","订单id","年份","月份","渠道类型", "渠道","联系人姓名","联系方式","物流公司","当前杂志数","运单号","物流周期(0:完結；1：未完結)","订购时间","物流导入时间"};
        //excel文件名
        String fileName = "运单信息表" + System.currentTimeMillis() + ".xls";
        //sheet名
        String sheetName = "运单信息表";
        String[][] content = new String[tracks.size()][];
        for (int i = 0; i < tracks.size(); i++) {
            content[i] = new String[title.length];
            ExpressCenterTrack obj = tracks.get(i);
            content[i][0] = obj.getId().toString();
            content[i][1] = obj.getCenterOrderId().toString();
            content[i][2] = obj.getYear().toString();
            content[i][3] = obj.getMonth().toString();
            content[i][4] = obj.getSrcType().toString();
            content[i][5] = obj.getSrcMark();
            content[i][6] = obj.getFullname();
            content[i][7] = obj.getPhone();
            if (obj.getTrackType() == 1){
                content[i][8] = "圆通快递";
            }else if (obj.getTrackType() == 2){
                content[i][8] = "中通快递";
            }else if (obj.getTrackType() == 3){
                content[i][8] = "韵达快递";
            }
            content[i][9] = obj.getVolCount().toString();
            content[i][10] = obj.getTrackNo();
            if (obj.getOrderIsOpen() != null){
                content[i][11] = obj.getOrderIsOpen().toString();
            }
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            if (obj.getOrderTime() != null){
                content[i][12] = sdf.format(obj.getOrderTime());
            }
            if (obj.getSendTime() != null){
                content[i][13] = sdf.format(obj.getSendTime());
            }
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
        return response;
    }


    @ApiOperation(value = "订单导入excel表", notes = "")
    @RequestMapping(value = "/order/import", method = RequestMethod.GET)
    @ResponseBody
    BaseResponse orderExcelImport(String fileName) {
        BaseResponse response = new BaseResponse();
        try {
            URL httpurl = new URL(ossUrl + fileName);
            URLConnection urlConnection = httpurl.openConnection();
            InputStream is = urlConnection.getInputStream();
            Integer statue = excelService.orderImport(fileName, is);
            if (statue == 0) {
                response.getStatus().setCode(0);
                response.getStatus().setMsg("上传文件格式不对");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return response;
    }

    @ApiOperation(value = "订单导出excel表", notes = "")
    @RequestMapping(value = "/order/export", method = RequestMethod.GET)
    @ResponseBody
    BaseResponse orderExcelExport(@RequestParam(required = false) Integer id,
                          String phone,
                          String fullname,
                          Integer isOpen,
                          String srcMark,
                          String outOrderId) {
        BaseResponse baseResponse = new BaseResponse();

        //获取数据
        ExpressCenterOrder order=new ExpressCenterOrder();
        if ( null != id){
            order.setId(id);
        }
        if (isOpen != null){
            order.setIsOpen(isOpen);
        }
        if (srcMark != null && !"".equals(srcMark)){
            order.setSrcMark(srcMark);
        }
        if (phone != null && !"".equals(phone)){
            order.setPhone(phone);
        }
        if (fullname != null && !"".equals(fullname)){
            order.setFullname(fullname);
        }
        if (outOrderId != null && !"".equals(outOrderId)) {
            order.setOutOrderId(outOrderId);
        }
        List<ExpressCenterOrder> list =orderService.findAllOrders(order);
        //excel标题
        String[] title = {"订单id","外部订单ID", "渠道类型", "渠道","用户收件人名","电话","用户收货地址","订阅总期数","已发期数","物流周期(0:完結；1：未完結)","订购时间"};
        //excel文件名
        String fileName = "订单信息表" + System.currentTimeMillis() + ".xls";
        //sheet名
        String sheetName = "订单信息表";
        String[][] content = new String[list.size()][];
        for (int i = 0; i < list.size(); i++) {
            content[i] = new String[title.length];
            ExpressCenterOrder obj = list.get(i);
            content[i][0] = obj.getId().toString();
            content[i][1] = obj.getOutOrderId();
            content[i][2] = obj.getSrcType().toString();
            content[i][3] = obj.getSrcMark();
            content[i][4] = obj.getFullname();
            content[i][5] = obj.getPhone();
            content[i][6] = obj.getProvince()+obj.getCity()+obj.getDistrict()+obj.getAddress();
            content[i][7] = obj.getVolCount()+"";
            content[i][8] = obj.getVolOver()+"";
            content[i][9] = obj.getIsOpen()+"";
            if (obj.getOrderTime() != null){
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                content[i][10] = sdf.format(obj.getOrderTime());
            }
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
                baseResponse.getStatus().setMsg(ossUrl+"exl/outpot/"+fileName);
            }
        }catch (Exception e) {

        }
        return baseResponse;
    }



}

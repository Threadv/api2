package com.ifenghui.storybookapi.app.express.controller;


import com.ifenghui.storybookapi.app.express.entity.ExpressCenterMag;
import com.ifenghui.storybookapi.app.express.entity.ExpressCenterOrder;
import com.ifenghui.storybookapi.app.express.entity.ExpressCenterTrack;
import com.ifenghui.storybookapi.app.express.response.ExpressCenterTrackResponse;
import com.ifenghui.storybookapi.app.express.response.ExpressCenterTracksResponse;
import com.ifenghui.storybookapi.app.express.response.YearContent;
import com.ifenghui.storybookapi.app.express.service.ExpressCenterMagService;
import com.ifenghui.storybookapi.app.express.service.ExpressCenterOrderService;
import com.ifenghui.storybookapi.app.express.service.ExpressCenterPhoneBindService;
import com.ifenghui.storybookapi.app.express.service.ExpressCenterTrackService;
import com.ifenghui.storybookapi.util.HttpUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import net.sf.json.JSONObject;
import org.apache.http.HttpResponse;
import org.apache.http.util.EntityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.*;

@CrossOrigin(origins = "*", maxAge = 3600)//支持跨域
@Controller
//@EnableAutoConfiguration
@Api(value = "物流中心", description = "物流中心")
@RequestMapping("/api/expresscenter/track")
public class ExpressCenterTrackController {

    @Autowired
    ExpressCenterPhoneBindService phoneBindService;

    @Autowired
    ExpressCenterOrderService orderService;

    @Autowired
    ExpressCenterTrackService trackService;

    @Autowired
    ExpressCenterMagService magService;

    @ApiOperation(value = "获得订单列表",notes = "返回某物流中心用户绑定某手机号的订单")
    @RequestMapping(value = "/get_express_center_tracks",method = RequestMethod.GET)
    @ResponseBody
    ExpressCenterTracksResponse getCenterTracks(Integer orderId){
        //活动订单的物流信息，返回所有物流记录，并且按月份返回。

        ExpressCenterTracksResponse response = new ExpressCenterTracksResponse();

        List<ExpressCenterTrack> tracks= trackService.findAlByOrderIdDesc(orderId);
        Set<YearContent> years = new HashSet<>();
//        for (ExpressCenterTrack t:tracks){
//            years.add(t.getYear());
//        }

        response.setExpressCenterTracks(tracks);

        return response;
    }


    @ApiOperation(value = "获得订单列表",notes = "返回某物流中心用户绑定某手机号的订单")
    @RequestMapping(value = "/get_express_center_track",method = RequestMethod.GET)
    @ResponseBody
    ExpressCenterTrackResponse getCenterTrack(Integer trackId){
        //活动订单的物流信息，返回所有物流记录，并且按月份返回。

        ExpressCenterTrackResponse response = new ExpressCenterTrackResponse();

        //查询单个物流信息
        ExpressCenterTrack tracks= trackService.findOne(trackId);
        //查询当年当月的杂志
        List<ExpressCenterMag> mags = new ArrayList<>();
        mags = magService.findByYearMonth(tracks.getYear(),tracks.getMonth());
        //杂志标题
        String title = tracks.getMonth()+"月份 《故事飞船》 绘本";
        //收货地址
        ExpressCenterOrder order = new ExpressCenterOrder();
        order = orderService.findOne(tracks.getCenterOrderId());

        response.setExpressCenterTrack(tracks);
//        response.setMags(mags);
        if (mags.size()>0){
            response.setMag1(mags.get(0));
            if (mags.size()>1){
                response.setMag2(mags.get(1));
            }
        }

        response.setTitle(title);
        response.setAdress("[收货地址]"+order.getAddress());
        response.setFullname("[联系人]"+order.getFullname()+"("+order.getPhone()+")");

        response.setTrackBody(this.getTrackBody(tracks.getTrackNo()));

        return response;
    }


    private Map getTrackBody(String no) {
        String host = "https://wuliu.market.alicloudapi.com";
        String path = "/kdi";
        String method = "GET";
//        System.out.println("请先替换成自己的AppCode");
        String appcode = "075e3442a30745969e73e6f2de67c889";  // !!!替换填写自己的AppCode 在买家中心查看
        Map<String, String> headers = new HashMap<String, String>();
        headers.put("Authorization", "APPCODE " + appcode); //格式为:Authorization:APPCODE 83359fd73fe11248385f570e3c139xxx
        Map<String, String> querys = new HashMap<String, String>();
        querys.put("no", no);// !!! 请求参数
//        querys.put("type", "zto");// !!! 请求参数
        //JDK 1.8示例代码请在这里下载：  http://code.fegine.com/Tools.zip
        try {
            /**
             * 重要提示如下:
             * HttpUtils请从
             * https://github.com/aliyun/api-gateway-demo-sign-java/blob/master/src/main/java/com/aliyun/api/gateway/demo/util/HttpUtils.java
             * 或者直接下载：
             * http://code.fegine.com/HttpUtils.zip
             * 下载
             *
             * 相应的依赖请参照
             * https://github.com/aliyun/api-gateway-demo-sign-java/blob/master/pom.xml
             * 相关jar包（非pom）直接下载：
             * http://code.fegine.com/aliyun-jar.zip
             */
            HttpResponse response = HttpUtils.doGet(host, path, method, headers, querys);
            //System.out.println(response.toString());如不输出json, 请打开这行代码，打印调试头部状态码。
            //状态码: 200 正常；400 URL无效；401 appCode错误； 403 次数用完； 500 API网管错误
            //获取response的body

             //输出json
            String respStr= EntityUtils.toString(response.getEntity());
//            System.out.println(respStr);
//            String respStr="{\"status\":\"0\",\"msg\":\"ok\",\"result\":{\"number\":\"75123206953821\",\"type\":\"zto\",\"list\":[{\"time\":\"2019-01-14 18:32:53\",\"status\":\"【北京市】  快件已在 【北京对外经贸】 签收, 签收人: 本人, 如有疑问请电联:17301097331 \\/ 010-56032887, 您的快递已经妥投, 如果您对我们的服务感到满意, 请给个五星好评, 鼓励一下我们【请在评价快递员处帮忙点亮五颗星星哦~】\"},{\"time\":\"2019-01-14 15:27:38\",\"status\":\"【北京市】  【北京对外经贸】 的何翔博17301097331（17301097331） 正在第1次派件, 请保持电话畅通,并耐心等待\"},{\"time\":\"2019-01-14 13:54:07\",\"status\":\"【北京市】  快件已经到达 【北京对外经贸】\"},{\"time\":\"2019-01-14 08:48:38\",\"status\":\"【北京市】  快件离开 【北京】 已发往 【北京对外经贸】\"},{\"time\":\"2019-01-14 06:00:38\",\"status\":\"【北京市】  快件已经到达 【北京】\"},{\"time\":\"2019-01-12 06:30:14\",\"status\":\"【广州市】  快件离开 【广州中心】 已发往 【北京】\"},{\"time\":\"2019-01-12 05:53:52\",\"status\":\"【广州市】  快件已经到达 【广州中心】\"},{\"time\":\"2019-01-12 03:22:20\",\"status\":\"【中山市】  快件离开 【中山中心】 已发往 【广州中心】\"},{\"time\":\"2019-01-12 03:04:20\",\"status\":\"【中山市】  快件已经到达 【中山中心】\"},{\"time\":\"2019-01-12 01:36:20\",\"status\":\"【中山市】  快件离开 【中山南头】 已发往 【中山中心】\"},{\"time\":\"2019-01-12 01:36:05\",\"status\":\"【中山市】  【中山南头】（0760-22502000、0760-22502555） 的 商永昆 （18938787682） 已揽收\"}],\"deliverystatus\":\"3\",\"issign\":\"1\",\"expName\":\"中通快递\",\"expSite\":\"www.zto.com\",\"expPhone\":\"95311\",\"courier\":\"\",\"courierPhone\":\"\"}}";

            Map jsonMap = JSONObject.fromObject(respStr);
            jsonMap=jsonMap;
            return jsonMap;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

//    public static void main(String[] args){
//       new ExpressCenterTrackController().getTrackBody("");
//    }
}

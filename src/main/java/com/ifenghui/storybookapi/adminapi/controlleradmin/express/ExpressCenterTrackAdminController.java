package com.ifenghui.storybookapi.adminapi.controlleradmin.express;


import com.ifenghui.storybookapi.app.app.response.BaseResponse;
import com.ifenghui.storybookapi.app.express.entity.ExpressCenterTrack;
import com.ifenghui.storybookapi.app.express.response.ExpressCenterTrackResponse;
import com.ifenghui.storybookapi.app.express.response.ExpressCenterTracksResponse;
import com.ifenghui.storybookapi.app.express.service.ExpressCenterOrderService;
import com.ifenghui.storybookapi.app.express.service.ExpressCenterPhoneBindService;
import com.ifenghui.storybookapi.app.express.service.ExpressCenterTrackService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Date;

@CrossOrigin(origins = "*", maxAge = 3600)//支持跨域
@Controller
//@EnableAutoConfiguration
@Api(value = "运单", description = "物流中心")
@RequestMapping("/adminapi/expresscenter/track")
public class ExpressCenterTrackAdminController {

    @Autowired
    ExpressCenterPhoneBindService phoneBindService;

    @Autowired
    ExpressCenterOrderService orderService;

    @Autowired
    ExpressCenterTrackService trackService;

    @ApiOperation(value = "获得运单列表",notes = "")
    @RequestMapping(value = "/get_express_center_tracks",method = RequestMethod.GET)
    @ResponseBody
    ExpressCenterTracksResponse getCenterTracksByOrderId(Integer orderId,
                                                         String trackNo,
                                                         String phone,
                                                         String fullname,
                                                         String srcMark,
                                                         Integer year,
                                                         Integer month,
                                                         Integer pageNo,Integer pageSize){

        ExpressCenterTracksResponse response = new ExpressCenterTracksResponse();
//        List<ExpressCenterTrack> tracks = new ArrayList<>();
//        if (orderId != null || month != null || trackNo != null){
//            ExpressCenterTrack track = new ExpressCenterTrack();
//            track.setMonth(month);
//            track.setCenterOrderId(orderId);
//            track.setTrackNo(trackNo);
//            tracks= trackService.findAllTracks(track);
//        } else if (phone != null || fullname !=null ||srcType != null){
//            ExpressCenterOrder order=new ExpressCenterOrder();
//            if (phone != null){
//                order.setPhone(phone);
//            }
//            if (fullname != null){
//                order.setFullname(fullname);
//            }
//            if (srcType != null){
//                order.setSrcType(srcType);
//            }
//            List<ExpressCenterOrder> orders = orderService.findAllOrders(order);
//            for (ExpressCenterOrder o:orders){
//                List<ExpressCenterTrack> trackList = trackService.findAlByOrderId(o.getId());
//                for (ExpressCenterTrack t:trackList){
//                    tracks.add(t);
//                }
//            }
//        }else {
//            tracks = expressCenterTrackDao.findAll();
//        }
//        for (ExpressCenterTrack t:tracks){
//          ExpressCenterOrder order = orderService.findOne(t.getCenterOrderId());
//            t.setFullname(order.getFullname());
//            t.setPhone(order.getPhone());
//            t.setOrderTime(order.getOrderTime());
//            t.setSrcType(order.getSrcType());
//            t.setSrcMark(order.getSrcMark());
//        }
//        response.setExpressCenterTracks(tracks);

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
        Page<ExpressCenterTrack> page= trackService.findAll(track,new PageRequest(pageNo,pageSize,new Sort(Sort.Direction.DESC,"id")));
//        List<ExpressCenterTrack> tracks = trackService.findAllTracks(track);
//        Set<String> set = new HashSet<String>();
//        for (ExpressCenterTrack t:tracks){
//            set.add(t.getSrcMark());
//        }
        response.setExpressCenterTracks(page.getContent());
//        response.setSrcMarks(set);
        response.setJpaPage(page);

        return response;
    }


    @ApiOperation(value = "获得运单列表",notes = "")
    @RequestMapping(value = "/get_express_center_track",method = RequestMethod.GET)
    @ResponseBody
    ExpressCenterTrackResponse getCenterTrack(Integer id){
        //活动订单的物流信息，返回所有物流记录，并且按月份返回。

        ExpressCenterTrackResponse response = new ExpressCenterTrackResponse();

        ExpressCenterTrack tracks= trackService.findOne(id);

        response.setExpressCenterTrack(tracks);
//        response.setTrackBody(this.getTrackBody(tracks.getTrackNo()));

        return response;
    }

    @ApiOperation(value = "修改运单信息",notes = "")
    @RequestMapping(value = "/update_express_center_track",method = RequestMethod.PUT)
    @ResponseBody
    ExpressCenterTrackResponse updateCenterTrack(
            Integer id,
//   物流公司id 1圆通
            Integer trackType,
            //
            String trackNo,
            Integer centerOrderId,
            Integer isSend,
            Integer volCount,
            Integer year,
            Integer month
    ){
        //活动订单的物流信息，返回所有物流记录，并且按月份返回。

        ExpressCenterTrackResponse response = new ExpressCenterTrackResponse();

        ExpressCenterTrack tracks= trackService.findOne(id);
        Integer  count = tracks.getVolCount();
        if(trackType!=null){
            tracks.setTrackType(trackType);
        }
        if(trackNo!=null){
            tracks.setTrackNo(trackNo);
        }
        if(centerOrderId!=null){
            tracks.setCenterOrderId(centerOrderId);
        }
        if(isSend!=null){
            tracks.setIsSend(isSend);
        }
        if(volCount!=null){
            tracks.setVolCount(volCount);

        }
        if(year!=null){
            tracks.setYear(year);
        }
        if(month!=null){
            tracks.setMonth(month);
        }
        trackService.updateTrack(count,tracks);
        response.setExpressCenterTrack(tracks);


        return response;
    }


    @ApiOperation(value = "增加运单信息",notes = "")
    @RequestMapping(value = "/add_express_center_track",method = RequestMethod.POST)
    @ResponseBody
    ExpressCenterTrackResponse addCenterTrack(

//   物流公司id 1圆通
            Integer trackType,
            //
            String trackNo,
            Integer centerOrderId,
            Integer isSend,
            Integer volCount,
            Integer year,
            Integer month,
//冗余订单表的状态，如果订单已完成，所有状态全改变
            Integer orderIsOpen
    ){
        //活动订单的物流信息，返回所有物流记录，并且按月份返回。

        ExpressCenterTrackResponse response = new ExpressCenterTrackResponse();

        ExpressCenterTrack tracks=new ExpressCenterTrack();

        if(trackType!=null){
            tracks.setTrackType(trackType);
        }
        if(trackNo!=null){
            tracks.setTrackNo(trackNo);
        }
        if(centerOrderId!=null){
            tracks.setCenterOrderId(centerOrderId);
        }
        if(isSend!=null){
            tracks.setIsSend(isSend);
        }
        if(volCount!=null){
            tracks.setVolCount(volCount);
        }
        if(year!=null){
            tracks.setYear(year);
        }
        if(month!=null){
            tracks.setMonth(month);
        }
        if(orderIsOpen!=null){
            tracks.setOrderIsOpen(orderIsOpen);
        }
        tracks.setCreateTime(new Date());
        tracks.setSendTime(new Date());
        trackService.addTrack(tracks);
        response.setExpressCenterTrack(tracks);


        return response;
    }

    @ApiOperation(value = "删除运单信息",notes = "")
    @RequestMapping(value = "/delete_express_center_track",method = RequestMethod.DELETE)
    @ResponseBody
    BaseResponse deleteCenterTrack(Integer id){
        trackService.deleteTrack(id);
        return  new BaseResponse();
    }

}

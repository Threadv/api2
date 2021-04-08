package com.ifenghui.storybookapi.app.story.controller;
/**
 * Created by jia on 2016/12/22.
 */
import com.ifenghui.storybookapi.app.story.response.LabelResponse;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import com.ifenghui.storybookapi.app.story.entity.Label;
import com.ifenghui.storybookapi.app.story.service.LabelService;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

@Controller
@EnableAutoConfiguration
//@EnableAutoConfiguration
//@SpringBootApplication
//@ConfigurationProperties(prefix = "spring")
@RequestMapping("/label")
public class LabelController {

    @Autowired
    LabelService labelService;

    @RequestMapping(value="/getLabels",method = RequestMethod.GET)
    @ResponseBody
    LabelResponse getLabels(@ApiParam(value = "no")@RequestParam int pageNo
    ,@ApiParam(value = "size")@RequestParam int pageSize) {
//        return "Hello World!";
        Page<Label> labels=labelService.getLabels(pageNo,pageSize);
        LabelResponse t=new LabelResponse();
        System.out.println(labels);
        t.setLabel(labels.getContent().get(0));

        return t;
    }

    @RequestMapping(value="/getLabel",method = RequestMethod.GET)
    @ResponseBody
    LabelResponse getLabel(@ApiParam(value = "ID")@RequestParam Long id) {
//        return "Hello World!";
        Label label=labelService.getLabel(id);
        LabelResponse t=new LabelResponse();
        System.out.println(label);
        t.setLabel(label);

        return t;
    }

//    @RequestMapping(value="/getLabel",method = RequestMethod.GET)
//    @ResponseBody
//    LabelResponse getLabels(@ApiParam(value = "ID")@RequestParam Long id) {
//
//    }

    @RequestMapping(value="/updateLabel",method = RequestMethod.PUT)
    @ResponseBody
    LabelResponse updateLabel(@ApiParam(value = "ID")@RequestParam Long id,
                              @ApiParam(value = "内容")@RequestParam String content
                              ) throws Exception {
        Label label=labelService.getLabel(id.longValue());
        label.setContent(content);
        Label l=labelService.updateLabel(id,content);

        LabelResponse t=new LabelResponse();
        t.setLabel(l);
        return  t;
    }

        @RequestMapping(value="/addLabel",method = RequestMethod.POST)
    @ResponseBody
    LabelResponse addLabel(
                           @ApiParam(value = "内容")@RequestParam String content,
                           @ApiParam(value = "状态")@RequestParam Integer status,
                           @ApiParam(value = "排序")@RequestParam Integer order_by,
                           @ApiParam(value = "时间")@RequestParam String create_time
        ) throws ParseException {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            Date date = sdf.parse(create_time);
            Label label=new Label();
            label.setContent(content);
//            label.setCreate_time(date);
//            label.setOrder_by(order_by);
            label.setOrderBy(order_by);
            label.setStatus(status);
            Label l= labelService.addLabel(label);
//            System.out.println(label);
            LabelResponse t=new LabelResponse();
            t.setLabel(l);
            return  t;
    }

        @RequestMapping(value="/delLabel",method = RequestMethod.DELETE)
    @ResponseBody
    LabelResponse delLabel(@ApiParam(value = "ID")@RequestParam Long id) {
            labelService.delLabel(id);
            LabelResponse t=new LabelResponse();
//            t.setLabel(l);
            return  t;
    }


}

package com.ifenghui.storybookapi.app.app.controller;

import com.ifenghui.storybookapi.app.app.entity.Hotword;
import com.ifenghui.storybookapi.app.app.response.GetHotwordsResponse;
import com.ifenghui.storybookapi.app.app.service.HotwordService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@Controller
@EnableAutoConfiguration
//@EnableAutoConfiguration
//@SpringBootApplication
//@ConfigurationProperties(prefix = "spring")
@Api(value = "HotwordController", description = "热词推荐管理api")
@RequestMapping("/api/hotword")
public class HotwordController {
//    @JsonBackReference

    @Autowired
    HotwordService hotwordService;

    @ApiOperation(value = "获取热词推荐",notes = "")
    @RequestMapping(value = "/getHotwords",method = RequestMethod.GET)
    @ResponseBody
    GetHotwordsResponse getHotwordsApi(@ApiParam(value = "页码")@RequestParam Integer pageNo,
                                       @ApiParam(value = "条数")@RequestParam Integer pageSize) {

        //默认从0开始，所以需要减1
        Integer status = 1;
        GetHotwordsResponse getHotwordsResponse=new GetHotwordsResponse();
        List<Hotword> hotwords=this.hotwordService.getHotwordsByStatus(status,pageNo,pageSize);
        getHotwordsResponse.setHotwords(hotwords);

        return getHotwordsResponse;
    }

}

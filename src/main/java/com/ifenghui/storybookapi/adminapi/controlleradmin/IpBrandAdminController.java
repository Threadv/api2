package com.ifenghui.storybookapi.adminapi.controlleradmin;


import com.ifenghui.storybookapi.adminapi.controlleradmin.resp.GetIpBrandsResponse;
import com.ifenghui.storybookapi.adminapi.controlleradmin.resp.IpBrand;
import com.ifenghui.storybookapi.app.story.entity.IpLabel;
import com.ifenghui.storybookapi.app.story.entity.SerialStory;
import com.ifenghui.storybookapi.app.story.response.GetUserSerialStorysResponse;
import com.ifenghui.storybookapi.app.story.service.IpLabelService;
import com.ifenghui.storybookapi.app.story.service.IpLabelStoryService;
import com.ifenghui.storybookapi.app.story.service.SerialStoryService;
import com.ifenghui.storybookapi.app.story.service.StoryService;
import com.ifenghui.storybookapi.exception.ApiNotTokenException;
import com.ifenghui.storybookapi.style.IpBrandStyle;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;

@CrossOrigin(origins = "*", maxAge = 3600)//内部开发暂时支持跨域
@Controller
@EnableAutoConfiguration
@Api(value = "ip品牌管理", description = "ip品牌管理")
@RequestMapping("/adminapi/ipbrand")
public class IpBrandAdminController {

    @Autowired
    StoryService storyService;

    @Autowired
    SerialStoryService serialStoryService;


    @Autowired
    IpLabelService ipLabelService;
    @Autowired
    HttpServletRequest request;

    @Autowired
    IpLabelStoryService ipLabelStoryService;


    @ApiOperation(value = "返回IP品牌，综合管理,针对2.12版本")
    @RequestMapping(value = "/get_ipbrand_list",method = RequestMethod.GET)
    @ResponseBody
    GetIpBrandsResponse getIpBrandList(
        @ApiParam(value = "status") @RequestParam(required = false,defaultValue = "") Integer status,
//        @ApiParam(value = "pageNo") @RequestParam(required = false) Integer pageNo,
//        @ApiParam(value = "pageSize") @RequestParam Integer pageSize,
        @ApiParam(value = "ipBrandId") @RequestParam(required = false) Integer ipBrandId
    ) throws ApiNotTokenException {
//        if(pageNo==null){
//            pageNo=0;
//        }


        GetIpBrandsResponse response=new GetIpBrandsResponse();
        List<IpBrand> ipBrandList=new ArrayList();
        for(IpBrandStyle ipBrandStyle: IpBrandStyle.values()){
            if(ipBrandStyle==IpBrandStyle.DEFAULT){
                continue;
            }
            IpBrand ipBrand=new IpBrand(ipBrandStyle);
            if(ipBrandId!=null&&ipBrandId!=ipBrand.getId().intValue()){
                continue;
            }
            //查询label
            ipBrand.setIpLabels(ipLabelService.getIpLabelListByIpBrandId(ipBrand.getId()));
            //查询label中的故事
            for(IpLabel ipLabel:ipBrand.getIpLabels()){
                ipLabel.setIpLabelStories(ipLabelStoryService.getIpLabelStorysAndSetStoryByIpLabelId(ipLabel.getId()));
            }
            //查询故事集
            ipBrand.setSerialStories(serialStoryService.getSerialStoryByIpBrandId(ipBrand.getId()));
            ipBrandList.add(ipBrand);
        }
        SerialStory serialStory=new SerialStory();
        if(status!=null){
            serialStory.setStatus(status);
        }
        Page<SerialStory> serialStoryPage = serialStoryService.getSerialStoryList(serialStory,new PageRequest(0,999));

        response.setIpBrands(ipBrandList);
//        response.setJpaPage(new PageImpl(new PageRequest(0,10),10));
        return response;
    }


}

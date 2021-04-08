package com.ifenghui.storybookapi.adminapi.controlleradmin.popup;


import com.ifenghui.storybookapi.app.app.entity.PopupUser;
import com.ifenghui.storybookapi.app.app.response.popup.PopupUsersResponse;
import com.ifenghui.storybookapi.app.app.service.PopupUserService;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;


@CrossOrigin(origins = "*", maxAge = 3600)//内部开发暂时支持跨域
@Controller
@RestController
@RequestMapping(value = "/adminapi/popup/popupuser", name = "弹框user管理")
public class PopupUserAdminController {



    @Autowired
    PopupUserService popupUserService;



    @RequestMapping(value = "/getPopupUsers", method = RequestMethod.GET)
    @ApiOperation("获得所有弹框")
    PopupUsersResponse getAdminPopupUsers(

            @ApiParam(value = "pageNo") @RequestParam(required = false,defaultValue = "0") Integer pageNo,
            @ApiParam(value = "pageSize") @RequestParam(required = false,defaultValue = "20") Integer pageSize
    ) {
//        if(type==null)
        PopupUser popup=new PopupUser();

        PopupUsersResponse response=new PopupUsersResponse();
        Page<PopupUser> popups= popupUserService.findAll(popup,new PageRequest(pageNo,pageSize));
        response.setPopupUsers(popups.getContent());
        response.setJpaPage(popups);
        return response ;
    }

    @RequestMapping(value = "/deletePopupUser", method = RequestMethod.DELETE)
    @ApiOperation("del")
    PopupUsersResponse deletePopupUser(

            @ApiParam(value = "id") @RequestParam(required = false,defaultValue = "0") Integer id
    ) {
//        if(type==null)
//        PopupUser popup=new PopupUser();

        PopupUsersResponse response=new PopupUsersResponse();
        popupUserService.delete(id);
//        response.setPopupUsers(popups.getContent());
//        response.setPage(popups);
        return response ;
    }
}

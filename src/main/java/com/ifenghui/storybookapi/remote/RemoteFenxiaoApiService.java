package com.ifenghui.storybookapi.remote;

import com.ifenghui.storybookapi.remote.resp.GetFenxiaoUserResponse;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Date;

//@Component
@FeignClient(name = "fenxiaoapi",url= "${remote.fenxiao.api.url}")
//@FeignClient(url= "http://www.baidu.com")
public interface RemoteFenxiaoApiService {


    @RequestMapping(value = "/user/get_user_by_unionid", method = RequestMethod.GET)
    @ResponseBody
    @ApiOperation(value = "分銷用戶信息", notes = "分銷用戶信息")
    GetFenxiaoUserResponse getUserByUnionId(
            @ApiParam(value = "unionid") @RequestParam("unionid") String unionid
    );
}

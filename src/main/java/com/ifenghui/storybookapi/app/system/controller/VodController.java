package com.ifenghui.storybookapi.app.system.controller;


import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.exceptions.ClientException;
import com.aliyuncs.exceptions.ServerException;
import com.aliyuncs.profile.DefaultProfile;
import com.aliyuncs.vod.model.v20170321.GetVideoPlayAuthRequest;
import com.aliyuncs.vod.model.v20170321.GetVideoPlayAuthResponse;
import com.ifenghui.storybookapi.app.system.response.AliVodPlayResponse;
import com.ifenghui.storybookapi.util.AesCbcUtil;
import com.ifenghui.storybookapi.util.HttpRequest;
import io.swagger.annotations.Api;
import net.sf.json.JSONObject;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.HashMap;
import java.util.Map;

@Controller
@EnableAutoConfiguration
@RequestMapping("/api/vod")
@Api(value="系统服务",description = "系统服务")
public class VodController {


    @Value("${oss.accesskeyid}")
    String accessKeyId;

    @Value("${oss.accesskeysecret}")
    String accessKeySecret;
    /**
     * 阿里云vod播放鉴权

     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/alivod_play", method = RequestMethod.GET)
    public AliVodPlayResponse aliVodPlay(
            String vid
    ) {
        DefaultProfile profile = DefaultProfile.getProfile("cn-hangzhou", accessKeyId, accessKeySecret);
        DefaultAcsClient client = new DefaultAcsClient(profile);
        AliVodPlayResponse response=new AliVodPlayResponse();
        try{
            GetVideoPlayAuthResponse respa=getVideoPlayAuth(client,vid);

            response.setPlayAuth(respa.getPlayAuth());
            response.setRequestId(respa.getRequestId());
            response.setVideoMeta(respa.getVideoMeta());
        }catch (RuntimeException e){
            response.getStatus().setCode(0);
            response.getStatus().setMsg(e.getMessage());
        }





        return response;
    }

    GetVideoPlayAuthResponse getVideoPlayAuth(DefaultAcsClient client,String vid) {


        GetVideoPlayAuthRequest request = new GetVideoPlayAuthRequest();
        request.setVideoId(vid);

        GetVideoPlayAuthResponse response = null;
        try {
            response = client.getAcsResponse(request);
        } catch (ServerException e) {
            throw new RuntimeException("GetVideoPlayAuthRequest Server failed");
        } catch (ClientException e) {
            throw new RuntimeException("GetVideoPlayAuthRequest Client failed");
        }
        response.getPlayAuth();              //播放凭证
        response.getVideoMeta();             //视频Meta信息
        return response;
    }

}

package com.ifenghui.storybookapi.app.system.response;

import com.aliyuncs.vod.model.v20170321.GetVideoPlayAuthResponse;
import com.ifenghui.storybookapi.api.response.base.ApiResponse;

/**
 * 阿里云鉴权回调
 */
public class AliVodPlayResponse extends ApiResponse{

    private String requestId;
    private String playAuth;
    private GetVideoPlayAuthResponse.VideoMeta videoMeta;

    public String getRequestId() {
        return requestId;
    }

    public void setRequestId(String requestId) {
        this.requestId = requestId;
    }

    public String getPlayAuth() {
        return playAuth;
    }

    public void setPlayAuth(String playAuth) {
        this.playAuth = playAuth;
    }

    public GetVideoPlayAuthResponse.VideoMeta getVideoMeta() {
        return videoMeta;
    }

    public void setVideoMeta(GetVideoPlayAuthResponse.VideoMeta videoMeta) {
        this.videoMeta = videoMeta;
    }
}

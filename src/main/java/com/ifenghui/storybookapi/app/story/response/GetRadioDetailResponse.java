package com.ifenghui.storybookapi.app.story.response;

import com.ifenghui.storybookapi.app.app.response.BaseResponse;
import java.io.Serializable;

public class GetRadioDetailResponse extends BaseResponse implements Serializable{

    String audioContent;

    public String getAudioContent() {
        return audioContent;
    }

    public void setAudioContent(String audioContent) {
        this.audioContent = audioContent;
    }
}

package com.ifenghui.storybookapi.app.story.response;

import com.ifenghui.storybookapi.api.response.base.ApiResponse;
import com.ifenghui.storybookapi.app.story.entity.IpLabel;
import com.ifenghui.storybookapi.app.story.entity.SerialStory;
import com.ifenghui.storybookapi.app.story.entity.Story;

import java.util.List;

/**
 * 2.12版本增加
 * ip专区单条的合集
 */
public class GetIpLabelResponse extends ApiResponse {

    IpLabel ipLabel;

    public IpLabel getIpLabel() {
        return ipLabel;
    }

    public void setIpLabel(IpLabel ipLabel) {
        this.ipLabel = ipLabel;
    }
}

package com.ifenghui.storybookapi.app.story.response;
import com.ifenghui.storybookapi.api.response.base.ApiResponse;
import com.ifenghui.storybookapi.app.story.entity.Label;
/**
 * Created by jia on 2016/12/22.
 */
public class LabelResponse extends ApiResponse {
    Label label;

    public Label getLabel() {
        return label;
    }

    public void setLabel(Label label) {
        this.label = label;
    }

}

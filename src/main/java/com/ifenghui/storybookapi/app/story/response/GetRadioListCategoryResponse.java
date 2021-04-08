package com.ifenghui.storybookapi.app.story.response;

import com.ifenghui.storybookapi.app.app.response.BaseResponse;
import java.io.Serializable;
import java.util.List;

public class GetRadioListCategoryResponse extends BaseResponse implements Serializable{

   List<SerialCategory> serialCategories;

    public List<SerialCategory> getSerialCategories() {
        return serialCategories;
    }

    public void setSerialCategories(List<SerialCategory> serialCategories) {
        this.serialCategories = serialCategories;
    }
}

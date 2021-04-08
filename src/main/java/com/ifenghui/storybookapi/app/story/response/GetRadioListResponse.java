package com.ifenghui.storybookapi.app.story.response;

import com.ifenghui.storybookapi.app.app.response.BaseResponse;
import com.ifenghui.storybookapi.app.story.entity.Story;
import java.io.Serializable;
import java.util.List;

public class GetRadioListResponse extends BaseResponse implements Serializable{

    Integer serialId;
    String name;
    String content;
    List<Story> storys;


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public List<Story> getStorys() {
        return storys;
    }

    public void setStorys(List<Story> storys) {
        this.storys = storys;
    }

    public Integer getSerialId() {
        return serialId;
    }

    public void setSerialId(Integer serialId) {
        this.serialId = serialId;
    }
}

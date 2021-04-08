package com.ifenghui.storybookapi.app.transaction.response;

import com.ifenghui.storybookapi.app.app.response.BaseResponse;
import java.util.Date;

public class GetSvipResponse extends BaseResponse {


    String title;

    String img;

    Date endTime;


    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }

    public Date getEndTime() {
        return endTime;
    }

    public void setEndTime(Date endTime) {
        this.endTime = endTime;
    }

}

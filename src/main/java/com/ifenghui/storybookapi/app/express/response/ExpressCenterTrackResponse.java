package com.ifenghui.storybookapi.app.express.response;

/**
 * Created by jia on 2016/12/23.
 */

import com.fasterxml.jackson.annotation.JsonFormat;
import com.ifenghui.storybookapi.api.response.base.ApiPageResponse;
import com.ifenghui.storybookapi.app.express.entity.ExpressCenterMag;
import com.ifenghui.storybookapi.app.express.entity.ExpressCenterTrack;

import java.util.Date;
import java.util.List;
import java.util.Map;

public class ExpressCenterTrackResponse extends ApiPageResponse{
    ExpressCenterTrack expressCenterTrack;

    //杂志列表
    List<ExpressCenterMag> mags;
    //杂志1
    ExpressCenterMag mag1;
    //杂志2
    ExpressCenterMag mag2;
    //杂志标题
    String title;
    //收货地址
    String adress;

    String fullname;

    //三方接口数据结构
    Map trackBody;

    public ExpressCenterTrack getExpressCenterTrack() {
        return expressCenterTrack;
    }

    public void setExpressCenterTrack(ExpressCenterTrack expressCenterTrack) {
        this.expressCenterTrack = expressCenterTrack;
    }

    public Map getTrackBody() {
        return trackBody;
    }

    public void setTrackBody(Map trackBody) {
        this.trackBody = trackBody;
    }

    public List<ExpressCenterMag> getMags() {
        return mags;
    }

    public void setMags(List<ExpressCenterMag> mags) {
        this.mags = mags;
    }

    public ExpressCenterMag getMag1() {
        return mag1;
    }

    public void setMag1(ExpressCenterMag mag1) {
        this.mag1 = mag1;
    }

    public ExpressCenterMag getMag2() {
        return mag2;
    }

    public void setMag2(ExpressCenterMag mag2) {
        this.mag2 = mag2;
    }

    public String getTitle() {return title;}

    public void setTitle(String title) {this.title = title;}

    public String getAdress() {return adress;}

    public void setAdress(String adress) {this.adress = adress;}

    public String getFullname() {return fullname;}

    public void setFullname(String fullname) {this.fullname = fullname;}
}

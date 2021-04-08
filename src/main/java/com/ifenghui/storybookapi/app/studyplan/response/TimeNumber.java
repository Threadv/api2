package com.ifenghui.storybookapi.app.studyplan.response;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.math.BigDecimal;
import java.text.DecimalFormat;

public class TimeNumber {

    Integer id;
    Integer number;

    public TimeNumber(Integer id, Integer number) {
        this.id = id;
        this.number = number;
    }

    @JsonProperty("name")
    public String getName() {
        String endString = "分钟";
        if(this.number >= 3600) {
            endString = "小时";
        }
        if(this.id.equals(1)) {
            return "在线阅读\n" + dateFormat(this.number) + endString;
        } else if(this.id.equals(2)) {
            return "收听音频\n" + dateFormat(this.number) + endString;
        } else if(this.id.equals(3)) {
            return "益智训练\n" + dateFormat(this.number) + endString;
        } else {
            return "";
        }
    }

    private String dateFormat(Integer time) {
        float hour = time.floatValue() / 60;
        if(time >= 3600) {
            hour = time.floatValue() / 3600;
        }
        int hourInt = Math.round(hour);
        return String.valueOf(hourInt);
    }

    @JsonProperty("imgPath")
    public String getImgPath() {
        if(this.id.equals(1)) {
            return "http://storybook.oss-cn-hangzhou.aliyuncs.com/weekplan/week_img_v1_1.png";
        } else if(this.id.equals(2)) {
            return "http://storybook.oss-cn-hangzhou.aliyuncs.com/weekplan/week_img_v1_2.png";
        } else if(this.id.equals(3)) {
            return "http://storybook.oss-cn-hangzhou.aliyuncs.com/weekplan/week_img_v1_3.png";
        } else {
            return "";
        }
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }
}

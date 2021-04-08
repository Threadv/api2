package com.ifenghui.storybookapi.app.express.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.ifenghui.storybookapi.config.MyEnv;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

/**
 * 物流中心订单
 */
@Entity
@Table(name="story_express_center_mag")
@JsonIgnoreProperties(value = {"hibernateLazyInitializer", "handler"},ignoreUnknown = true)
public class ExpressCenterMag implements Serializable {
    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    Integer id;

//   物流公司id 1圆通
    String title;
    //
    String cover;

    Integer year;

    Integer month;

    //在当月中的排序
    Integer position;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getCover() {
        return cover;
    }

    public String getCoverUrl() {
        return MyEnv.env.getProperty("oss.url")+cover;
    }

    public void setCover(String cover) {
        this.cover = cover;
    }

    public Integer getYear() {
        return year;
    }

    public void setYear(Integer year) {
        this.year = year;
    }

    public Integer getMonth() {
        return month;
    }

    public void setMonth(Integer month) {
        this.month = month;
    }

    public Integer getPosition() {
        return position;
    }

    public void setPosition(Integer position) {
        this.position = position;
    }
}

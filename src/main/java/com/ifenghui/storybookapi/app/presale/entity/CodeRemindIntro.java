package com.ifenghui.storybookapi.app.presale.entity;

/**
 * @program: api2
 * @description: 兑换码提示信息封装类
 * @author: wjs
 * @create: 2018-12-05 13:39
 **/
public class CodeRemindIntro {

    Integer id;

    /**
     * 兑换码提示信息
     */
    String title;

    public CodeRemindIntro() {
    }

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
}

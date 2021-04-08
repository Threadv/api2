package com.ifenghui.storybookapi.app.story.response;


/**
 * 信息弹框内容
 */
public class Prompt {


    Integer id;

    String name;

    String url;

    Integer planStyle;

    public Prompt(Integer id,String name,String url,int planStyle) {

        this.id=id;
        this.name=name;
        this.url=url;
        this.planStyle=planStyle;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public Integer getPlanStyle() {
        return planStyle;
    }

    public void setPlanStyle(Integer planStyle) {
        this.planStyle = planStyle;
    }
}

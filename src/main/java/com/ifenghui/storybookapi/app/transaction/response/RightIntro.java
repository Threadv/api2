package com.ifenghui.storybookapi.app.transaction.response;

/**
 * @Date: 2019/1/17 11:07
 * @Description:
 */
public class RightIntro {

    String title;
    String icon;
    String content;
    Integer price;
    /**购买须知*/
    String intro;

    public RightIntro(String title,String content,Integer price,String intro,String icon) {

        this.title=title;
        this.content=content;
        this.price=price;
        this.intro=intro;
        this.icon=icon;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Integer getPrice() {
        return price;
    }

    public void setPrice(Integer price) {
        this.price = price;
    }

    public String getIntro() {
        return intro;
    }

    public void setIntro(String intro) {
        this.intro = intro;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }
}

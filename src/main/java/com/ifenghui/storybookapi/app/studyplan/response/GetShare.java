package com.ifenghui.storybookapi.app.studyplan.response;

public class GetShare {

    Integer id;
    String name;
    String url;
    String content;
    String imgPath;
    Integer width;
    Integer height;

    public GetShare(Integer id,String name,String url,String content,String imgPath,Integer width,Integer height) {

        this.id=id;
        this.name=name;
        this.url=url;
        this.content=content;
        this.imgPath=imgPath;
        this.width=width;
        this.height=height;
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

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getImgPath() {
        return imgPath;
    }

    public void setImgPath(String imgPath) {
        this.imgPath = imgPath;
    }

    public Integer getWidth() {
        return width;
    }

    public void setWidth(Integer width) {
        this.width = width;
    }

    public Integer getHeight() {
        return height;
    }

    public void setHeight(Integer height) {
        this.height = height;
    }
}

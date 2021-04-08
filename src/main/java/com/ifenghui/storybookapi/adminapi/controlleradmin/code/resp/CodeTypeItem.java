package com.ifenghui.storybookapi.adminapi.controlleradmin.code.resp;

import com.ifenghui.storybookapi.style.VipGoodsStyle;

public class CodeTypeItem {
    int id;
    String title;
    String content;
    public CodeTypeItem(){

    }

    public CodeTypeItem(VipGoodsStyle vipGoodsStyle){
        this.id=vipGoodsStyle.getId();
        this.title=vipGoodsStyle.name();
        this.content=vipGoodsStyle.getTitle();
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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
}

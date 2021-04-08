package com.ifenghui.storybookapi.style;

/**
 * 2019-2-21
 * weisiliang
 * 2.12版本，添加ip专区和故事集混排功能，增加了这个枚举
 */
public enum IpBrandStyle {
    DEFAULT(0, "无品牌"),
    WULALA(1,"乌拉拉"),
    CHIELD(2,"完美小孩"),
    XIYOUJI(3, "西游记")
    ;

    int id;
    String name;

    IpBrandStyle(int id, String name){
        this.id=id;
        this.name=name;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public static IpBrandStyle getById(int id){
        for(IpBrandStyle style: IpBrandStyle.values()){
            if(style.getId()==id){
                return style;
            }
        }
        return null;
    }
}

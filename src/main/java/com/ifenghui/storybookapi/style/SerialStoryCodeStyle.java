package com.ifenghui.storybookapi.style;

import com.ifenghui.storybookapi.config.MyEnv;

/**
 * 系列故事兑换码
 */
public enum SerialStoryCodeStyle {

    /**
     *  兑换类型
     *
     *  故事集兑换，这里的id跟数据库中故事集id相同
     */
    WULALA_SERIAL(1,"乌拉拉探索文明古国","serial.wulala.price",SerialStoryDetailStyle.WULALA_SERIAL),
    AUDIO_SERIAL(2,"故事飞船精选音频","serial.audio.price",SerialStoryDetailStyle.AUDIO_SERIAL),
    XIYOUJI_SERIAL(42,"西游记","serial.audio.price",null),
    XIYOUJI_SERIAL_2(60,"西游记2","serial.audio.price",null),
    ;

    int id;
    String name;
    String priceCode;
    SerialStoryDetailStyle serialStoryDetailStyle;
    SerialStoryCodeStyle(int id, String name, String priceCode, SerialStoryDetailStyle serialStoryDetailStyle){
        this.id=id;
        this.name=name;
        this.priceCode=priceCode;
        this.serialStoryDetailStyle=serialStoryDetailStyle;
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

    public String getPriceCode() {
        return priceCode;
    }

    public Integer getPrice() {
        return Integer.parseInt(MyEnv.env.getProperty(priceCode));
    }

    public void setPriceCode(String priceCode) {
        this.priceCode = priceCode;
    }

    public static SerialStoryCodeStyle getById(int id) {
        for (SerialStoryCodeStyle serialStoryCodeStyle : SerialStoryCodeStyle.values()) {
            if (serialStoryCodeStyle.getId() == id) {
                return serialStoryCodeStyle;
            }
        }
        return null;
    }

    public SerialStoryDetailStyle getSerialStoryDetailStyle() {
        return serialStoryDetailStyle;
    }

    public void setSerialStoryDetailStyle(SerialStoryDetailStyle serialStoryDetailStyle) {
        this.serialStoryDetailStyle = serialStoryDetailStyle;
    }
}

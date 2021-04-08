package com.ifenghui.storybookapi.style;

import java.util.Map;

public enum NoticeStyle {

    /*  1外链
    2单本
    3分组
    4故事集
    5文字
    6课程*/

    BUY_LESSON(1,"恭喜您购买{{lessonNum}}节{{name}}课程成功！获得系统奖励{{starCount}}颗星星，可至星乐园查看。","", RedirectStyle.FONT),
    ANDROID(2,"恭喜您充值{{money}}元成功，可前往 我的->我的账户 查看余额。同时您已享有余额支付再享{{discount}}折优惠权益。","",RedirectStyle.FONT),
    IOS(3,"恭喜您充值{{money}}元成功，可前往 我的->我的账户 查看余额。","",RedirectStyle.FONT),
    IOS_REWARD(4,"恭喜您充值{{money}}元成功，可前往 我的->我的账户 查看余额。同时获得50元代金券一张，代金券有效期3个月。","",RedirectStyle.FONT),
    BUY_SVIP(5, "恭喜您购买故事飞船会员卡","",RedirectStyle.FONT),
    BUY_ABILITY(7, "恭喜您成功开通故事飞船宝宝会读","",RedirectStyle.FONT),
    SHARE(6,"恭喜您，成功分享宝宝学习成果，请添加客服微信 storyship_01，领取您的杂志奖励。","",RedirectStyle.FONT);

    int id;
    String content;
    String icon;
    RedirectStyle noticeTypeStyle;
    NoticeStyle(int id, String content, String icon, RedirectStyle noticeTypeStyle){
        this.id = id;
        this.content = content;
        this.icon = icon;
        this.noticeTypeStyle = noticeTypeStyle;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public static NoticeStyle getById(int id){
        switch (id){
            case 1:
                return BUY_LESSON;
            case 2:
                return ANDROID;
            case 3:
                return IOS;
            case 4:
                return IOS_REWARD;
            default:
                return null;
        }
    }

    public String getContent(Map<String, String> contentMap) {
        String result=content;
        for(Map.Entry<String,String> entry: contentMap.entrySet()){
            result=result.replace("{{"+entry.getKey()+"}}",entry.getValue());
        }
        return result;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getContent() {
        return content;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public RedirectStyle getNoticeTypeStyle() {
        return noticeTypeStyle;
    }

    public void setNoticeTypeStyle(RedirectStyle noticeTypeStyle) {
        this.noticeTypeStyle = noticeTypeStyle;
    }
}
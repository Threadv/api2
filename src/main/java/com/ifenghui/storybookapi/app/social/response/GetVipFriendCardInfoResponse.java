package com.ifenghui.storybookapi.app.social.response;

import com.ifenghui.storybookapi.app.app.response.BaseResponse;
import com.ifenghui.storybookapi.app.social.entity.VipFriendCard;
import com.ifenghui.storybookapi.app.user.entity.User;

public class GetVipFriendCardInfoResponse extends BaseResponse {

    VipFriendCard vipFriendCard;

    String nick;

    String headImg;

    String storyImg;

    public String getNick() {
        return nick;
    }

    public void setNick(String nick) {
        this.nick = nick;
    }

    public String getHeadImg() {
        return headImg;
    }

    public void setHeadImg(String headImg) {
        this.headImg = headImg;
    }

    public String getStoryImg() {
        return storyImg;
    }

    public void setStoryImg(String storyImg) {
        this.storyImg = storyImg;
    }

    public VipFriendCard getVipFriendCard() {
        return vipFriendCard;
    }

    public void setVipFriendCard(VipFriendCard vipFriendCard) {
        this.vipFriendCard = vipFriendCard;
    }
}

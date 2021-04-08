package com.ifenghui.storybookapi.app.social.response;

import com.ifenghui.storybookapi.api.response.base.ApiPageResponse;
import com.ifenghui.storybookapi.app.social.entity.VipFriendCard;

import java.util.List;

public class GetVipFriendCardPageResponse extends ApiPageResponse {

    List<VipFriendCard> vipFriendCardList;

    public List<VipFriendCard> getVipFriendCardList() {
        return vipFriendCardList;
    }

    public void setVipFriendCardList(List<VipFriendCard> vipFriendCardList) {
        this.vipFriendCardList = vipFriendCardList;
    }
}

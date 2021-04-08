package com.ifenghui.storybookapi.app.user.response;

/**
 * Created by jia on 2016/12/23.
 */
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.ifenghui.storybookapi.api.response.base.ApiResponse;
import com.ifenghui.storybookapi.config.MyEnv;
import com.ifenghui.storybookapi.app.user.entity.UserToken;
@JsonIgnoreProperties(value = {"hibernateLazyInitializer", "handler"},ignoreUnknown = true)
public class PhoneRegisterResponse extends ApiResponse {
    public PhoneRegisterResponse(){
        this.getStatus().setMsg(MyEnv.getMessage("reg.success"));
    }
    UserToken userToken;
    public UserToken getUserToken() {
        return userToken;
    }

    public void setUserToken(UserToken userToken) {
        this.userToken = userToken;
    }

    Integer isHasFriendCard;

    public Integer getIsHasFriendCard() {
        return isHasFriendCard;
    }

    public void setIsHasFriendCard(Integer isHasFriendCard) {
        this.isHasFriendCard = isHasFriendCard;
    }
}

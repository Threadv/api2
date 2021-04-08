package com.ifenghui.storybookapi.app.app.response.popup;




import com.ifenghui.storybookapi.api.response.base.ApiPageResponse;
import com.ifenghui.storybookapi.app.app.entity.PopupUser;

import java.util.List;

public class PopupUsersResponse extends ApiPageResponse{
    List<PopupUser> popupUsers;

    public List<PopupUser> getPopupUsers() {
        return popupUsers;
    }

    public void setPopupUsers(List<PopupUser> popupUsers) {
        this.popupUsers = popupUsers;
    }
}

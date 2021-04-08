package com.ifenghui.storybookapi.app.app.response.popup;


import com.ifenghui.storybookapi.api.response.base.ApiPageResponse;
import com.ifenghui.storybookapi.app.app.entity.Popup;

import java.util.List;

public class PopupsResponse extends ApiPageResponse{
    List<Popup> popups;

    public List<Popup> getPopups() {
        return popups;
    }

    public void setPopups(List<Popup> popups) {
        this.popups = popups;
    }
}

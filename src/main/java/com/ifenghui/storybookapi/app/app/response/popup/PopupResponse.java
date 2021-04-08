package com.ifenghui.storybookapi.app.app.response.popup;


import com.ifenghui.storybookapi.app.app.entity.Popup;
import com.ifenghui.storybookapi.app.app.response.BaseResponse;

public class PopupResponse extends BaseResponse {
    Popup popup;

    public Popup getPopup() {
        return popup;
    }

    public void setPopup(Popup popup) {
        this.popup = popup;
    }
}

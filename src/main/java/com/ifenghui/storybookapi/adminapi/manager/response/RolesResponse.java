package com.ifenghui.storybookapi.adminapi.manager.response;



import com.ifenghui.storybookapi.app.app.response.BaseResponse;

import java.util.List;

public class RolesResponse extends BaseResponse {
    List<RoleObj> roles;

    public List<RoleObj> getRoles() {
        return roles;
    }

    public void setRoles(List<RoleObj> roles) {
        this.roles = roles;
    }
}

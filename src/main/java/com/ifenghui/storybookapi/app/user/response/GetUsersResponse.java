package com.ifenghui.storybookapi.app.user.response;

import com.ifenghui.storybookapi.api.response.base.ApiPageResponse;
import com.ifenghui.storybookapi.api.response.base.ApiResponse;
import com.ifenghui.storybookapi.app.user.entity.User;

import java.util.List;

/**
 * Created by wslhk on 2016/12/19.
 */
public class GetUsersResponse extends ApiPageResponse {
    List<User> users;

    public List<User> getUsers() {
        return users;
    }

    public void setUsers(List<User> users) {
        this.users = users;
    }
}

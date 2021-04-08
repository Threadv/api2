package com.ifenghui.storybookapi.adminapi.manager.service;


import com.ifenghui.storybookapi.adminapi.manager.entity.ManagerToken;

public interface ManagerTokenService {

    ManagerToken save(ManagerToken managerToken);

    ManagerToken findOne(Integer id);

    ManagerToken findOneByToken(String token);

    ManagerToken logout(String token);

//    ManagerToken createTokenByManagerId(int userId);
}

package com.ifenghui.storybookapi.app.shop.service;

import com.ifenghui.storybookapi.app.shop.entity.ShopUserAddress;
import java.util.List;

public interface ShopUserAddressService {


    /**
     * 删除收货地址
     * @param id
     * @return
     */
    void deleteAddress(Integer id,Integer userId);

    /**
     * 编辑收货地址
     * @param id
     * @param userId
     * @param phone
     * @param address
     * @param province
     * @param city
     * @param county
     * @return
     */
    ShopUserAddress editAddress(Integer id,Integer userId,String receiver,String phone,String address,String province,String city,String county);
    /**
     * 添加地址
     * @param userId
     * @param receiver
     * @param phone
     * @param address
     * @param province
     * @param city
     * @param county
     * @return
     */
    ShopUserAddress addAddress(Integer userId,String receiver ,String phone,String address,String province,String city,String county);

    /**
     * 默认地址
     * @param userId
     * @return
     */
    ShopUserAddress  getLastestUserAddress(Integer userId);

    /**
     * 查询地址
     * @param id
     * @return
     */
    ShopUserAddress findAddressById(Integer id);

    /**
     * 查询用户地址列表
     * @param userId
     * @return
     */
    List<ShopUserAddress> findAddressListByUserId(Integer userId);
}

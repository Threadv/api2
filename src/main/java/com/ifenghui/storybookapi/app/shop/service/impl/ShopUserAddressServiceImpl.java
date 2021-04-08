package com.ifenghui.storybookapi.app.shop.service.impl;

import com.ifenghui.storybookapi.app.shop.dao.ShopUserAddressDao;
import com.ifenghui.storybookapi.app.shop.entity.ShopUserAddress;
import com.ifenghui.storybookapi.app.shop.service.ShopUserAddressService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

@Component
@Transactional
public class ShopUserAddressServiceImpl implements ShopUserAddressService {


    @Autowired
    ShopUserAddressDao shopUserAddressDao;


    /**
     * 删除收货地址
     *
     * @param id
     * @return
     */
    @Override
    public void deleteAddress(Integer id, Integer userId) {

        ShopUserAddress shopUserAddress = shopUserAddressDao.findOneByUserIdAndId(id, userId);
        shopUserAddressDao.delete(shopUserAddress);
    }

    /**
     * 编辑收货地址
     *
     * @param id
     * @param userId
     * @param phone
     * @param address
     * @param province
     * @param city
     * @param county
     * @return
     */
    @Override
    public ShopUserAddress editAddress(Integer id, Integer userId, String receiver, String phone, String address, String province, String city, String county) {

        ShopUserAddress shopUserAddress = shopUserAddressDao.findOne(id);

        shopUserAddress.setUserId(userId);
        shopUserAddress.setReceiver(receiver);
        shopUserAddress.setPhone(phone);
        shopUserAddress.setAddress(address);
        shopUserAddress.setProvince(province);
        shopUserAddress.setCity(city);
        shopUserAddress.setCounty(county);
        shopUserAddress.setCreateTime(new Date());
        return shopUserAddress;
    }

    /**
     * 添加地址
     *
     * @param userId
     * @param receiver
     * @param phone
     * @param address
     * @param province
     * @param city
     * @param county
     * @return
     */
    @Override
    public ShopUserAddress addAddress(Integer userId, String receiver, String phone, String address, String province, String city, String county) {

        ShopUserAddress shopUserAddress = new ShopUserAddress();

        shopUserAddress.setUserId(userId);
        shopUserAddress.setReceiver(receiver);
        shopUserAddress.setPhone(phone);
        shopUserAddress.setAddress(address);
        shopUserAddress.setProvince(province);
        shopUserAddress.setCity(city);
        shopUserAddress.setCounty(county);
        shopUserAddress.setCreateTime(new Date());
        return shopUserAddressDao.save(shopUserAddress);
    }

    /**
     * 默认地址
     *
     * @param userId
     * @return
     */
    @Override
    public ShopUserAddress getLastestUserAddress(Integer userId) {

        Sort sort = new Sort(Sort.Direction.ASC, "id");
        List<ShopUserAddress> list = shopUserAddressDao.getLastestUserAddress(userId, sort);
        if (list.size() > 0) {
            return list.get(0);
        }
        return null;
    }

    /**
     * 查询地址
     *
     * @param id
     * @return
     */
    @Override
    public ShopUserAddress findAddressById(Integer id) {

        ShopUserAddress shopUserAddress = shopUserAddressDao.findOne(id);
        return shopUserAddress;
    }

    /**
     * 查询用户地址列表
     *
     * @param userId
     * @return
     */
    @Override
    public List<ShopUserAddress> findAddressListByUserId(Integer userId) {
        List<ShopUserAddress> addressListByUserId = shopUserAddressDao.findAddressListByUserId(userId);
        return addressListByUserId;
    }
}

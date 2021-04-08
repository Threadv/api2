package com.ifenghui.storybookapi.app.shop.response;

import com.ifenghui.storybookapi.app.app.response.BaseResponse;
import com.ifenghui.storybookapi.app.shop.entity.ShopUserAddress;

import java.util.List;

public class ShopUserAddressListResponse extends BaseResponse {

    Integer maxSize;

    Integer size;

    List<ShopUserAddress> shopUserAddressesList;

    public Integer getMaxSize() {
        return maxSize;
    }

    public void setMaxSize(Integer maxSize) {
        this.maxSize = maxSize;
    }

    public Integer getSize() {
        return size;
    }

    public void setSize(Integer size) {
        this.size = size;
    }

    public List<ShopUserAddress> getShopUserAddressesList() {
        return shopUserAddressesList;
    }

    public void setShopUserAddressesList(List<ShopUserAddress> shopUserAddressesList) {
        this.shopUserAddressesList = shopUserAddressesList;
    }
}

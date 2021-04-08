package com.ifenghui.storybookapi.app.shop.response;


import com.ifenghui.storybookapi.app.app.response.BaseResponse;
import com.ifenghui.storybookapi.app.shop.entity.ShopUserAddress;

public class ShopUserAddressResponse extends BaseResponse {

    ShopUserAddress shopUserAddresses;

    public ShopUserAddress getShopUserAddresses() {
        return shopUserAddresses;
    }

    public void setShopUserAddresses(ShopUserAddress shopUserAddresses) {
        this.shopUserAddresses = shopUserAddresses;
    }
}

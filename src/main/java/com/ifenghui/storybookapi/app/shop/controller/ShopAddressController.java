package com.ifenghui.storybookapi.app.shop.controller;

import com.ifenghui.storybookapi.app.app.response.BaseResponse;
import com.ifenghui.storybookapi.app.shop.entity.ShopUserAddress;
import com.ifenghui.storybookapi.app.shop.response.GetDeleteAddressSizeResponse;
import com.ifenghui.storybookapi.app.shop.response.ShopUserAddressListResponse;
import com.ifenghui.storybookapi.app.shop.response.ShopUserAddressResponse;
import com.ifenghui.storybookapi.app.shop.service.ShopUserAddressService;
import com.ifenghui.storybookapi.app.user.service.UserService;
import com.ifenghui.storybookapi.exception.ApiException;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@EnableAutoConfiguration
@Api(value = "飞船商城地址接口", description = "飞船商城地址接口")
@RequestMapping("/api/shop/address")
public class ShopAddressController {


    @Autowired
    UserService userService;

    @Autowired
    ShopUserAddressService shopUserAddressService;

    @CrossOrigin
    @ApiOperation(value = "删除收货地址")
    @RequestMapping(value = "/delete_address", method = RequestMethod.POST)
    @ResponseBody
    GetDeleteAddressSizeResponse deleteAddress(
            @ApiParam(value = "token") @RequestParam() String token,
            @ApiParam(value = "id") @RequestParam() Integer id
    ) {
        GetDeleteAddressSizeResponse response = new GetDeleteAddressSizeResponse();

        Long userId;
        if (token == null || token.length() <= 0) {
            userId = 0L;
        } else {
            userId = userService.checkAndGetCurrentUserId(token);
        }

        shopUserAddressService.deleteAddress(id,userId.intValue());
        List<ShopUserAddress> addressList = shopUserAddressService.findAddressListByUserId(userId.intValue());

        response.setSize(addressList.size());
        response.setMaxSize(10);
        return response;
    }

    @CrossOrigin
    @ApiOperation(value = "编辑收货地址")
    @RequestMapping(value = "/edit_address", method = RequestMethod.POST)
    @ResponseBody
    ShopUserAddressResponse editAddress(
            @ApiParam(value = "id") @RequestParam() Integer id,
            @ApiParam(value = "收件人") @RequestParam() String receiver,
            @ApiParam(value = "token") @RequestParam() String token,
            @ApiParam(value = "电话") @RequestParam() String phone,
            @ApiParam(value = "地址") @RequestParam() String address,
            @ApiParam(value = "省") @RequestParam() String province,
            @ApiParam(value = "市") @RequestParam() String city,
            @ApiParam(value = "区") @RequestParam() String county
    ) throws ApiException {

        ShopUserAddressResponse response = new ShopUserAddressResponse();

        Long userId;
        if (token == null || token.length() <= 0) {
            userId = 0L;
        } else {
            userId = userService.checkAndGetCurrentUserId(token);
        }
        ShopUserAddress shopUserAddress = shopUserAddressService.editAddress(id, userId.intValue(),receiver, phone, address, province, city, county);
        response.setShopUserAddresses(shopUserAddress);
        return response;
    }

    @CrossOrigin
    @ApiOperation(value = "添加收货地址")
    @RequestMapping(value = "/add_address", method = RequestMethod.POST)
    @ResponseBody
    ShopUserAddressResponse addAddress(
            @ApiParam(value = "token") @RequestParam() String token,
            @ApiParam(value = "收件人") @RequestParam() String receiver,
            @ApiParam(value = "电话") @RequestParam() String phone,
            @ApiParam(value = "地址") @RequestParam() String address,
            @ApiParam(value = "省") @RequestParam() String province,
            @ApiParam(value = "市") @RequestParam() String city,
            @ApiParam(value = "区") @RequestParam() String county
    ) throws ApiException {

        ShopUserAddressResponse response = new ShopUserAddressResponse();

        Long userId;
        if (token == null || token.length() <= 0) {
            userId = 0L;
        } else {
            userId = userService.checkAndGetCurrentUserId(token);
        }
        List<ShopUserAddress> list = shopUserAddressService.findAddressListByUserId(userId.intValue());
        if(list.size()>=10){
            response.getStatus().setCode(0);
            response.getStatus().setMsg("添加数量已达上限");
            return response;
        }
        ShopUserAddress shopUserAddress = shopUserAddressService.addAddress(userId.intValue(),receiver, phone, address, province, city, county);
        response.setShopUserAddresses(shopUserAddress);
        return response;
    }

    @CrossOrigin
    @ApiOperation(value = "默认收货地址")
    @RequestMapping(value = "/get_default_address", method = RequestMethod.GET)
    @ResponseBody
    ShopUserAddressResponse getDefaultAddress(
            @RequestHeader(required = false) String ssToken
    ) throws ApiException {

        ShopUserAddressResponse response = new ShopUserAddressResponse();
        Long userId;
        if (ssToken == null || ssToken.length() <= 0) {
            userId = 0L;
        } else {
            userId = userService.checkAndGetCurrentUserId(ssToken);
        }

        ShopUserAddress address = shopUserAddressService.getLastestUserAddress(userId.intValue());
        response.setShopUserAddresses(address);
        return response;
    }

    @CrossOrigin
    @ApiOperation(value = "单个收货地址")
    @RequestMapping(value = "/get_address", method = RequestMethod.GET)
    @ResponseBody
    ShopUserAddressResponse getAddress(
            @ApiParam(value = "地址id") @RequestParam() Integer id
    ) throws ApiException {

        ShopUserAddressResponse response = new ShopUserAddressResponse();
        ShopUserAddress address = shopUserAddressService.findAddressById(id);
        response.setShopUserAddresses(address);
        return response;
    }

    @CrossOrigin
    @ApiOperation(value = "收货地址列表")
    @RequestMapping(value = "/get_address_list", method = RequestMethod.GET)
    @ResponseBody
    ShopUserAddressListResponse getAddressList(
            @ApiParam(value = "token") @RequestParam() String token
    ) throws ApiException {

        ShopUserAddressListResponse response = new ShopUserAddressListResponse();

        Long userId;
        if (token == null || token.length() <= 0) {
            userId = 0L;
        } else {
            userId = userService.checkAndGetCurrentUserId(token);
        }
        List<ShopUserAddress> addressList = shopUserAddressService.findAddressListByUserId(userId.intValue());

        response.setMaxSize(10);
        response.setSize(addressList.size());
        response.setShopUserAddressesList(addressList);
        return response;
    }


}

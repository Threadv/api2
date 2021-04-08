package com.ifenghui.storybookapi.app.transaction.controller;

import com.ifenghui.storybookapi.api.response.exception.ExceptionResponse;
import com.ifenghui.storybookapi.app.transaction.entity.single.ShoppingTrolley;
import com.ifenghui.storybookapi.app.transaction.response.AddShoppingCartResponse;
import com.ifenghui.storybookapi.app.transaction.response.DelShoppingCartResponse;
import com.ifenghui.storybookapi.app.transaction.response.EditShoppingCartResponse;
import com.ifenghui.storybookapi.app.transaction.response.GetShoppingCartResponse;
import com.ifenghui.storybookapi.exception.ApiException;
import com.ifenghui.storybookapi.exception.ApiNotTokenException;
import com.ifenghui.storybookapi.app.transaction.service.ShoppingCartService;
import com.ifenghui.storybookapi.app.user.service.UserService;
import io.swagger.annotations.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@Controller
@EnableAutoConfiguration
@RequestMapping("/api/shoppingcart")
@Api(value="购物车",description = "购物车相关")
public class ShoppingCartController {

    @Autowired
    UserService userService;

    @Autowired
    ShoppingCartService shoppingCartService;

    @RequestMapping(value="/getShoppingCart",method = RequestMethod.GET)
    @ResponseBody
    @ApiOperation(value = "购物车列表", notes = "")
    public GetShoppingCartResponse getShoppingCart(
            @ApiParam(value = "用户token")@RequestParam String  token,
            @ApiParam(value = "页码")@RequestParam Integer pageNo,
            @ApiParam(value = "条数")@RequestParam Integer pageSize
    )throws ApiNotTokenException {
        Long userId;
        userId = userService.checkAndGetCurrentUserId(token);
       //默认从0开始，所以需要减1h
        GetShoppingCartResponse getShoppingCartResponse=new GetShoppingCartResponse();
        //分页获取购物车数据
        Page<ShoppingTrolley> shoppingCarts = shoppingCartService.getShoppingCartByUserId(userId,pageNo,pageSize);

        getShoppingCartResponse.setShoppingCarts(shoppingCarts.getContent());
        getShoppingCartResponse.setJpaPage(shoppingCarts);
        //saleRule优惠规则
//        String discountRule = "优惠规则：\n1.一次购买3本以及以上可享受9折优惠\n2.一次购买5本以上可享受8折优惠";
        String discountRule="";
        getShoppingCartResponse.setDiscountRule(discountRule);
        return getShoppingCartResponse;
    }
    @RequestMapping(value="/delShoppingCart",method = RequestMethod.POST)
    @ResponseBody
    @ApiOperation(value = "删除购物车", notes = "")
    @ApiResponses({@ApiResponse(code=206,message="无权限删除",response = ExceptionResponse.class)})
    public DelShoppingCartResponse delShoppingCart(
            @ApiParam(value = "用户token")@RequestParam String  token,
//              @ApiParam(value = "购物车id（多个id数组）")@RequestParam Long cartIds[]
            @ApiParam(value = "购物车id（多个id逗号分割）")@RequestParam String cartIdsStr
    )throws ApiException {
        Long userId;
        userId = userService.checkAndGetCurrentUserId(token);
        String[] cartIdsStrArray = cartIdsStr.split(",");
        for (int i = 0; i < cartIdsStrArray.length; i++) {
            Long cartId = Long.parseLong(cartIdsStrArray[i]);
            ShoppingTrolley delCart = shoppingCartService.delShoppingCart(userId,Long.parseLong(cartIdsStrArray[i]));//删除购物车
        }

        DelShoppingCartResponse delShoppingCartResponse =new DelShoppingCartResponse();
        return delShoppingCartResponse;
    }


    @RequestMapping(value="/addShoppingCart",method = RequestMethod.POST)
    @ResponseBody
    @ApiResponses({@ApiResponse(code=205,message="已添加过购物车",response = ExceptionResponse.class)
            ,@ApiResponse(code=207,message="超出数量限制",response = ExceptionResponse.class)})
    @ApiOperation(value = "添加购物车", notes = "")
    public AddShoppingCartResponse addShoppingCart(
            @ApiParam(value = "用户token")@RequestParam String  token,
            @ApiParam(value = "故事id")@RequestParam Long storyId
    )throws ApiException {
        Long userId;
        userId = userService.checkAndGetCurrentUserId(token);

        AddShoppingCartResponse addShoppingCartResponse =new AddShoppingCartResponse();
        //添加购物车
        ShoppingTrolley shoppingTrolley = shoppingCartService.addShoppingCart(storyId,userId);//添加购物车
        addShoppingCartResponse.getStatus().setMsg("添加购物车成功");
        return addShoppingCartResponse;
    }

    @RequestMapping(value="/checkShoppingCart",method = RequestMethod.GET)
    @ResponseBody
    @ApiResponses({@ApiResponse(code=205,message="已添加过购物车",response = ExceptionResponse.class)
            ,@ApiResponse(code=207,message="超出数量限制",response = ExceptionResponse.class)})
    @ApiOperation(value = "验证有没有添加购物车的限制", notes = "")
    AddShoppingCartResponse checkShoppingCart(
            @ApiParam(value = "用户token")@RequestParam String  token,
            @ApiParam(value = "故事id")@RequestParam Long storyId
    )throws ApiException {
        Long userId;
        userId = userService.checkAndGetCurrentUserId(token);

        AddShoppingCartResponse addShoppingCartResponse =new AddShoppingCartResponse();
        //添加购物车
        ShoppingTrolley shoppingTrolley = shoppingCartService.checkShoppingCart(storyId,userId);//添加购物车

        return addShoppingCartResponse;
    }

    @RequestMapping(value="/checkStoryInOrder",method = RequestMethod.GET)
    @ResponseBody
    @ApiOperation(value = "验证这个故事是否有购买过", notes = "用于不添加购物车直接购买")
    AddShoppingCartResponse checkStoryInOrder(
            @ApiParam(value = "用户token")@RequestParam String  token,
            @ApiParam(value = "故事id")@RequestParam Long storyId
    )throws ApiException {
        Long userId;
        userId = userService.checkAndGetCurrentUserId(token);

        AddShoppingCartResponse addShoppingCartResponse =new AddShoppingCartResponse();
        //添加购物车
        shoppingCartService.checkStoryInOrder(storyId,userId);//添加购物车

        return addShoppingCartResponse;
    }
    @RequestMapping(value="/editShoppingCartStatus",method = RequestMethod.POST)
    @ResponseBody
    @ApiOperation(value = "选中购物车修改状态", notes = "")
    public EditShoppingCartResponse editShoppingCartStatus(
            @ApiParam(value = "用户token")@RequestParam String  token,
            @ApiParam(value = "购物车id")@RequestParam Long cartId,
            @ApiParam(value = "类型1选中2取消选中")@RequestParam Integer type
    )throws ApiNotTokenException {
        Long userId;
        userId = userService.checkAndGetCurrentUserId(token);

        EditShoppingCartResponse editShoppingTrolleyResponse =new EditShoppingCartResponse();
        //分页获取购物车数据
        ShoppingTrolley shoppingTrolley = shoppingCartService.editShoppingCartStatus(cartId,userId,type);//修改状态

        //查询所有选中购物车并计算
        Integer status = 1;

        List<ShoppingTrolley> shoppingCarts = shoppingCartService.getShoppingCartByUserIdAndStatus(userId,status);
        Integer sumPrice = 0;//共价格
        Integer count = shoppingCarts.size();//商品数量

        for (ShoppingTrolley st :shoppingCarts) {
//            getShoppingTrolleyResponse.getStorys().add(shoppingTrolley.getStory());
            sumPrice = sumPrice + st.getStory().getPrice();
        }
        editShoppingTrolleyResponse.setSumPrice(sumPrice);
        editShoppingTrolleyResponse.setCount(count);
        //saleRule优惠规则
        return editShoppingTrolleyResponse;
    }





}

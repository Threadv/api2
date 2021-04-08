package com.ifenghui.storybookapi.app.transaction.service;

import com.ifenghui.storybookapi.app.transaction.entity.single.ShoppingTrolley;
import com.ifenghui.storybookapi.exception.ApiException;
import org.springframework.data.domain.Page;

import java.util.List;
/**
 * Created by wml on 2017/2/16.
 */
public interface ShoppingCartService {

    Page<ShoppingTrolley> getShoppingCartByUserId(Long userId, Integer pageNo, Integer pageSize);//

    List<ShoppingTrolley> getShoppingCartByUserIdAndStatus(Long userId, Integer status);//获取此用户所有选中购物车数据

    ShoppingTrolley editShoppingCartStatus(Long cartId,Long userId,Integer type);//

    /**
     * //添加购物车
     * @param storyId 故事id
     * @param userId 用户id
     * @return
     * @throws ApiException
     */
    ShoppingTrolley addShoppingCart(Long storyId,Long userId)throws ApiException;

    /**
     * 验证是否存在于订单
     * @param storyId
     * @param userId
     * @return
     * @throws ApiException
     */
    public ShoppingTrolley checkStoryInOrder(Long storyId,Long userId)throws ApiException;
    /**
     * 验证是否可以提阿甲到购物车
     * @param storyId
     * @param userId
     * @return
     * @throws ApiException
     */
    ShoppingTrolley checkShoppingCart(Long storyId,Long userId)throws ApiException;


    ShoppingTrolley delShoppingCart(Long userId,Long cartId)throws ApiException;//删除购物车

    void clearStoryInShoppingCart(List<Long> storyIds, Long userId);
}

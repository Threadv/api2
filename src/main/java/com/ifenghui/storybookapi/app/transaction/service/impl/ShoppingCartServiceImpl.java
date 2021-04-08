package com.ifenghui.storybookapi.app.transaction.service.impl;
import com.ifenghui.storybookapi.app.story.dao.StoryDao;
import com.ifenghui.storybookapi.app.transaction.dao.OrderStoryDao;
import com.ifenghui.storybookapi.app.transaction.dao.PayStoryOrderDao;
import com.ifenghui.storybookapi.app.transaction.dao.ShoppingCartDao;
import com.ifenghui.storybookapi.app.user.dao.UserDao;
import com.ifenghui.storybookapi.config.MyEnv;
import com.ifenghui.storybookapi.app.transaction.entity.OrderStory;
import com.ifenghui.storybookapi.app.transaction.entity.single.ShoppingTrolley;
import com.ifenghui.storybookapi.app.story.entity.Story;
import com.ifenghui.storybookapi.app.transaction.entity.PayStoryOrder;
import com.ifenghui.storybookapi.app.user.entity.User;
import com.ifenghui.storybookapi.exception.*;
import com.ifenghui.storybookapi.app.transaction.service.ShoppingCartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;
import java.util.Locale;

/**
 * Created by wml on 2017/2/16.
 */
@Transactional
@Component
public class ShoppingCartServiceImpl implements ShoppingCartService {


    @Autowired
    ShoppingCartDao shoppingCartDao;
    @Autowired
    StoryDao storyDao;
    @Autowired
    UserDao userDao;

    @Autowired
    OrderStoryDao orderStoryDao;

    @Autowired
    PayStoryOrderDao payStoryOrderDao;

    @Autowired
    MessageSource messageSource;

    @Transactional
    @Override
    public Page<ShoppingTrolley> getShoppingCartByUserId(Long userId, Integer pageNo, Integer pageSize) {
        Pageable pageable = new PageRequest(pageNo, pageSize, new Sort(Sort.Direction.DESC,"id"));
        Page<ShoppingTrolley> shoppingTrolleys = this.shoppingCartDao.getShoppingTrolleysByUserId(userId,pageable);

        return shoppingTrolleys;
    }
    @Override
    public List<ShoppingTrolley> getShoppingCartByUserIdAndStatus(Long userId, Integer status) {
        Integer pageNo = 0;
        Integer pageSize = 1000;
        Pageable pageable = new PageRequest(pageNo, pageSize, new Sort(Sort.Direction.DESC,"id"));
        Page<ShoppingTrolley> shoppingTrolleys = this.shoppingCartDao.getShoppingTrolleyByUserIdAndStatus(userId,status,pageable);
//        List<ShoppingTrolley> shoppingTrolleys = this.shoppingTrolleyDao.getShoppingTrolleysByUserIdAndStatus(userId,status);

        return shoppingTrolleys.getContent();
    }
    @Override
    public ShoppingTrolley editShoppingCartStatus(Long cartId ,Long userId, Integer type) {

        Integer status;
        if (type==1){
            status = 1;//选中单位结算状态
        }else{
            status = 0;//未选中状态
        }
        ShoppingTrolley shoppingTrolley = this.shoppingCartDao.findOne(cartId);

//        ShoppingTrolley shoppingTrolley2 = new ShoppingTrolley();
        shoppingTrolley.setStatus(status);

        ShoppingTrolley s = this.shoppingCartDao.save(shoppingTrolley);

        return shoppingTrolley;
    }
    @Override
    public ShoppingTrolley addShoppingCart(Long storyId ,Long userId)throws ApiException
    {
        Locale locale= LocaleContextHolder.getLocale();

        Story story = storyDao.findOne(storyId);
        if (story!=null && story.getStatus().intValue()!=1){
            //故事未发布
            throw new ApiDuplicateException("此故事未发布");
        }
        User user = userDao.findOne(userId);

        this.checkShoppingCart(storyId,userId);
        //先判断此story是否已经加入购物车
//        Integer pageNo = 0;
//        Integer pageSize =1;
//        Pageable pageable = new PageRequest(pageNo, pageSize, new Sort("id"));
//
//        ShoppingTrolley findShoppingTorrley=new ShoppingTrolley();
//        findShoppingTorrley.setUserId(userId);
//        findShoppingTorrley.setStoryId(storyId);
//        ShoppingTrolley shoppingCartPageItem = this.shoppingCartDao.findOne(Example.of(findShoppingTorrley));
//        if (shoppingCartPageItem!=null){
//            //已添加过
//            throw new ApiIsAddException("已添加过购物车");
//        }
//        //查询此用户所有购物车
//        pageSize =100;
//        findShoppingTorrley=new ShoppingTrolley();
//        findShoppingTorrley.setUserId(userId);
////        findShoppingTorrley.setStoryId(storyId);
//        Long countTorry = this.shoppingCartDao.count(Example.of(findShoppingTorrley));
//
////        pageable = new PageRequest(pageNo, pageSize, new Sort("id"));
////        Page<ShoppingTrolley> shoppingCartsPage = this.shoppingCartDao.getShoppingTrolleysByUserId(userId,pageable);
//        if (countTorry > 100){
//            throw new ApiBeyondLimitException("超出数量，最多只能加入100个");
//        }
//
//        //验证订单是否存在
//        OrderStory orderStory=new OrderStory();
//        orderStory.setStoryId(storyId);
//        PayStoryOrder payStoryOrder=new PayStoryOrder();
//        payStoryOrder.setIsDel(0);
//        orderStory.setPayStoryOrder(payStoryOrder);
//
//        List<OrderStory> orderStories= orderStoryDao.findAll(Example.of(orderStory));
//        for(OrderStory orderStory1:orderStories){
//
//           PayStoryOrder findPayStoryOrder=payStoryOrderDao.findOne(orderStory1.getOrderId());
//            if(findPayStoryOrder.getStatus()==1||findPayStoryOrder.getStatus()==0){
//                throw new ApiStoryOrderRepeatException(findPayStoryOrder);
//            }
//
//        }

        ShoppingTrolley shoppingTrolley = new ShoppingTrolley();
        shoppingTrolley.setStatus(0);
        shoppingTrolley.setStory(story);
        shoppingTrolley.setUser(user);
        shoppingTrolley.setCreateTime(new Date());
        ShoppingTrolley res = this.shoppingCartDao.save(shoppingTrolley);

        return shoppingTrolley;
    }
    @Override
    public ShoppingTrolley checkStoryInOrder(Long storyId,Long userId)throws ApiException{

        Story story = storyDao.findOne(storyId);
        if (story!=null && story.getStatus().intValue()!=1){
            //故事未发布
            throw new ApiDuplicateException("此故事未发布");
        }
//        if (story != null && (story.getSerialStoryId().equals(1L) || story.getSerialStoryId().equals(2L))){
//            throw new ApiNotFoundException("此故事为绘本丛书，无法单本购买！");
//        }
        //验证订单是否存在
        OrderStory orderStory=new OrderStory();
        orderStory.setStoryId(storyId);
        orderStory.setUserId(userId);
//        PayStoryOrder payStoryOrder=new PayStoryOrder();
//        payStoryOrder.setIsDel(0);
//
//        orderStory.setPayStoryOrder(payStoryOrder);

        List<OrderStory> orderStories= orderStoryDao.findAll(Example.of(orderStory));
        for(OrderStory orderStory1:orderStories){

            PayStoryOrder findPayStoryOrder=payStoryOrderDao.findOne(orderStory1.getOrderId());
            if(findPayStoryOrder.getStatus()==1){

                throw new ApiDuplicateException(MyEnv.getMessage("order.story.hasbuy"));
            }else if(findPayStoryOrder.getStatus()==0){
                throw new ApiStoryOrderRepeatException(findPayStoryOrder);
            }

        }
        return  null;
    }
    @Override
    public ShoppingTrolley checkShoppingCart(Long storyId,Long userId)throws ApiException{
        ShoppingTrolley findShoppingTorrley=new ShoppingTrolley();
        ShoppingTrolley shoppingCartPageItem = this.shoppingCartDao.getShoppingTrolleyByUserIdAndStoryId(userId,storyId);
        if (shoppingCartPageItem!=null){
            //已添加过
            throw new ApiIsAddException("已添加过购物车");
        }
        //查询此用户所有购物车
//        pageSize =100;
        findShoppingTorrley=new ShoppingTrolley();
        findShoppingTorrley.setUserId(userId);
//        findShoppingTorrley.setStoryId(storyId);
        Long countTorry = this.shoppingCartDao.count(Example.of(findShoppingTorrley));

//        pageable = new PageRequest(pageNo, pageSize, new Sort("id"));
//        Page<ShoppingTrolley> shoppingCartsPage = this.shoppingCartDao.getShoppingTrolleysByUserId(userId,pageable);
        if (countTorry > 100){
            throw new ApiBeyondLimitException("超出数量，最多只能加入100个");
        }

        this.checkStoryInOrder(storyId,userId);
        return  null;
    }

    @Override
    public ShoppingTrolley delShoppingCart(Long userId ,Long cartId)throws ApiException {

        ShoppingTrolley shoppingCart = shoppingCartDao.findOne(cartId);
        Long uid = shoppingCart.getUser().getId();
        if ( uid.longValue() == userId){
            shoppingCartDao.delete(cartId);
        }else{
            //返回个状态//判断用户是否是此用户否则无权限删除
            throw new ApiNoPermissionDelException("无权限删除");
        }
        return null;
    }

    @Override
    public void clearStoryInShoppingCart(List<Long> storyIds, Long userId) {
        for (Long storyId:storyIds){
            ShoppingTrolley shoppingTrolley=new ShoppingTrolley();
            shoppingTrolley.setUserId(userId);
            shoppingTrolley.setStoryId(storyId);
            List<ShoppingTrolley> shoppingTrolleys= shoppingCartDao.findAll(Example.of(shoppingTrolley));
            for(ShoppingTrolley item :shoppingTrolleys){
                shoppingCartDao.delete(item);
            }
        }
    }
}

package com.ifenghui.storybookapi.app.transaction.dao;

import com.ifenghui.storybookapi.app.transaction.entity.single.ShoppingTrolley;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import javax.transaction.Transactional;


/**
 * Created by wml on 2017/2/16.
 */
@Transactional
public interface ShoppingCartDao extends JpaRepository<ShoppingTrolley, Long> {

    @Query("from ShoppingTrolley s where s.user.id = :userId and (status=0 or status=1)")
    Page<ShoppingTrolley> getShoppingTrolleysByUserId(@Param("userId") Long userId, Pageable pageable);

    Page<ShoppingTrolley> getShoppingTrolleyByUserIdAndStatus(Long userId, Integer status, Pageable pageable);

    Page<ShoppingTrolley> getShoppingTrolleyByUserIdAndStoryId(Long userId, Long storyId,Pageable pageable);


    @Query("select t from  ShoppingTrolley as t where t.userId=:userId and  t.storyId =:storyId")
    ShoppingTrolley getShoppingTrolleyByUserIdAndStoryId(@Param("userId") Long userId,@Param("storyId") Long storyId);



}
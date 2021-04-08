package com.ifenghui.storybookapi.app.transaction.dao;

import com.ifenghui.storybookapi.app.transaction.entity.OrderStory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import javax.transaction.Transactional;
import java.util.List;


/**
 * Created by wml on 2017/2/16.
 */
@Transactional
public interface OrderStoryDao extends JpaRepository<OrderStory, Long> {


        Page<OrderStory> getOrderStoryByOrderId(Long orderId, Pageable pageable);
        List<OrderStory> getOrderStoryByOrderId(Long orderId);
        List<OrderStory> getOrderStoryByStoryId(Long storyId);
        OrderStory getByStoryIdAndUserId(Long storyId,Long userId);

        @Query("select o from OrderStory as o where o.userId=:userId and o.storyId=:storyId")
        List<OrderStory> getOrderStoriesByUserIdAndStoryId(
                @Param("userId") Long userId,
                @Param("storyId") Long storyId
        );

}
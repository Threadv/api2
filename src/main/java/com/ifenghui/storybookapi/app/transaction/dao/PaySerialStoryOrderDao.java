package com.ifenghui.storybookapi.app.transaction.dao;

import com.ifenghui.storybookapi.app.transaction.entity.serial.PaySerialStoryOrder;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface PaySerialStoryOrderDao extends JpaRepository<PaySerialStoryOrder, Integer> {

    @Query("select p from PaySerialStoryOrder as p where p.userId=:userId and p.serialStoryId=:serialStoryId and p.isDel=0 and p.status=0")
    List<PaySerialStoryOrder> getPaySerialStoryOrdersByUserIdAndSerialStoryId(
        @Param("userId") Integer userId,
        @Param("serialStoryId") Integer serialStoryId
    );

}

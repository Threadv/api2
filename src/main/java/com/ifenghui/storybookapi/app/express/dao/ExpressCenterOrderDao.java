package com.ifenghui.storybookapi.app.express.dao;

import com.ifenghui.storybookapi.app.express.entity.ExpressCenterOrder;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ExpressCenterOrderDao extends JpaRepository<ExpressCenterOrder, Integer> {

    @Query("select w from ExpressCenterOrder as w where w.phone=:phone ")
    Page<ExpressCenterOrder> findOrdersByPhone(@Param("phone") String phone, Pageable pageable);

    @Query("select w from ExpressCenterOrder as w where w.phone=:phone ")
    List<ExpressCenterOrder> findByPhone(@Param("phone") String phone);

    @Query("select w from ExpressCenterOrder as w where w.outOrderId=:outOrderId ")
    List<ExpressCenterOrder> findOrderByOutId(@Param("outOrderId")String outOrderId);

    @Query("select w from ExpressCenterOrder as w where w.outOrderId=:outOrderId and w.srcType=:srcType")
    ExpressCenterOrder findOne(@Param("outOrderId")String outOrderId,
                               @Param("srcType")Integer srcType);

    @Query("select count(w) from ExpressCenterOrder as w where w.srcType=:srcType and w.outOrderId=:outOrderId ")
    Integer countBySrcTypeAndOutOrderId(@Param("srcType")Integer srcType,@Param("outOrderId")String outOrderId);

    //    Page<ExpressCenterOrder> findAllByPhone(String phone, PageRequest pageRequest);
}

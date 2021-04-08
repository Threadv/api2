package com.ifenghui.storybookapi.app.express.dao;


import com.ifenghui.storybookapi.app.express.entity.ExpressCenterTrack;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ExpressCenterTrackDao extends JpaRepository<ExpressCenterTrack, Integer> {

    @Query("select t from ExpressCenterTrack as t where t.trackType=:trackType and t.trackNo=:trackNo and t.centerOrderId=:centerOrderId and t.month=:month")
    List<ExpressCenterTrack> findTracks(@Param("trackType") Integer trackType, @Param("trackNo") String trackNo,@Param("centerOrderId") Integer centerOrderId,@Param("month") Integer month);

    @Query("select t from ExpressCenterTrack as t where t.centerOrderId=:orderId")
    List<ExpressCenterTrack> findTracksByOrderId(@Param("orderId") Integer orderId);

}

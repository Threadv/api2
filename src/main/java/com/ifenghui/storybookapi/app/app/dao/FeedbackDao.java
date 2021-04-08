package com.ifenghui.storybookapi.app.app.dao;

import com.ifenghui.storybookapi.app.app.entity.Ads2;
import com.ifenghui.storybookapi.app.app.entity.Feedback;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

/**
 * Created by jia on 2016/12/22.
 */
public interface FeedbackDao extends JpaRepository<Feedback, Long> {
    List<Feedback> findAllByUserId(long userId);

    Page<Feedback> getFeedbacksByUserId(long userId, Pageable pageable);
//    List<Feedback>


    @Query("select COUNT(f) from Feedback as f where f.userId=:userId and f.readType=:readType")
    Integer findCountBy(
            @Param("userId") Long userId,
            @Param("readType") Integer readType
    );
}

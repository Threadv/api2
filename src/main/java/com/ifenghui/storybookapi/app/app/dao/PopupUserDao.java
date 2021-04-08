package com.ifenghui.storybookapi.app.app.dao;


import com.ifenghui.storybookapi.app.app.entity.PopupUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;


public interface PopupUserDao extends JpaRepository<PopupUser, Integer>,JpaSpecificationExecutor {

    Long countAllByPopupIdAndUserIdAndIsRead(@Param("popupId") Integer popupId, @Param("userId") Integer userId, @Param("isRead") Integer isRead);


    PopupUser findByPopupIdAndUserId(Integer popupId, Integer userId);


    @Query("select p from PopupUser p where p.popupId=:popupId and p.userId=:userId and p.isRead=0")
    List<PopupUser> findAllByPopupIdAndUserIdUnRead(@Param("popupId") Integer popupId, @Param("userId") Integer userId);
    /**
     * 查询所有未读弹框
     * @param userId
     * @param isRead
     * @return
     */
    List<PopupUser> findAllByUserIdAndIsRead(Integer userId, Integer isRead);
}

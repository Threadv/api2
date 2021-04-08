package com.ifenghui.storybookapi.app.app.dao;


import com.ifenghui.storybookapi.app.app.entity.Popup;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Date;
import java.util.List;


public interface PopupDao extends JpaRepository<Popup, Integer>,JpaSpecificationExecutor {

    @Query("select popup from Popup popup where popup.status=1 ")
 List<Popup> findAllCurrent();

//    Popup findByClassName(String className);


//    @Query("select popup from Popup popup where popup.status=1 and popup.className ='' and popup.beginTime<:currentTime and popup.endTime>:currentTime ")
//    List<Popup> findAllByClassNameIsNull(@Param("currentTime") Date currentTime);

    @Query("select popup from Popup popup where popup.status=1 and popup.beginTime<:currentTime and popup.endTime>:currentTime ")
    List<Popup> findAllPublishPopupByTime(@Param("currentTime") Date currentTime);
}

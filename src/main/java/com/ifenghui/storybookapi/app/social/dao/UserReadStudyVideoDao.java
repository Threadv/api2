package com.ifenghui.storybookapi.app.social.dao;

import com.ifenghui.storybookapi.app.social.entity.UserReadStudyVideo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;


public interface UserReadStudyVideoDao extends JpaRepository<UserReadStudyVideo, Integer> {


    @Query("from  UserReadStudyVideo as u where u.userId =:userId and u.videoId =  :videoId")
    Page<UserReadStudyVideo> findAllByUserIdAndVideoId(@Param("userId") Integer userId, @Param("videoId") Integer videoId, Pageable pageable);


    @Override
    UserReadStudyVideo save(UserReadStudyVideo userReadStudyVideo);
}

package com.ifenghui.storybookapi.app.presale.dao;



import com.ifenghui.storybookapi.app.social.entity.UserReadStudyVideo;
import org.springframework.data.jpa.repository.JpaRepository;

@Deprecated
public interface VideoReadStudyRecordDao extends JpaRepository<UserReadStudyVideo, Integer> {

}

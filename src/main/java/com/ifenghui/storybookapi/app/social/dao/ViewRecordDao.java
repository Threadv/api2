package com.ifenghui.storybookapi.app.social.dao;


import com.ifenghui.storybookapi.app.social.entity.ViewRecord;
import com.ifenghui.storybookapi.app.user.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;


import javax.transaction.Transactional;
import java.util.List;

/**
/**
 * Created by jia on 2016/12/23.
 */
@Transactional
public interface ViewRecordDao extends JpaRepository<ViewRecord, Long> {


    /**
     * 故事足迹  类型 1 4
     * @param user
     * @param pageable
     * @return
     */
    @Query("from ViewRecord  v where v.user =:user and (v.type=1 or v.type=4) ")
    Page<ViewRecord> getViewrecordByUserIdAndType(@Param("user") User user,Pageable pageable);

    List<ViewRecord> findAllByUserId(Long userId);
    Page<ViewRecord> getViewrecordsByUserId(Long userId, Pageable pageable);
    Page<ViewRecord> getViewRecordByUserIdAndStoryId(Long userId,Long storyId,Pageable pageable);

    @Query("from ViewRecord s where s.user =:user  and s.type!=2")
    Page<ViewRecord> getUserViewRecord(@Param("user") User user, Pageable pageable);
}

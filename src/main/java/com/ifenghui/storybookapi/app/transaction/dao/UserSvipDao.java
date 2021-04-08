package com.ifenghui.storybookapi.app.transaction.dao;

/**
 * Created by jia on 2016/12/23.
 */


import com.ifenghui.storybookapi.app.transaction.entity.vip.UserSvip;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import javax.transaction.Transactional;
import java.util.Date;
import java.util.List;

@Transactional
public interface UserSvipDao extends JpaRepository<UserSvip, Long> {

    /**
     * 返回没有过期的svip状态
     * @param endTime
     * @return
     */
    @Query("select s from UserSvip s where s.userId=:userId and s.endTime >:endTime and (s.type = 3 or s.type=4)")
    List<UserSvip> findByUserIdAndEndTime(
            @Param("userId") Long userId,
            @Param("endTime") Date endTime,
            Sort sort
    );

    @Query("select s from UserSvip s where s.userId=:userId and s.type=:type and s.endTime >:endTime")
    List<UserSvip> findByUserIdAndTypeAndEndTime(@Param("userId") Long userId,@Param("type") Integer type, @Param("endTime") Date endTime);


}

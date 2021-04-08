package com.ifenghui.storybookapi.app.social.dao;

import com.ifenghui.storybookapi.app.social.entity.UserGrowthDiaryImg;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface UserGrowthDiaryImgDao extends JpaRepository<UserGrowthDiaryImg, Integer> {

    @Query("select distinct weekNum from UserGrowthDiaryImg where userId=:userId and status=1 order by weekNum desc")
    List<Integer> getAllWeekNumByUserId(
        @Param("userId") Integer userId
    );

//    @Cacheable(cacheNames = "getUserGrowthImgPageByWeekNum", key="'growthDiaryImgpage' + #root.args[0] + '_' + #root.args[1] + #root.args[2].getPageNumber()")
    @Query("select ugd from UserGrowthDiaryImg as ugd where ugd.userId=:userId and ugd.weekNum=:weekNum and ugd.status=1")
    Page<UserGrowthDiaryImg> getUserGrowthImgPageByWeekNum(
        @Param("userId") Integer userId,
        @Param("weekNum") Integer weekNum,
        Pageable pageable
    );

    @Query("select ugd from UserGrowthDiaryImg as ugd where ugd.id <:userGrowthId and ugd.userId=:userId and ugd.weekNum=:weekNum and ugd.status=1")
    Page<UserGrowthDiaryImg> getUserGrowthImgPageByWeekNum(
        @Param("userId") Integer userId,
        @Param("weekNum") Integer weekNum,
        @Param("userGrowthId") Integer userGrowthImgId,
        Pageable pageable
    );

    @Query("select count(ugd) from UserGrowthDiaryImg as ugd where ugd.userId=:userId and ugd.weekNum=:weekNum and ugd.status=:status")
    Integer getRsCountByUserIdAndWeekNum(
        @Param("userId") Integer userId,
        @Param("weekNum") Integer weekNum,
        @Param("status") Integer status
    );
}

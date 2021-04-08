package com.ifenghui.storybookapi.app.social.dao;


import com.ifenghui.storybookapi.app.social.entity.UserGrowthDiaryMusic;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface UserGrowthDiaryMusicDao extends JpaRepository<UserGrowthDiaryMusic, Integer> {

    @Query("select ugd from UserGrowthDiaryMusic as ugd where ugd.status=:status")
    Page<UserGrowthDiaryMusic> getUserGrowthDiaryMusicsByStatus(
            @Param("status") Integer status,
            Pageable pageable
    );

}

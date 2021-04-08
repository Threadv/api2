package com.ifenghui.storybookapi.app.social.dao;

import com.ifenghui.storybookapi.app.social.entity.VipFriendCard;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface VipFriendCardDao extends JpaRepository<VipFriendCard, Integer> {

    @Query("select v from VipFriendCard as v where v.userId=:userId")
    Page<VipFriendCard> getVipFriendCardsByUserId(
        @Param("userId") Integer userId,
        Pageable pageable
    );

    VipFriendCard getVipFriendCardBySrcInfo(String srcInfo);

}

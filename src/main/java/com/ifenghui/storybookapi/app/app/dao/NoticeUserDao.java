package com.ifenghui.storybookapi.app.app.dao;

import com.ifenghui.storybookapi.app.app.entity.NoticeUser;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface NoticeUserDao extends JpaRepository<NoticeUser, Integer> {

    @Query("select notice from NoticeUser as notice where notice.userId=:userId")
    List<NoticeUser> getNoticeUsersByUserId(
        @Param("userId") Integer userId
    );

    @Query("select notice from NoticeUser as notice where notice.userId=:userId and notice.isDel=:isDel")
    Page<NoticeUser> getNoticeUsersByUserIdAndIsDel(
        @Param("userId") Integer userId,
        @Param("isDel") Integer isDel,
        Pageable pageable
    );

    @Query("select notice from NoticeUser as notice where notice.userId=:userId and notice.isRead=:isRead and notice.isDel=:isDel")
    Page<NoticeUser> getNoticeUsersByUserIdAndIsReadAndIsDel(
            @Param("userId") Integer userId,
            @Param("isRead") Integer isRead,
            @Param("isDel") Integer isDel,
            Pageable pageable
    );

    @Query("select notice from NoticeUser as notice where notice.userId=:userId and notice.isRead=:isRead")
    List<NoticeUser> getNoticeUsersByUserIdAndIsRead(
            @Param("userId") Integer userId,
            @Param("isRead") Integer isRead
    );

}

package com.ifenghui.storybookapi.app.transaction.dao;

import com.ifenghui.storybookapi.app.transaction.entity.subscription.BuyMagazineRecord;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import javax.transaction.Transactional;


/**
 * Created by wml on 2016/2/15.
 */
@Transactional

public interface BuyMagazineRecordDao extends JpaRepository<BuyMagazineRecord, Long> {

    Page<BuyMagazineRecord> getRecordByUserId(Long userId, Pageable pageable);
    Page<BuyMagazineRecord> getRecordByUserIdAndMagazineId(Long userId,Long magazineId, Pageable pageable);
}
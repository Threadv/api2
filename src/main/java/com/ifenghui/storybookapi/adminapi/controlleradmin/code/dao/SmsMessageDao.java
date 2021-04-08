package com.ifenghui.storybookapi.adminapi.controlleradmin.code.dao;

import com.ifenghui.storybookapi.adminapi.controlleradmin.code.entity.SmsMessage;
import com.ifenghui.storybookapi.app.app.entity.Ads;
import com.ifenghui.storybookapi.app.app.entity.Ads2;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import javax.transaction.Transactional;
import java.util.List;


/**
 * Created by wml on 2016/12/27.
 */
@Transactional
public interface SmsMessageDao extends JpaRepository<SmsMessage, Integer>,JpaSpecificationExecutor {


}
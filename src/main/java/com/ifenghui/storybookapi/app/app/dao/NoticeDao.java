package com.ifenghui.storybookapi.app.app.dao;

import com.ifenghui.storybookapi.app.app.entity.Notice;
import org.springframework.data.jpa.repository.JpaRepository;

public interface NoticeDao extends JpaRepository<Notice, Integer> {
}

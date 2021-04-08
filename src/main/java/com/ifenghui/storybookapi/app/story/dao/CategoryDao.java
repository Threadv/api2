package com.ifenghui.storybookapi.app.story.dao;

import com.ifenghui.storybookapi.app.story.entity.Category;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import javax.transaction.Transactional;


/**
 * Created by wml on 2016/12/27.
 */
@Transactional
public interface CategoryDao extends JpaRepository<Category, Long> {
    @Query("from Category c where  c.status=:status ")
    Page<Category> getCategoryByStatus(@Param("status") Integer status, Pageable pageable);
}
package com.ifenghui.storybookapi.app.story.service;

import com.ifenghui.storybookapi.app.story.entity.Category;
import org.springframework.data.domain.Page;

/**
 * Created by wml on 2016/12/27.
 */
public interface CategoryService {

    Page<Category> getIndexCategory(Integer pageNo, Integer pageSize);//故事分类


}

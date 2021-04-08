package com.ifenghui.storybookapi.app.story.service.impl;

import com.ifenghui.storybookapi.app.story.dao.CategoryDao;
import com.ifenghui.storybookapi.app.story.entity.Category;
import com.ifenghui.storybookapi.app.story.entity.Story;
import com.ifenghui.storybookapi.app.story.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;


/**
 * Created by wml on 2017/2/15.
 */
@Transactional
@Component
public class CategoryServiceImpl implements CategoryService {


    @Autowired
    CategoryDao categoryDao;

    @Transactional
    @Override
    public Page<Category> getIndexCategory(Integer pageNo, Integer pageSize) {

        Integer status = 1;
        Pageable pageable = new PageRequest(pageNo, pageSize, new Sort(Sort.Direction.DESC,"id"));
        Page<Category> categories=this.categoryDao.getCategoryByStatus(status,pageable);
        Story story;

//        for (Category categories :groupRelevance.getContent()) {
//            story = relevance.getStory();
//            bookPrice = this.bookPriceDao.getBookPriceByStoryId(story.getId());
//            if (bookPrice != null){
//                story.setAndroidPriceId(bookPrice.getAndroidPriceId());
//                story.setIosPriceId(bookPrice.getIosPriceId());
//                story.getIosPrice().setIap(bookPrice.getIap());
//                story.setShopUrl(bookPrice.getShopUrl());
//                if(story.getIosPrice().getPrice() > 0 || story.getAndroidPrice().getPrice() > 0){
//                    story.setIsFree(0);
//                }else{
//                    story.setIsFree(1);
//                }
//            }
//
//        }

        return categories;
    }
}

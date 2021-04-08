package com.ifenghui.storybookapi.app.story.service.impl;
import com.ifenghui.storybookapi.app.story.dao.LabelDao;
import com.ifenghui.storybookapi.app.story.entity.Label;
import com.ifenghui.storybookapi.exception.ApiNotFoundException;
import com.ifenghui.storybookapi.app.story.service.LabelService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;


/**
 * Created by jia on 2016/12/22.
 */
@Transactional
@Component
public class LabelServiceImpl  implements LabelService{

    @Autowired
    LabelDao labelDao;

    @Override
    public Page getLabels(int pageNo, int pageSize) {
        Pageable pageable=new PageRequest(pageNo,pageSize,new Sort("id"));
        return labelDao.findAll(pageable);
    }

    //    @Transactional
    @Override
    public Label getLabel(long id) {
        return labelDao.findOne(id);
    }


    //    @Transactional
    @Override
    public Label updateLabel(long id,String content)throws Exception{
        Label label=labelDao.getOne(id);
        if(label==null){
            throw new ApiNotFoundException("");
        }
        label.setContent(content);
        labelDao.save(label);
        return null;
    }

    @Override
    public Label addLabel(Label label){
      Label ts = labelDao.save(label);
        return ts;

    }

    @Override
    public Label delLabel(long id){
        labelDao.delete(id);
        return null;

    }


}


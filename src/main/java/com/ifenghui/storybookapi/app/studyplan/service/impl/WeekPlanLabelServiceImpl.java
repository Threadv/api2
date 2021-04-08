package com.ifenghui.storybookapi.app.studyplan.service.impl;

import com.ifenghui.storybookapi.app.studyplan.dao.WeekPlanLabelDao;
import com.ifenghui.storybookapi.app.studyplan.entity.WeekPlanLabel;
import com.ifenghui.storybookapi.app.studyplan.entity.WeekPlanLabelStory;
import com.ifenghui.storybookapi.app.studyplan.entity.WeekPlanTaskRelate;
import com.ifenghui.storybookapi.app.studyplan.response.GetStatisticWeekPlanLabelResponse;
import com.ifenghui.storybookapi.app.studyplan.service.WeekPlanLabelService;
import com.ifenghui.storybookapi.app.studyplan.service.WeekPlanLabelStoryService;
import com.ifenghui.storybookapi.app.studyplan.service.WeekPlanTaskRelateService;
import com.ifenghui.storybookapi.style.LabelTargetStyle;
import com.ifenghui.storybookapi.style.WeekPlanLabelParentStyle;
import com.ifenghui.storybookapi.style.WeekPlanLabelStyle;
import com.ifenghui.storybookapi.style.WeekPlanStyle;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.*;

@Component
public class WeekPlanLabelServiceImpl implements WeekPlanLabelService {

    @Autowired
    WeekPlanLabelDao weekPlanLabelDao;

    @Autowired
    WeekPlanLabelStoryService weekPlanLabelStoryService;

    @Autowired
    SortByIsFinishAndOrderBy sortByIsFinishAndOrderBy;

    @Autowired
    WeekPlanTaskRelateService weekPlanTaskRelateService;

    @Override
    public WeekPlanLabel findOne(int id) {
        WeekPlanLabel weekPlanLabel = weekPlanLabelDao.findOne(id);
        if(weekPlanLabel != null && !weekPlanLabel.getParentId().equals(0)) {
            WeekPlanLabelParentStyle parentStyle = WeekPlanLabelParentStyle.getById(weekPlanLabel.getParentId());
            if(parentStyle != null) {
                weekPlanLabel.setParentName(parentStyle.getName());
            }
        }
        return weekPlanLabel;
    }

    @Override
    public List<WeekPlanLabel> findListFromWeekPlanLabelStoryList(List<WeekPlanLabelStory> weekPlanLabelStoryList, Integer isFinish) {
        List<WeekPlanLabel> weekPlanLabelList = new ArrayList<>();
        for(WeekPlanLabelStory item : weekPlanLabelStoryList) {
            WeekPlanLabel labelItem = this.findOne(item.getLabelId());
            if(labelItem.getIsFinish() == null || labelItem.getIsFinish().equals(0)) {
                labelItem.setIsFinish(isFinish);
                labelItem.setFinishNum(isFinish);
            } else {
                if(labelItem.getFinishNum() == null) {
                    labelItem.setFinishNum(0);
                }
            }

            weekPlanLabelList.add(labelItem);
        }
        return weekPlanLabelList;
    }

    @Override
    public List<WeekPlanLabel> findListByTaskRelateAndType(List<WeekPlanTaskRelate> weekPlanTaskRelateList, WeekPlanLabelStyle weekPlanLabelStyle) {
        List<WeekPlanLabel> weekPlanLabelList = new ArrayList<>();
        for(WeekPlanTaskRelate item : weekPlanTaskRelateList) {
            List<WeekPlanLabelStory> weekPlanLabelStoryList =
                    weekPlanLabelStoryService.findListByTargetTypeAndTargetValueAndLabelType(
                    item.getTargetType(), item.getStoryId(), weekPlanLabelStyle
            );
            List<WeekPlanLabel> itemList = this.findListFromWeekPlanLabelStoryList(weekPlanLabelStoryList, item.getIsFinish());
            weekPlanLabelList.addAll(itemList);
        }
        return weekPlanLabelList;
    }

    @Override
    public List<WeekPlanLabel> dealUniqueListFromWeekPlanLabelList(List<WeekPlanLabel> weekPlanLabelList) {
        List<WeekPlanLabel> tempList = new ArrayList<>();
        Iterator<WeekPlanLabel> iterator = weekPlanLabelList.iterator();
        while(iterator.hasNext()){
            WeekPlanLabel t = iterator.next();
            if(tempList.contains(t)){
                iterator.remove();
            } else{
                tempList.add(t);
            }
        }
        weekPlanLabelList.sort(sortByIsFinishAndOrderBy);
        return weekPlanLabelList;
    }

    @Override
    public List<WeekPlanLabel> findListByWeekPlanIdAndUserId(Integer weekPlanId, Integer userId, WeekPlanLabelStyle weekPlanLabelStyle) {
        List<WeekPlanTaskRelate> weekPlanTaskRelateList = weekPlanTaskRelateService.getWeekPlanTaskRelateListByWeekPlanId(weekPlanId, userId);
        List<WeekPlanLabel> weekPlanLabelList = this.findListByTaskRelateAndType(weekPlanTaskRelateList, weekPlanLabelStyle);
        weekPlanLabelList = this.dealUniqueListFromWeekPlanLabelList(weekPlanLabelList);
        if(WeekPlanLabelStyle.LITERACY_TYPE.equals(weekPlanLabelStyle) || WeekPlanLabelStyle.COGNITION_TYPE.equals(weekPlanLabelStyle)) {
            weekPlanLabelList = this.removeIsNotFinish(weekPlanLabelList);
        }
        return weekPlanLabelList;
    }

    @Override
    public List<WeekPlanLabel> removeIsNotFinish(List<WeekPlanLabel> weekPlanLabelList) {
        Iterator<WeekPlanLabel> iterator = weekPlanLabelList.iterator();
        while(iterator.hasNext()){
            WeekPlanLabel t = iterator.next();
            if(t.getIsFinish().equals(0)) {
                iterator.remove();
            }
        }
        return weekPlanLabelList;
    }

    @Override
    public List<WeekPlanLabel> findListByLabelType(WeekPlanLabelStyle weekPlanLabelStyle) {
        return weekPlanLabelDao.getListByLabelType(weekPlanLabelStyle.getId());
    }

    @Override
    public HashMap<Integer, WeekPlanLabel> getHashMapLabelListByType(WeekPlanLabelStyle weekPlanLabelStyle) {
        List<WeekPlanLabel> weekPlanLabelList = this.findListByLabelType(weekPlanLabelStyle);
        HashMap<Integer, WeekPlanLabel> hashMap = new HashMap<>();
        for(WeekPlanLabel item : weekPlanLabelList) {
            item.setFinishNum(0);
            hashMap.put(item.getId(), item);
        }
        return hashMap;
    }

}

package com.ifenghui.storybookapi.app.studyplan.response;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.ifenghui.storybookapi.api.response.base.ApiResponse;
import com.ifenghui.storybookapi.app.studyplan.entity.WeekPlanLabel;
import com.ifenghui.storybookapi.app.studyplan.service.impl.SortByFinishNumAndOrderBy;
import com.ifenghui.storybookapi.style.WeekPlanLabelParentStyle;
import com.ifenghui.storybookapi.style.WeekPlanLabelStyle;
import org.codehaus.jackson.annotate.JsonProperty;

import java.util.*;

public class GetStatisticWeekPlanLabelResponse extends ApiResponse {

    @JsonIgnore
    Integer storyWordCount;

    @JsonIgnore
    Integer magazineWordCount;

    @JsonIgnore
    Integer storyCount;

    @JsonIgnore
    Integer magazineCount;

    @JsonIgnore
    HashMap<Integer, WeekPlanLabel> readTypeMap;

    @JsonIgnore
    HashMap<Integer, WeekPlanLabel> cognitionTypeMap;

    @JsonIgnore
    HashMap<Integer, WeekPlanLabel> literacyTypeMap;

    @JsonIgnore
    HashMap<Integer, WeekPlanLabel> fiveAreaTypeMap;

    public Integer getfiveAreaTypeLimit(){
        return 500;
    }

    public Integer getStoryWordCount() {
        return storyWordCount;
    }

    public void setStoryWordCount(Integer storyWordCount) {
        this.storyWordCount = storyWordCount;
    }

    public Integer getMagazineWordCount() {
        return magazineWordCount;
    }

    public void setMagazineWordCount(Integer magazineWordCount) {
        this.magazineWordCount = magazineWordCount;
    }

    public Integer getStoryCount() {
        return storyCount;
    }

    public void setStoryCount(Integer storyCount) {
        this.storyCount = storyCount;
    }

    public Integer getMagazineCount() {
        return magazineCount;
    }

    public void setMagazineCount(Integer magazineCount) {
        this.magazineCount = magazineCount;
    }

    public GetStatisticWeekPlanLabelResponse(HashMap<Integer, WeekPlanLabel> readTypeMap, HashMap<Integer, WeekPlanLabel> fiveAreaTypeMap) {
        this.readTypeMap = readTypeMap;
        this.cognitionTypeMap = new HashMap<>();
        this.literacyTypeMap = new HashMap<>();
        this.fiveAreaTypeMap = fiveAreaTypeMap;
    }

    public void addStatisticWeekPlanLabel(StatisticWeekPlanLabel statisticWeekPlanLabel) {
        if(statisticWeekPlanLabel != null) {
            this.readTypeMap = this.addWeekPlanLabelList(this.readTypeMap, statisticWeekPlanLabel.getReadLabelList());
            this.cognitionTypeMap = this.addWeekPlanLabelList(this.cognitionTypeMap, statisticWeekPlanLabel.getCognitionLabelList());
            this.literacyTypeMap = this.addWeekPlanLabelList(this.literacyTypeMap, statisticWeekPlanLabel.getLiteracyLabelList());
            this.fiveAreaTypeMap = this.addWeekPlanLabelList(this.fiveAreaTypeMap, statisticWeekPlanLabel.getFiveAreaLabelList());
        }
    }

    public HashMap<Integer, WeekPlanLabel> addWeekPlanLabelList(HashMap<Integer, WeekPlanLabel> weekPlanLabelMap, List<WeekPlanLabel> newList) {
        for(WeekPlanLabel item : newList) {
            WeekPlanLabel oldItem = weekPlanLabelMap.get(item.getId());
            if(oldItem != null) {
                oldItem.setFinishNum(oldItem.getFinishNum() + 1);
            } else {
                item.setFinishNum(1);
                weekPlanLabelMap.put(item.getId(), item);
            }
        }
        return weekPlanLabelMap;
    }

    @JsonProperty("readTypeList")
    public List<WeekPlanLabel> getReadTypeList() {
        return this.weekPlanLabelListByMap(this.readTypeMap);
    }

    @JsonProperty("cognitionTypeList")
    public List<WeekPlanLabelParent> getCognitionTypeList() {
        return this.weekPlanLabelParentListByMap(this.cognitionTypeMap);
    }

    @JsonProperty("literacyTypeList")
    public List<WeekPlanLabel> getLiteracyTypeList() {
        return this.weekPlanLabelListByMap(this.literacyTypeMap);
    }

    @JsonProperty("fiveAreaTypeList")
    public List<WeekPlanLabel> getFiveAreaTypeList() {
        return this.weekPlanLabelListByMap(this.fiveAreaTypeMap);
    }

    private List<WeekPlanLabel> weekPlanLabelListByMap(HashMap<Integer, WeekPlanLabel> map) {

        List<WeekPlanLabel> weekPlanLabelList = new ArrayList<>();

        for(Map.Entry<Integer, WeekPlanLabel> entry : map.entrySet()){
            WeekPlanLabel item = entry.getValue();
            weekPlanLabelList.add(item);
        }
        WeekPlanLabelStyle weekPlanLabelStyle = null;

        if(weekPlanLabelList.size() > 0) {
            weekPlanLabelStyle = weekPlanLabelList.get(0).getLabelType();
        }

        if(weekPlanLabelStyle != null && !weekPlanLabelStyle.equals(WeekPlanLabelStyle.FIVE_AREA)){
            weekPlanLabelList.sort(new SortByFinishNumAndOrderBy());
        }
        return weekPlanLabelList;
    }

    private List<WeekPlanLabelParent> weekPlanLabelParentListByMap(HashMap<Integer, WeekPlanLabel> map) {
        HashMap<String, WeekPlanLabelParent> parentHashMap = new HashMap<>();
        for(Map.Entry<Integer, WeekPlanLabel> entry : map.entrySet()){
            WeekPlanLabel item = entry.getValue();
            WeekPlanLabelParentStyle parentStyle = WeekPlanLabelParentStyle.getById(item.getParentId());
            if(parentStyle != null) {
                WeekPlanLabelParent labelParentItem = parentHashMap.get(parentStyle.getName());
                if(labelParentItem == null) {
                    WeekPlanLabelParent parent = new WeekPlanLabelParent();
                    parent.setName(parentStyle.getName());
                    List<WeekPlanLabel> labelList = new ArrayList<>();
                    labelList.add(item);
                    parent.setWeekPlanLabelList(labelList);
                    parentHashMap.put(parentStyle.getName(), parent);
                } else {
                    List<WeekPlanLabel> labelList = labelParentItem.getWeekPlanLabelList();
                    labelList.add(item);
                    labelParentItem.setWeekPlanLabelList(labelList);
                    parentHashMap.put(parentStyle.getName(), labelParentItem);
                }
            }
        }
        List<WeekPlanLabelParent> weekPlanLabelParentList = new ArrayList<>();
        for(Map.Entry<String, WeekPlanLabelParent> entry : parentHashMap.entrySet()) {
            weekPlanLabelParentList.add(entry.getValue());
        }
        return weekPlanLabelParentList;
    }
}

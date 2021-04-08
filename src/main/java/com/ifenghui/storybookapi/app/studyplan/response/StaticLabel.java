package com.ifenghui.storybookapi.app.studyplan.response;

import com.ifenghui.storybookapi.app.studyplan.entity.WeekPlanLabel;

import java.util.List;

public class StaticLabel {

    Integer id;

    String title;

    String subTitle;

    List<String> wordCountList;

    List<TimeNumber> timeNumberList;

    List<WeekPlanLabelParent> weekPlanLabelParentList;

    public List<TimeNumber> getTimeNumberList() {
        return timeNumberList;
    }

    public void setTimeNumberList(List<TimeNumber> timeNumberList) {
        this.timeNumberList = timeNumberList;
    }

    public List<String> getWordCountList() {
        return wordCountList;
    }

    public void setWordCountList(List<String> wordCountList) {
        this.wordCountList = wordCountList;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    List<WeekPlanLabel> weekPlanLabelList;

    public String getTitle() {
        if(this.id.equals(2)) {
            return "学习时长";
        } else if(this.id.equals(3)) {
            return "阅读类型统计";
        } else if(this.id.equals(4)) {
            return "认知学习";
        } else if(this.id.equals(5)) {
            return "识字学习";
        } else if(this.id.equals(6)) {
            return "五大领域指数分析";
        }
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getSubTitle() {
        if(this.id.equals(6)) {
            return "涵盖幼儿教育五大领域，促进儿童全面发展";
        } else if(this.id.equals(3)) {
            return "精选多样化故事类型，拓展知识面，提升综合阅读能力";
        }
        return subTitle;
    }

    public void setSubTitle(String subTitle) {
        this.subTitle = subTitle;
    }

    public List<WeekPlanLabel> getWeekPlanLabelList() {
        return weekPlanLabelList;
    }

    public void setWeekPlanLabelList(List<WeekPlanLabel> weekPlanLabelList) {
        this.weekPlanLabelList = weekPlanLabelList;
    }

    public List<WeekPlanLabelParent> getWeekPlanLabelParentList() {
        return weekPlanLabelParentList;
    }

    public void setWeekPlanLabelParentList(List<WeekPlanLabelParent> weekPlanLabelParentList) {
        this.weekPlanLabelParentList = weekPlanLabelParentList;
    }
}

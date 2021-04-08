package com.ifenghui.storybookapi.app.studyplan.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.ifenghui.storybookapi.api.response.base.ApiResponse;
import com.ifenghui.storybookapi.app.studyplan.entity.WeekPlanLabel;
import com.ifenghui.storybookapi.app.user.entity.User;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Random;

public class WeekStudyReportResponse extends ApiResponse {

    List<WeekPlanLabel> readLabelList;

    List<WeekPlanLabel> literacyLabelList;

    List<WeekPlanLabel> readLabelListNew;

    Integer readWordCount;

    Integer score;

    Integer isFinishReview;

    Integer isHasContent;

    public List<WeekPlanLabel> getLiteracyLabelList() {
        return literacyLabelList;
    }

    public void setLiteracyLabelList(List<WeekPlanLabel> literacyLabelList) {
        this.literacyLabelList = literacyLabelList;
    }

    public List<WeekPlanLabel> getReadLabelList() {
        return readLabelList;
    }

    public void setReadLabelList(List<WeekPlanLabel> readLabelList) {
        this.readLabelList = readLabelList;
    }

    public Integer getScore() {
        return score;
    }

    public void setScore(Integer score) {
        this.score = score;
    }

    public Integer getIsFinishReview() {
        return isFinishReview;
    }

    public void setIsFinishReview(Integer isFinishReview) {
        this.isFinishReview = isFinishReview;
    }

    public Integer getReadWordCount() {
        return readWordCount;
    }

    public void setReadWordCount(Integer readWordCount) {
        this.readWordCount = readWordCount;
    }

    public List<WeekPlanLabel> getReadLabelListNew() {
        return readLabelListNew;
    }

    public void setReadLabelListNew(List<WeekPlanLabel> readLabelListNew) {
        if(readLabelListNew != null && readLabelListNew.size() < 8 ) {
            HashSet<Integer> hashSet = new HashSet<>();
            List<WeekPlanLabel> weekPlanLabelList = new ArrayList<>();
            for(WeekPlanLabel item : readLabelListNew) {
                hashSet.add(item.getId());
                weekPlanLabelList.add(item);
            }
            for(int i = 1; i < 9;i++) {
                if(hashSet.size() < 8) {
                    if(hashSet.contains(i)) {
                        continue;
                    } else {
                        hashSet.add(i);
                        WeekPlanLabel weekPlanLabel = new WeekPlanLabel();
                        weekPlanLabel.setId(i);
                        weekPlanLabel.setFinishNum(0);
                        weekPlanLabel.setLabelType(1);
                        weekPlanLabel.setParentId(0);
                        weekPlanLabelList.add(weekPlanLabel);
                    }
                }
            }
            this.readLabelListNew = weekPlanLabelList;
        } else {
            this.readLabelListNew = readLabelListNew;
        }
    }

    public Integer getIsHasContent() {
        return isHasContent;
    }

    public void setIsHasContent(Integer isHasContent) {
        this.isHasContent = isHasContent;
    }
}

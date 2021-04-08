package com.ifenghui.storybookapi.app.studyplan.response;

public class MagazineTask {

    Integer magPlanId;
    Integer planType;
    String magImg;
    Integer isFinish;
    Integer id;

    public MagazineTask(Integer id, Integer magPlanId, Integer planType, String magImg, Integer isFinish) {
        this.id = id;
        this.magPlanId = magPlanId;
        this.planType = planType;
        this.magImg = magImg;
        this.isFinish = isFinish;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getMagPlanId() {
        return magPlanId;
    }

    public void setMagPlanId(Integer magPlanId) {
        this.magPlanId = magPlanId;
    }

    public Integer getPlanType() {
        return planType;
    }

    public void setPlanType(Integer planType) {
        this.planType = planType;
    }

    public String getMagImg() {
        return magImg;
    }

    public void setMagImg(String magImg) {
        this.magImg = magImg;
    }

    public Integer getIsFinish() {
        return isFinish;
    }

    public void setIsFinish(Integer isFinish) {
        this.isFinish = isFinish;
    }
}

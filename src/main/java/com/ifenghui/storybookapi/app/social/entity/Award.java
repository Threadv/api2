package com.ifenghui.storybookapi.app.social.entity;


import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * 奖赏
 */
public class Award {

    /**
     * id
     */
    Integer id;
    /**
     * 星星
     */
    Integer star;
    /**
     * 时间
     */
    Integer days;
    /**
     * 图标
     */
    String icon;


    boolean isFinish;

    public Award(){

    }

    public Award(int id,int star,int days,String icon){
        this.id=id;
        this.star=star;
        this.days=days;
        this.icon=icon;
        this.isFinish=false;
    }

    public static List<Award> getAwardAll(int continueDay){
        List<Award>  awards=getAwardAll();
        for(Award award:awards){
            if(continueDay>=award.getDays()){
                award.setFinish(true);
            }
        }

        return awards;
    }

//    public static List<Award> getAwardNewFour(int continueDay, List<ScheduleUserRewardRecord> scheduleUserRewardRecordList){
//        List<Award> awardList = getAwardAll();
//        int times = 0;
//
//        Iterator<Award> awardIterator = awardList.iterator();
//        while(awardIterator.hasNext()){
//            Award award = awardIterator.next();
//            if(scheduleUserRewardRecordList != null){
//                for(ScheduleUserRewardRecord scheduleUserRewardRecord:scheduleUserRewardRecordList) {
//                    if (scheduleUserRewardRecord.getRewardId().equals(award.getId())) {
//                        awardIterator.remove();
//                    }
//                }
//            }
//
//
//
//            if(continueDay >= award.getDays()){
//                award.setFinish(true);
//            } else {
//                times+=1;
//            }
//            if(times > 4){
//                awardIterator.remove();
//            }
//        }
//
//        return awardList;
//    }

    public static Award getAwardByAwardId(int awardId){
        List<Award> awardList = getAwardAll();
        for(Award award:awardList){
            if(awardId == award.getId()){
                return award;
            }
        }
        return null;
    }

    public static List<Award> getAwardAll(){
        List<Award> awards=new ArrayList();
        awards.add(new Award(7,200,7,""));
        awards.add(new Award(14,500,14,""));
        awards.add(new Award(60,1500,60,""));
        awards.add(new Award(100,3000,100,""));
        awards.add(new Award(360,8000,360,""));
        awards.add(new Award(720,15000,720,""));
        awards.add(new Award(1000,30000,1000,""));

//        awards.add(new Award(7,200,1,""));
//        awards.add(new Award(14,500,2,""));
//        awards.add(new Award(60,1500,3,""));
//        awards.add(new Award(100,3000,4,""));
//        awards.add(new Award(360,8000,5,""));
//        awards.add(new Award(720,15000,6,""));
//        awards.add(new Award(1000,30000,7,""));

        return awards;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getStar() {
        return star;
    }

    public void setStar(Integer star) {
        this.star = star;
    }


    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public Integer getDays() {
        return days;
    }

    public void setDays(Integer days) {
        this.days = days;
    }

    public boolean isFinish() {
        return isFinish;
    }

    public void setFinish(boolean finish) {
        isFinish = finish;
    }
}

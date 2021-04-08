package com.ifenghui.storybookapi.style;

import java.util.Calendar;
import java.util.Date;

//时间周期枚举
//用于sql查询传递参数，
//不同的枚举值可以用于请求不同的sql方法，从而优化缓存
//直接在hql中使用time范围是无法增加缓存的
public enum RangeTimeStyle {
    CURRENT_DAY(1,"当前一天"),
    All_DAY(2,"17年6月1日到当天")
    ;
//    ALL_PLAN(3, "全部"),
//    DEFAULT(0, "没有参加");

    int id;
    String name;

    RangeTimeStyle(int id, String name){
        this.id=id;
        this.name=name;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public static RangeTimeStyle getById(int id){
        for(RangeTimeStyle style: RangeTimeStyle.values()){
            if(style.getId()==id){
                return style;
            }
        }
        return null;
    }
    public Date getBeginDate(){
        Calendar calendar=Calendar.getInstance();
        if(id==1){
            calendar.set(Calendar.HOUR_OF_DAY,0);
            calendar.set(Calendar.MINUTE,0);
            calendar.set(Calendar.SECOND,0);
        }else if(id==2){
            calendar.set(Calendar.YEAR,2017);
            calendar.set(Calendar.MONTH,5);
            calendar.set(Calendar.DATE,1);

            calendar.set(Calendar.HOUR_OF_DAY,0);
            calendar.set(Calendar.MINUTE,0);
            calendar.set(Calendar.SECOND,0);
        }
        return calendar.getTime();
    }
    public Date getEndDate(){
        Calendar calendar=Calendar.getInstance();
            calendar.set(Calendar.MILLISECOND,0);
            calendar.set(Calendar.HOUR_OF_DAY,23);
            calendar.set(Calendar.MINUTE,59);
            calendar.set(Calendar.SECOND,59);

        return calendar.getTime();
    }

    public Long getEndDateLong(){
        return this.getEndDate().getTime();
    }
}

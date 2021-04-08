package com.ifenghui.storybookapi.style;

/**
 * 后台行为记录
 * 用户记录后台管理元的操作
 *
 * 每个枚举名称需要跟dao类名对应
 * 只有枚举包含的操作才会被记录
 */
public enum ActionLogType {

    Article(1,true)
    ,Ad(2,true)
    ,MagUserPush(3,false) //后台推送
    ,MagZip(4,false) //生成杂志zip
//    ,Subscription(3)
    ;


    int id;
    boolean isEntity;

    ActionLogType(int id, boolean isEntity){
        this.id=id;this.isEntity=isEntity;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public static ActionLogType getById(int id){
        for(ActionLogType tagType:ActionLogType.values()){
            if(tagType.getId()==id){
                return tagType;
            }
        }
        return null;
    }

    //这里适合dao绑定的entity操作统计
    public static ActionLogType getByName(String name){
        for(ActionLogType tagType:ActionLogType.values()){
            if(tagType.name().equals(name)&&tagType.isEntity){
                return tagType;
            }
        }
        return null;
    }
}

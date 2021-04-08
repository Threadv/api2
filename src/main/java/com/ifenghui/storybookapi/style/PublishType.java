package com.ifenghui.storybookapi.style;

/**
 * Created by wslhk on 2018/7/22.
 */
public enum PublishType {
    UNPUBLISH(0) //未发布
    ,PUBLISH(1)  //已发布
    ,DELETE(2)   //删除
    ,PREVIEW(3)  //预览
    ,WAITPUBLISH(4)  //等待发布。等待系统运行发布
    ,PROCESSING(5) //异步程序处理中
    ;

    int id;

    PublishType(int id){
        this.id=id;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public static PublishType getById(int id){
        for(PublishType item:PublishType.values()){
            if(item.getId()==id){
                return item;
            }
        }
        return null;
    }
}

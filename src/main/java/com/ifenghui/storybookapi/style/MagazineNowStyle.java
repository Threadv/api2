package com.ifenghui.storybookapi.style;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by narella on 2017/3/6.
 */
public enum MagazineNowStyle {
    NOTNOW(0,"非当期"),
    NOW(1,"当期"),
    ;

    int id;
    String name;
    MagazineNowStyle(int id, String name){
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

    static class Output{
        int id;
        String name;
        public Output(int id,String name){
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
    }
    /**
     * 获得所有分类
     * @return
     */
    public static List<MagazineNowStyle.Output> getAll(){
        List<MagazineNowStyle.Output> storyCategories=new ArrayList();
        for(MagazineNowStyle storyCategory: MagazineNowStyle.values()){
            storyCategories.add(
                    new MagazineNowStyle.Output(storyCategory.getId(),storyCategory.getName())
            );
        }
        return  storyCategories;
    }
}

package com.ifenghui.storybookapi.style;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by wslhk on 2016/12/22.
 */
public enum StoryCategory {
    DEFAULT(0,"默认选择"),
    SINGLE(1,"故事单本"),SERIAL(2,"故事合集"),AUDIO(3,"音频"),GAME(2,"游戏");

    int id;
    String name;
    StoryCategory(int id, String name){
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
    public static List<Output> getStoryCategorys(){
        List<Output> storyCategories=new ArrayList();
        for(StoryCategory storyCategory: StoryCategory.values()){
            storyCategories.add(
                    new Output(storyCategory.getId(),storyCategory.getName())
            );
        }
        return  storyCategories;
    }

//    public static void main(String[] args){
//        List aa=getStoryCategorys();
//    }
}

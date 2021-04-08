package com.ifenghui.storybookapi.app.social.response;

public class StarRuleIntro {

    String name;

    Integer star;

    Integer times;

    public StarRuleIntro(String name, Integer star, Integer times){
        this.name = name;
        this.star = star;
        this.times = times;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getStar() {
        return star;
    }

    public void setStar(Integer star) {
        this.star = star;
    }

    public Integer getTimes() {
        return times;
    }

    public void setTimes(Integer times) {
        this.times = times;
    }
}

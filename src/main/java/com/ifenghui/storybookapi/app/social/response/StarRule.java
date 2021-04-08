package com.ifenghui.storybookapi.app.social.response;

import java.util.List;

public class StarRule {

    Integer id;

    String name;

    String intro;

    List<StarRuleIntro> starRuleIntroList;

    public StarRule(Integer id, String name, String intro, List<StarRuleIntro> starRuleIntroList){
        this.id = id;
        this.name = name;
        this.intro = intro;
        this.starRuleIntroList = starRuleIntroList;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getIntro() {
        return intro;
    }

    public void setIntro(String intro) {
        this.intro = intro;
    }

    public List<StarRuleIntro> getStarRuleIntroList() {
        return starRuleIntroList;
    }

    public void setStarRuleIntroList(List<StarRuleIntro> starRuleIntroList) {
        this.starRuleIntroList = starRuleIntroList;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }
}

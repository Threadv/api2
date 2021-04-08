package com.ifenghui.storybookapi.app.system.service.elastic;

import java.util.ArrayList;
import java.util.List;

public class Hit<T> {
    Integer total;
    Integer max_score;
    List<HitInner<T>> hits;


    public Integer getTotal() {
        return total;
    }

    public void setTotal(Integer total) {
        this.total = total;
    }

    public Integer getMax_score() {
        return max_score;
    }

    public void setMax_score(Integer max_score) {
        this.max_score = max_score;
    }

    public List<HitInner<T>> getHits() {
        return hits;
    }

    public void setHits(List<HitInner<T>> hits) {
        this.hits = hits;
    }

    public List<T> getSouceList(){
        if(hits==null){
            return new ArrayList(0);
        }
        List<T> lists=new ArrayList<T>(hits.size());
        for(HitInner<T> hitInner:hits){
            lists.add(hitInner.get_source());
        }
        return lists;
    }
}

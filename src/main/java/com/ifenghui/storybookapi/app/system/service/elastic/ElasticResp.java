package com.ifenghui.storybookapi.app.system.service.elastic;

public class ElasticResp<T> {
    Integer took;
    Boolean timed_out;
    Shard _shards;
    Hit<T> hits;

    public Hit<T> getHits() {
        return hits;
    }

    public void setHits(Hit<T> hits) {
        this.hits = hits;
    }

    public Integer getTook() {
        return took;
    }

    public void setTook(Integer took) {
        this.took = took;
    }

    public Boolean getTimed_out() {
        return timed_out;
    }

    public void setTimed_out(Boolean timed_out) {
        this.timed_out = timed_out;
    }

    public Shard get_shards() {
        return _shards;
    }

    public void set_shards(Shard _shards) {
        this._shards = _shards;
    }
}

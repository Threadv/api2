package com.ifenghui.storybookapi.app.system.service.elastic;

public class HitInner<T> {
    String _index;
    String _type;
    String _id;
    T _source;


    public String get_index() {
        return _index;
    }

    public void set_index(String _index) {
        this._index = _index;
    }

    public String get_type() {
        return _type;
    }

    public void set_type(String _type) {
        this._type = _type;
    }

    public String get_id() {
        return _id;
    }

    public void set_id(String _id) {
        this._id = _id;
    }

    public T get_source() {
        return _source;
    }

    public void set_source(T _source) {
        this._source = _source;
    }
}

package com.ifenghui.storybookapi.app.express.response;

import java.io.Serializable;

public class YearContent implements Serializable {
    int year;

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    @Override
    public int hashCode() {
        return this.year;
    }

    @Override
    public boolean equals(Object obj) {
        if(((YearContent)obj).year==this.year){
            return true;
        }
        return false;
    }
}

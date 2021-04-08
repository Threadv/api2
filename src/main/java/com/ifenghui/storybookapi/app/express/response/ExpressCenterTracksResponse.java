package com.ifenghui.storybookapi.app.express.response;

/**
 * Created by jia on 2016/12/23.
 */

import com.ifenghui.storybookapi.api.response.base.ApiPageResponse;
import com.ifenghui.storybookapi.app.express.entity.ExpressCenterTrack;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class ExpressCenterTracksResponse extends ApiPageResponse {

    Set<YearContent> years;

    Set<String> srcMarks;

    List<ExpressCenterTrack> expressCenterTracks;


    public List<ExpressCenterTrack> getExpressCenterTracks() {
        return expressCenterTracks;
    }

    public void setExpressCenterTracks(List<ExpressCenterTrack> expressCenterTracks) {
        this.expressCenterTracks = expressCenterTracks;
        years=new HashSet<>();

        for(ExpressCenterTrack track:expressCenterTracks){
            YearContent yearContent=new YearContent();
            yearContent.setYear(track.getYear());
            years.add(yearContent);
        }

    }

    public Set<YearContent> getYears() {
        return years;
    }

    public void setYears(Set<YearContent> years) {
        this.years = years;
    }

    public Set<String> getSrcMarks() {
        return srcMarks;
    }

    public void setSrcMarks(Set<String> srcMarks) {
        this.srcMarks = srcMarks;
    }

}

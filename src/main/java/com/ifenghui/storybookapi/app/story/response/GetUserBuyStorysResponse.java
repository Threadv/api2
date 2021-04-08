package com.ifenghui.storybookapi.app.story.response;

import com.ifenghui.storybookapi.api.response.base.ApiPageResponse;
import com.ifenghui.storybookapi.app.transaction.entity.BuyStoryRecord;
import com.ifenghui.storybookapi.app.story.entity.Magazine;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by wml on 2016/12/30.
 */
public class GetUserBuyStorysResponse extends ApiPageResponse {
    List<BuyStoryRecord> buyStoryRecords;
    List<Magazine> magazines;
    public List<BuyStoryRecord> getBuyStoryRecords() {
        return buyStoryRecords;
    }

    public void setBuyStoryRecords(List<BuyStoryRecord> buyStoryRecords) {
        this.buyStoryRecords = buyStoryRecords;
    }

    public List<Magazine> getMagazines() {
        if(magazines==null){magazines=new ArrayList();}
        return magazines;
    }

    public void setMagazines(List<Magazine> magazines) {
        this.magazines = magazines;
    }
}

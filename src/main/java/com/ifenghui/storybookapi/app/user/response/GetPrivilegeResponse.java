package com.ifenghui.storybookapi.app.user.response;/**
 * @Date: 2018/12/13 17:05
 * @Description:
 */

import com.ifenghui.storybookapi.app.app.response.BaseResponse;
import com.ifenghui.storybookapi.app.user.entity.Msg;

import java.util.Date;
import java.util.List;

/**
 * @Date: 2018/12/13 17:05
 * @Description:
 */
public class GetPrivilegeResponse extends BaseResponse {

    Integer isAbliltyPlan;
    Date endTime;
    List<Msg> msgList;

    String url;

    public Integer getIsAbliltyPlan() {
        return isAbliltyPlan;
    }

    public void setIsAbliltyPlan(Integer isAbliltyPlan) {
        this.isAbliltyPlan = isAbliltyPlan;
    }

    public Date getEndTime() {
        return endTime;
    }

    public void setEndTime(Date endTime) {
        this.endTime = endTime;
    }

    public List<Msg> getMsgList() {
        return msgList;
    }

    public void setMsgList(List<Msg> msgList) {
        this.msgList = msgList;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}

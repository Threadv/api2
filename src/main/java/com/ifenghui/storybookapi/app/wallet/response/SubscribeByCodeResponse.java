package com.ifenghui.storybookapi.app.wallet.response;

/**
 * Created by jia on 2017/1/9.
 */
import com.ifenghui.storybookapi.api.response.base.ApiResponse;

public class SubscribeByCodeResponse extends ApiResponse {
    /**
     * 1跳转书架订阅区
     * 2跳故事集区
     * 3跳转购买单本区
     * 4跳转到课程章节详情区
     * 5跳转至vip购买区域
     * 6跳转至宝宝会读2-4购买区域
     * 7跳转至宝宝会读4-6购买区域
     */
    Integer type;

    /**
     * 成功页面提示文字
     */
    String intro;

    Integer targetValue;

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public String getIntro() {
        return intro;
    }

    public void setIntro(String intro) {
        this.intro = intro;
    }

    public Integer getTargetValue() {
        return targetValue;
    }

    public void setTargetValue(Integer targetValue) {
        this.targetValue = targetValue;
    }
}

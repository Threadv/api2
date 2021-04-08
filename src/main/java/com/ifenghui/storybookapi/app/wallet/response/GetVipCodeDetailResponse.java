package com.ifenghui.storybookapi.app.wallet.response;

import com.ifenghui.storybookapi.api.response.base.ApiResponse;
import com.ifenghui.storybookapi.app.presale.entity.CodeRemindIntro;

import java.util.List;

public class GetVipCodeDetailResponse extends ApiResponse {

    String intro;

    Integer codeType;

    String content;

    /**
     * 1跳转书架订阅区
     * 2跳故事集区
     * 3跳转购买单本区
     * 4跳转到课程章节详情区
     * 5跳转至vip购买区域
     * 6跳转至宝宝会读购买区域
     */
    Integer type;

    /**
     * 兑换码提示信息集合
     */
    List<CodeRemindIntro> introList;

    public Integer getCodeType() {
        if(this.codeType == null){
            return 1;
        } else {
            return codeType;
        }
    }

    public List<CodeRemindIntro> getIntroList() {
        return introList;
    }

    public void setIntroList(List<CodeRemindIntro> introList) {
        this.introList = introList;
    }

    public void setCodeType(Integer codeType) {
        this.codeType = codeType;
    }

    public String getIntro() {
        return intro;
    }

    public void setIntro(String intro) {
        this.intro = intro;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }
}

package com.ifenghui.storybookapi.active1902.response;


import com.ifenghui.storybookapi.active1902.entity.Answer;
import com.ifenghui.storybookapi.api.response.base.ApiResponse;

import java.util.List;

/**
 * @Date: 2019/2/19 15:21
 * @Description:
 */
public class AnswerListResponse extends ApiResponse {

    List<Answer> answerList;

    public List<Answer> getAnswerList() {
        return answerList;
    }

    public void setAnswerList(List<Answer> answerList) {
        this.answerList = answerList;
    }
}

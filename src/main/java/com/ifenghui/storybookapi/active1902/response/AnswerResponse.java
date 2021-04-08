package com.ifenghui.storybookapi.active1902.response;


import com.ifenghui.storybookapi.active1902.entity.Answer;
import com.ifenghui.storybookapi.api.response.base.ApiResponse;

/**
 * @Date: 2019/2/19 15:21
 * @Description:
 */
public class AnswerResponse extends ApiResponse {

    Answer answer;

    public Answer getAnswer() {
        return answer;
    }

    public void setAnswer(Answer answer) {
        this.answer = answer;
    }
}

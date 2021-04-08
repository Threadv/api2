package com.ifenghui.storybookapi.active1902.response;


import com.ifenghui.storybookapi.active1902.entity.Question;
import com.ifenghui.storybookapi.api.response.base.ApiResponse;

/**
 * @Date: 2019/2/19 15:21
 * @Description:
 */
public class QuestionResponse extends ApiResponse {

    Question question;

    public Question getQuestion() {
        return question;
    }

    public void setQuestion(Question question) {
        this.question = question;
    }
}

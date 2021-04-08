package com.ifenghui.storybookapi.active1902.response;


import com.ifenghui.storybookapi.active1902.entity.Question;
import com.ifenghui.storybookapi.api.response.base.ApiPageResponse;

import java.util.List;

/**
 * @Date: 2019/2/19 15:21
 * @Description:
 */
public class QuestionListResponse extends ApiPageResponse {

    List<Question> questionList;

    public List<Question> getQuestionList() {
        return questionList;
    }

    public void setQuestionList(List<Question> questionList) {
        this.questionList = questionList;
    }
}

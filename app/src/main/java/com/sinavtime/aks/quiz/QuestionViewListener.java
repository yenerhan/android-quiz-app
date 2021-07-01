package com.sinavtime.aks.quiz;

import com.sinavtime.aks.model.quiz.QuestionDTO;

/**
 * Created by erhan.yener on 25.10.2018.
 */

public interface QuestionViewListener {
    void radioButtonItemSelected(String questionId, String userAnswer);
    Boolean favouriteQuestionFound(String questionUniqueId);
    void favouriteQuestionSave(QuestionDTO favouriteQuestion);
    void favouriteQuestionDelete(String questionUniqueId);
}

package com.sinavtime.aks.quiz;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.sinavtime.aks.R;
import com.sinavtime.aks.model.quiz.QuestionDTO;
import com.sinavtime.aks.quiz.viewHolder.EndViewHolder;
import com.sinavtime.aks.quiz.viewHolder.QestionViewHolder;

import java.util.List;

/**
 * Created by erhan.yener on 23.10.2018.
 */

public class QuizAdapter extends RecyclerView.Adapter {

    List<QuestionDTO> questionDTOList;
    LayoutInflater inflater;
    QuestionViewListener questionViewListener;
    EndViewListener endViewListener;
    QuizFinishDTO quizFinishDTO;

    public QuizAdapter(Context context, List<QuestionDTO> questionDTOList, QuestionViewListener questionViewListener, EndViewListener endViewListener, QuizFinishDTO quizFinishDTO) {
        this.questionViewListener = questionViewListener;
        this.endViewListener = endViewListener;
        inflater = LayoutInflater.from(context);
        this.questionDTOList = questionDTOList;
        this.quizFinishDTO = quizFinishDTO;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view;
        switch (viewType) {
            case 1:
                view = inflater.inflate(R.layout.card_question_text_type, parent, false);
                return new QestionViewHolder(view, questionViewListener, questionDTOList);
            case 3:
                view = inflater.inflate(R.layout.card_question_end_type, parent, false);
                return new EndViewHolder(view, endViewListener, questionDTOList);
        }
        return null;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        QuestionDTO selectedQuestion = questionDTOList.get(position);
        switch (selectedQuestion.getQuizType().getId()) {
            case 1:
                ((QestionViewHolder) holder).setData(selectedQuestion, position, quizFinishDTO);
                break;
            case 2:
                break;
            case 3:
                ((EndViewHolder) holder).setData(selectedQuestion, position, quizFinishDTO);
                break;
        }
    }

    @Override
    public int getItemCount() {
        return questionDTOList.size();
    }

    @Override
    public int getItemViewType(int position) {
        switch (questionDTOList.get(position).getQuizType().getId()) {
            case 1:
                return QuizType.QUESTION.getId();
            case 2:
                return QuizType.IMAGE.getId();
            case 3:
                return QuizType.END.getId();
            default:
                return -1;
        }
    }


}

package com.sinavtime.aks.quiz.viewHolder;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.sinavtime.aks.R;
import com.sinavtime.aks.model.quiz.QuestionDTO;
import com.sinavtime.aks.quiz.EndViewListener;
import com.sinavtime.aks.quiz.QuizFinishDTO;

import java.util.List;

/**
 * Created by erhan.yener on 24.10.2018.
 */

public class EndViewHolder extends RecyclerView.ViewHolder {

    List<QuestionDTO> questionDTOList;
    TextView tv_score_total,tv_true_total,tv_false_total;
    Button sinav_bitti;
    EndViewListener endViewListener;

    public EndViewHolder(View itemView, EndViewListener listener,List<QuestionDTO> questionDTOList) {
        super(itemView);
        this.questionDTOList = questionDTOList;
        this.endViewListener=listener;
        sinav_bitti = (Button) itemView.findViewById(R.id.sinav_bitti);
        tv_score_total = (TextView) itemView.findViewById(R.id.tv_score_total);
        tv_true_total = (TextView) itemView.findViewById(R.id.tv_true_total);
        tv_false_total = (TextView) itemView.findViewById(R.id.tv_false_total);

        sinav_bitti.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sinav_bitti.setEnabled(false);
                endViewListener.quizFinished();
            }
        });

    }

    public void setData(QuestionDTO selectedQuestion, int position, QuizFinishDTO quizFinishDTO) {
        this.tv_score_total.setText(""+quizFinishDTO.getTotalScore());
        this.tv_true_total.setText(""+quizFinishDTO.getTotalTrueCount());
        this.tv_false_total.setText(""+quizFinishDTO.getTotalFalseCount());
    }
}


package com.sinavtime.aks.adapter;

import android.app.Activity;
import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.sinavtime.aks.R;
import com.sinavtime.aks.model.quiz.QuizDTO;

import java.util.ArrayList;

public class QuizAdapter extends BaseAdapter {

    private Context context;
    private ArrayList<QuizDTO> quizDTOList = new ArrayList<>();

    public QuizAdapter(Context context, ArrayList<QuizDTO> userExamDTOList) {
        this.context = context;
        if (userExamDTOList != null) {
            this.quizDTOList = userExamDTOList;
        }
    }

    @Override
    public int getCount() {
        return quizDTOList.size();
    }

    @Override
    public Object getItem(int position) {
        return quizDTOList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return quizDTOList.get(position).getQuizId();
    }

    @Override
    public View getView(int position, View view, ViewGroup viewGroup) {
        QuizDTO quizDTO = quizDTOList.get(position);
        LinearLayout linearLayout = (LinearLayout) ((Activity) context).getLayoutInflater().inflate(R.layout.custom_quiz_listview, null);
        TextView quiz_name = (TextView) linearLayout.findViewById(R.id.quiz_name);
        TextView quiz_comment = (TextView) linearLayout.findViewById(R.id.quiz_comment);
        TextView quiz_question_total = (TextView) linearLayout.findViewById(R.id.quiz_question_total);
        TextView quiz_true_total = (TextView) linearLayout.findViewById(R.id.quiz_true_total);
        TextView quiz_false_total = (TextView) linearLayout.findViewById(R.id.quiz_false_total);
        TextView quiz_score_total = (TextView) linearLayout.findViewById(R.id.quiz_score_total);
        ImageView quiz_image = (ImageView) linearLayout.findViewById(R.id.quiz_image);
        ProgressBar progressBar=linearLayout.findViewById(R.id.progressBar);

        quiz_name.setText(quizDTO.getQuiz_name());
        quiz_comment.setText(quizDTO.getComment());
        quiz_question_total.setText(""+quizDTO.getTotalQuestion());
        quiz_true_total.setText(""+quizDTO.getTotalTrueCount());
        quiz_false_total.setText(""+quizDTO.getTotalFalseCount());
        quiz_score_total.setText(""+quizDTO.getTotalScore());
        progressBar.setProgress((quizDTO.getTotalScore()*100)/(quizDTO.getTotalQuestion()*5));
        if(quizDTO.getTotalTrueCount()!=0 || quizDTO.getTotalFalseCount()!=0){
            quiz_image.setImageResource(R.drawable.ic_check_finished);
        }else{
            quiz_image.setImageResource(R.drawable.ic_check);
        }
        return linearLayout;
    }
}

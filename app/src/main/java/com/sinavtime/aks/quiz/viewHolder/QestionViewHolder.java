package com.sinavtime.aks.quiz.viewHolder;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.sinavtime.aks.R;
import com.sinavtime.aks.helper.FavouriteQuestionSQLHelper;
import com.sinavtime.aks.model.quiz.QuestionDTO;
import com.sinavtime.aks.quiz.QuestionViewListener;
import com.sinavtime.aks.quiz.QuizActivity;
import com.sinavtime.aks.quiz.QuizFinishDTO;

import java.util.List;

/**
 * Created by erhan.yener on 24.10.2018.
 */

public class QestionViewHolder extends RecyclerView.ViewHolder {


    List<QuestionDTO> questionDTOList;
    // CardView icersindeki objeler
    TextView questionId, questionText, correctAnswer;
    private RadioGroup radioGroup;
    private RadioButton a_option, b_option, c_option, d_option, e_option, selected_option;
    public ImageView btnFavourite;
    QuestionViewListener questionViewListener;

    public QestionViewHolder(final View itemView, QuestionViewListener listener, List<QuestionDTO> questionDTOList) {
        super(itemView);
        this.questionDTOList = questionDTOList;
        this.questionViewListener = listener;
        questionId = (TextView) itemView.findViewById(R.id.questionId);
        questionText = (TextView) itemView.findViewById(R.id.questionText);
        radioGroup = (RadioGroup) itemView.findViewById(R.id.radioGroup);
        a_option = (RadioButton) itemView.findViewById(R.id.a_option);
        b_option = (RadioButton) itemView.findViewById(R.id.b_option);
        c_option = (RadioButton) itemView.findViewById(R.id.c_option);
        d_option = (RadioButton) itemView.findViewById(R.id.d_option);
        e_option = (RadioButton) itemView.findViewById(R.id.e_option);
        correctAnswer = (TextView) itemView.findViewById(R.id.correctAnswer);
        btnFavourite = (ImageView) itemView.findViewById(R.id.btnFavourite);

        // Tıklama işlemleri burada gerceklestiriyoruz
        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                int id = radioGroup.getCheckedRadioButtonId();
                selected_option = (RadioButton) itemView.findViewById(id);
                String userAnswer = "";
                if (selected_option == a_option) {
                    userAnswer = "a";
                } else if (selected_option == b_option) {
                    userAnswer = "b";
                } else if (selected_option == c_option) {
                    userAnswer = "c";
                } else if (selected_option == d_option) {
                    userAnswer = "d";
                } else if (selected_option == e_option) {
                    userAnswer = "e";
                }
                questionViewListener.radioButtonItemSelected(questionId.getText().toString(), userAnswer);
            }
        });


    }

    public void setData(final QuestionDTO selectedQuestion, int position, QuizFinishDTO quizFinishDTO) {
        this.questionId.setText(selectedQuestion.getQuestionId() + "/" + (questionDTOList.size() - 1));
        this.questionText.setText(selectedQuestion.getQuestionText());
        this.a_option.setText(selectedQuestion.getChoice_a());
        this.b_option.setText(selectedQuestion.getChoice_b());
        this.c_option.setText(selectedQuestion.getChoice_c());
        this.d_option.setText(selectedQuestion.getChoice_d());
        this.e_option.setText(selectedQuestion.getChoice_e());
        if (quizFinishDTO.getQuizFinish()) {
            if (selectedQuestion.getUserAnswer().equals(selectedQuestion.getCorrectAnswer())) {
                this.correctAnswer.setText("Tebrikler");
                this.correctAnswer.setBackgroundResource(R.color.greenTextBack);
            } else {
                this.correctAnswer.setText("Doğru Cevap " + selectedQuestion.getCorrectAnswer().toUpperCase());
                this.correctAnswer.setBackgroundResource(R.color.redTextBack);
            }
            radioGroupCheckByUserAnswer(selectedQuestion);
        }else if(!quizFinishDTO.getQuizFinish() && (selectedQuestion.getUserAnswer()==null || selectedQuestion.getUserAnswer().equals(""))){
            this.radioGroup.clearCheck();
        }else{
            radioGroupCheckByUserAnswer(selectedQuestion);
        }

        if (quizFinishDTO.getQuizFinish()) {
            for (int i = 0; i < radioGroup.getChildCount(); i++) {
                ((RadioButton) radioGroup.getChildAt(i)).setEnabled(false);
            }
        }


        //********************************Favourite Button
        btnFavourite.setTag(R.drawable.ic_favourite);
        btnFavourite.setImageResource(R.drawable.ic_favourite);
        if (questionViewListener.favouriteQuestionFound(selectedQuestion.getQuestionUniqueId())) {
            btnFavourite.setTag(R.drawable.ic_favourite_clicked);
            btnFavourite.setImageResource(R.drawable.ic_favourite_clicked);
        }

        btnFavourite.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {

                int id = (int)btnFavourite.getTag();
                if( id == R.drawable.ic_favourite){
                    btnFavourite.setImageResource(R.drawable.ic_favourite_clicked);
                    questionViewListener.favouriteQuestionSave(selectedQuestion);
                    btnFavourite.setTag(R.drawable.ic_favourite_clicked);
                }else{
                    btnFavourite.setImageResource(R.drawable.ic_favourite);
                    questionViewListener.favouriteQuestionDelete(selectedQuestion.getQuestionUniqueId());
                    btnFavourite.setTag(R.drawable.ic_favourite);
                }
            }
        });
    }

    private void radioGroupCheckByUserAnswer(QuestionDTO selectedQuestion){
        if (selectedQuestion.getUserAnswer().equals("a")) {
            radioGroup.check(a_option.getId());
        } else if (selectedQuestion.getUserAnswer().equals("b")) {
            radioGroup.check(b_option.getId());
        } else if (selectedQuestion.getUserAnswer().equals("c")) {
            radioGroup.check(c_option.getId());
        } else if (selectedQuestion.getUserAnswer().equals("d")) {
            radioGroup.check(d_option.getId());
        } else if (selectedQuestion.getUserAnswer().equals("e")) {
            radioGroup.check(e_option.getId());
        }else{
            radioGroup.clearCheck();
        }
    }

}

package com.sinavtime.aks.fragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.sinavtime.aks.R;
import com.sinavtime.aks.helper.FavouriteQuestionSQLHelper;
import com.sinavtime.aks.model.quiz.QuestionDTO;
import com.sinavtime.aks.quiz.EndViewListener;
import com.sinavtime.aks.quiz.QuestionViewListener;
import com.sinavtime.aks.quiz.QuizAdapter;
import com.sinavtime.aks.quiz.QuizFinishDTO;
import com.sinavtime.aks.quiz.QuizType;

import java.util.ArrayList;
import java.util.List;

public class FavouriteFragment extends Fragment implements QuestionViewListener, EndViewListener {

    RecyclerView recyclerView;
    QuizAdapter quizAdapter;
    List<QuestionDTO> questionDTOList = new ArrayList<>();
    QuizFinishDTO quizFinishDTO;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_favourite, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        quizFinishDTO = new QuizFinishDTO();
        FavouriteQuestionSQLHelper db = new FavouriteQuestionSQLHelper(getContext());
        ArrayList<QuestionDTO> questionDTOListFromDb = db.findAll();
        if (questionDTOListFromDb!=null && questionDTOListFromDb.size() > 0) {
            int questionId=1;
            for (QuestionDTO questionDTO : questionDTOListFromDb) {
                questionDTO.setQuestionId(String.valueOf(questionId));
                questionDTO.setQuizType(QuizType.QUESTION);
                questionDTOList.add(questionDTO);
                questionId++;
            }
            QuestionDTO finishViewHolder = new QuestionDTO();
            finishViewHolder.setQuizType(QuizType.END);
            questionDTOList.add(finishViewHolder);
        }
        recyclerView = view.findViewById(R.id.favourite_question_recyclerView);
        quizAdapter = new QuizAdapter(getContext(), questionDTOList, this, this, quizFinishDTO);
        recyclerView.setAdapter(quizAdapter);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(linearLayoutManager);
    }

    @Override
    public void quizFinished() {
        int totalTrueCount = 0;
        int totalFalseCount = 0;
        for (QuestionDTO questionDTO : questionDTOList) {
            if (questionDTO.getCorrectAnswer() != null) {
                if (questionDTO.getCorrectAnswer().equals(questionDTO.getUserAnswer())) {
                    totalTrueCount += 1;
                } else {
                    totalFalseCount += 1;
                }
            }
        }
        quizFinishDTO.setTotalScore(totalTrueCount * 5);
        quizFinishDTO.setTotalTrueCount(totalTrueCount);
        //Sınavı bitir için fazlada bir soru ekledik
        //null oldugu için totalFalseCounta +1 ekleniyor
        quizFinishDTO.setTotalFalseCount(totalFalseCount);
        quizFinishDTO.setQuizFinish(Boolean.TRUE);
        quizAdapter.notifyDataSetChanged();
    }

    @Override
    public void radioButtonItemSelected(String questionId, String userAnswer) {
        for (QuestionDTO questionDTO : questionDTOList) {
            String[] splitStr = questionId.split("/");
            if (questionDTO.getQuestionId().equals(splitStr[0])) {
                questionDTO.setUserAnswer(userAnswer);
                break;
            }
        }
    }

    @Override
    public Boolean favouriteQuestionFound(String questionUniqueId) {
        FavouriteQuestionSQLHelper db = new FavouriteQuestionSQLHelper(getContext());
        QuestionDTO questionDTO = db.findById(questionUniqueId);
        if (questionDTO.getQuestionUniqueId() != null) {
            return Boolean.TRUE;
        }
        return Boolean.FALSE;
    }

    @Override
    public void favouriteQuestionSave(QuestionDTO favouriteQuestion) {
        FavouriteQuestionSQLHelper db = new FavouriteQuestionSQLHelper(getContext());
        QuestionDTO questionDTO = db.findById(favouriteQuestion.getQuestionUniqueId());
        if (questionDTO.getQuestionUniqueId() != null) {
            Toast.makeText(getContext(), "Kayit Zaten Var!", Toast.LENGTH_SHORT).show();
        } else {
            Long id = db.create(favouriteQuestion);
            if (id == -1) {
                Toast.makeText(getContext(), "Kayit Sırasında Hata Oluştu!", Toast.LENGTH_SHORT).show();
            }
        }
    }

    @Override
    public void favouriteQuestionDelete(String questionUniqueId) {
        FavouriteQuestionSQLHelper db = new FavouriteQuestionSQLHelper(getContext());
        db.delete(questionUniqueId);
    }
}

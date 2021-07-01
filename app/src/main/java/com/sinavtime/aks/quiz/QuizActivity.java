package com.sinavtime.aks.quiz;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.sinavtime.aks.R;
import com.sinavtime.aks.activities.MainActivity;
import com.sinavtime.aks.helper.FavouriteQuestionSQLHelper;
import com.sinavtime.aks.helper.SQLHelper;
import com.sinavtime.aks.model.quiz.QuestionDTO;
import com.sinavtime.aks.model.quiz.QuizDTO;

import java.util.ArrayList;
import java.util.List;

public class QuizActivity extends AppCompatActivity implements QuestionViewListener, EndViewListener {

    RecyclerView recyclerView;
    QuizAdapter quizAdapter;
    QuizDTO selectedQuiz;
    List<QuestionDTO> questionDTOList = new ArrayList<>();
    QuizFinishDTO quizFinishDTO;
    Bundle arguments;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quiz);

        //quiz menu toolbar
        Toolbar toolbar = (Toolbar) findViewById(R.id.quiz_menu_toolbar);
        setSupportActionBar(toolbar);

        quizFinishDTO = new QuizFinishDTO();
        arguments = getIntent().getBundleExtra("selectedQuizActivity");
        selectedQuiz = (QuizDTO) arguments.getSerializable("selectedQuiz");
        getSupportActionBar().setTitle(selectedQuiz.getQuiz_name());
        for (QuestionDTO questionDTO : selectedQuiz.getQestionClobDTO().getQestionDTOList()) {
            questionDTO.setQuizType(QuizType.QUESTION);
            questionDTOList.add(questionDTO);
        }
        QuestionDTO finishViewHolder = new QuestionDTO();
        finishViewHolder.setQuizType(QuizType.END);
        questionDTOList.add(finishViewHolder);
        recyclerView = findViewById(R.id.quiz_recyclerView);
        quizAdapter = new QuizAdapter(this, questionDTOList, this, this, quizFinishDTO);
        recyclerView.setAdapter(quizAdapter);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
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
        //Quiz sonucunu kaydet.
        selectedQuiz.setTotalScore(totalTrueCount * 5);
        selectedQuiz.setTotalTrueCount(totalTrueCount);
        selectedQuiz.setTotalFalseCount(totalFalseCount);
        SQLHelper db = new SQLHelper(this);
        db.update(selectedQuiz);
        quizFinishDTO.setTotalScore(totalTrueCount * 5);
        quizFinishDTO.setTotalTrueCount(totalTrueCount);
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
        FavouriteQuestionSQLHelper db = new FavouriteQuestionSQLHelper(this);
        QuestionDTO questionDTO = db.findById(questionUniqueId);
        if (questionDTO.getQuestionUniqueId() != null) {
            return Boolean.TRUE;
        }
        return Boolean.FALSE;
    }

    @Override
    public void favouriteQuestionSave(QuestionDTO favouriteQuestion) {
        FavouriteQuestionSQLHelper db = new FavouriteQuestionSQLHelper(this);
        QuestionDTO questionDTO = db.findById(favouriteQuestion.getQuestionUniqueId());
        if (questionDTO.getQuestionUniqueId() != null) {
            Toast.makeText(this, "Kayit Zaten Var!", Toast.LENGTH_SHORT).show();
        } else {
            Long id = db.create(favouriteQuestion);
            if (id == -1) {
                Toast.makeText(this, "Kayit Sırasında Hata Oluştu!", Toast.LENGTH_SHORT).show();
            }
        }
    }

    @Override
    public void favouriteQuestionDelete(String questionUniqueId) {
        FavouriteQuestionSQLHelper db = new FavouriteQuestionSQLHelper(this);
        db.delete(questionUniqueId);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.quiz_toolbar_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle item selection
        switch (item.getItemId()) {
            case R.id.quiz_toolbar_finish:
                MainActivityStart();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    public void MainActivityStart() {
        Intent intent = new Intent(this, MainActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
    }
}

package com.sinavtime.aks.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.sinavtime.aks.R;
import com.sinavtime.aks.helper.SQLHelper;
import com.sinavtime.aks.model.quiz.QuestionDTO;
import com.sinavtime.aks.model.quiz.QuizDTO;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.UUID;

public class SplashActivity extends AppCompatActivity {

    private static String TAG = SplashActivity.class.getName();
    private static long SLEEP_TIME = 2; // Bekletilecek saniye

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        loadQuizList();
        this.requestWindowFeature(Window.FEATURE_NO_TITLE); // Splash ekrandan basligi kaldirir
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN); // Bilgi cubugunu kaldirir
        setContentView(R.layout.activity_splash);

        IntentLauncher launcher = new IntentLauncher();
        launcher.start();
    }

    private class IntentLauncher extends Thread {
        @Override

        public void run() {
            try {
                // Sleeping
                Thread.sleep(SLEEP_TIME * 1000);
            } catch (Exception e) {
                Log.e(TAG, e.getMessage());
            }

            // Start main activity
            Intent intent = new Intent(SplashActivity.this, MainActivity.class);
            startActivity(intent);
            finish(); //Bu activity kapanır
        }
    }

    private void loadQuizList() {
        SQLHelper db = new SQLHelper(this);
        if (!db.checkDataBase(this)) {
            ArrayList<QuizDTO> quizDTOList = new ArrayList<>();
            try {
                //Load File
                BufferedReader jsonReader = new BufferedReader(new InputStreamReader(this.getResources().openRawResource(R.raw.aks_soru)));
                StringBuilder jsonBuilder = new StringBuilder();
                for (String line = null; (line = jsonReader.readLine()) != null; ) {
                    jsonBuilder.append(line).append("\n");
                }
                Gson gson = new Gson(); //json’u parse etmek için Gson kütüphanesini kullanıyoruz
                Type listType = new TypeToken<ArrayList<QuizDTO>>() {
                }.getType();
                quizDTOList = gson.fromJson(jsonBuilder.toString(), listType);
            } catch (FileNotFoundException e) {
                Log.e("jsonFile", "Dosya Bulunamadı");
            } catch (IOException e) {
                Log.e("jsonFile", "ioerror");
            }
            db.deleteAll();
            for (QuizDTO quizDTO : quizDTOList) {
                for(QuestionDTO questionDTO:quizDTO.getQestionClobDTO().getQestionDTOList()){
                    questionDTO.setQuestionUniqueId(UUID.randomUUID().toString());
                }
                Long id = db.create(quizDTO);
                if (id == -1) {
                    Toast.makeText(this, "Kayit Sırasında Hata Oluştu!", Toast.LENGTH_SHORT).show();
                }
            }
        }
    }

}



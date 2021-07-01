package com.sinavtime.aks.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;

import com.sinavtime.aks.R;
import com.sinavtime.aks.fragment.FavouriteFragment;
import com.sinavtime.aks.fragment.ProfileFragment;
import com.sinavtime.aks.fragment.QuizFragment;
import com.sinavtime.aks.helper.SQLHelper;
import com.sinavtime.aks.model.quiz.QuizDTO;
import com.sinavtime.aks.quiz.QuizActivity;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements
        QuizFragment.TestFragmentDinleyicisi,
        BottomNavigationView.OnNavigationItemSelectedListener {

    Fragment fragment = null;
    private ArrayList<QuizDTO> quizDTOList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //toolbar
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle(R.string.app_name_title);

        //bottom menu
        BottomNavigationView bottomNavigationView = findViewById(R.id.navigation);
        bottomNavigationView.setOnNavigationItemSelectedListener(this);

        loadLessonList();

        //İlk açılış fragment
        fragment = new QuizFragment();
        Bundle bundle = new Bundle();
        bundle.putSerializable("quizList", quizDTOList);
        fragment.setArguments(bundle);
        displayFragment(fragment);
    }

    private void displayFragment(Fragment fragment) {
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.fragment_container, fragment)
                .commit();
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        fragment = null;
        switch (item.getItemId()) {
            case R.id.navigation_test:
                fragment = new QuizFragment();
                Bundle bundle = new Bundle();
                bundle.putSerializable("quizList", quizDTOList);
                fragment.setArguments(bundle);
                break;
            case R.id.navigation_favourite:
                fragment = new FavouriteFragment();
                break;
            case R.id.navigation_profile:
                fragment = new ProfileFragment();
                break;
        }
        if (fragment != null) {
            displayFragment(fragment);
            return true;
        }

        return false;
    }

    private void loadLessonList() {
        SQLHelper db = new SQLHelper(this);
        quizDTOList = db.findAll();
    }


    @Override
    public void quizActivityStart(Bundle arguments) {
        Intent intent = new Intent(this, QuizActivity.class);
        intent.putExtra("selectedQuizActivity", arguments);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
    }

}

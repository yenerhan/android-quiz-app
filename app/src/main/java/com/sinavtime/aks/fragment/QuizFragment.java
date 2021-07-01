package com.sinavtime.aks.fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import com.sinavtime.aks.R;
import com.sinavtime.aks.adapter.QuizAdapter;
import com.sinavtime.aks.model.quiz.QuizDTO;

import java.util.ArrayList;

public class QuizFragment extends Fragment {


    private TestFragmentDinleyicisi dinleyici;

    public interface TestFragmentDinleyicisi {
        public void quizActivityStart(Bundle arguments);
    }

    private QuizAdapter quizAdapter;
    private ListView quizListView;
    private ArrayList<QuizDTO> quizDTOList;
    Bundle arguments;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_quiz, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        quizListView = (ListView) getActivity().findViewById(R.id.quiz_list_view);
        arguments = getArguments();
        quizDTOList = (ArrayList<QuizDTO>) arguments.getSerializable("quizList");
        listele();

        quizListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                QuizDTO selectedQuiz = (QuizDTO) quizListView.getAdapter().getItem(position);
                Bundle selectedQuizBundle = new Bundle();
                selectedQuizBundle.putSerializable("selectedQuiz", selectedQuiz);
                dinleyici.quizActivityStart(selectedQuizBundle);
            }
        });
    }

    public void listele() {
        quizAdapter = new QuizAdapter(getActivity(), quizDTOList);
        quizListView.setChoiceMode(ListView.CHOICE_MODE_SINGLE);
        quizListView.setAdapter(quizAdapter);
    }


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        dinleyici = (QuizFragment.TestFragmentDinleyicisi) context;
    }

    @Override
    public void onDetach() {
        super.onDetach();
        dinleyici = null;
    }
}

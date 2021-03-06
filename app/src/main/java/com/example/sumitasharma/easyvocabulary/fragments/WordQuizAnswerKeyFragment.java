package com.example.sumitasharma.easyvocabulary.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.sumitasharma.easyvocabulary.R;
import com.example.sumitasharma.easyvocabulary.wordui.MainActivity;

import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import timber.log.Timber;

import static com.example.sumitasharma.easyvocabulary.util.WordUtil.CORRECT_ANSWERS;

public class WordQuizAnswerKeyFragment extends Fragment {
    @BindView(R.id.quiz_answer_word1)
    TextView word1;
    @BindView(R.id.quiz_answer_word2)
    TextView word2;
    @BindView(R.id.quiz_answer_word3)
    TextView word3;
    @BindView(R.id.quiz_answer_word4)
    TextView word4;
    @BindView(R.id.quiz_answer_meaning1)
    TextView meaning1;
    @BindView(R.id.quiz_answer_meaning2)
    TextView meaning2;
    @BindView(R.id.quiz_answer_meaning3)
    TextView meaning3;
    @BindView(R.id.quiz_answer_meaning4)
    TextView meaning4;
    private HashMap<String, String> mCorrectAnswers = new HashMap<>();

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View mRootView = inflater.inflate(R.layout.fragment_word_quiz_answer, container, false);
        Timber.i("Inside WordQuizAnswerKeyFragment");
        ButterKnife.bind(this, mRootView);
        if (getArguments() != null) {
            mCorrectAnswers = new HashMap<>();
            mCorrectAnswers = (HashMap<String, String>) getArguments().getSerializable(CORRECT_ANSWERS);
        } else {
            mCorrectAnswers = new HashMap<>();
            mCorrectAnswers = (HashMap<String, String>) savedInstanceState.getSerializable(CORRECT_ANSWERS);
        }
        int i = 1;
        if (mCorrectAnswers != null) {
            for (Map.Entry<String, String> entry : mCorrectAnswers.entrySet()) {
                switch (i) {
                    case 1:
                        word1.setText(entry.getValue());
                        meaning1.setText(entry.getKey());
                        i = 2;
                        break;
                    case 2:
                        word2.setText(entry.getValue());
                        meaning2.setText(entry.getKey());
                        i = 3;
                        break;
                    case 3:
                        word3.setText(entry.getValue());
                        meaning3.setText(entry.getKey());
                        i = 4;
                        break;
                    case 4:
                        word4.setText(entry.getValue());
                        meaning4.setText(entry.getKey());
                        break;
                    default:
                        Timber.i("Default");
                        break;
                }
            }

        }
        return mRootView;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRetainInstance(true);
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putSerializable(CORRECT_ANSWERS, this.mCorrectAnswers);
    }

    @OnClick(R.id.go_home_main)
    public void goHomeFromQuiz() {
        Intent intentMain = new Intent(getContext(), MainActivity.class);
        startActivity(intentMain);
    }
}

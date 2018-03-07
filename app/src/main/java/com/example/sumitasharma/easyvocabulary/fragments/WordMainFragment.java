package com.example.sumitasharma.easyvocabulary.fragments;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.sumitasharma.easyvocabulary.R;

import butterknife.ButterKnife;
import butterknife.OnClick;

import static com.example.sumitasharma.easyvocabulary.util.WordUtil.DICTIONARY_CARD_VIEW_IDENTIFIER;
import static com.example.sumitasharma.easyvocabulary.util.WordUtil.PROGRESS_CARD_VIEW_IDENTIFIER;
import static com.example.sumitasharma.easyvocabulary.util.WordUtil.QUIZ_CARD_VIEW_IDENTIFIER;
import static com.example.sumitasharma.easyvocabulary.util.WordUtil.WORD_MEANING_CARD_VIEW_IDENTIFIER;

public class WordMainFragment extends Fragment {
    private static final String TAG = WordMainFragment.class.getSimpleName();
    View rootView;
    private PassCardViewInformation mPassCardViewInformation;

    public WordMainFragment() {

    }

    @OnClick(R.id.word_meaning_card_view)
    public void cardClickPractice() {
        mPassCardViewInformation.cardViewInformation(WORD_MEANING_CARD_VIEW_IDENTIFIER);
    }

    @OnClick(R.id.quiz_card_view)
    public void cardClickQuiz() {
        mPassCardViewInformation.cardViewInformation(QUIZ_CARD_VIEW_IDENTIFIER);
    }

    @OnClick(R.id.progress_card_view)
    public void cardClickProgress() {
        mPassCardViewInformation.cardViewInformation(PROGRESS_CARD_VIEW_IDENTIFIER);
    }

    @OnClick(R.id.dictionary_card_view)
    public void cardClickDictionary() {
        mPassCardViewInformation.cardViewInformation(DICTIONARY_CARD_VIEW_IDENTIFIER);
    }

    @Nullable
    @Override
    public View onCreateView(final LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        rootView = inflater.inflate(R.layout.fragment_word_main, container, false);
        ButterKnife.bind(this, rootView);
        return rootView;
    }


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mPassCardViewInformation = (PassCardViewInformation) context;
    }

    public interface PassCardViewInformation {
        void cardViewInformation(String cardViewNumber);
    }

}
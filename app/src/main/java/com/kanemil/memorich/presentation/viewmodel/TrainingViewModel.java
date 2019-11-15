package com.kanemil.memorich.presentation.viewmodel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.kanemil.memorich.data.model.Card;
import com.kanemil.memorich.data.repository.DecksProvider;

import java.util.HashSet;
import java.util.List;
import java.util.Objects;

public class TrainingViewModel extends ViewModel {
    private DecksProvider mDecksProvider;
    private MutableLiveData<List<Card>> mCardsList = new MutableLiveData<>();
    private MutableLiveData<Integer> mCorrectAnswers = new MutableLiveData<>(0);
    private MutableLiveData<HashSet<Integer>> mAnsweredCards = new MutableLiveData<>(new HashSet<Integer>());
    private int mDeckId;

    TrainingViewModel(int deckId, DecksProvider decksProvider) {
        mDeckId = deckId;
        mDecksProvider = decksProvider;
    }

    public void loadCards() {
        mCardsList.setValue((mDecksProvider.provideDecks().get(mDeckId).getCardList()));
    }

    public LiveData<List<Card>> getCardsList() {
        return mCardsList;
    }

    public LiveData<Integer> getCorrectAnswers() {
        return mCorrectAnswers;
    }

    public LiveData<HashSet<Integer>> getAnsweredCards() {
        return mAnsweredCards;
    }

    public void incrementCorrectAnswers() {
        if (mCorrectAnswers.getValue() != null && mCorrectAnswers.getValue() < Objects.requireNonNull(mCardsList.getValue()).size()) {
            mCorrectAnswers.setValue(mCorrectAnswers.getValue() + 1);
        }
    }

    public void disableButtonAfterButtonPressed(int position) {
        HashSet<Integer> modifiedSet = new HashSet<>(Objects.requireNonNull(mAnsweredCards.getValue()));
        modifiedSet.add(position);
        mAnsweredCards.setValue(modifiedSet);
    }
}

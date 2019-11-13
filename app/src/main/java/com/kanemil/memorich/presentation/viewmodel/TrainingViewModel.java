package com.kanemil.memorich.presentation.viewmodel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.kanemil.memorich.data.model.Card;
import com.kanemil.memorich.data.repository.DecksProvider;

import java.util.List;

public class TrainingViewModel extends ViewModel {
    private DecksProvider mDecksProvider;
    private MutableLiveData<List<Card>> mCardsList = new MutableLiveData<>();
    private int mDeckId;

    public TrainingViewModel(int deckId, DecksProvider decksProvider) {
        mDeckId = deckId;
        mDecksProvider = decksProvider;
    }

    public void loadCards() {
        mCardsList.setValue((mDecksProvider.provideDecks().get(mDeckId).getCardList()));
    }

    public LiveData<List<Card>> getCardsList() {
        return mCardsList;
    }
}

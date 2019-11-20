package com.kanemil.memorich.presentation.viewmodel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.kanemil.memorich.data.db.entity.Card;
import com.kanemil.memorich.data.repository.Repository;

import java.util.List;

public class CardsViewModel extends ViewModel {
    private Repository mRepository;
    private LiveData<List<Card>> mCardsList = new MutableLiveData<>();
    private long mDeckId;

    CardsViewModel(long deckId, Repository repository) {
        mDeckId = deckId;
        mRepository = repository;
        loadCards();
    }

    private void loadCards() {
        mCardsList = mRepository.getCards(mDeckId);
    }

    public void addCard(Card card) {
        mRepository.insertCard(card);
    }

    public LiveData<List<Card>> getCardsList() {
        return mCardsList;
    }
}

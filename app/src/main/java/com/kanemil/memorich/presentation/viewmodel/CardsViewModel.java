package com.kanemil.memorich.presentation.viewmodel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.kanemil.memorich.data.db.entity.CardEntity;
import com.kanemil.memorich.data.repository.Repository;

import java.util.List;

public class CardsViewModel extends ViewModel {
    private Repository mRepository;
    private LiveData<List<CardEntity>> mCardsList = new MutableLiveData<>();
    private long mDeckId;

    CardsViewModel(long deckId, Repository repository) {
        mDeckId = deckId;
        mRepository = repository;
        loadCards();
    }

    private void loadCards() {
        mCardsList = mRepository.getCards(mDeckId);
    }

    public void addCard(CardEntity card) {
        mRepository.insertCard(card);
    }

    public LiveData<List<CardEntity>> getCardsList() {
        return mCardsList;
    }

    public void updateCard(CardEntity card) {
        mRepository.updateCard(card);
    }

    public void updateCardsOrder(List<CardEntity> cardList) {
        mRepository.updateCardList(cardList);
    }

    public void deleteCard(CardEntity card, List<CardEntity> cardListAfterDeletion) {
        mRepository.deleteCard(card, cardListAfterDeletion);
    }
}

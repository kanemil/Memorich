package com.kanemil.memorich.presentation.viewmodel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.kanemil.memorich.data.db.entity.Card;
import com.kanemil.memorich.data.repository.Repository;

import java.util.List;

import javax.inject.Inject;

public class CardsViewModel extends ViewModel {

    // inject
    private Repository mRepository;


    private LiveData<List<Card>> mCardsList = new MutableLiveData<>();
    private long mDeckId;

    @Inject
    CardsViewModel(Repository repository) {
        mRepository = repository;
    }

    public void setDeckId(long deckId) {
        mDeckId = deckId;
        loadCards();
    }

    public void addCard(Card card) {
        mRepository.insertCard(card);
    }

    public LiveData<List<Card>> getCardsList() {
        return mCardsList;
    }

    public void updateCard(Card card) {
        mRepository.updateCard(card);
    }

    public void updateCardsOrder(List<Card> cardList) {
        mRepository.updateCardList(cardList);
    }

    public void deleteCard(Card card, List<Card> cardListAfterDeletion) {
        mRepository.deleteCard(card, cardListAfterDeletion);
    }

    private void loadCards() {
        mCardsList = mRepository.getCards(mDeckId);
    }
}

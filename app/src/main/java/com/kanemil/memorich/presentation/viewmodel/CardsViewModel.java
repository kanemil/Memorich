package com.kanemil.memorich.presentation.viewmodel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.kanemil.memorich.data.db.entity.Card;
import com.kanemil.memorich.data.repository.DecksRepository;

import java.util.List;

public class CardsViewModel extends ViewModel {
    private DecksRepository mDecksRepository;
    private MutableLiveData<List<Card>> mCardsList = new MutableLiveData<>();
    private int mDeckId;

    CardsViewModel(int deckId, DecksRepository decksRepository) {
        mDeckId = deckId;
        mDecksRepository = decksRepository;
    }

    public void loadCards() {
//        mCardsList.setValue((mDecksRepository.getDecks().get(mDeckId).getCardList()));
    }

    /**
     * Adds deck to list.
     * // TODO Сделать механизм добавления карты, но уже после того как реализую БД
     *
     * @param card
     */

    public void addDeck(Card card) {
        // TODO
    }

    public LiveData<List<Card>> getCardsList() {
        return mCardsList;
    }
}

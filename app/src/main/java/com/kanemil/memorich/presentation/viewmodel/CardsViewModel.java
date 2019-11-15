package com.kanemil.memorich.presentation.viewmodel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.kanemil.memorich.data.model.Card;
import com.kanemil.memorich.data.repository.DecksProvider;

import java.util.List;

public class CardsViewModel extends ViewModel {
    private DecksProvider mDecksProvider;
    private MutableLiveData<List<Card>> mCardsList = new MutableLiveData<>();
    private int mDeckId;

    CardsViewModel(int deckId, DecksProvider decksProvider) {
        mDeckId = deckId;
        mDecksProvider = decksProvider;
    }

    public void loadCards() {
        mCardsList.setValue((mDecksProvider.provideDecks().get(mDeckId).getCardList()));
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

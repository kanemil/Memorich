package com.kanemil.memorich.presentation.viewmodel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.kanemil.memorich.data.model.Deck;
import com.kanemil.memorich.data.repository.DecksProvider;

import java.util.List;

public class DecksViewModel extends ViewModel {

    private static final String TAG = "DecksViewModel";
    private DecksProvider mDecksProvider = new DecksProvider();
    private MutableLiveData<List<Deck>> mDecksList = new MutableLiveData<>();

    public void loadDecks() {
        mDecksList.setValue((mDecksProvider.provideDecks()));
    }

    /**
     * Adds deck to list.
     * // TODO Сделать механизм добавления колоды, но уже после того как реализую БД
     * @param deck
     */

    public void addDeck(Deck deck) {
        if (mDecksList.getValue() != null) {
            mDecksList.getValue().add(deck);
        }
    }

    public LiveData<List<Deck>> getDecksList() {
        return mDecksList;
    }
}

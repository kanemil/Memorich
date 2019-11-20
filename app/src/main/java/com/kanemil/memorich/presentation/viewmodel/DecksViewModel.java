package com.kanemil.memorich.presentation.viewmodel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.kanemil.memorich.data.db.entity.Deck;
import com.kanemil.memorich.data.repository.DecksRepository;

import java.util.List;

public class DecksViewModel extends ViewModel {

    private static final String TAG = "DecksViewModel";
    private DecksRepository mDecksRepository;
    private LiveData<List<Deck>> mDecksList = new MutableLiveData<>();

    DecksViewModel(DecksRepository decksRepository) {
        mDecksRepository = decksRepository;
    }

    public void loadDecks() {
        mDecksList = mDecksRepository.getDecks();
    }

    public void addDeck(String deckName) {
        if (mDecksList.getValue() != null) {
            Deck deck = new Deck(deckName);
            mDecksRepository.insertDeck(deck);
        }
    }

    public LiveData<List<Deck>> getDecksList() {
        return mDecksList;
    }
}

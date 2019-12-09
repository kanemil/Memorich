package com.kanemil.memorich.presentation.viewmodel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.kanemil.memorich.data.db.entity.DeckEntity;
import com.kanemil.memorich.data.repository.Repository;

import java.util.List;

public class DecksViewModel extends ViewModel {

    private static final String TAG = "DecksViewModel";
    private Repository mRepository;
    private LiveData<List<DeckEntity>> mDecksList = new MutableLiveData<>();

    DecksViewModel(Repository repository) {
        mRepository = repository;
        loadDecks();
    }

    public void addDeck(String deckName) {
        DeckEntity deck = new DeckEntity(deckName);
        mRepository.insertDeck(deck);
    }

    public void renameDeck(long deckId, String newDeckName) {
        DeckEntity deck = new DeckEntity(newDeckName);
        deck.setId(deckId);
        mRepository.renameDeck(deck);
    }

    private void loadDecks() {
        mDecksList = mRepository.loadDecks();
    }

    public void deleteDeck(DeckEntity deck) {
        mRepository.deleteDeck(deck);
    }

    public LiveData<List<DeckEntity>> getDecksList() {
        return mDecksList;
    }
}

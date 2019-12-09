package com.kanemil.memorich.presentation.viewmodel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.kanemil.memorich.data.db.entity.Deck;
import com.kanemil.memorich.data.repository.Repository;

import java.util.List;

import javax.inject.Inject;

public class DecksViewModel extends ViewModel {

    private static final String TAG = "DecksViewModel";
    private LiveData<List<Deck>> mDecksList = new MutableLiveData<>();

    private Repository mRepository;

    @Inject
    public DecksViewModel(Repository repository) {
        mRepository = repository;
//        loadDecks();
    }

    public void addDeck(String deckName) {
        Deck deck = new Deck(deckName);
        mRepository.insertDeck(deck);
    }

    public void renameDeck(long deckId, String newDeckName) {
        Deck deck = new Deck(newDeckName);
        deck.setId(deckId);
        mRepository.renameDeck(deck);
    }

    public void deleteDeck(Deck deck) {
        mRepository.deleteDeck(deck);
    }

    public LiveData<List<Deck>> getDecksList() {
        return mDecksList;
    }

    private void loadDecks() {
        mDecksList = mRepository.getDecks();
    }
}

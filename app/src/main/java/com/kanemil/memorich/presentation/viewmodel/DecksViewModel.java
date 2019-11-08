package com.kanemil.memorich.presentation.viewmodel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.kanemil.memorich.data.model.Deck;
import com.kanemil.memorich.data.repository.DecksProvider;

import java.util.List;

public class DecksViewModel extends ViewModel {

    private static final String TAG = "DecksViewModel";
    // TODO: Implement the ViewModel
    private DecksProvider mDecksProvider = new DecksProvider();
    private MutableLiveData<List<Deck>> mDecksList = new MutableLiveData<>();

    public void loadDecks() {
        mDecksList.setValue((mDecksProvider.provideDecks()));
    }

    public LiveData<List<Deck>> getDecksList() {
        return mDecksList;
    }
}

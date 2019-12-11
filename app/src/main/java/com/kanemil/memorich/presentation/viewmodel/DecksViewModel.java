package com.kanemil.memorich.presentation.viewmodel;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.kanemil.memorich.R;
import com.kanemil.memorich.data.db.entity.Deck;
import com.kanemil.memorich.data.repository.Repository;
import com.kanemil.memorich.presentation.viewmodel.utils.ResourceWrapper;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.CompletableObserver;
import io.reactivex.disposables.Disposable;

public class DecksViewModel extends ViewModel {

    private static final String TAG = "DecksViewModel";
    private LiveData<List<Deck>> mDecksList = new MutableLiveData<>();
    private MutableLiveData<String> mOnDeckAddedMessage = new MutableLiveData<>();

    // inject
    private Repository mRepository;
    private ResourceWrapper mResourceWrapper;

    @Inject
    public DecksViewModel(Repository repository, ResourceWrapper resourceWrapper) {
        mRepository = repository;
        mResourceWrapper = resourceWrapper;
        loadDecks();
    }

    public void addDeck(final String deckName) {
        Deck deck = new Deck(deckName);
        mRepository.insertDeck(deck)
                .subscribe(new CompletableObserver() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        Log.d(TAG, "onSubscribe: ");
                    }

                    @Override
                    public void onComplete() {
                        mOnDeckAddedMessage.setValue(mResourceWrapper.getString(R.string.added_deck_formatter, deckName));
                        Log.d(TAG, "onComplete: " + mResourceWrapper.getString(R.string.added_deck_formatter, deckName));
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.e(TAG, "onError: ", e);
                    }
                });
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

    public LiveData<String> getOnDeckAddedMessage() {
        return mOnDeckAddedMessage;
    }

    private void loadDecks() {
        mDecksList = mRepository.getDecks();
    }
}

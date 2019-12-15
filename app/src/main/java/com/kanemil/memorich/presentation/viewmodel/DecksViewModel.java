package com.kanemil.memorich.presentation.viewmodel;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.kanemil.memorich.R;
import com.kanemil.memorich.data.db.entity.Deck;
import com.kanemil.memorich.data.repository.Repository;
import com.kanemil.memorich.presentation.SingleLiveEvent;
import com.kanemil.memorich.presentation.viewmodel.utils.ResourceWrapper;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.CompletableObserver;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;

public class DecksViewModel extends ViewModel {

    private static final String TAG = "DecksViewModel";
    private LiveData<List<Deck>> mDecksList = new MutableLiveData<>();
    private SingleLiveEvent<String> mSnackbarMessage = new SingleLiveEvent<>();

    // inject
    private Repository mRepository;
    private ResourceWrapper mResourceWrapper;

    private CompositeDisposable mDisposables = new CompositeDisposable();

    @Inject
    public DecksViewModel(Repository repository, ResourceWrapper resourceWrapper) {
        mRepository = repository;
        mResourceWrapper = resourceWrapper;
        loadDecks();
    }

    public void addDeck(final String deckName) {
        final Deck deck = new Deck(deckName);
        mRepository.insertDeck(deck)
                .subscribe(new CompletableObserver() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        mDisposables.add(d);
                    }

                    @Override
                    public void onComplete() {
                        mSnackbarMessage.postValue(mResourceWrapper.getString(R.string.added_deck_formatter, deck.getName()));
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.e(TAG, "onError: ", e);
                    }
                });
    }

    public void renameDeck(long deckId, final String newDeckName) {
        final Deck deck = new Deck(newDeckName);
        deck.setId(deckId);
        mRepository.renameDeck(deck)
                .subscribe(new CompletableObserver() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        mDisposables.add(d);
                    }

                    @Override
                    public void onComplete() {
                        mSnackbarMessage.postValue(mResourceWrapper.getString(R.string.renamed_deck_formatter, deck.getName()));
                    }

                    @Override
                    public void onError(Throwable e) {

                    }
                });
    }

    public void deleteDeck(final Deck deck) {
        mRepository.deleteDeck(deck)
                .subscribe(new CompletableObserver() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        mDisposables.add(d);
                    }

                    @Override
                    public void onComplete() {
                        mSnackbarMessage.postValue(mResourceWrapper.getString(R.string.deleted_deck_formatter, deck.getName()));
                    }

                    @Override
                    public void onError(Throwable e) {

                    }
                });
    }

    public LiveData<List<Deck>> getDecksList() {
        return mDecksList;
    }

    public LiveData<String> getSnackbarMessage() {
        return mSnackbarMessage;
    }

    private void loadDecks() {
        mDecksList = mRepository.getDecks();
    }

    @Override
    protected void onCleared() {
        mDisposables.clear();
    }
}

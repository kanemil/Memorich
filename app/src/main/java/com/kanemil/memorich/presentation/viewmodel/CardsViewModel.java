package com.kanemil.memorich.presentation.viewmodel;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.kanemil.memorich.R;
import com.kanemil.memorich.data.db.entity.Card;
import com.kanemil.memorich.data.repository.Repository;
import com.kanemil.memorich.presentation.SingleLiveEvent;
import com.kanemil.memorich.presentation.viewmodel.utils.ResourceWrapper;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.CompletableObserver;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;

public class CardsViewModel extends ViewModel {

    private static final String TAG = "CardsViewModel";

    // inject
    private Repository mRepository;
    private ResourceWrapper mResourceWrapper;


    private LiveData<List<Card>> mCardsList = new MutableLiveData<>();
    private SingleLiveEvent<String> mSnackbarMessage = new SingleLiveEvent<>();

    private long mDeckId;

    private CompositeDisposable mDisposables = new CompositeDisposable();

    @Inject
    CardsViewModel(Repository repository, ResourceWrapper resourceWrapper) {
        mRepository = repository;
        mResourceWrapper = resourceWrapper;
    }

    public void setDeckId(long deckId) {
        mDeckId = deckId;
        loadCards();
    }

    public LiveData<List<Card>> getCardsList() {
        return mCardsList;
    }

    public void addCard(Card card) {
        mRepository.insertCard(card)
                .subscribe(new CompletableObserver() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        mDisposables.add(d);
                    }

                    @Override
                    public void onComplete() {
                        mSnackbarMessage.postValue(mResourceWrapper.getString(R.string.card_added));
                    }

                    @Override
                    public void onError(Throwable e) {
                    }
                });
    }

    public void updateCard(Card card) {
        mRepository.updateCard(card)
                .subscribe(new CompletableObserver() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        mDisposables.add(d);
                    }

                    @Override
                    public void onComplete() {
                        mSnackbarMessage.postValue(mResourceWrapper.getString(R.string.card_updated));
                    }

                    @Override
                    public void onError(Throwable e) {

                    }
                });
    }

    public void updateCardsOrder(List<Card> cardList) {
        mRepository.updateCardList(cardList)
                .subscribe(new CompletableObserver() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        mDisposables.add(d);
                    }

                    @Override
                    public void onComplete() {

                    }

                    @Override
                    public void onError(Throwable e) {

                    }
                });
    }

    public void deleteCard(Card card, List<Card> cardListAfterDeletion) {
        mRepository.deleteCard(card, cardListAfterDeletion)
                .subscribe(new CompletableObserver() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        mDisposables.add(d);
                    }

                    @Override
                    public void onComplete() {
                        mSnackbarMessage.postValue(mResourceWrapper.getString(R.string.card_deleted));
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.e(TAG, "onError: ", e);
                    }
                });
    }

    private void loadCards() {
        mCardsList = mRepository.getCards(mDeckId);
    }

    public SingleLiveEvent<String> getSnackbarMessage() {
        return mSnackbarMessage;
    }

    @Override
    protected void onCleared() {
        mDisposables.clear();
    }
}

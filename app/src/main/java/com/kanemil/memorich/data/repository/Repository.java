package com.kanemil.memorich.data.repository;

import androidx.lifecycle.LiveData;

import com.kanemil.memorich.data.db.AppDatabase;
import com.kanemil.memorich.data.db.entity.Card;
import com.kanemil.memorich.data.db.entity.Deck;

import java.util.List;

import javax.inject.Inject;
import javax.inject.Singleton;

import io.reactivex.Completable;
import io.reactivex.CompletableObserver;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

@Singleton
public class Repository {

    private AppDatabase mDb;

    @Inject
    public Repository(AppDatabase db) {
        mDb = db;
    }

    // ----------------------------Decks----------------------------

    public LiveData<List<Deck>> getDecks() {
        return mDb.getDeckDao().getDecks();
    }

    public Completable insertDeck(Deck deck) {
        return mDb.getDeckDao().insert(deck)
                .subscribeOn(Schedulers.io());
    }

    public Completable renameDeck(Deck deck) {
        return mDb.getDeckDao().update(deck)
                .subscribeOn(Schedulers.io());
    }

    public Completable deleteDeck(Deck deck) {
        return mDb.getDeckDao().delete(deck)
                .subscribeOn(Schedulers.io());
    }


    // ----------------------------Cards----------------------------

    public LiveData<List<Card>> getCards(long deckId) {
        return mDb.getCardDao().getCardsByDeckId(deckId);
    }

    public Completable insertCard(Card card) {
        return mDb.getCardDao().insert(card)
                .subscribeOn(Schedulers.io());
    }

    public Completable updateCard(Card card) {
        return mDb.getCardDao().update(card)
                .subscribeOn(Schedulers.io());
    }

    public void updateCardList(List<Card> cardList) {
        final Completable completable = mDb.getCardDao().updateList(cardList);
        execute(completable);
    }

    public void deleteCard(Card card, final List<Card> cardListAfterDeletion) {
        final Completable completable = mDb.getCardDao().delete(card);
        completable
                .subscribeOn(Schedulers.io())
                .subscribe(new CompletableObserver() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onComplete() {
                        updateCardList(cardListAfterDeletion);
                    }

                    @Override
                    public void onError(Throwable e) {

                    }
                });
    }

    private void execute(Completable completable) {
        completable.subscribeOn(Schedulers.io())
                .subscribe();
    }
}

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

    public LiveData<List<Deck>> getDecks() {
        return mDb.getDeckDao().getDecks();
    }


    /* ВОПРОС
        Допустим, я хочу сделать тост, который уведомит юзера, что колода/карта была добавлена.
        Как это правильно сделать?
        Может быть, insertDeck должен возвращать какой-нибудь объект RxJava, и вьюмодель уже будет
        как-то что-то с ним делать, например, обзервить его и по результату засовывать что-то в
        лайвДату, которая будет обзервиться во фрагменте, в результате чего и будет показываться тост?
     */
    public void insertDeck(Deck deck) {
        final Completable completable = mDb.getDeckDao().insert(deck);
        execute(completable);
    }

    public void renameDeck(Deck deck) {
        final Completable completable = mDb.getDeckDao().update(deck);
        execute(completable);
    }

    public void deleteDeck(Deck deck) {
        final Completable completable = mDb.getDeckDao().delete(deck);
        execute(completable);
    }

    public LiveData<List<Card>> getCards(long deckId) {
        return mDb.getCardDao().getCardsByDeckId(deckId);
    }

    public void insertCard(Card card) {
        final Completable completable = mDb.getCardDao().insert(card);
        execute(completable);
    }

    public void insertCardList(List<Card> cardList) {
        final Completable completable = mDb.getCardDao().insertList(cardList);
        execute(completable);
    }

    public void updateCard(Card card) {
        final Completable completable = mDb.getCardDao().update(card);
        execute(completable);
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

    public void deleteCardList(List<Card> cardList) {
        final Completable completable = mDb.getCardDao().deleteList(cardList);
        execute(completable);
    }

    private void execute(Completable completable) {
        completable.subscribeOn(Schedulers.io())
                .subscribe();
    }
}

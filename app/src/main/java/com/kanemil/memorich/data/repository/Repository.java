package com.kanemil.memorich.data.repository;

import androidx.lifecycle.LiveData;

import com.kanemil.memorich.app.App;
import com.kanemil.memorich.data.db.AppDatabase;
import com.kanemil.memorich.data.db.entity.CardEntity;
import com.kanemil.memorich.data.db.entity.DeckEntity;
import com.kanemil.memorich.domain.IDecksRepository;

import java.util.List;

import io.reactivex.Completable;
import io.reactivex.CompletableObserver;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class Repository implements IDecksRepository {

    /* ВОПРОС
     Вот здесь плохо инициализировать наверное, ведь тогда репозиторий (слой данных) знает о контексте.
     То есть, тут неплохо было бы применить DI, но пока что я не умею, поэтому исправлю потом.
     */
    private AppDatabase db = App.getInstance().getDatabase();

    @Override
    public LiveData<List<DeckEntity>> loadDecks() {
        return db.getDeckDao().loadDecks();
    }


    /* ВОПРОС
        Допустим, я хочу сделать тост, который уведомит юзера, что колода/карта была добавлена.
        Как это правильно сделать?
        Может быть, insertDeck должен возвращать какой-нибудь объект RxJava, и вьюмодель уже будет
        как-то что-то с ним делать, например, обзервить его и по результату засовывать что-то в
        лайвДату, которая будет обзервиться во фрагменте, в результате чего и будет показываться тост?
     */
    @Override
    public void insertDeck(DeckEntity deck) {
        final Completable completable = db.getDeckDao().insert(deck);
        execute(completable);
    }

    @Override
    public void renameDeck(DeckEntity deck) {
        final Completable completable = db.getDeckDao().update(deck);
        execute(completable);
    }

    @Override
    public void deleteDeck(DeckEntity deck) {
        final Completable completable = db.getDeckDao().delete(deck);
        execute(completable);
    }

    public LiveData<List<CardEntity>> getCards(long deckId) {
        return db.getCardDao().loadCardsByDeckId(deckId);
    }

    public void insertCard(CardEntity card) {
        final Completable completable = db.getCardDao().insert(card);
        execute(completable);
    }

    public void insertCardList(List<CardEntity> cardList) {
        final Completable completable = db.getCardDao().insertList(cardList);
        execute(completable);
    }

    public void updateCard(CardEntity card) {
        final Completable completable = db.getCardDao().update(card);
        execute(completable);
    }

    public void updateCardList(List<CardEntity> cardList) {
        final Completable completable = db.getCardDao().updateList(cardList);
        execute(completable);
    }

    public void deleteCard(CardEntity card, final List<CardEntity> cardListAfterDeletion) {
        final Completable completable = db.getCardDao().delete(card);
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

    public void deleteCardList(List<CardEntity> cardList) {
        final Completable completable = db.getCardDao().deleteList(cardList);
        execute(completable);
    }

    private void execute(Completable completable) {
        completable.subscribeOn(Schedulers.io())
                .subscribe();
    }
}

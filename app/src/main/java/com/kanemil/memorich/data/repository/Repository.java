package com.kanemil.memorich.data.repository;

import androidx.lifecycle.LiveData;

import com.kanemil.memorich.app.App;
import com.kanemil.memorich.data.db.AppDatabase;
import com.kanemil.memorich.data.db.entity.Card;
import com.kanemil.memorich.data.db.entity.Deck;

import java.util.List;

import io.reactivex.Completable;
import io.reactivex.schedulers.Schedulers;

public class Repository {

    /* ВОПРОС
     Вот здесь плохо инициализировать наверное, ведь тогда репозиторий (слой данных) знает о контексте.
     То есть, тут неплохо было бы применить DI, но пока что я не умею, поэтому исправлю потом.
     */
    private AppDatabase db = App.getInstance().getDatabase();

    public LiveData<List<Deck>> getDecks() {
        return db.mDeckDao().getDecks();
    }


    /* ВОПРОС
        Допустим, я хочу сделать тост, который уведомит юзера, что колода/карта была добавлена.
        Как это правильно сделать?
        Может быть, insertDeck должен возвращать какой-нибудь объект RxJava, и вьюмодель уже будет
        как-то что-то с ним делать, например, обзервить его и по результату засовывать что-то в
        лайвДату, которая будет обзервиться во фрагменте, в результате чего и будет показываться тост?
     */
    public void insertDeck(Deck deck) {
        final Completable completable = db.mDeckDao().insert(deck);
        completable.subscribeOn(Schedulers.io())
                .subscribe();
    }

    public void renameDeck(Deck deck) {
        final Completable completable = db.mDeckDao().update(deck);
        completable.subscribeOn(Schedulers.io())
                .subscribe();
    }

    public LiveData<List<Card>> getCards(long deckId) {
        return db.mCardDao().getCardsByDeckId(deckId);
    }

    public void insertCard(Card card) {
        final Completable completable = db.mCardDao().insert(card);
        completable.subscribeOn(Schedulers.io())
                .subscribe();
    }
}

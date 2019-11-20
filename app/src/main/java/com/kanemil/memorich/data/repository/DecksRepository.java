package com.kanemil.memorich.data.repository;

import androidx.lifecycle.LiveData;

import com.kanemil.memorich.app.App;
import com.kanemil.memorich.data.db.AppDatabase;
import com.kanemil.memorich.data.db.entity.Card;
import com.kanemil.memorich.data.db.entity.Deck;

import java.util.ArrayList;
import java.util.List;

public class DecksRepository {

    // Вот здесь плохо инициализировать наверное, ведь тогда репозиторий (слой данных) знает о контексте.
    // То есть, тут неплохо было бы применить DI, но пока что я не умею, поэтому исправлю потом.
    private AppDatabase db = App.getInstance().getDatabase();

    /**
     * Provides decks list. Right now it's mocked list.
     *
     * @return decks list.
     */
    public LiveData<List<Deck>> getDecks() {
        return db.mDeckDao().getDecks();
    }

    public void insertDeck(Deck deck) {
        db.mDeckDao().insert(deck);
    }



    /**
     * Provides simple mocked decks list.
     *
     * @return mocked decks list.
     */
    private List<Deck> provideMockedDecks() {
        ArrayList<Deck> decks = new ArrayList<>();
        Card card0 = new Card("f0 stub stub stub stub", "b0 stub stub stub");
        Card card1 = new Card("f1", "b1");
        Card card2 = new Card("f2", "b2");
        Card card3 = new Card("f3", "b3");
        Card card4 = new Card("f4", "b4");
        Card card5 = new Card("f5", "b5");
        Card card6 = new Card("f6", "b6");
        Card card7 = new Card("f7", "b7");
        Card card8 = new Card("f8", "b8");
        Card card9 = new Card("f9", "b9");
        Card card10 = new Card("f10", "b10");
        Card card11 = new Card("f11", "b11");
        Card card12 = new Card("f12", "b12");
        Card card13 = new Card("f13", "b13");
        Card card14 = new Card("f14", "b14");
        Card card15 = new Card("f15", "b15");
//        for (int i = 0; i < 16; i++) {
//            Deck deck = new Deck("Deck #" + i);
//            deck.addCard(card0);
//            deck.addCard(card1);
//            deck.addCard(card2);
//            deck.addCard(card3);
//            deck.addCard(card4);
//            deck.addCard(card5);
//            deck.addCard(card6);
//            deck.addCard(card7);
//            deck.addCard(card8);
//            deck.addCard(card9);
//            deck.addCard(card10);
//            deck.addCard(card11);
//            deck.addCard(card12);
//            deck.addCard(card13);
//            deck.addCard(card14);
//            deck.addCard(card15);
//            decks.add(deck);
//        }
        return decks;
    }
}

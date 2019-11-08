package com.kanemil.memorich.data.repository;

import com.kanemil.memorich.data.model.Card;
import com.kanemil.memorich.data.model.Deck;

import java.util.ArrayList;
import java.util.List;

public class DecksProvider {

    /**
     * Provides decks list. Right now it's mocked list.
     *
     * @return decks list.
     */
    public List<Deck> provideDecks() {
        return provideMockedDecks();
    }

    /**
     * Provides simple mocked decks list.
     *
     * @return mocked decks list.
     */
    private List<Deck> provideMockedDecks() {
        ArrayList<Deck> decks = new ArrayList<>();
        Card card0 = new Card("f0", "b0");
        Card card1 = new Card("f1", "b1");
        Card card2 = new Card("f2", "b2");
        for (int i = 0; i < 15; i++) {
            Deck deck = new Deck("Deck #" + i);
            deck.addCard(card0);
            deck.addCard(card1);
            deck.addCard(card2);
            decks.add(deck);
        }
        return decks;
    }
}

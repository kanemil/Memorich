package com.kanemil.memorich.data.model;

import java.util.ArrayList;
import java.util.List;

/**
 * Simple deck of cards containing List<Card> field.
 */
public class Deck {
    private String mName;
    private List<Card> mCardList = new ArrayList<>();

    public Deck(String name) {
        mName = name;
    }

    /**
     * Returns defensive copy of cards list.
     *
     * @return List<Card> field.
     */
    public List<Card> getCardList() {
        return new ArrayList<>(mCardList);
    }

    public void setCardList(List<Card> cardList) {
        mCardList = cardList;
    }

    /**
     * Adds card to list.
     *
     * @param card card to be added.
     */
    public void addCard(Card card) {
        mCardList.add(card);
    }

    public String getName() {
        return mName;
    }

    public void setName(String name) {
        mName = name;
    }
}

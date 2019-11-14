package com.kanemil.memorich.data.model;

/**
 * Basic card
 */

public class Card {
    private String mFront;
    private String mBack;
    private boolean mRevealedInTraining = false;

    /**
     * Default constructor
     *
     * @param front front side of a card.
     * @param back  back side of a card.
     */
    public Card(String front, String back) {
        mFront = front;
        mBack = back;
    }

    public String getFront() {
        return mFront;
    }

    public void setFront(String front) {
        mFront = front;
    }

    public String getBack() {
        return mBack;
    }

    public void setBack(String back) {
        mBack = back;
    }

    public boolean isRevealedInTraining() {
        return mRevealedInTraining;
    }

    public void setRevealedInTraining(boolean revealedInTraining) {
        mRevealedInTraining = revealedInTraining;
    }
}

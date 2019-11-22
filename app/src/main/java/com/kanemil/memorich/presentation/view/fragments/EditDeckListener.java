package com.kanemil.memorich.presentation.view.fragments;

public interface EditDeckListener {
    /**
     * Shows all cards of a deck to edit them. Implemented by an activity.
     *
     * @param deckId
     */
    void editDeck(long deckId);
}

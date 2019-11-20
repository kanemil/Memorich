package com.kanemil.memorich.presentation.view.activities;

public interface OnDeckClickedListener {
    /**
     * Shows content of deck
     */
    void onDeckLongClicked(long deckId);

    /**
     * Starts training
     */
    void onDeckClicked(long deckId);
}

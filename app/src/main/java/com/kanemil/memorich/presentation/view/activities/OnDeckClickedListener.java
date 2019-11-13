package com.kanemil.memorich.presentation.view.activities;

public interface OnDeckClickedListener {
    /**
     * Shows content of deck
     */
    void onDeckLongClicked(int position);

    /**
     * Starts training
     */
    void onDeckClicked(int position);
}

package com.kanemil.memorich.presentation.view.adapters;

/**
 * Implemented by fragment. Then fragment may or may not tell its activity to do something.
 */

public interface DecksAdapterActionsListener {

    void onDeckClicked(long deckId);

    void onDeckMenuEditClicked(long deckId);
}

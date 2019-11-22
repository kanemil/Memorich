package com.kanemil.memorich.presentation.view.adapters;

import com.kanemil.memorich.data.db.entity.Deck;

/**
 * Implemented by fragment. Then fragment may or may not tell its activity to do something.
 */

public interface DecksAdapterActionsListener {

    void onDeckClicked(long deckId);

    void onDeckMenuEditClicked(long deckId);

    void onDeckMenuRenameClicked(Deck deck);

    void onDeckMenuDeleteClicked(Deck deck);
}

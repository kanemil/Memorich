package com.kanemil.memorich.presentation.view.fragments.adapters;

import com.kanemil.memorich.data.db.entity.Deck;

/**
 * Implemented by fragment. Then fragment may or may not tell its activity to do something.
 */

public interface DecksAdapterActionsListener {

    void onDeckTrainClicked(long deckId);

    void showBottomBar(boolean showNavBar);

    void onDeckMenuEditClicked(long deckId);

    void onDeckMenuRenameClicked(Deck deck);

    void onDeckMenuDeleteClicked(Deck deck);
}

package com.kanemil.memorich.presentation.view.fragments.adapters.contracts;

import com.kanemil.memorich.data.db.entity.DeckEntity;

/**
 * Implemented by fragment. Then fragment may or may not tell its activity to do something.
 */

public interface DecksAdapterActionsListener {

    void onDeckTrainClicked(long deckId);

    void onShowNavBar(boolean showNavBar);

    void onDeckMenuEditClicked(long deckId);

    void onDeckMenuRenameClicked(DeckEntity deck);

    void onDeckMenuDeleteClicked(DeckEntity deck);
}

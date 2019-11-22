package com.kanemil.memorich.presentation.view.adapters.contracts;

import com.kanemil.memorich.data.db.entity.Card;

/**
 * Implemented by fragment. Then fragment may or may not tell its activity to do something.
 */
public interface CardsAdapterActionsListener {
    void editCard(Card card);
}

package com.kanemil.memorich.presentation.view.fragments.adapters;

import com.kanemil.memorich.data.db.entity.Card;

import java.util.List;

/**
 * Implemented by fragment. Then fragment may or may not tell its activity to do something.
 */
public interface CardsAdapterActionsListener {
    void editCard(Card card);

    void deleteCard(Card card, List<Card> cardListAfterDeletion);
}

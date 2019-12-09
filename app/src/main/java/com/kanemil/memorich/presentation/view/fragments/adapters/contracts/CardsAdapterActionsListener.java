package com.kanemil.memorich.presentation.view.fragments.adapters.contracts;

import com.kanemil.memorich.data.db.entity.CardEntity;

import java.util.List;

/**
 * Implemented by fragment. Then fragment may or may not tell its activity to do something.
 */
public interface CardsAdapterActionsListener {
    void editCard(CardEntity card);

    void deleteCard(CardEntity card, List<CardEntity> cardListAfterDeletion);
}

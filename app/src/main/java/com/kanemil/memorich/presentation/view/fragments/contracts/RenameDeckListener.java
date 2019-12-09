package com.kanemil.memorich.presentation.view.fragments.contracts;

import com.kanemil.memorich.data.db.entity.DeckEntity;

public interface RenameDeckListener {
    /**
     * Shows rename deck dialog. Implemented by an activity.
     *
     * @param deck
     */
    void renameDeck(DeckEntity deck);
}

package com.kanemil.memorich.presentation.view.activities;

public interface OnDeckRenamedListener {
    /**
     * Commands Fragment/ViewModel to update decks name list in a database.
     * Activity -> Fragment -> ViewModel -> Repository -> DAO -> Database.
     *
     * @param deckId     deckId to be renamed in db
     * @param newDeckName new name for the deck
     */
    void onDeckRenamed(long deckId, String newDeckName);
}

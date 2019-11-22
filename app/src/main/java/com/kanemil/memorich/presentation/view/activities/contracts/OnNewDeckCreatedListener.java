package com.kanemil.memorich.presentation.view.activities.contracts;

public interface OnNewDeckCreatedListener {
    /**
     * Commands Fragment/ViewModel to update decks list in a database.
     * Activity -> Fragment -> ViewModel -> Repository -> DAO -> Database.
     *
     * @param deckName
     */
    void onNewDeckCreated(String deckName);
}

package com.kanemil.memorich.presentation.view.fragments.contracts;


public interface ShowAddCardScreenListener {
    /**
     * Shows add card fragment. Implemented by an activity.
     *
     * @param deckId
     */
    void showAddCardScreen(long deckId, long cardOrderId);
}

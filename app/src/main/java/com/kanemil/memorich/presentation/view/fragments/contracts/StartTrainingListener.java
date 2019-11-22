package com.kanemil.memorich.presentation.view.fragments.contracts;

public interface StartTrainingListener {
    /**
     * Shows training fragment. Implemented by an activity.
     *
     * @param deckId
     */
    void startTraining(long deckId);
}

package com.kanemil.memorich.presentation.view.dialogs.contracts;

public interface OnDeckAddedDialogClickListener {
    /**
     * Tells activity we filled a new deck's name and clicked "Add" button in a dialog.
     * Implemented by an activity, because it's a <b>fragment</b> dialog
     *
     * @param deckName
     */
    void onDeckAddedDialogClick(String deckName);
}

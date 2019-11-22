package com.kanemil.memorich.presentation.view.dialogs;

public interface OnDeckRenameDialogClickListener {
    /**
     * Tells activity we filled a new deck's name and clicked "Rename" button in a dialog.
     * Implemented by an activity, because it's a <b>fragment</b> dialog
     *
     * @param deckId     deckId to be renamed in db
     * @param newDeckName new name for the deck
     */
    void onDeckRenameDialogClick(long deckId, String newDeckName);
}

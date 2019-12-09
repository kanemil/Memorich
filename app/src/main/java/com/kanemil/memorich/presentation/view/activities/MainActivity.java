package com.kanemil.memorich.presentation.view.activities;

import android.os.Bundle;

import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;

import com.kanemil.memorich.R;
import com.kanemil.memorich.base.BaseActivity;
import com.kanemil.memorich.data.db.entity.Deck;
import com.kanemil.memorich.presentation.view.activities.contracts.OnDeckRenamedListener;
import com.kanemil.memorich.presentation.view.activities.contracts.OnNewDeckCreatedListener;
import com.kanemil.memorich.presentation.view.dialogs.AddDeckDialogFragment;
import com.kanemil.memorich.presentation.view.dialogs.RenameDeckDialogFragment;
import com.kanemil.memorich.presentation.view.dialogs.contracts.OnDeckAddedDialogClickListener;
import com.kanemil.memorich.presentation.view.dialogs.contracts.OnDeckRenameDialogClickListener;
import com.kanemil.memorich.presentation.view.fragments.AddOrEditCardFragment;
import com.kanemil.memorich.presentation.view.fragments.CardsFragment;
import com.kanemil.memorich.presentation.view.fragments.DecksFragment;
import com.kanemil.memorich.presentation.view.fragments.TrainingFragment;
import com.kanemil.memorich.presentation.view.fragments.contracts.EditDeckListener;
import com.kanemil.memorich.presentation.view.fragments.contracts.RenameDeckListener;
import com.kanemil.memorich.presentation.view.fragments.contracts.ShowAddCardScreenListener;
import com.kanemil.memorich.presentation.view.fragments.contracts.ShowAddDeckDialogListener;
import com.kanemil.memorich.presentation.view.fragments.contracts.ShowEditCardScreenListener;
import com.kanemil.memorich.presentation.view.fragments.contracts.StartTrainingListener;

public class MainActivity extends BaseActivity
        implements ShowAddDeckDialogListener,
        OnDeckAddedDialogClickListener,
        ShowAddCardScreenListener,
        StartTrainingListener,
        EditDeckListener,
        RenameDeckListener,
        OnDeckRenameDialogClickListener,
        ShowEditCardScreenListener {

    private static final String DECKS_FRAGMENT = "decksFragment";
    private static final String CARDS_FRAGMENT = "cardsFragment";
    private static final String TRAINING_FRAGMENT = "trainingFragment";
    private static final String ADD_DECK_DIALOG_FRAGMENT = "addDeckDialogFragment";
    private static final String ADD_OR_EDIT_CARD_FRAGMENT = "addOrEditCardFragment";
    private static final String RENAME_DECK_DIALOG_FRAGMENT = "renameDeckDialogFragment";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .setCustomAnimations(android.R.anim.slide_in_left, android.R.anim.slide_out_right)
                    .replace(R.id.root_view, DecksFragment.newInstance(), DECKS_FRAGMENT)
                    .commit();
        }
    }

    @Override
    public void showAddDeckDialog() {
        DialogFragment dialogFragment = new AddDeckDialogFragment();
        dialogFragment.show(getSupportFragmentManager(), ADD_DECK_DIALOG_FRAGMENT);
    }

    @Override
    public void onDeckAddedDialogClick(String deckName) {
        final Fragment fragment = getSupportFragmentManager().findFragmentByTag(DECKS_FRAGMENT);
        if (fragment instanceof OnNewDeckCreatedListener) {
            ((OnNewDeckCreatedListener) fragment).onNewDeckCreated(deckName);
        }
    }

    @Override
    public void showAddCardScreen(long deckId, long cardOrderId) {
        getSupportFragmentManager().beginTransaction()
                .setCustomAnimations(android.R.anim.slide_in_left, android.R.anim.slide_out_right)
                .replace(R.id.root_view, AddOrEditCardFragment.newInstance(deckId, cardOrderId), ADD_OR_EDIT_CARD_FRAGMENT)
                .addToBackStack(null)
                .commit();
    }

    @Override
    public void startTraining(long deckId) {
        getSupportFragmentManager().beginTransaction()
                .setCustomAnimations(android.R.anim.slide_in_left, android.R.anim.slide_out_right, android.R.animator.fade_in, android.R.animator.fade_out)
                .replace(R.id.root_view, TrainingFragment.newInstance(deckId), TRAINING_FRAGMENT)
                .addToBackStack(null)
                .commit();
    }

    @Override
    public void editDeck(long deckId) {
        getSupportFragmentManager().beginTransaction()
                .setCustomAnimations(android.R.anim.slide_in_left, android.R.anim.slide_out_right, android.R.animator.fade_in, android.R.animator.fade_out)
                .replace(R.id.root_view, CardsFragment.newInstance(deckId), CARDS_FRAGMENT)
                .addToBackStack(null)
                .commit();
    }

    @Override
    public void onDeckRenameDialogClick(long deckId, String newDeckName) {
        final Fragment fragment = getSupportFragmentManager().findFragmentByTag(DECKS_FRAGMENT);
        if (fragment instanceof OnDeckRenamedListener) {
            ((OnDeckRenamedListener) fragment).onDeckRenamed(deckId, newDeckName);
        }
    }

    @Override
    public void renameDeck(Deck deck) {
        DialogFragment dialogFragment = RenameDeckDialogFragment.newInstance(deck);
        dialogFragment.show(getSupportFragmentManager(), RENAME_DECK_DIALOG_FRAGMENT);
    }

    @Override
    public void showEditCardScreen(long deckId, long cardId, String front, String back) {
        getSupportFragmentManager().beginTransaction()
                .setCustomAnimations(android.R.anim.slide_in_left, android.R.anim.slide_out_right, android.R.animator.fade_in, android.R.animator.fade_out)
                .replace(R.id.root_view, AddOrEditCardFragment.newInstance(deckId, cardId, front, back), ADD_OR_EDIT_CARD_FRAGMENT)
                .addToBackStack(null)
                .commit();
    }
}

package com.kanemil.memorich.presentation.view.activities;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;

import com.kanemil.memorich.R;
import com.kanemil.memorich.presentation.view.dialogs.AddDeckDialogFragment;
import com.kanemil.memorich.presentation.view.dialogs.OnDeckAddedDialogClick;
import com.kanemil.memorich.presentation.view.fragments.AddCardFragment;
import com.kanemil.memorich.presentation.view.fragments.CardsFragment;
import com.kanemil.memorich.presentation.view.fragments.DecksFragment;
import com.kanemil.memorich.presentation.view.fragments.EditDeckListener;
import com.kanemil.memorich.presentation.view.fragments.ShowAddCardScreenListener;
import com.kanemil.memorich.presentation.view.fragments.ShowAddDeckDialogListener;
import com.kanemil.memorich.presentation.view.fragments.StartTrainingListener;
import com.kanemil.memorich.presentation.view.fragments.TrainingFragment;

public class MainActivity extends AppCompatActivity
        implements ShowAddDeckDialogListener,
        OnDeckAddedDialogClick,
        ShowAddCardScreenListener,
        StartTrainingListener,
        EditDeckListener {

    private static final String DECKS_FRAGMENT = "decksFragment";
    private static final String CARDS_FRAGMENT = "cardsFragment";
    private static final String TRAINING_FRAGMENT = "trainingFragment";
    private static final String ADD_DECK_DIALOG_FRAGMENT = "addDeckDialogFragment";
    private static final String ADD_CARD_FRAGMENT = "addCardFragment";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
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
    public void showAddCardScreen(long deckId) {
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.root_view, AddCardFragment.newInstance(deckId), ADD_CARD_FRAGMENT)
                .addToBackStack(null)
                .commit();
    }

    @Override
    public void startTraining(long deckId) {
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.root_view, TrainingFragment.newInstance(deckId), TRAINING_FRAGMENT)
                .addToBackStack(null)
                .commit();
    }

    @Override
    public void editDeck(long deckId) {
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.root_view, CardsFragment.newInstance(deckId), CARDS_FRAGMENT)
                .addToBackStack(null)
                .commit();
    }
}

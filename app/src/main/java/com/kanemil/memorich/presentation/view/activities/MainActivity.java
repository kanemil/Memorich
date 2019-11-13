package com.kanemil.memorich.presentation.view.activities;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.DialogFragment;

import com.kanemil.memorich.R;
import com.kanemil.memorich.presentation.view.dialogs.AddDeckDialogFragment;
import com.kanemil.memorich.presentation.view.fragments.CardsFragment;
import com.kanemil.memorich.presentation.view.fragments.DecksFragment;
import com.kanemil.memorich.presentation.view.fragments.TrainingFragment;

public class MainActivity extends AppCompatActivity implements OnShowAddDeckDialogClickListener,
        OnDeckClickedListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        getSupportFragmentManager().beginTransaction()
                .replace(R.id.root_view, DecksFragment.newInstance(), "decksFragment")
                .commit();
    }

    @Override
    public void showAddDeckDialog() {
        DialogFragment dialogFragment = new AddDeckDialogFragment();
        if (getFragmentManager() != null) {
            dialogFragment.show(getSupportFragmentManager(), null);
        }
    }

    @Override
    public void onDeckLongClicked(int position) {
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.root_view, CardsFragment.newInstance(position), "cardsFragment: " + position)
                .addToBackStack(null)
                .commit();
    }

    @Override
    public void onDeckClicked(int position) {
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.root_view, TrainingFragment.newInstance(position), "trainingFragment: " + position)
                .addToBackStack(null)
                .commit();
    }
}

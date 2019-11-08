package com.kanemil.memorich.presentation.view.activities;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.DialogFragment;

import com.kanemil.memorich.R;
import com.kanemil.memorich.presentation.view.fragments.AddDeckDialogFragment;
import com.kanemil.memorich.presentation.view.fragments.DecksFragment;

public class MainActivity extends AppCompatActivity
        implements OnShowAddDeckDialogClickListener {

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
}

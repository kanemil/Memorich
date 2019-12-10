package com.kanemil.memorich.presentation.view.activities;

import android.os.Bundle;

import com.kanemil.memorich.R;
import com.kanemil.memorich.base.BaseActivity;
import com.kanemil.memorich.presentation.view.fragments.DecksFragment;

public class MainActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .setCustomAnimations(android.R.anim.slide_in_left, android.R.anim.slide_out_right)
                    .replace(R.id.root_view, DecksFragment.newInstance(), null)
                    .commit();
        }
    }
}

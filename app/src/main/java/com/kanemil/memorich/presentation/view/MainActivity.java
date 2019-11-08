package com.kanemil.memorich.presentation.view;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.kanemil.memorich.R;
import com.kanemil.memorich.presentation.view.fragments.DecksFragment;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        getSupportFragmentManager().beginTransaction()
                .replace(R.id.root_view, DecksFragment.newInstance(), "decksFragment")
                .commit();
    }
}

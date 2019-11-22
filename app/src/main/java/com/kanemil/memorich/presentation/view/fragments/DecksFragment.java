package com.kanemil.memorich.presentation.view.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.kanemil.memorich.R;
import com.kanemil.memorich.data.db.entity.Deck;
import com.kanemil.memorich.presentation.view.activities.OnNewDeckCreatedListener;
import com.kanemil.memorich.presentation.view.adapters.DecksAdapter;
import com.kanemil.memorich.presentation.view.adapters.DecksAdapterActionsListener;
import com.kanemil.memorich.presentation.viewmodel.CustomViewModelFactory;
import com.kanemil.memorich.presentation.viewmodel.DecksViewModel;

import java.util.List;

public class DecksFragment extends Fragment
        implements OnNewDeckCreatedListener, DecksAdapterActionsListener {

    private static final String TAG = "DecksFragment";
    private View.OnClickListener mFabOnClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            if (requireActivity() instanceof ShowAddDeckDialogListener) {
                ShowAddDeckDialogListener listener = (ShowAddDeckDialogListener) requireActivity();
                listener.showAddDeckDialog();
            }
        }
    };
    private DecksViewModel mViewModel;
    private RecyclerView mRecyclerView;
    private FloatingActionButton fab;
    private DecksAdapter mAdapter;

    public static DecksFragment newInstance() {
        return new DecksFragment();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mAdapter = new DecksAdapter(this);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_decks, container, false);
        mRecyclerView = root.findViewById(R.id.rv_decks);
        mRecyclerView.setAdapter(mAdapter);
        fab = root.findViewById(R.id.fab_add_deck);
        fab.setOnClickListener(mFabOnClickListener);
        return root;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        setupViewModel();
    }

    private void setupViewModel() {
        mViewModel = ViewModelProviders
                .of(this, new CustomViewModelFactory())
                .get(DecksViewModel.class);
        mViewModel.getDecksList().observe(this, new Observer<List<Deck>>() {
            @Override
            public void onChanged(List<Deck> decks) {
                mAdapter.setDecks(decks);
            }
        });
    }

    @Override
    public void onNewDeckCreated(String deckName) {
        mViewModel.addDeck(deckName);
    }


    @Override
    public void onDeckClicked(long deckId) {
        final FragmentActivity activity = requireActivity();
        if (activity instanceof StartTrainingListener) {
            ((StartTrainingListener) activity).startTraining(deckId);
        }
    }

    @Override
    public void onDeckMenuEditClicked(long deckId) {
        final FragmentActivity activity = requireActivity();
        if (activity instanceof EditDeckListener) {
            ((EditDeckListener) activity).editDeck(deckId);
        }
    }
}

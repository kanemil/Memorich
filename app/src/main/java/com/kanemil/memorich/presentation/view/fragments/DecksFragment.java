package com.kanemil.memorich.presentation.view.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.FragmentActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.kanemil.memorich.R;
import com.kanemil.memorich.base.BaseFragment;
import com.kanemil.memorich.data.db.entity.Deck;
import com.kanemil.memorich.presentation.view.activities.contracts.OnDeckRenamedListener;
import com.kanemil.memorich.presentation.view.activities.contracts.OnNewDeckCreatedListener;
import com.kanemil.memorich.presentation.view.fragments.adapters.DecksAdapter;
import com.kanemil.memorich.presentation.view.fragments.adapters.contracts.DecksAdapterActionsListener;
import com.kanemil.memorich.presentation.view.fragments.contracts.EditDeckListener;
import com.kanemil.memorich.presentation.view.fragments.contracts.RenameDeckListener;
import com.kanemil.memorich.presentation.view.fragments.contracts.ShowAddDeckDialogListener;
import com.kanemil.memorich.presentation.view.fragments.contracts.StartTrainingListener;
import com.kanemil.memorich.presentation.viewmodel.DecksViewModel;
import com.kanemil.memorich.presentation.viewmodel.ViewModelProviderFactory;

import java.util.List;
import java.util.Objects;

import javax.inject.Inject;

public class DecksFragment extends BaseFragment
        implements OnNewDeckCreatedListener, DecksAdapterActionsListener, OnDeckRenamedListener {

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
    private BottomNavigationView mBottomNavigationView;
    private FloatingActionButton fab;
    private DecksAdapter mAdapter;

    @Inject
    ViewModelProviderFactory mViewModelProviderFactory;

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
        mBottomNavigationView = root.findViewById(R.id.bot_nav);
        mBottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                switch (menuItem.getItemId()) {
                    case R.id.menu_deck_rename:
                        onDeckMenuRenameClicked(mAdapter.getCurrentDeck());
                        return true;
                    case R.id.menu_deck_train:
                        onDeckTrainClicked(mAdapter.getCurrentDeck().getId());
                        return true;
                    case R.id.menu_deck_edit:
                        onDeckMenuEditClicked(mAdapter.getCurrentDeck().getId());
                        return true;
                    case R.id.menu_deck_delete:
                        onDeckMenuDeleteClicked(mAdapter.getCurrentDeck());
                        return true;
                    default:
                }
                return false;
            }
        });
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

    @Override
    public void onStop() {
        super.onStop();
        mAdapter.clearDeckSelection();
    }

    private void setupViewModel() {
        mViewModel = ViewModelProviders
                .of(this, mViewModelProviderFactory)
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
    public void onDeckTrainClicked(long deckId) {
        final FragmentActivity activity = requireActivity();
        if (activity instanceof StartTrainingListener) {
            ((StartTrainingListener) activity).startTraining(deckId);
        }
    }

    @Override
    public void onShowNavBar(boolean showNavBar) {
        if (showNavBar)
            mBottomNavigationView.setVisibility(View.VISIBLE);
        else
            mBottomNavigationView.setVisibility(View.GONE);
    }

    @Override
    public void onDeckMenuEditClicked(long deckId) {
        final FragmentActivity activity = requireActivity();
        if (activity instanceof EditDeckListener) {
            ((EditDeckListener) activity).editDeck(deckId);
        }
    }

    @Override
    public void onDeckRenamed(long deckId, String newDeckName) {
        mViewModel.renameDeck(deckId, newDeckName);
        mAdapter.getCurrentDeck().setName(newDeckName);
    }

    @Override
    public void onDeckMenuRenameClicked(Deck deck) {
        final FragmentActivity activity = requireActivity();
        if (activity instanceof RenameDeckListener) {
            ((RenameDeckListener) activity).renameDeck(deck);
        }
    }

    @Override
    public void onDeckMenuDeleteClicked(final Deck deck) {
        Snackbar snackbar = Snackbar.make(Objects.requireNonNull(getView()).findViewById(R.id.coordinator_decks),
                getString(R.string.press_undo_to_cancel), Snackbar.LENGTH_LONG)
                .setAction(getString(R.string.undo), new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        // do nothing
                    }
                })
                .setActionTextColor(getResources().getColor(R.color.colorAccent))
                .addCallback(new Snackbar.Callback() {
                    @Override
                    public void onDismissed(Snackbar transientBottomBar, int event) {
                        if (event == DISMISS_EVENT_TIMEOUT || event == DISMISS_EVENT_SWIPE
                                || event == DISMISS_EVENT_CONSECUTIVE || event == DISMISS_EVENT_MANUAL) {
                            mViewModel.deleteDeck(deck);
                            mBottomNavigationView.setVisibility(View.GONE);
                        }
                    }
                });
        snackbar.show();
    }
}

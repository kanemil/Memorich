package com.kanemil.memorich.presentation.view.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.fragment.app.DialogFragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.kanemil.memorich.R;
import com.kanemil.memorich.base.BaseFragment;
import com.kanemil.memorich.data.db.entity.Deck;
import com.kanemil.memorich.presentation.view.dialogs.AddDeckDialogFragment;
import com.kanemil.memorich.presentation.view.dialogs.RenameDeckDialogFragment;
import com.kanemil.memorich.presentation.view.fragments.adapters.DecksAdapter;
import com.kanemil.memorich.presentation.view.fragments.adapters.DecksAdapterActionsListener;
import com.kanemil.memorich.presentation.viewmodel.DecksViewModel;
import com.kanemil.memorich.presentation.viewmodel.ViewModelProviderFactory;

import java.util.List;

import javax.inject.Inject;

public class DecksFragment extends BaseFragment
        implements DecksAdapterActionsListener {

    private static final String TAG = "DecksFragment";
    private View.OnClickListener mFabOnClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            DialogFragment dialogFragment = new AddDeckDialogFragment();
            dialogFragment.show(requireFragmentManager(), null);
        }
    };

    private DecksViewModel mViewModel;

    private CoordinatorLayout mCoordinatorLayout;
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
        initBottomNavView(root);
        mRecyclerView.setAdapter(mAdapter);
        fab = root.findViewById(R.id.fab_add_deck);
        fab.setOnClickListener(mFabOnClickListener);
        mCoordinatorLayout = root.findViewById(R.id.coordinator_decks);
        return root;
    }

    private void initBottomNavView(View root) {
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
                .of(requireActivity(), mViewModelProviderFactory)
                .get(DecksViewModel.class);
        subscribeObservers();
    }

    private void subscribeObservers() {
        mViewModel.getDecksList().observe(getViewLifecycleOwner(), new Observer<List<Deck>>() {
            @Override
            public void onChanged(List<Deck> decks) {
                mAdapter.setDecks(decks);
            }
        });

        mViewModel.getSnackbarMessage().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(String s) {
                Snackbar.make(mCoordinatorLayout, s, Snackbar.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public void onDeckTrainClicked(long deckId) {
        requireFragmentManager().beginTransaction()
                .setCustomAnimations(android.R.anim.slide_in_left, android.R.anim.slide_out_right, android.R.animator.fade_in, android.R.animator.fade_out)
                .replace(R.id.root_view, TrainingFragment.newInstance(deckId), null)
                .addToBackStack(null)
                .commit();
    }

    @Override
    public void showBottomBar(boolean showNavBar) {
        if (!showNavBar) {
            mBottomNavigationView.setBackgroundColor(getResources().getColor(android.R.color.darker_gray));
        } else {
            mBottomNavigationView.setBackgroundColor(getResources().getColor(R.color.colorPrimary));
        }
        mBottomNavigationView.getMenu().setGroupVisible(0, showNavBar);
    }

    @Override
    public void onDeckMenuEditClicked(long deckId) {
        requireFragmentManager().beginTransaction()
                .setCustomAnimations(android.R.anim.slide_in_left, android.R.anim.slide_out_right, android.R.animator.fade_in, android.R.animator.fade_out)
                .replace(R.id.root_view, CardsFragment.newInstance(deckId), null)
                .addToBackStack(null)
                .commit();
    }

    @Override
    public void onDeckMenuRenameClicked(Deck deck) {
        DialogFragment dialogFragment = RenameDeckDialogFragment.newInstance(deck);
        dialogFragment.show(requireFragmentManager(), null);
    }

    @Override
    public void onDeckMenuDeleteClicked(final Deck deck) {
        Snackbar snackbar = Snackbar.make(mCoordinatorLayout,
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
                        }
                    }
                });
        snackbar.show();
    }
}

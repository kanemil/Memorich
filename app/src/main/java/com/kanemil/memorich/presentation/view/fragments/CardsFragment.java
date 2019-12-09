package com.kanemil.memorich.presentation.view.fragments;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.kanemil.memorich.R;
import com.kanemil.memorich.data.db.entity.CardEntity;
import com.kanemil.memorich.presentation.view.fragments.adapters.CardsAdapter;
import com.kanemil.memorich.presentation.view.fragments.adapters.contracts.CardsAdapterActionsListener;
import com.kanemil.memorich.presentation.view.fragments.adapters.drag.SimpleItemTouchHelperCallback;
import com.kanemil.memorich.presentation.view.fragments.contracts.ShowAddCardScreenListener;
import com.kanemil.memorich.presentation.view.fragments.contracts.ShowEditCardScreenListener;
import com.kanemil.memorich.presentation.viewmodel.CardsViewModel;
import com.kanemil.memorich.presentation.viewmodel.CustomViewModelFactory;

import java.util.List;
import java.util.Objects;

public class CardsFragment extends Fragment implements CardsAdapterActionsListener {

    private static final String TAG = "CardsFragment";
    private static final int SPAN_COUNT = 3;
    private static final String DECK_ID = "deckId";

    private View.OnClickListener mFabOnClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            if (requireActivity() instanceof ShowAddCardScreenListener) {
                if (getArguments() != null) {
                    ShowAddCardScreenListener listener = (ShowAddCardScreenListener) requireActivity();
                    listener.showAddCardScreen(getArguments().getLong(DECK_ID), mAdapter.getItemCount());
                }
            }
        }
    };
    private CardsViewModel mViewModel;
    private RecyclerView mRecyclerView;
    private FloatingActionButton fab;
    private CardsAdapter mAdapter;

    public static CardsFragment newInstance(long deckId) {
        Bundle args = new Bundle();
        args.putLong(DECK_ID, deckId);
        CardsFragment fragment = new CardsFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mAdapter = new CardsAdapter(CardsAdapter.DisplayMode.EDIT);
        mAdapter.setCardsAdapterActionsListener(this);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_cards, container, false);
        initRecyclerView(root);
        initFab(root);
        return root;
    }

    private void initFab(View root) {
        fab = root.findViewById(R.id.fab_add_card);
        fab.setOnClickListener(mFabOnClickListener);
    }

    private void initRecyclerView(View root) {
        mRecyclerView = root.findViewById(R.id.rv_cards);
        mRecyclerView.setAdapter(mAdapter);
        GridLayoutManager layoutManager = new GridLayoutManager(requireContext(), SPAN_COUNT);
        mRecyclerView.setLayoutManager(layoutManager);
        ItemTouchHelper.Callback callback = new SimpleItemTouchHelperCallback(mAdapter);
        ItemTouchHelper touchHelper = new ItemTouchHelper(callback);
        touchHelper.attachToRecyclerView(mRecyclerView);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        setupViewModel();
    }

    private void setupViewModel() {
        if (getArguments() != null) {
            mViewModel = ViewModelProviders
                    .of(this, new CustomViewModelFactory(getArguments().getLong(DECK_ID)))
                    .get(CardsViewModel.class);
        }
        mViewModel.getCardsList().observe(this, new Observer<List<CardEntity>>() {
            @Override
            public void onChanged(List<CardEntity> cards) {
                mAdapter.setCards(cards);
            }
        });
    }

    @Override
    public void onStop() {
        super.onStop();
        mViewModel.updateCardsOrder(mAdapter.getCards());
        Log.d(TAG, "cards: " + mAdapter.getCards().toString());
    }

    @Override
    public void editCard(CardEntity card) {
        final FragmentActivity activity = requireActivity();
        if (activity instanceof ShowEditCardScreenListener) {
            ((ShowEditCardScreenListener) activity).showEditCardScreen(
                    card.getDeckId(),
                    card.getId(),
                    card.getFront(),
                    card.getBack());
        }
    }

    @Override
    public void deleteCard(CardEntity card, List<CardEntity> cardListAfterDeletion) {
        mViewModel.deleteCard(card, cardListAfterDeletion);
        Snackbar snackbar = Snackbar.make(Objects.requireNonNull(getView()).findViewById(R.id.coordinator_cards),
                getString(R.string.card_deleted), Snackbar.LENGTH_SHORT);
        snackbar.show();
    }
}

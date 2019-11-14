package com.kanemil.memorich.presentation.view.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.kanemil.memorich.R;
import com.kanemil.memorich.data.model.Card;
import com.kanemil.memorich.presentation.view.adapters.CardsAdapter;
import com.kanemil.memorich.presentation.viewmodel.CardsViewModel;
import com.kanemil.memorich.presentation.viewmodel.CardsViewModelFactory;

import java.util.List;

public class CardsFragment extends Fragment {

    private static final int SPAN_COUNT = 3;
    private static final String DECK_ID = "cardId";

    private View.OnClickListener mFabOnClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            // TODO  добавление новой карты
            Toast.makeText(requireContext(), "New card will be added in the future", Toast.LENGTH_SHORT).show();
        }
    };
    private CardsViewModel mCardsViewModel;
    private RecyclerView mRecyclerView;
    private FloatingActionButton fab;
    private CardsAdapter mAdapter;

    public static CardsFragment newInstance(int deckId) {
        Bundle args = new Bundle();
        args.putInt(DECK_ID, deckId);
        CardsFragment fragment = new CardsFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mAdapter = new CardsAdapter(CardsAdapter.DisplayMode.EDIT);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_cards, container, false);
        mRecyclerView = root.findViewById(R.id.rv_cards);
        mRecyclerView.setAdapter(mAdapter);
        GridLayoutManager layoutManager = new GridLayoutManager(requireContext(), SPAN_COUNT);
        mRecyclerView.setLayoutManager(layoutManager);
        fab = root.findViewById(R.id.fab_add_card);
        fab.setOnClickListener(mFabOnClickListener);
        return root;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        setupViewModel();
    }

    private void setupViewModel() {
        if (getArguments() != null) {
            mCardsViewModel = ViewModelProviders
                    .of(this, new CardsViewModelFactory(getArguments().getInt(DECK_ID)))
                    .get(CardsViewModel.class);
        }
        mCardsViewModel.getCardsList().observe(this, new Observer<List<Card>>() {
            @Override
            public void onChanged(List<Card> cards) {
                mAdapter.setCards(cards);
            }
        });
        mCardsViewModel.loadCards();
    }
}

package com.kanemil.memorich.presentation.view.fragments;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.kanemil.memorich.R;
import com.kanemil.memorich.base.BaseFragment;
import com.kanemil.memorich.data.db.entity.Card;
import com.kanemil.memorich.presentation.view.fragments.adapters.CardsAdapter;
import com.kanemil.memorich.presentation.view.fragments.adapters.CardsAdapterActionsListener;
import com.kanemil.memorich.presentation.view.fragments.adapters.utils.SimpleItemTouchHelperCallback;
import com.kanemil.memorich.presentation.viewmodel.CardsViewModel;
import com.kanemil.memorich.presentation.viewmodel.ViewModelProviderFactory;

import java.util.List;
import java.util.Objects;

import javax.inject.Inject;

public class CardsFragment extends BaseFragment implements CardsAdapterActionsListener {

    private static final String TAG = "CardsFragment";
    private static final int SPAN_COUNT = 3;
    private static final String DECK_ID = "deckId";

    private View.OnClickListener mFabOnClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            requireFragmentManager().beginTransaction()
                    .setCustomAnimations(android.R.anim.slide_in_left, android.R.anim.slide_out_right)
                    .replace(R.id.root_view, AddOrEditCardFragment.newInstance(mDeckId, mAdapter.getItemCount()), null)
                    .addToBackStack(null)
                    .commit();
        }
    };
    private CardsViewModel mViewModel;
    private RecyclerView mRecyclerView;
    private FloatingActionButton fab;
    private CardsAdapter mAdapter;

    private long mDeckId;

    @Inject
    ViewModelProviderFactory mViewModelProviderFactory;

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
        if (getArguments() != null) {
            mDeckId = getArguments().getLong(DECK_ID);
        }
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
        mViewModel = ViewModelProviders
                .of(this, mViewModelProviderFactory)
                .get(CardsViewModel.class);
        mViewModel.setDeckId(mDeckId);
        mViewModel.getCardsList().observe(this, new Observer<List<Card>>() {
            @Override
            public void onChanged(List<Card> cards) {
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
    public void editCard(Card card) {
        requireFragmentManager().beginTransaction()
                .setCustomAnimations(android.R.anim.slide_in_left, android.R.anim.slide_out_right, android.R.animator.fade_in, android.R.animator.fade_out)
                .replace(R.id.root_view, AddOrEditCardFragment.newInstance(
                        card.getDeckId(), card.getId(), card.getFront(), card.getBack()), null)
                .addToBackStack(null)
                .commit();
    }

    @Override
    public void deleteCard(Card card, List<Card> cardListAfterDeletion) {
        mViewModel.deleteCard(card, cardListAfterDeletion);
        Snackbar snackbar = Snackbar.make(Objects.requireNonNull(getView()).findViewById(R.id.coordinator_cards),
                getString(R.string.card_deleted), Snackbar.LENGTH_SHORT);
        snackbar.show();
    }
}

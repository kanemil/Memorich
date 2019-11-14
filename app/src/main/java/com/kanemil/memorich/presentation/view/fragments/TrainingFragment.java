package com.kanemil.memorich.presentation.view.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.viewpager2.widget.ViewPager2;

import com.kanemil.memorich.R;
import com.kanemil.memorich.data.model.Card;
import com.kanemil.memorich.presentation.view.adapters.CardsAdapter;
import com.kanemil.memorich.presentation.viewmodel.TrainingViewModel;
import com.kanemil.memorich.presentation.viewmodel.TrainingViewModelFactory;

import java.util.List;

public class TrainingFragment extends Fragment {

    private static final String DECK_ID = "deckId";

    private TrainingViewModel mViewModel;
    private CardsAdapter mAdapter;

    private ViewPager2 mViewPager2;
    private TextView mTextViewProgress;
    private Button mButtonRemember;
    private Button mButtonRepeat;

    public static TrainingFragment newInstance(int deckId) {
        Bundle args = new Bundle();
        args.putInt(DECK_ID, deckId);
        TrainingFragment fragment = new TrainingFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mAdapter = new CardsAdapter(CardsAdapter.DisplayMode.TRAINING);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.training_fragment, container, false);
        mViewPager2 = root.findViewById(R.id.vp);
        mViewPager2.setAdapter(mAdapter);
        mTextViewProgress = root.findViewById(R.id.tv_progress);
        mButtonRemember = root.findViewById(R.id.btn_remember);
        mButtonRepeat = root.findViewById(R.id.btn_repeat);

        return root;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        setupViewModel();
    }

    private void setupViewModel() {
        if (getArguments() != null) {
            mViewModel = ViewModelProviders
                    .of(this, new TrainingViewModelFactory(getArguments().getInt(DECK_ID)))
                    .get(TrainingViewModel.class);
        }
        mViewModel.getCardsList().observe(this, new Observer<List<Card>>() {
            @Override
            public void onChanged(List<Card> cards) {
                mAdapter.setCards(cards);
            }
        });
        mViewModel.loadCards();
    }

}

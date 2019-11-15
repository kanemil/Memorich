package com.kanemil.memorich.presentation.view.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

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

import java.util.HashSet;
import java.util.List;

public class TrainingFragment extends Fragment {

    private static final String DECK_ID = "deckId";
    private static final float OFFSET_TO_IMITATE_SCROLL = -500;

    private TrainingViewModel mViewModel;
    private CardsAdapter mAdapter;

    private ViewPager2 mViewPager2;
    private TextView mTextViewProgress;
    private TextView mTextViewCardsSize;
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
        View root = inflater.inflate(R.layout.fragment_training, container, false);
        mViewPager2 = root.findViewById(R.id.vp);
        mViewPager2.setAdapter(mAdapter);
        mTextViewProgress = root.findViewById(R.id.tv_progress);
        mTextViewCardsSize = root.findViewById(R.id.tv_cards_size);
        mButtonRemember = root.findViewById(R.id.btn_remember);
        mButtonRepeat = root.findViewById(R.id.btn_repeat);
        mViewPager2.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageSelected(int position) {
                super.onPageSelected(position);
                if (mViewModel.getAnsweredCards().getValue().contains(position)) {
                    mButtonRemember.setEnabled(false);
                    mButtonRepeat.setEnabled(false);
                } else {
                    mButtonRemember.setEnabled(true);
                    mButtonRepeat.setEnabled(true);
                }
            }
        });
        mViewPager2.setUserInputEnabled(false);
        return root;
    }

    private void initButtonListeners() {
        mButtonRemember.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mViewModel.incrementCorrectAnswers();
                mViewModel.disableButtonAfterButtonPressed(mViewPager2.getCurrentItem());
                finishOrContinueTraining();
            }
        });
        mButtonRepeat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mViewModel.disableButtonAfterButtonPressed(mViewPager2.getCurrentItem());
                finishOrContinueTraining();
            }
        });
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        setupViewModel();
        initButtonListeners();
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
                mTextViewCardsSize.setText(String.format(getResources().getString(R.string.cards_size), cards.size()));
            }
        });
        mViewModel.loadCards();
        mViewModel.getCorrectAnswers().observe(this, new Observer<Integer>() {
            @Override
            public void onChanged(Integer integer) {
                mTextViewProgress.setText(String.valueOf(integer));
            }
        });
        mViewModel.getAnsweredCards().observe(this, new Observer<HashSet<Integer>>() {
            @Override
            public void onChanged(HashSet<Integer> answeredCards) {
                // Disable button if already pressed to avoid double counting.
                if (answeredCards.contains(mViewPager2.getCurrentItem())) {
                    mButtonRemember.setEnabled(false);
                    mButtonRepeat.setEnabled(false);
                }
            }
        });
    }

    /**
     * Shows result if it is the last card or swipes to the next card.
     */
    private void finishOrContinueTraining() {
        if (mViewPager2.getCurrentItem() == mAdapter.getItemCount() - 1) {
            // Механика конца тренировки будет модифицирована позже, поэтому здесь пока просто тост.
            Toast.makeText(requireContext(),
                    "Your result is: " + mViewModel.getCorrectAnswers().getValue() * 1f / mAdapter.getItemCount() * 100 + "%",
                    Toast.LENGTH_SHORT).show();
        } else {
            swipeToNextCard();
        }
    }

    /**
     * Swipes to the next card.
     */
    private void swipeToNextCard() {
        mViewPager2.beginFakeDrag();
        mViewPager2.fakeDragBy(OFFSET_TO_IMITATE_SCROLL);
        mViewPager2.endFakeDrag();
    }
}

package com.kanemil.memorich.presentation.view.fragments;

import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.SeekBar;
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
import com.kanemil.memorich.presentation.viewmodel.CustomViewModelFactory;
import com.kanemil.memorich.presentation.viewmodel.TrainingViewModel;

import java.util.List;
import java.util.TreeSet;

public class TrainingFragment extends Fragment {

    private static final String DECK_ID = "deckId";

    private TrainingViewModel mViewModel;
    private CardsAdapter mAdapter;

    private SeekBar mSeekBar;
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
        mSeekBar = root.findViewById(R.id.sb_progress);
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
                mViewModel.checkIfCardAlreadyAnswered(position);
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                    mSeekBar.setProgress(position, true);
                } else {
                    mSeekBar.setProgress(position);
                }
            }
        });
        return root;
    }

    private void initButtonListeners() {
        mButtonRemember.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finishOrContinueTraining(true);
            }
        });
        mButtonRepeat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finishOrContinueTraining(false);
            }
        });
    }

    private void initSeekBarListener() {
        mSeekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                mViewPager2.setCurrentItem(i, true);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        setupViewModel();
        initButtonListeners();
        initSeekBarListener();
    }

    private void setupViewModel() {
        if (getArguments() != null) {
            mViewModel = ViewModelProviders
                    .of(this, new CustomViewModelFactory(getArguments().getInt(DECK_ID)))
                    .get(TrainingViewModel.class);
        }
        mViewModel.getCardsList().observe(this, new Observer<List<Card>>() {
            @Override
            public void onChanged(List<Card> cards) {
                mAdapter.setCards(cards);
                mTextViewCardsSize.setText(String.format(getResources().getString(R.string.cards_size), cards.size()));
                mSeekBar.setMax(cards.size() - 1);
            }
        });
        mViewModel.loadCards();
        mViewModel.getCorrectAnswers().observe(this, new Observer<Integer>() {
            @Override
            public void onChanged(Integer integer) {
                // update number of correct answers
                mTextViewProgress.setText(String.valueOf(integer));
            }
        });
        mViewModel.getAnsweredCardsPositions().observe(this, new Observer<TreeSet<Integer>>() {
            @Override
            public void onChanged(TreeSet<Integer> answeredCards) {
                // Disable button if already pressed on current card
                if (answeredCards.contains(mViewPager2.getCurrentItem())) {
                    toggleButtons(false);
                }
            }
        });
        mViewModel.getIsCardAlreadyAnswered().observe(this, new Observer<Boolean>() {
            @Override
            public void onChanged(Boolean b) {
                // Disable buttons on previously answered cards
                toggleButtons(!b);
            }
        });
        mViewModel.getTrainingScore().observe(this, new Observer<String>() {
            @Override
            public void onChanged(String s) {
                // Finish training with a result toast
                Toast.makeText(requireContext(), s, Toast.LENGTH_LONG).show();
            }
        });
        mViewModel.getFirstUnansweredCard().observe(this, new Observer<Integer>() {
            @Override
            public void onChanged(Integer position) {
                mViewPager2.setCurrentItem(position);
            }
        });
    }

    /**
     * Shows result if it is the last card or swipes to the next card.
     */
    private void finishOrContinueTraining(boolean incrementOrNot) {
        mViewModel.markCardAsAnswered(mViewPager2.getCurrentItem());
        mViewModel.checkIfAllCardsAreAnswered();
        mViewModel.incrementCorrectAnswersNumber(incrementOrNot);
    }

    private void toggleButtons(boolean b) {
        mButtonRemember.setEnabled(b);
        mButtonRepeat.setEnabled(b);
    }
}

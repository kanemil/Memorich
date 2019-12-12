package com.kanemil.memorich.presentation.view.fragments;

import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.SeekBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.viewpager2.widget.ViewPager2;

import com.google.android.material.snackbar.Snackbar;
import com.kanemil.memorich.R;
import com.kanemil.memorich.base.BaseFragment;
import com.kanemil.memorich.data.db.entity.Card;
import com.kanemil.memorich.presentation.view.fragments.adapters.CardsAdapter;
import com.kanemil.memorich.presentation.viewmodel.TrainingViewModel;
import com.kanemil.memorich.presentation.viewmodel.ViewModelProviderFactory;

import java.util.List;
import java.util.TreeSet;

import javax.inject.Inject;

public class TrainingFragment extends BaseFragment {

    private static final String DECK_ID = "deckId";

    private TrainingViewModel mViewModel;
    private CardsAdapter mAdapter;

    private SeekBar mSeekBar;
    private ViewPager2 mViewPager2;
    private TextView mTextViewProgress;
    private TextView mTextViewCardsSize;
    private Button mButtonRemember;
    private Button mButtonRepeat;
    private View mResultScreen;

    private long mDeckId;

    @Inject
    ViewModelProviderFactory mViewModelProviderFactory;

    public static TrainingFragment newInstance(long deckId) {
        Bundle args = new Bundle();
        args.putLong(DECK_ID, deckId);
        TrainingFragment fragment = new TrainingFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setupAdapter();
        extractArguments();
    }

    private void setupAdapter() {
        mAdapter = new CardsAdapter(CardsAdapter.DisplayMode.TRAINING);
    }

    private void extractArguments() throws RuntimeException {
        if (getArguments() != null) {
            mDeckId = getArguments().getLong(DECK_ID);
        } else {
            throw new RuntimeException("TrainingFragment did not receive arguments");
        }
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_training, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        setupViews(view);
    }

    private void setupViews(View root) {
        mSeekBar = root.findViewById(R.id.sb_progress);
        mTextViewProgress = root.findViewById(R.id.tv_progress);
        mTextViewCardsSize = root.findViewById(R.id.tv_cards_size);
        mButtonRemember = root.findViewById(R.id.btn_remember);
        mButtonRepeat = root.findViewById(R.id.btn_restart);
        mResultScreen = root.findViewById(R.id.result_screen);
        mViewPager2 = root.findViewById(R.id.vp);
        setupViewPager();
    }

    private void setupViewPager() {
        mViewPager2.setAdapter(mAdapter);
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
    }

    private void setupButtonListeners() {
        mButtonRemember.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mViewModel.finishOrContinueTraining(true, mViewPager2.getCurrentItem());
            }
        });
        mButtonRepeat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mViewModel.finishOrContinueTraining(false, mViewPager2.getCurrentItem());
            }
        });
    }

    private void setupSeekBarListener() {
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
        setupButtonListeners();
        setupSeekBarListener();
    }

    private void setupViewModel() {

        mViewModel = ViewModelProviders
                .of(this, mViewModelProviderFactory)
                .get(TrainingViewModel.class);

        mViewModel.setDeckId(mDeckId);

        subscribeObservers();
    }

    private void subscribeObservers() {
        mViewModel.getCardsList().observe(getViewLifecycleOwner(), new Observer<List<Card>>() {
            @Override
            public void onChanged(List<Card> cards) {
                if (cards.size() == 0) {
                    showEmptyDeckScreenLayout();
                } else {
                    mAdapter.setCards(cards);
                    mTextViewCardsSize.setText(String.format(getResources().getString(R.string.cards_size), cards.size()));
                    mSeekBar.setMax(cards.size() - 1);
                }
            }
        });

        mViewModel.getCorrectAnswers().observe(getViewLifecycleOwner(), new Observer<Integer>() {
            @Override
            public void onChanged(Integer integer) {
                // update number of correct answers
                mTextViewProgress.setText(String.valueOf(integer));
            }
        });

        mViewModel.getAnsweredCardsPositions().observe(getViewLifecycleOwner(), new Observer<TreeSet<Integer>>() {
            @Override
            public void onChanged(TreeSet<Integer> answeredCards) {
                // Disable button if already pressed on current card
                if (answeredCards.contains(mViewPager2.getCurrentItem())) {
                    toggleButtons(false);
                }
            }
        });

        mViewModel.getIsCardAlreadyAnswered().observe(getViewLifecycleOwner(), new Observer<Boolean>() {
            @Override
            public void onChanged(Boolean b) {
                // Disable buttons on previously answered cards
                toggleButtons(!b);
            }
        });

        mViewModel.getTrainingScore().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(String s) {
                // Finish training with a result screen
                showResultScreen(s);
            }
        });

        mViewModel.getFirstUnansweredCard().observe(getViewLifecycleOwner(), new Observer<Integer>() {
            @Override
            public void onChanged(Integer position) {
                mViewPager2.setCurrentItem(position);
            }
        });
    }

    private void showEmptyDeckScreenLayout() {
        Snackbar.make(getView(), getString(R.string.add_card_pls), Snackbar.LENGTH_SHORT).show();
        mTextViewProgress.setVisibility(View.INVISIBLE);
        mSeekBar.setMax(0);
        mSeekBar.setEnabled(false);
        toggleButtons(false);
    }

    private void showResultScreen(String s) {
        final TextView v = mResultScreen.findViewById(R.id.tv_result);
        v.setText(s);
        mResultScreen.setVisibility(View.VISIBLE);
        mResultScreen.findViewById(R.id.btn_finish).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finishTraining();
            }
        });
        mResultScreen.findViewById(R.id.btn_restart).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                restartTraining();
            }
        });
        mResultScreen.findViewById(R.id.btn_observe).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mResultScreen.setVisibility(View.GONE);
            }
        });
    }

    private void finishTraining() {
        requireFragmentManager().popBackStack();
    }

    private void restartTraining() {
        final FragmentManager fragmentManager = requireFragmentManager();
        fragmentManager.popBackStack();
        fragmentManager.beginTransaction()
                .setCustomAnimations(android.R.anim.slide_in_left, android.R.anim.slide_out_right, android.R.animator.fade_in, android.R.animator.fade_out)
                .replace(R.id.root_view, TrainingFragment.newInstance(mDeckId), null)
                .addToBackStack(null)
                .commit();
    }

    private void toggleButtons(boolean b) {
        mButtonRemember.setEnabled(b);
        mButtonRepeat.setEnabled(b);
    }
}

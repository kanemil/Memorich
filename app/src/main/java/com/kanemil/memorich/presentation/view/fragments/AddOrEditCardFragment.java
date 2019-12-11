package com.kanemil.memorich.presentation.view.fragments;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.ViewModelProviders;

import com.kanemil.memorich.R;
import com.kanemil.memorich.base.BaseFragment;
import com.kanemil.memorich.data.db.entity.Card;
import com.kanemil.memorich.presentation.viewmodel.CardsViewModel;
import com.kanemil.memorich.presentation.viewmodel.ViewModelProviderFactory;

import javax.inject.Inject;

public class AddOrEditCardFragment extends BaseFragment {

    private static final String TAG = "AddOrEditCardFragment";

    private static final String DECK_ID = "deckId";
    private static final String CARD_ORDER_ID = "cardOrderId";
    private static final String CARD_ID = "cardId";
    private static final String CARD_FRONT = "cardFront";
    private static final String CARD_BACK = "cardBack";

    private CardsViewModel mViewModel;
    private DISPLAY_MODE mMode;

    private EditText mEditTextFront;
    private EditText mEditTextBack;
    private Button mButton;

    @Inject
    ViewModelProviderFactory mViewModelProviderFactory;

    private long mDeckId;
    private long mCardOrderId;

    /**
     * Constructor for adding a new card
     *
     * @param deckId
     * @return
     */
    public static AddOrEditCardFragment newInstance(long deckId, long cardOrderId) {
        Bundle args = new Bundle();
        args.putLong(DECK_ID, deckId);
        args.putLong(CARD_ORDER_ID, cardOrderId);
        AddOrEditCardFragment fragment = new AddOrEditCardFragment();
        fragment.setArguments(args);
        return fragment;
    }

    /**
     * Constructor for editing a card
     *
     * @param deckId
     * @param cardId card's id
     * @return
     */
    public static AddOrEditCardFragment newInstance(long deckId, long cardId, String front, String back, long cardOrderId) {
        Bundle args = new Bundle();
        args.putLong(DECK_ID, deckId);
        args.putLong(CARD_ID, cardId);
        args.putLong(CARD_ORDER_ID, cardOrderId);
        args.putString(CARD_FRONT, front);
        args.putString(CARD_BACK, back);
        AddOrEditCardFragment fragment = new AddOrEditCardFragment();
        fragment.setArguments(args);
        return fragment;
    }

    // True card's id cannot be equal to -1, so it is a default value of getLong() method.
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            final long cardId = getArguments().getLong(CARD_ID, -1);
            if (cardId == -1) {
                mMode = DISPLAY_MODE.ADD_CARD;
            } else {
                mMode = DISPLAY_MODE.EDIT_CARD;
            }

            mDeckId = getArguments().getLong(DECK_ID);
            mCardOrderId = getArguments().getLong(CARD_ORDER_ID);
        }
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.add_card_fragment, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        setupViews(view);
    }

    private void setupViews(View root) {
        mEditTextFront = root.findViewById(R.id.et_card_front);
        mEditTextBack = root.findViewById(R.id.et_card_back);
        if (mMode == DISPLAY_MODE.EDIT_CARD) {
            mEditTextFront.setText(getArguments() != null ? getArguments().getString(CARD_FRONT) : null);
            mEditTextBack.setText(getArguments() != null ? getArguments().getString(CARD_BACK) : null);
        }
        mButton = root.findViewById(R.id.btn_add);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        setupViewModel();
        setupButton();
        if (mMode == DISPLAY_MODE.ADD_CARD) {
            mButton.setEnabled(false);
        }
        mEditTextFront.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                mButton.setEnabled(TextUtils.isGraphic(charSequence));
            }

            @Override
            public void afterTextChanged(Editable editable) {
            }
        });
    }

    private void setupViewModel() {
        mViewModel = ViewModelProviders
                .of(requireActivity(), mViewModelProviderFactory)
                .get(CardsViewModel.class);
    }

    private void setupButton() {
        switch (mMode) {
            case ADD_CARD:
                mButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        final Card card = createCardWithEnteredData();
                        card.setOrderId(mCardOrderId);
                        mViewModel.addCard(card);
                        requireFragmentManager().popBackStack();
                    }
                });
                break;

            case EDIT_CARD:
                mButton.setText(R.string.save);
                mButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        if (getArguments() != null) {
                            final Card card = createCardWithEnteredData();
                            card.setId(getArguments().getLong(CARD_ID));
                            card.setOrderId(mCardOrderId);
                            mViewModel.updateCard(card);
                            requireFragmentManager().popBackStack();
                        }
                    }
                });
                break;
        }
    }

    private Card createCardWithEnteredData() {
        return new Card(
                mEditTextFront.getText().toString(),
                mEditTextBack.getText().toString(),
                mDeckId);
    }

    public enum DISPLAY_MODE {
        ADD_CARD, EDIT_CARD
    }
}

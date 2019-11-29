package com.kanemil.memorich.presentation.view.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;

import com.kanemil.memorich.R;
import com.kanemil.memorich.data.db.entity.Card;
import com.kanemil.memorich.presentation.viewmodel.CardsViewModel;
import com.kanemil.memorich.presentation.viewmodel.CustomViewModelFactory;

// TODO: 22.11.19 ТУТ КОСТЫЛЬ НА КОСТЫЛЕ, НАДО НОРМАЛЬНО ПЕРЕДЕЛАТЬ ЧЕРЕЗ ВЬЮМОДЕЛЬ!!!
public class AddOrEditCardFragment extends Fragment {

    private static final String DECK_ID = "deckId";
    private static final String CARD_ORDER_ID = "cardOrderId";
    private static final String CARD_ID = "cardId";
    private static final String CARD_FRONT = "cardFront";
    private static final String CARD_BACK = "cardBack";

    private CardsViewModel mViewModel;
    private DISPLAY_MODE mMode;

    private EditText mEditTextFront;
    private EditText mEditTextBack;
    private Button mButtonAdd;

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
    public static AddOrEditCardFragment newInstance(long deckId, long cardId, String front, String back) {
        Bundle args = new Bundle();
        args.putLong(DECK_ID, deckId);
        args.putLong(CARD_ID, cardId);
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
        }
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.add_card_fragment, container, false);
        mEditTextFront = root.findViewById(R.id.et_card_front);
        mEditTextBack = root.findViewById(R.id.et_card_back);
        if (mMode == DISPLAY_MODE.EDIT_CARD) {
            mEditTextFront.setText(getArguments() != null ? getArguments().getString(CARD_FRONT) : null);
            mEditTextBack.setText(getArguments() != null ? getArguments().getString(CARD_BACK) : null);
        }
        mButtonAdd = root.findViewById(R.id.btn_add);
        return root;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        setupViewModel();
    }

    private void setupViewModel() {
        if (getArguments() != null) {
            final long deckId = getArguments().getLong(DECK_ID);
            mViewModel = ViewModelProviders
                    .of(this, new CustomViewModelFactory(deckId))
                    .get(CardsViewModel.class);
            if (mMode == DISPLAY_MODE.ADD_CARD) {
                mButtonAdd.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        final Card card = new Card(
                                mEditTextFront.getText().toString(),
                                mEditTextBack.getText().toString(),
                                deckId);
                        card.setOrderId(getArguments() != null ? getArguments().getLong(CARD_ORDER_ID) : 0);
                        mViewModel.addCard(card);
                        Toast.makeText(requireContext(), "Added!", Toast.LENGTH_SHORT).show();
                        if (getFragmentManager() != null) {
                            getFragmentManager().popBackStack();
                        }
                    }
                });
            } else {
                mButtonAdd.setText(R.string.save);
                mButtonAdd.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        if (getArguments() != null) {
                            Card card = new Card(mEditTextFront.getText().toString(),
                                    mEditTextBack.getText().toString(),
                                    deckId);
                            card.setId(getArguments().getLong(CARD_ID));
                            mViewModel.updateCard(card);
                            if (getFragmentManager() != null) {
                                getFragmentManager().popBackStack();
                            }
                        }
                    }
                });
            }

        }
    }

    public enum DISPLAY_MODE {
        ADD_CARD, EDIT_CARD
    }

}

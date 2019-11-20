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

public class AddCardFragment extends Fragment {

    private static final String DECK_ID = "deckId";

    private CardsViewModel mViewModel;

    private EditText mEditTextFront;
    private EditText mEditTextBack;
    private Button mButtonAdd;

    public static AddCardFragment newInstance(long deckId) {
        Bundle args = new Bundle();
        args.putLong(DECK_ID, deckId);
        AddCardFragment fragment = new AddCardFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.add_card_fragment, container, false);
        mEditTextFront = root.findViewById(R.id.et_card_front);
        mEditTextBack = root.findViewById(R.id.et_card_back);
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
            mButtonAdd.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    mViewModel.addCard(new Card(
                            mEditTextFront.getText().toString(),
                            mEditTextBack.getText().toString(),
                            deckId));
                    Toast.makeText(requireContext(), "Added!", Toast.LENGTH_SHORT).show();
                }
            });
        }
    }

}

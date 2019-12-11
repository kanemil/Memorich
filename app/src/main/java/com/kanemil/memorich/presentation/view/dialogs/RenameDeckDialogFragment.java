package com.kanemil.memorich.presentation.view.dialogs;

import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.lifecycle.ViewModelProviders;

import com.kanemil.memorich.R;
import com.kanemil.memorich.base.BaseDialogFragment;
import com.kanemil.memorich.data.db.entity.Deck;
import com.kanemil.memorich.presentation.viewmodel.DecksViewModel;
import com.kanemil.memorich.presentation.viewmodel.ViewModelProviderFactory;

import javax.inject.Inject;

/**
 * Adds new deck to list.
 */
public class RenameDeckDialogFragment extends BaseDialogFragment {

    private static final String DECK_ID_TO_RENAME = "deck_id";
    private static final String DECK_NAME = "deck_name";
    private AlertDialog mAlertDialog;

    private EditText mEditTextDeckName;

    @Inject
    ViewModelProviderFactory mViewModelProviderFactory;
    private DecksViewModel mViewModel;

    public static RenameDeckDialogFragment newInstance(Deck deck) {
        Bundle args = new Bundle();
        args.putLong(DECK_ID_TO_RENAME, deck.getId());
        args.putString(DECK_NAME, deck.getName());
        RenameDeckDialogFragment fragment = new RenameDeckDialogFragment();
        fragment.setArguments(args);
        return fragment;
    }

    private void setupViewModel() {
        mViewModel = ViewModelProviders.of(requireActivity(), mViewModelProviderFactory).get(DecksViewModel.class);
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        setupViewModel();

        AlertDialog.Builder builder = new AlertDialog.Builder(requireActivity());
        LayoutInflater inflater = requireActivity().getLayoutInflater();
        View root = inflater.inflate(R.layout.dialog_add_deck, null);
        mEditTextDeckName = root.findViewById(R.id.et_deck_name);
        if (getArguments() != null) {
            final String deckName = getArguments().getString(DECK_NAME);
            mEditTextDeckName.setText(deckName);
            mEditTextDeckName.setSelection(deckName != null ? deckName.length() : 0);
        }
        builder.setView(root);

        builder.setPositiveButton(getString(R.string.rename), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                if (getArguments() != null) {
                    mViewModel.renameDeck(getArguments().getLong(DECK_ID_TO_RENAME),
                            mEditTextDeckName.getText().toString());
                }
            }
        }).setTitle(R.string.set_new_name_for_deck);

        mAlertDialog = builder.create();
        return mAlertDialog;
    }

    @Override
    public void onStart() {
        super.onStart();
        mEditTextDeckName.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                mAlertDialog.getButton(DialogInterface.BUTTON_POSITIVE).setEnabled(TextUtils.isGraphic(charSequence));
            }

            @Override
            public void afterTextChanged(Editable editable) {
            }
        });
    }
}

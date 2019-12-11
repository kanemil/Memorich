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
import com.kanemil.memorich.presentation.viewmodel.DecksViewModel;
import com.kanemil.memorich.presentation.viewmodel.ViewModelProviderFactory;

import javax.inject.Inject;

/**
 * Adds new deck to list.
 */
public class AddDeckDialogFragment extends BaseDialogFragment {

    private AlertDialog mAlertDialog;

    private EditText mEditTextDeckName;

    @Inject
    ViewModelProviderFactory mViewModelProviderFactory;
    private DecksViewModel mViewModel;

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        setupViewModel();

        final AlertDialog.Builder builder = new AlertDialog.Builder(requireActivity());
        LayoutInflater inflater = requireActivity().getLayoutInflater();
        View root = inflater.inflate(R.layout.dialog_add_deck, null);
        mEditTextDeckName = root.findViewById(R.id.et_deck_name);

        builder.setView(root);

        builder.setPositiveButton(R.string.add, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                mViewModel.addDeck(mEditTextDeckName.getText().toString());
            }
        }).setTitle(R.string.set_name_for_deck);

        mAlertDialog = builder.create();
        return mAlertDialog;
    }

    private void setupViewModel() {
        mViewModel = ViewModelProviders.of(requireActivity(), mViewModelProviderFactory).get(DecksViewModel.class);
    }

    @Override
    public void onStart() {
        super.onStart();
        mAlertDialog.getButton(DialogInterface.BUTTON_POSITIVE).setEnabled(false);
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

package com.kanemil.memorich.presentation.view.dialogs;

import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.FragmentActivity;

import com.kanemil.memorich.R;
import com.kanemil.memorich.presentation.view.activities.OnDeckAddedDialogClick;

/**
 * Adds new deck to list.
 */
public class AddDeckDialogFragment extends DialogFragment {

    private EditText mEditTextDeckName;

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(requireActivity());
        LayoutInflater inflater = requireActivity().getLayoutInflater();
        View root = inflater.inflate(R.layout.dialog_add_deck, null);
        mEditTextDeckName = root.findViewById(R.id.et_deck_name);
        builder.setView(root);

        builder
                .setPositiveButton(R.string.add, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        final FragmentActivity activity = requireActivity();
                        if (activity instanceof OnDeckAddedDialogClick) {
                            ((OnDeckAddedDialogClick) activity)
                                    .onDeckAddedDialogClick(mEditTextDeckName.getText().toString());
                        }
                    }
                })
                .setTitle(R.string.set_name_for_deck);

        return builder.create();
    }
}

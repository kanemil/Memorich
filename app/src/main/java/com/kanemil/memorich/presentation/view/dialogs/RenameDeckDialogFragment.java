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
import com.kanemil.memorich.data.db.entity.Deck;

/**
 * Adds new deck to list.
 */
public class RenameDeckDialogFragment extends DialogFragment {

    private static final String DECK_ID_TO_RENAME = "deck_id";

    private EditText mEditTextDeckName;

    public static RenameDeckDialogFragment newInstance(Deck deck) {
        Bundle args = new Bundle();
        args.putLong(DECK_ID_TO_RENAME, deck.getId());
        RenameDeckDialogFragment fragment = new RenameDeckDialogFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(requireActivity());
        LayoutInflater inflater = requireActivity().getLayoutInflater();
        View root = inflater.inflate(R.layout.dialog_add_deck, null);
        mEditTextDeckName = root.findViewById(R.id.et_deck_name);
        builder.setView(root);


        builder.setPositiveButton(getString(R.string.rename), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                final FragmentActivity activity = requireActivity();
                if (activity instanceof OnDeckAddedDialogClickListener && getArguments() != null) {
                    ((OnDeckRenameDialogClickListener) activity)
                            .onDeckRenameDialogClick(getArguments().getLong(DECK_ID_TO_RENAME), mEditTextDeckName.getText().toString());
                }
            }
        }).setTitle(R.string.set_name_for_deck);

        return builder.create();
    }
}

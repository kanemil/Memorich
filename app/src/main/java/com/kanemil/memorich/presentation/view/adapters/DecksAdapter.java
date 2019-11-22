package com.kanemil.memorich.presentation.view.adapters;

import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.PopupMenu;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.kanemil.memorich.R;
import com.kanemil.memorich.data.db.entity.Deck;

import java.util.ArrayList;
import java.util.List;

public class DecksAdapter extends RecyclerView.Adapter<DecksAdapter.DeckHolder> implements
        PopupMenu.OnMenuItemClickListener {

    private static final String TAG = "DecksAdapter";

    private List<Deck> mDecks = new ArrayList<>();
    private Deck mSelectedDeck;

    private DecksAdapterActionsListener mDecksAdapterActionsListener;

    public DecksAdapter(DecksAdapterActionsListener decksAdapterActionsListener) {
        mDecksAdapterActionsListener = decksAdapterActionsListener;
    }

    public void setDecks(List<Deck> decks) {
        mDecks = decks;
        notifyDataSetChanged();
        Log.d(TAG, "deckSize " + getItemCount());
    }

    @NonNull
    @Override
    public DeckHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_view_deck, parent, false);
        return new DeckHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull final DeckHolder holder, final int position) {
        final Deck deck = mDecks.get(position);
        holder.bind(deck);
        holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                mSelectedDeck = deck;
                PopupMenu popupMenu = new PopupMenu(view.getContext(), holder.mSpace, Gravity.RIGHT);
                popupMenu.inflate(R.menu.menu_decks);
                popupMenu.setOnMenuItemClickListener(DecksAdapter.this);
                popupMenu.show();
                return true;
            }
        });
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mDecksAdapterActionsListener.onDeckClicked(deck.getId());
            }
        });
    }

    @Override
    public int getItemCount() {
        return mDecks.size();
    }

    @Override
    public boolean onMenuItemClick(MenuItem menuItem) {
        switch (menuItem.getItemId()) {
            case R.id.menu_deck_rename:
                mDecksAdapterActionsListener.onDeckMenuRenameClicked(mSelectedDeck.getId());
                return true;
            case R.id.menu_deck_edit:
                mDecksAdapterActionsListener.onDeckMenuEditClicked(mSelectedDeck.getId());
                return true;
            case R.id.menu_deck_delete:

                return true;
            default:
        }
        return false;
    }

    static class DeckHolder extends RecyclerView.ViewHolder {
        private TextView mTextViewDeckName;
        private View mSpace;
        private TextView mTextViewDeckSize;

        DeckHolder(@NonNull View itemView) {
            super(itemView);
            mTextViewDeckName = itemView.findViewById(R.id.tv_deck_name);
            mSpace = itemView.findViewById(R.id.space_deck_item_view);
            mTextViewDeckSize = itemView.findViewById(R.id.tv_deck_size);
        }

        private void bind(Deck deck) {
            mTextViewDeckName.setText(deck.getName());
            // TODO разобраться с отображением размера колоды
//            Context ctx = mTextViewDeckSize.getContext();
//            mTextViewDeckSize.setText(String.format(ctx.getString(R.string.size_formatter),
//                    ctx.getString(R.string.size), deck.getCardList().size()));
        }
    }
}

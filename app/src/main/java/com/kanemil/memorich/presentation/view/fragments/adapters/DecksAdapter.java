package com.kanemil.memorich.presentation.view.fragments.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.kanemil.memorich.R;
import com.kanemil.memorich.data.db.entity.Deck;

import java.util.ArrayList;
import java.util.List;

public class DecksAdapter extends RecyclerView.Adapter<DecksAdapter.DeckHolder> {

    private static final String TAG = "DecksAdapter";

    private List<Deck> mDecks = new ArrayList<>();
    private int mSelectedDeckPosition = -1;
    private Deck mCurrentDeck;

    private DecksAdapterActionsListener mDecksAdapterActionsListener;

    public DecksAdapter(DecksAdapterActionsListener decksAdapterActionsListener) {
        mDecksAdapterActionsListener = decksAdapterActionsListener;
    }

    public void setDecks(List<Deck> decks) {
        mDecks = decks;
        clearDeckSelection();
        notifyDataSetChanged();
    }

    public Deck getCurrentDeck() {
        return mCurrentDeck;
    }

    private void clearDeckSelection() {
        mSelectedDeckPosition = -1;
        mDecksAdapterActionsListener.showBottomBar(false);
        notifyDataSetChanged();
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
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                // remove selection if already selected and clicked one more time
                if (position == mSelectedDeckPosition) {
                    clearDeckSelection();
                    holder.itemView.setSelected(false);

                } else { // select deck and highlight row
                    mSelectedDeckPosition = position;
                    mCurrentDeck = mDecks.get(mSelectedDeckPosition);
                    mDecksAdapterActionsListener.showBottomBar(true);
                }

                notifyDataSetChanged();
            }
        });
        holder.itemView.setSelected(position == mSelectedDeckPosition);
    }

    @Override
    public int getItemCount() {
        return mDecks.size();
    }

    static class DeckHolder extends RecyclerView.ViewHolder {
        private TextView mTextViewDeckName;

        DeckHolder(@NonNull View itemView) {
            super(itemView);
            mTextViewDeckName = itemView.findViewById(R.id.tv_deck_name);
        }

        private void bind(Deck deck) {
            mTextViewDeckName.setText(deck.getName());
        }
    }
}

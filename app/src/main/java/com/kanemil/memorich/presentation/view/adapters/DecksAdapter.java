package com.kanemil.memorich.presentation.view.adapters;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.kanemil.memorich.R;
import com.kanemil.memorich.data.model.Deck;
import com.kanemil.memorich.presentation.view.activities.OnDeckClickedListener;

import java.util.ArrayList;
import java.util.List;

public class DecksAdapter extends RecyclerView.Adapter<DecksAdapter.DeckHolder> {

    private static final String TAG = "DecksAdapter";

    private List<Deck> mDecks = new ArrayList<>();

    private OnDeckClickedListener mOnDeckClickedListener;

    public DecksAdapter(OnDeckClickedListener onDeckClickedListener) {
        mOnDeckClickedListener = onDeckClickedListener;
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
    public void onBindViewHolder(@NonNull DeckHolder holder, final int position) {
        holder.bind(mDecks.get(position));
        holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                mOnDeckClickedListener.onDeckLongClicked(position);
                return true;
            }
        });
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mOnDeckClickedListener.onDeckClicked(position);
            }
        });
    }

    @Override
    public int getItemCount() {
        return mDecks.size();
    }

    static class DeckHolder extends RecyclerView.ViewHolder {
        private TextView mTextViewDeckName;
        private TextView mTextViewDeckSize;

        DeckHolder(@NonNull View itemView) {
            super(itemView);
            mTextViewDeckName = itemView.findViewById(R.id.tv_deck_name);
            mTextViewDeckSize = itemView.findViewById(R.id.tv_deck_size);
        }

        private void bind(Deck deck) {
            mTextViewDeckName.setText(deck.getName());
            Context ctx = mTextViewDeckSize.getContext();
            mTextViewDeckSize.setText(String.format(ctx.getString(R.string.size_formatter),
                    ctx.getString(R.string.size), deck.getCardList().size()));
        }
    }
}

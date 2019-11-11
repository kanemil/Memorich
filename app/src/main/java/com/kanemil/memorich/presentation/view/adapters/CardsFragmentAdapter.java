package com.kanemil.memorich.presentation.view.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.kanemil.memorich.R;
import com.kanemil.memorich.data.model.Card;

import java.util.List;

public class CardsFragmentAdapter extends RecyclerView.Adapter<CardsFragmentAdapter.CardHolder> {

    private List<Card> mCards;

    public void setCards(List<Card> cards) {
        mCards = cards;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public CardHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View root = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_view_card, parent, false);
        return new CardHolder(root);
    }

    @Override
    public void onBindViewHolder(@NonNull CardHolder holder, int position) {
        holder.bind(mCards.get(position));
    }

    @Override
    public int getItemCount() {
        return mCards.size();
    }

    static class CardHolder extends RecyclerView.ViewHolder {
        private TextView mCardFront;
        private TextView mCardBack;

        CardHolder(@NonNull View itemView) {
            super(itemView);
            mCardFront = itemView.findViewById(R.id.tv_card_front);
            mCardBack = itemView.findViewById(R.id.tv_card_back);
        }

        void bind(Card card) {
            mCardFront.setText(card.getFront());
            mCardBack.setText(card.getBack());
        }
    }
}

package com.kanemil.memorich.presentation.view.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.kanemil.memorich.R;
import com.kanemil.memorich.data.model.Card;

import java.util.ArrayList;
import java.util.List;

public class CardsAdapter extends RecyclerView.Adapter<CardsAdapter.CardHolder> {

    private List<Card> mCards = new ArrayList<>();

    /**
     * DisplayMode is needed because ViewPager2 requires root view of page to be match_parent
     * both in height and width
     */
    private DisplayMode mDisplayMode;

    public CardsAdapter(DisplayMode displayMode) {
        mDisplayMode = displayMode;
    }

    public void setCards(List<Card> cards) {
        mCards = cards;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public CardHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        int cardLayout;
        switch (mDisplayMode) {
            case GRID:
                cardLayout = R.layout.item_card;
                break;
            case PAGE:
            default:
                cardLayout = R.layout.item_card_page;
                break;
        }
        View root = LayoutInflater.from(parent.getContext())
                .inflate(cardLayout, parent, false);
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

    public enum DisplayMode {
        GRID, PAGE
    }
}

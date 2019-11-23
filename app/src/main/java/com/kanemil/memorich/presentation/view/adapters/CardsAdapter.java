package com.kanemil.memorich.presentation.view.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.kanemil.memorich.R;
import com.kanemil.memorich.data.db.entity.Card;
import com.kanemil.memorich.presentation.view.adapters.contracts.CardsAdapterActionsListener;
import com.kanemil.memorich.presentation.view.adapters.drag.ItemTouchHelperAdapter;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

// TODO записать карты в бд в новом порядке после drag-n-drop'а

public class CardsAdapter extends RecyclerView.Adapter<CardsAdapter.CardHolder>
        implements ItemTouchHelperAdapter {

    private List<Card> mCards = new ArrayList<>();

    /**
     * DisplayMode is needed because ViewPager2 requires root view of page to be match_parent
     * both in height and width
     */
    private DisplayMode mDisplayMode;
    private CardsAdapterActionsListener mCardsAdapterActionsListener;

    public CardsAdapter(DisplayMode displayMode) {
        mDisplayMode = displayMode;
    }

    /**
     * Fragment implements it, then tells activity to call edit card screen
     *
     * @param cardsAdapterActionsListener
     */
    public void setCardsAdapterActionsListener(CardsAdapterActionsListener cardsAdapterActionsListener) {
        mCardsAdapterActionsListener = cardsAdapterActionsListener;
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
            case EDIT:
                cardLayout = R.layout.item_card_small;
                break;
            case TRAINING:
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
        final Card card = mCards.get(position);
        holder.bind(card);
        if (mDisplayMode == DisplayMode.EDIT) {
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    mCardsAdapterActionsListener.editCard(card);
                }
            });
        }
    }

    @Override
    public int getItemCount() {
        return mCards.size();
    }

    /**
     * Drag-n-drop mechanics
     *
     * @param fromPosition
     * @param toPosition
     * @return
     */
    @Override
    public boolean onItemMove(int fromPosition, int toPosition) {
        if (fromPosition < toPosition) {
            for (int i = fromPosition; i < toPosition; i++) {
                Collections.swap(mCards, i, i + 1);
                swapOrderId(i, i + 1);
            }
        } else {
            for (int i = fromPosition; i > toPosition; i--) {
                Collections.swap(mCards, i, i - 1);
                swapOrderId(i, i - 1);
            }
        }
        notifyItemMoved(fromPosition, toPosition);
        // TODO: 23.11.19 анимация получилась дерганная, поэтому надо сделать режим редактирования и сохранять транзакцией!
        mCardsAdapterActionsListener.updateCardsOrder(mCards);
        return true;
    }

    private void swapOrderId(int i, int j) {
        long temp = mCards.get(i).getOrderId();
        mCards.get(i).setOrderId(mCards.get(j).getOrderId());
        mCards.get(j).setOrderId(temp);
    }

    public enum DisplayMode {
        EDIT, TRAINING;
        private boolean mInReorderingState = false;

        public boolean isInReorderingState() {
            return mInReorderingState;
        }

        public void setInReorderingState(boolean inReorderingState) {
            mInReorderingState = inReorderingState;
        }
    }



    class CardHolder extends RecyclerView.ViewHolder {
        private TextView mCardFront;
        private TextView mCardBack;

        CardHolder(@NonNull View itemView) {
            super(itemView);
            mCardFront = itemView.findViewById(R.id.tv_card_front);
            mCardBack = itemView.findViewById(R.id.tv_card_back);
        }

        void bind(final Card card) {
            mCardFront.setText(card.getFront());
            mCardBack.setText(card.getBack());

            if (mDisplayMode == DisplayMode.TRAINING) {
                setUpRevealMechanicsForTrainingMode(card);
            }
        }

        /**
         * Reveals back side of card after click on bottom part of card
         */
        private void setUpRevealMechanicsForTrainingMode(final Card card) {
            itemView.findViewById(R.id.layout_back).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    card.setRevealedInTraining(true);
                    mCardBack.setTextColor(mCardBack.getContext().getResources().getColor(android.R.color.black));
                }
            });

            if (card.isRevealedInTraining()) {
                mCardBack.setTextColor(mCardBack.getContext().getResources().getColor(android.R.color.black));
            } else {
                mCardBack.setTextColor(mCardBack.getContext().getResources().getColor(android.R.color.white));
            }
        }
    }
}

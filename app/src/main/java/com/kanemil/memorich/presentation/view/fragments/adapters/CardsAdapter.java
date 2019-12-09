package com.kanemil.memorich.presentation.view.fragments.adapters;

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
import com.kanemil.memorich.data.db.entity.CardEntity;
import com.kanemil.memorich.presentation.view.fragments.adapters.contracts.CardsAdapterActionsListener;
import com.kanemil.memorich.presentation.view.fragments.adapters.drag.ItemTouchHelperAdapter;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class CardsAdapter extends RecyclerView.Adapter<CardsAdapter.CardHolder>
        implements ItemTouchHelperAdapter, PopupMenu.OnMenuItemClickListener {

    private static final String TAG = "CardsAdapter";

    private List<CardEntity> mCards = new ArrayList<>();
    private CardEntity mCurrentCard;

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

    public List<CardEntity> getCards() {
        return mCards;
    }

    public void setCards(List<CardEntity> cards) {
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
        final CardEntity card = mCards.get(position);
        holder.bind(card);
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
                swapOrderId(i, i + 1);
                Collections.swap(mCards, i, i + 1);
            }
        } else {
            for (int i = fromPosition; i > toPosition; i--) {
                swapOrderId(i, i - 1);
                Collections.swap(mCards, i, i - 1);
            }
        }
        notifyItemMoved(fromPosition, toPosition);
        return true;
    }

    private void swapOrderId(int i, int j) {
        long temp = mCards.get(i).getOrderId();
        mCards.get(i).setOrderId(mCards.get(j).getOrderId());
        mCards.get(j).setOrderId(temp);
    }

    @Override
    public boolean onMenuItemClick(MenuItem menuItem) {
        switch (menuItem.getItemId()) {
            case R.id.menu_edit:
                mCardsAdapterActionsListener.editCard(mCurrentCard);
                return true;
            case R.id.menu_delete:
                mCards.remove(mCurrentCard);
                for (int i = 0; i < mCards.size(); i++) {
                    mCards.get(i).setOrderId(i);
                }
                mCardsAdapterActionsListener.deleteCard(mCurrentCard, mCards);
                return true;
            default:
        }
        return false;
    }

    public enum DisplayMode {
        EDIT, TRAINING;
    }

    class CardHolder extends RecyclerView.ViewHolder {
        private TextView mCardFront;
        private TextView mCardBack;

        CardHolder(@NonNull View itemView) {
            super(itemView);
            mCardFront = itemView.findViewById(R.id.tv_card_front);
            mCardBack = itemView.findViewById(R.id.tv_card_back);
        }

        void bind(final CardEntity card) {
            mCardFront.setText(card.getFront());
            mCardBack.setText(card.getBack());

            if (mDisplayMode == DisplayMode.TRAINING) {
                setUpRevealMechanicsForTrainingMode(card);
            } else if (mDisplayMode == DisplayMode.EDIT) {
                itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        mCurrentCard = card;
                        Log.d(TAG, "cards: " + mCards);
                        Log.d(TAG, "current " + mCurrentCard);
                        Log.d(TAG, "clicked" + mCurrentCard.toString());
                        PopupMenu popupMenu = new PopupMenu(view.getContext(), itemView, Gravity.RIGHT);
                        popupMenu.inflate(R.menu.menu_cards);
                        popupMenu.setOnMenuItemClickListener(CardsAdapter.this);
                        popupMenu.show();
                    }
                });
            }
        }



        /**
         * Reveals back side of card after click on bottom part of card
         */
        private void setUpRevealMechanicsForTrainingMode(final CardEntity card) {
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

package com.kanemil.memorich.data.db.entity;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

import static androidx.room.ForeignKey.CASCADE;

/**
 * Basic card
 */

@Entity(tableName = "cards",
        foreignKeys = @ForeignKey(
                entity = Deck.class,
                parentColumns = "id",
                childColumns = "deck_id",
                onDelete = CASCADE))
public class Card {

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    private long mId;

    @ColumnInfo(name = "front")
    private String mFront;

    @ColumnInfo(name = "back")
    private String mBack;

    @ColumnInfo(name = "deck_id")
    private long mDeckId;

    @Ignore
    private boolean mRevealedInTraining = false;

    /**
     * Default constructor
     *
     * @param front front side of a card.
     * @param back  back side of a card.
     */
    public Card(String front, String back) {
        mFront = front;
        mBack = back;
    }

    public long getId() {
        return mId;
    }

    public void setId(long id) {
        mId = id;
    }

    public String getFront() {
        return mFront;
    }

    public void setFront(String front) {
        mFront = front;
    }

    public String getBack() {
        return mBack;
    }

    public void setBack(String back) {
        mBack = back;
    }

    public long getDeckId() {
        return mDeckId;
    }

    public void setDeckId(long deckId) {
        mDeckId = deckId;
    }

    public boolean isRevealedInTraining() {
        return mRevealedInTraining;
    }

    public void setRevealedInTraining(boolean revealedInTraining) {
        mRevealedInTraining = revealedInTraining;
    }
}

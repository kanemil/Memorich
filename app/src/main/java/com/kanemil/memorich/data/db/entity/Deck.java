package com.kanemil.memorich.data.db.entity;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.util.Objects;

/**
 * Simple deck of cards containing List<Card> field.
 */

@Entity(tableName = "decks")
public class Deck {

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    private long mId;

    @ColumnInfo(name = "name")
    private String mName;

    public Deck(String name) {
        mName = name;
    }

    public long getId() {
        return mId;
    }

    public void setId(long id) {
        mId = id;
    }

    public String getName() {
        return mName;
    }

    public void setName(String name) {
        mName = name;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Deck deck = (Deck) o;
        return mId == deck.mId &&
                Objects.equals(mName, deck.mName);
    }

    @Override
    public int hashCode() {
        return Objects.hash(mId, mName);
    }
}

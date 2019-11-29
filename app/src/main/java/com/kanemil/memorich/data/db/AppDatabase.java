package com.kanemil.memorich.data.db;

import androidx.room.Database;
import androidx.room.RoomDatabase;

import com.kanemil.memorich.data.db.dao.CardDao;
import com.kanemil.memorich.data.db.dao.DeckDao;
import com.kanemil.memorich.data.db.entity.Card;
import com.kanemil.memorich.data.db.entity.Deck;

@Database(entities = {Deck.class, Card.class}, version = 1)
public abstract class AppDatabase extends RoomDatabase {
    public abstract DeckDao getDeckDao();

    public abstract CardDao getCardDao();
}


package com.kanemil.memorich.data.db;

import androidx.room.Database;
import androidx.room.RoomDatabase;

import com.kanemil.memorich.data.db.dao.CardDao;
import com.kanemil.memorich.data.db.dao.DeckDao;
import com.kanemil.memorich.data.db.entity.CardEntity;
import com.kanemil.memorich.data.db.entity.DeckEntity;

@Database(entities = {DeckEntity.class, CardEntity.class}, version = 1)
public abstract class AppDatabase extends RoomDatabase {
    public abstract DeckDao getDeckDao();

    public abstract CardDao getCardDao();
}


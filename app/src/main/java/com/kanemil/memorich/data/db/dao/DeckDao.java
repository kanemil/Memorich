package com.kanemil.memorich.data.db.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.kanemil.memorich.data.db.entity.DeckEntity;

import java.util.List;

import io.reactivex.Completable;

@Dao
public interface DeckDao {

    @Query("SELECT * FROM DeckEntity")
    LiveData<List<DeckEntity>> loadDecks();

    @Insert
    Completable insert(DeckEntity deck);

    @Update
    Completable update(DeckEntity deck);

    @Delete
    Completable delete(DeckEntity deck);
}

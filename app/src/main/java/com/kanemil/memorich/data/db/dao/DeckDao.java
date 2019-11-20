package com.kanemil.memorich.data.db.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.kanemil.memorich.data.db.entity.Deck;

import java.util.List;

import io.reactivex.Completable;

@Dao
public interface DeckDao {

    @Query("SELECT * FROM decks")
    LiveData<List<Deck>> getDecks();

    @Insert
    Completable insert(Deck deck);

    @Update
    void update(Deck deck);

    @Delete
    void delete(Deck deck);
}

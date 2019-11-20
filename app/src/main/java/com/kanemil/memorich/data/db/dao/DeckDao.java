package com.kanemil.memorich.data.db.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.kanemil.memorich.data.db.entity.Deck;

import java.util.List;

@Dao
public interface DeckDao {

    @Query("SELECT * FROM decks")
    LiveData<List<Deck>> getDecks();

    @Insert
    void insert(Deck deck);

    @Update
    void update(Deck deck);

    @Delete
    void delete(Deck deck);
}

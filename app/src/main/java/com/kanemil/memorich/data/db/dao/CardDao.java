package com.kanemil.memorich.data.db.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.kanemil.memorich.data.db.entity.Card;

import java.util.List;

import io.reactivex.Completable;

@Dao
public interface CardDao {

    @Query("SELECT * FROM cards WHERE deck_id = :deckId")
    LiveData<List<Card>> getCardsByDeckId(long deckId);

    @Insert
    Completable insert(Card card);

    @Update
    Completable update(Card card);

    @Delete
    Completable delete(Card card);
}

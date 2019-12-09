package com.kanemil.memorich.data.db.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.kanemil.memorich.data.db.entity.CardEntity;

import java.util.List;

import io.reactivex.Completable;

@Dao
public interface CardDao {

    @Query("SELECT * FROM CardEntity WHERE deck_id = :deckId ORDER BY order_id ASC")
    LiveData<List<CardEntity>> loadCardsByDeckId(long deckId);

    @Insert
    Completable insert(CardEntity card);

    @Insert
    Completable insertList(List<CardEntity> cardList);

    @Update
    Completable update(CardEntity card);

    @Update
    Completable updateList(List<CardEntity> cardList);

    @Delete
    Completable delete(CardEntity card);

    @Delete
    Completable deleteList(List<CardEntity> cardList);
}

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

    @Query("SELECT * FROM cards WHERE deck_id = :deckId ORDER BY order_id ASC")
    LiveData<List<Card>> getCardsByDeckId(long deckId);

    @Insert
    Completable insert(Card card);

    @Insert
    Completable insertList(List<Card> cardList);

    @Update
    Completable update(Card card);

    @Update
    Completable updateList(List<Card> cardList);

    @Delete
    Completable delete(Card card);

    @Delete
    Completable deleteList(List<Card> cardList);
}

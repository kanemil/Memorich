package com.kanemil.memorich.di;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;

import com.kanemil.memorich.data.db.AppDatabase;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module
public class DatabaseModule {

    @Singleton
    @Provides
    AppDatabase provideDatabase(Application application) {
        return Room
                .databaseBuilder(application, AppDatabase.class, "database")
                .addCallback(new RoomDatabase.Callback() {
                    @Override
                    public void onCreate(@NonNull final SupportSQLiteDatabase db) {
                        prePopulateDatabase(db);
                    }
                })
                .build();
    }


    private void prePopulateDatabase(@NonNull SupportSQLiteDatabase db) {
        db.execSQL("INSERT INTO decks (id, name) VALUES (1, 'Дебаг колода')");
        db.execSQL("INSERT INTO cards (id, front, back, deck_id, order_id) VALUES (1, 'first front', 'first back', 1, 0)");
        db.execSQL("INSERT INTO cards (id, front, back, deck_id, order_id) VALUES (2, 'second front', 'second back', 1, 1)");
        db.execSQL("INSERT INTO cards (id, front, back, deck_id, order_id) VALUES (3, 'third front', 'third back', 1, 2)");
        db.execSQL("INSERT INTO cards (id, front, back, deck_id, order_id) VALUES (4, 'fourth front', 'fourth back', 1, 3)");
        db.execSQL("INSERT INTO cards (id, front, back, deck_id, order_id) VALUES (5, 'fifth front', 'fifth back', 1, 4)");

        db.execSQL("INSERT INTO decks (id, name) VALUES (2, 'My First English Words')");
        db.execSQL("INSERT INTO cards (id, front, back, deck_id, order_id) VALUES (6, 'Hello', 'Привет', 2, 0)");
        db.execSQL("INSERT INTO cards (id, front, back, deck_id, order_id) VALUES (7, 'Dog', 'Собака', 2, 1)");
        db.execSQL("INSERT INTO cards (id, front, back, deck_id, order_id) VALUES (8, 'Mother', 'Мама', 2, 2)");
        db.execSQL("INSERT INTO cards (id, front, back, deck_id, order_id) VALUES (9, 'Five', 'Пять', 2, 3)");
        db.execSQL("INSERT INTO cards (id, front, back, deck_id, order_id) VALUES (10, 'Milk', 'Молоко', 2, 4)");

        db.execSQL("INSERT INTO decks (id, name) VALUES (3, ':-)')");
        db.execSQL("INSERT INTO cards (id, front, back, deck_id, order_id) VALUES (11, 'Architecture pattern', 'MVPM (MVVM без Databinding)', 3, 0)");
        db.execSQL("INSERT INTO cards (id, front, back, deck_id, order_id) VALUES (12, 'Architecture components', 'ViewModel, LiveData', 3, 1)");
        db.execSQL("INSERT INTO cards (id, front, back, deck_id, order_id) VALUES (13, 'Persistency', 'Room', 3, 2)");
        db.execSQL("INSERT INTO cards (id, front, back, deck_id, order_id) VALUES (14, 'Async operations', 'RxJava 2\n(android.arch.persistence.room:rxjava2\nio.reactivex.rxjava2:rxandroid)', 3, 3)");
        db.execSQL("INSERT INTO cards (id, front, back, deck_id, order_id) VALUES (15, 'Clean architecture?', 'No country for domain layer\n(ViewModels talks to the Repository)', 3, 4)");
        db.execSQL("INSERT INTO cards (id, front, back, deck_id, order_id) VALUES (16, 'Dependency injection', 'Dagger2 for Repository and ResourceWrapper injection', 3, 5)");
    }
}

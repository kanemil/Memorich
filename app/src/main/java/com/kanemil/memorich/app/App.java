package com.kanemil.memorich.app;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;

import com.kanemil.memorich.data.db.AppDatabase;

public class App extends Application {

    /* ВОПРОС
        Здесь где-то должен быть применен даггер, я это чувствую, но пока не понимаю.
     */

    private static final String TAG = "MyTag";
    public static App sInstance;
    private AppDatabase mDatabase;

    public static App getInstance() {
        return sInstance;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        sInstance = this;
        mDatabase = Room.databaseBuilder(this.getApplicationContext(), AppDatabase.class, "database")
                .addCallback(new RoomDatabase.Callback() {
                    @Override
                    public void onCreate(@NonNull SupportSQLiteDatabase db) {
                        populateDatabase(db);
                    }
                })
                .build();
    }

    public AppDatabase getDatabase() {
        return mDatabase;
    }

    private void populateDatabase(@NonNull SupportSQLiteDatabase db) {
        db.execSQL("INSERT INTO decks (id, name) VALUES (1, 'My First Deck')");
        db.execSQL("INSERT INTO cards (id, front, back, deck_id, order_id) VALUES (1, 'first front', 'first back', 1, 1)");
        db.execSQL("INSERT INTO cards (id, front, back, deck_id, order_id) VALUES (2, 'second front', 'second back', 1, 2)");
        db.execSQL("INSERT INTO cards (id, front, back, deck_id, order_id) VALUES (3, 'third front', 'third back', 1, 3)");
        db.execSQL("INSERT INTO cards (id, front, back, deck_id, order_id) VALUES (4, 'fourth front', 'fourth back', 1, 4)");
        db.execSQL("INSERT INTO cards (id, front, back, deck_id, order_id) VALUES (5, 'fifth front', 'fifth back', 1, 5)");
    }
}

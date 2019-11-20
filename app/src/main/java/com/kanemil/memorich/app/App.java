package com.kanemil.memorich.app;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;

import com.kanemil.memorich.data.db.AppDatabase;

public class App extends Application {

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
                        db.execSQL("INSERT INTO decks (id, name) VALUES (0, 'My First Deck')");
                        db.execSQL("INSERT INTO cards (id, front, back, deck_id) VALUES (0, 'first front', 'first back', 0)");
                        db.execSQL("INSERT INTO cards (id, front, back, deck_id) VALUES (1, 'second front', 'second back', 0)");
                    }
                })
                .build();
    }

    public AppDatabase getDatabase() {
        return mDatabase;
    }
}

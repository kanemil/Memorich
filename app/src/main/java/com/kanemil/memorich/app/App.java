package com.kanemil.memorich.app;

import android.app.Application;
import android.util.Log;
import android.widget.Toast;

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


    // TODO разобраться с препопуляцией базы данных
    // TODO переделать на асинхронные обращения
    @Override
    public void onCreate() {
        super.onCreate();
        sInstance = this;
        Log.d(TAG, "onCreate: ");
        mDatabase = Room.databaseBuilder(this.getApplicationContext(), AppDatabase.class, "database")
                .addCallback(new RoomDatabase.Callback() {
                    @Override
                    public void onCreate(@NonNull SupportSQLiteDatabase db) {
                        db.execSQL("INSERT INTO decks (id, name) VALUES (0, 'My First Deck')");
                    }
                })
                //                .allowMainThreadQueries()
                .build();
        Log.d(TAG, "db created ");
        Toast.makeText(sInstance, mDatabase.toString(), Toast.LENGTH_LONG).show();
    }

    public AppDatabase getDatabase() {
        return mDatabase;
    }
}

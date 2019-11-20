package com.kanemil.memorich.app;

import android.app.Application;
import android.util.Log;
import android.widget.Toast;

import androidx.room.Room;

import com.kanemil.memorich.data.db.AppDatabase;

public class App extends Application {

    private static final String TAG = "MyTag";
    public static App sInstance;
    private AppDatabase mDatabase;

    public static App getInstance() {
        return sInstance;
    }

    //                .addCallback(new RoomDatabase.Callback() {
//                    @Override
//                    public void onCreate(@NonNull SupportSQLiteDatabase db) {
//                        db.execSQL("INSERT INTO Deck (id, name) VALUES (1, 'My First Deck')");
//                    }
//                })
//                .allowMainThreadQueries()

    // TODO разобраться с препопуляцией базы данных
    // TODO переделать на асинхронные обращения
    @Override
    public void onCreate() {
        super.onCreate();
        sInstance = this;
        Log.d(TAG, "onCreate: ");
        mDatabase = Room.databaseBuilder(this.getApplicationContext(), AppDatabase.class, "database")
                .build();
        Log.d(TAG, "db created ");
        Toast.makeText(sInstance, mDatabase.toString(), Toast.LENGTH_LONG).show();
    }

    public AppDatabase getDatabase() {
        return mDatabase;
    }
}

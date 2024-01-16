package com.example.projektsm;


import android.content.Context;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.migration.Migration;
import androidx.sqlite.db.SupportSQLiteDatabase;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Database(entities = {Jedzenie.class}, version = 2, exportSchema = false)
public abstract class JedzenieDatabase extends RoomDatabase {
    public abstract JedzenieDao jedzenieDao();

    private static volatile JedzenieDatabase databaseInstance;
    static final ExecutorService databaseWriteExecutor = Executors.newSingleThreadExecutor();
    private static final Migration MIGRATION_1_2 = new Migration(1, 2) {
        @Override
        public void migrate(@NonNull SupportSQLiteDatabase database) {
            // Add any necessary migration steps here
            // For example, you can drop and recreate the table
            database.execSQL("ALTER TABLE jedzenie ADD COLUMN dataDodania INTEGER");
        }
    };
    static JedzenieDatabase getDatabase(final Context context) {
        if (databaseInstance == null)
            databaseInstance = Room.databaseBuilder(context.getApplicationContext(), JedzenieDatabase.class, "jedzenie_db")
                    .addCallback(sRoomDatabaseCallback)
                    .addMigrations(MIGRATION_1_2)
                    .build();
        return databaseInstance;
    }

    private static RoomDatabase.Callback sRoomDatabaseCallback = new RoomDatabase.Callback() {
        @Override
        public void onOpen(@NonNull SupportSQLiteDatabase db) {
            super.onOpen(db);
            databaseWriteExecutor.execute(() -> {
                JedzenieDao dao = databaseInstance.jedzenieDao();

            });
        }
    };
}
package uk.ac.reading.student.zj018597.whatstheplan.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * {@link RoomDatabase} implemented as singleton to prevent having multiple instances of
 * the database opened at the same time.
 */
@Database(entities = {PlanEntity.class, RestaurantEntity.class}, version = 4,
exportSchema = false)
public abstract class AppDatabase extends RoomDatabase {

    private static final String DB_NAME = "app-database.db";
    private static volatile AppDatabase INSTANCE;
    private static final int NUMBER_OF_THREADS = 4;
    static final ExecutorService databaseWriteExecutor =
            Executors.newFixedThreadPool(NUMBER_OF_THREADS);

    public abstract PlanDao planDao();
    public abstract RestaurantDao restaurantDao();

    static AppDatabase getDatabase(final Context context) {
        if (INSTANCE == null) {
            synchronized (AppDatabase.class) {
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                            AppDatabase.class, DB_NAME)
                            .fallbackToDestructiveMigration()
                            .build();
                }
            }
        }
        return INSTANCE;
    }
}

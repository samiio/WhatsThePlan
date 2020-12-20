package uk.ac.reading.student.zj018597.whatstheplan.db;

import androidx.sqlite.db.SupportSQLiteDatabase;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.os.AsyncTask;
import androidx.annotation.NonNull;

/**
 * {@link RoomDatabase} acts as a layer on top of {@link SQLiteDatabase}
 * Room simplifies the implementation and the query execution of the database
 * This class is implemented as Singleton to prevent having multiple instances of the database
 * opened at the same time
 */
@Database(entities = {PlanEntity.class, RestaurantEntity.class}, version = 4,
exportSchema = false)
public abstract class AppDatabase extends RoomDatabase {

    private static final String DB_NAME = "app-database.db";
    private static volatile AppDatabase INSTANCE;

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

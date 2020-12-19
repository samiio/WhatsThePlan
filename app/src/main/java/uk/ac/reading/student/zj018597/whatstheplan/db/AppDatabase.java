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
@Database(entities = {PlanEntity.class, RestaurantEntity.class}, version = 2)
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
                            .addCallback(sAppDatabaseCallback)
                            .fallbackToDestructiveMigration()
                            .build();
                }
            }
        }
        return INSTANCE;
    }

    /**
     * Callback to populate the database with some data on a background thread by
     * calling {@link PopulatePlansTableTask}
     */
    private static AppDatabase.Callback sAppDatabaseCallback = new AppDatabase.Callback() {

        @Override
        public void onOpen(@NonNull SupportSQLiteDatabase db) {
            super.onOpen(db);
            new PopulatePlansTableTask(INSTANCE).execute();
            new PopulateRestaurantsTableTask(INSTANCE).execute();
        }
    };

    /**
     * Populates the {@link PlanEntity} table with three records if it is empty.
     */
    private static class PopulatePlansTableTask extends AsyncTask<Void, Void, Void> {

        private final PlanDao mDao;

        PopulatePlansTableTask(AppDatabase db) {
            mDao = db.planDao();
        }

        @Override
        protected Void doInBackground(Void... voids) {
            if (mDao.countRecords() < 4) {
                mDao.deleteAll();
                PlanEntity plan = new PlanEntity("FIFA");
                mDao.insert(plan);
                plan = new PlanEntity("Cards");
                mDao.insert(plan);
                plan = new PlanEntity("Shatti AlQurum");
                mDao.insert(plan);
            }
            return null;
        }
    }

    /**
     * Populates the {@link RestaurantEntity} table with three records if it is empty.
     */
    private static class PopulateRestaurantsTableTask extends AsyncTask<Void, Void, Void> {

        private final RestaurantDao mDao;

        PopulateRestaurantsTableTask(AppDatabase db) {
            mDao = db.restaurantDao();
        }

        @Override
        protected Void doInBackground(Void... voids) {
            if (mDao.countRecords() < 4) {
                mDao.deleteAll();
                RestaurantEntity restaurant = new RestaurantEntity("KFC");
                mDao.insert(restaurant);
                restaurant = new RestaurantEntity("Fairoz");
                mDao.insert(restaurant);
                restaurant = new RestaurantEntity("Baba Salem");
                mDao.insert(restaurant);
            }
            return null;
        }
    }

}

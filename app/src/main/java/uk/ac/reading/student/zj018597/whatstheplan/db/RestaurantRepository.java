package uk.ac.reading.student.zj018597.whatstheplan.db;

import android.app.Application;

import androidx.lifecycle.LiveData;

import java.util.List;

/**
 * Manages queries to {@link PlanEntity}.
 */
public class RestaurantRepository {

    private final RestaurantDao mRestaurantDao;
    private final LiveData<List<RestaurantEntity>> mAllRestaurants;

    /**
     * Gets a handle to the database and initialises member variables.
     */
    public RestaurantRepository(Application application) {
        AppDatabase db = AppDatabase.getDatabase(application);
        mRestaurantDao = db.restaurantDao();
        mAllRestaurants = mRestaurantDao.getAllRestaurants();
    }

    public LiveData<Integer> getCount() {
        return mRestaurantDao.getRecordCount();
    }

    public LiveData<List<RestaurantEntity>> getAllRestaurants() {
        return mAllRestaurants;
    }

    public void insert(RestaurantEntity restaurant) {
        AppDatabase.databaseWriteExecutor.execute(() -> mRestaurantDao.insert(restaurant));
    }

    public void delete(RestaurantEntity restaurant) {
        AppDatabase.databaseWriteExecutor.execute(() -> mRestaurantDao.delete(restaurant));
    }

    public void deleteAll() {
        AppDatabase.databaseWriteExecutor.execute(mRestaurantDao::deleteAll);
    }
}

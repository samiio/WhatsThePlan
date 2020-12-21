package uk.ac.reading.student.zj018597.whatstheplan.db;

import android.app.Application;

import androidx.lifecycle.LiveData;

import java.util.List;

public class RestaurantRepository {

    private RestaurantDao mRestaurantDao;
    private LiveData<List<RestaurantEntity>> mAllRestaurants;

    /**
     * Constructor that gets a handle to the database and initializes the member variables
     */
    public RestaurantRepository(Application application) {
        AppDatabase db = AppDatabase.getDatabase(application);
        mRestaurantDao = db.restaurantDao();
        mAllRestaurants = mRestaurantDao.getAllRestaurants();
    }

    /**
     * Get row count.
     */
    public LiveData<Integer> getCount() {
        return mRestaurantDao.getRecordCount();
    }

    /**
     * Get all the {@link RestaurantEntity} from the database
     */
    public LiveData<List<RestaurantEntity>> getAllRestaurants() {
        return mAllRestaurants;
    }

    /**
     * Insert a {@link PlanEntity} into the database
     */
    public void insert(RestaurantEntity restaurant) {
        AppDatabase.databaseWriteExecutor.execute(() -> mRestaurantDao.insert(restaurant));
    }

    /**
     * Delete a {@link PlanEntity} from the table
     */
    public void delete(RestaurantEntity restaurant) {
        AppDatabase.databaseWriteExecutor.execute(() -> mRestaurantDao.delete(restaurant));
    }

}

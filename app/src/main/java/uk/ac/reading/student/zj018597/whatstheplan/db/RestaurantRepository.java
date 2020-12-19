package uk.ac.reading.student.zj018597.whatstheplan.db;

import android.app.Application;
import android.arch.lifecycle.LiveData;
import android.os.AsyncTask;

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
     * Get all the {@link RestaurantEntity} from the database
     */
    public LiveData<List<RestaurantEntity>> getAllRestaurants() {
        return mAllRestaurants;
    }

    /**
     * Insert a {@link PlanEntity} into the database
     */
    public void insert(RestaurantEntity restaurant) {
        new InsertTask(mRestaurantDao).execute(restaurant);
    }

    /**
     * Delete a {@link PlanEntity} from the table
     */
    public void delete(RestaurantEntity restaurant) {
        new DeleteTask(mRestaurantDao).execute(restaurant);
    }

    /*--------------------------------------------------------------------------------------------*/
    /*--------------------------------------- AsyncTasks -----------------------------------------*/
    /*--------------------------------------------------------------------------------------------*/

    /**
     * {@link AsyncTask} to insert {@link RestaurantEntity} on background thread.
     */
    private static class InsertTask extends AsyncTask<RestaurantEntity, Void, Void> {

        private RestaurantDao mTaskDao;

        InsertTask(RestaurantDao dao) {
            mTaskDao = dao;
        }

        @Override
        protected Void doInBackground(final RestaurantEntity... restaurants) {
            mTaskDao.insert(restaurants[0]);
            return null;
        }
    }

    /**
     * {@link AsyncTask} to delete {@link RestaurantEntity} on background thread.
     */
    private static class DeleteTask extends AsyncTask<RestaurantEntity, Void, Void> {

        private RestaurantDao mTaskDao;

        DeleteTask(RestaurantDao dao) {
            mTaskDao = dao;
        }

        @Override
        protected Void doInBackground(RestaurantEntity... restaurants) {
            mTaskDao.delete(restaurants[0]);
            return null;
        }
    }
}

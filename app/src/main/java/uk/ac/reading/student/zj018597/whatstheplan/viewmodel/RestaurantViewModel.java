package uk.ac.reading.student.zj018597.whatstheplan.viewmodel;

import android.app.Application;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import java.util.List;

import uk.ac.reading.student.zj018597.whatstheplan.db.RestaurantEntity;
import uk.ac.reading.student.zj018597.whatstheplan.db.RestaurantRepository;

/**
 * Provides data from {@link RestaurantEntity} table to the UI and survives configuration changes.
 * Acts as a communication center between {@link RestaurantRepository} and the UI.
 */
public class RestaurantViewModel extends AndroidViewModel {

    private final RestaurantRepository mRepository;
    private final LiveData<List<RestaurantEntity>> mAllRestaurants;

    public RestaurantViewModel(Application application) {
        super(application);
        mRepository = new RestaurantRepository(application);
        mAllRestaurants = mRepository.getAllRestaurants();
    }

    public LiveData<Integer> getCount() {
        return mRepository.getCount();
    }

    public LiveData<List<RestaurantEntity>> getAllRestaurants() {
        return mAllRestaurants;
    }

    public void insert(RestaurantEntity restaurant) {
        mRepository.insert(restaurant);
    }

    public void delete(RestaurantEntity restaurant) {
        mRepository.delete(restaurant);
    }

    public void empty() {
        mRepository.deleteAll();
    }
}

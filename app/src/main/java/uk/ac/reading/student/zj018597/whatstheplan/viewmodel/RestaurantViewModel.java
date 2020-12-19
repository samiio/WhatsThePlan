package uk.ac.reading.student.zj018597.whatstheplan.viewmodel;

import android.app.Application;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import java.util.List;

import uk.ac.reading.student.zj018597.whatstheplan.db.RestaurantEntity;
import uk.ac.reading.student.zj018597.whatstheplan.db.RestaurantRepository;

/**
 * The {@link ViewModel} role is to provide data to the UI and survive configuration changes.
 * This class acts as a communication center between the Repository and the UI.
 */
public class RestaurantViewModel extends AndroidViewModel {

    private RestaurantRepository mRepository;
    private LiveData<List<RestaurantEntity>> mAllRestaurants;

    public RestaurantViewModel(Application application) {
        super(application);
        mRepository = new RestaurantRepository(application);
        mAllRestaurants = mRepository.getAllRestaurants();
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

}

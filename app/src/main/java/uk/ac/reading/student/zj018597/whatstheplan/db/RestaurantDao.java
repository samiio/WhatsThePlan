package uk.ac.reading.student.zj018597.whatstheplan.db;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Room;

import java.util.List;

/**
 * DAO class for implementing the {@link RestaurantEntity} in the {@link Room} database
 * The DAO class is used to specify the SQL queries used to interact with the database
 */
@Dao
public interface RestaurantDao {

    /**
     * Count the number of restaurants.
     */
    @Query("SELECT COUNT(*) FROM restaurants")
    int countRecords();

    /**
     * Insert new restaurant.
     */
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(RestaurantEntity plan);

    /**
     * Return list of restaurants.
     */
    @Query("SELECT * FROM restaurants ORDER BY name ASC")
    LiveData<List<RestaurantEntity>> getAllRestaurants();

    /**
     * Delete given restaurant item.
     */
    @Delete
    void delete(RestaurantEntity plan);

    /**
     * Delete all restaurants in list.
     */
    @Query("DELETE FROM restaurants")
    void deleteAll();
}

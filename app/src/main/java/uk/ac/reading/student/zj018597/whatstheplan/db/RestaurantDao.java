package uk.ac.reading.student.zj018597.whatstheplan.db;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import java.util.List;

/**
 * DAO interface to query the {@link RestaurantEntity} table.
 */
@Dao
public interface RestaurantDao {

    @Query("SELECT COUNT(*) FROM restaurants")
    LiveData<Integer> getRecordCount();

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(RestaurantEntity plan);

    @Query("SELECT * FROM restaurants ORDER BY name ASC")
    LiveData<List<RestaurantEntity>> getAllRestaurants();

    @Delete
    void delete(RestaurantEntity plan);

    @Query("DELETE FROM restaurants")
    void deleteAll();

}

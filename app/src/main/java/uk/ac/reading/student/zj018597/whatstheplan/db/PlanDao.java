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
 * DAO class for implementing the {@link PlanEntity} in the {@link Room} database
 * The DAO class is used to specify the SQL queries used to interact with the database
 */
@Dao
public interface PlanDao {

    @Query("SELECT COUNT(*) FROM plans")
    LiveData<Integer> getRecordCount();

    /**
     * Insert new plan.
     */
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(PlanEntity plan);

    /**
     * Return list of plans.
     */
    @Query("SELECT * FROM plans ORDER BY name ASC")
    LiveData<List<PlanEntity>> getAllPlans();

    /**
     * Delete given plan item.
     */
    @Delete
    void delete(PlanEntity plan);

}

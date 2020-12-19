package uk.ac.reading.student.zj018597.whatstheplan.db;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Room;

import java.util.List;

/**
 * DAO class for implementing the {@link PlanEntity} in the {@link Room} database
 * The DAO class is used to specify the SQL queries used to interact with the database
 */
@Dao
public interface PlanDao {

    /**
     * Count the number of plans.
     */
    @Query("SELECT COUNT(*) FROM plans")
    int countRecords();

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

    /**
     * Delete all plans in list.
     */
    @Query("DELETE FROM plans")
    void deleteAll();

}

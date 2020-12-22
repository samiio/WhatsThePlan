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
 * DAO interface to query the {@link PlanEntity} table.
 */
@Dao
public interface PlanDao {

    @Query("SELECT COUNT(*) FROM plans")
    LiveData<Integer> getRecordCount();

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(PlanEntity plan);

    @Query("SELECT * FROM plans ORDER BY name ASC")
    LiveData<List<PlanEntity>> getAllPlans();

    @Delete
    void delete(PlanEntity plan);

    @Query("DELETE FROM plans")
    void deleteAll();

}

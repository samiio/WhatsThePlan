package uk.ac.reading.student.zj018597.whatstheplan.db;

import android.app.Application;

import androidx.lifecycle.LiveData;

import java.util.List;

/**
 * A Repository is a class that abstracts access to multiple data sources.
 * It is not part of the Architecture Components libraries,
 * but is a suggested best practice for code separation and architecture.
 */
public class PlanRepository {

    private PlanDao mPlanDao;
    private LiveData<List<PlanEntity>> mAllPlans;

    /**
     * Constructor that gets a handle to the database and initializes the member variables
     */
    public PlanRepository(Application application) {
        AppDatabase db = AppDatabase.getDatabase(application);
        mPlanDao = db.planDao();
        mAllPlans = mPlanDao.getAllPlans();
    }

    public LiveData<Integer> getCount() {
        return mPlanDao.getRecordCount();
    }

    /**
     * Get all the {@link PlanEntity} from the database
     */
    public LiveData<List<PlanEntity>> getAllPlans() {
        return mAllPlans;
    }

    /**
     * Insert a {@link PlanEntity} into the database
     */
    public void insert(PlanEntity plan) {
        AppDatabase.databaseWriteExecutor.execute(() -> mPlanDao.insert(plan));
    }

    /**
     * Delete a {@link PlanEntity} from the table
     */
    public void delete(PlanEntity plan) {
        AppDatabase.databaseWriteExecutor.execute(() -> mPlanDao.delete(plan));
    }

}

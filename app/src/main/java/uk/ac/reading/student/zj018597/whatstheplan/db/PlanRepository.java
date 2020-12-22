package uk.ac.reading.student.zj018597.whatstheplan.db;

import android.app.Application;

import androidx.lifecycle.LiveData;

import java.util.List;

/**
 * Manages queries to {@link PlanEntity}.
 */
public class PlanRepository {

    private final PlanDao mPlanDao;
    private final LiveData<List<PlanEntity>> mAllPlans;

    /**
     * Gets a handle to the database and initialises member variables.
     */
    public PlanRepository(Application application) {
        AppDatabase db = AppDatabase.getDatabase(application);
        mPlanDao = db.planDao();
        mAllPlans = mPlanDao.getAllPlans();
    }

    public LiveData<Integer> getCount() {
        return mPlanDao.getRecordCount();
    }

    public LiveData<List<PlanEntity>> getAllPlans() {
        return mAllPlans;
    }

    public void insert(PlanEntity plan) {
        AppDatabase.databaseWriteExecutor.execute(() -> mPlanDao.insert(plan));
    }

    public void delete(PlanEntity plan) {
        AppDatabase.databaseWriteExecutor.execute(() -> mPlanDao.delete(plan));
    }

    public void deleteAll() {
        AppDatabase.databaseWriteExecutor.execute(mPlanDao::deleteAll);
    }

}

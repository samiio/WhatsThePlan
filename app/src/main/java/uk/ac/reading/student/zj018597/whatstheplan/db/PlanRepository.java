package uk.ac.reading.student.zj018597.whatstheplan.db;

import android.app.Application;
import android.arch.lifecycle.LiveData;
import android.os.AsyncTask;

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
        new InsertTask(mPlanDao).execute(plan);
    }

    /**
     * Delete a {@link PlanEntity} from the table
     */
    public void delete(PlanEntity plan) {
        new DeleteTask(mPlanDao).execute(plan);
    }

    /*--------------------------------------------------------------------------------------------*/
    /*--------------------------------------- AsyncTasks -----------------------------------------*/
    /*--------------------------------------------------------------------------------------------*/

    /**
     * {@link AsyncTask} to insert {@link PlanEntity} on background thread.
     */
    private static class InsertTask extends AsyncTask<PlanEntity, Void, Void> {

        private PlanDao mTaskDao;

        InsertTask(PlanDao dao) {
            mTaskDao = dao;
        }

        @Override
        protected Void doInBackground(final PlanEntity... plans) {
            mTaskDao.insert(plans[0]);
            return null;
        }
    }

    /**
     * {@link AsyncTask} to delete {@link PlanEntity} on background thread.
     */
    private static class DeleteTask extends AsyncTask<PlanEntity, Void, Void> {

        private PlanDao mTaskDao;

        DeleteTask(PlanDao dao) {
            mTaskDao = dao;
        }

        @Override
        protected Void doInBackground(PlanEntity... plans) {
            mTaskDao.delete(plans[0]);
            return null;
        }
    }

}

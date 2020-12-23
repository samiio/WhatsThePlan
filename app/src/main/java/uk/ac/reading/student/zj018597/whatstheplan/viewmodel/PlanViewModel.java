package uk.ac.reading.student.zj018597.whatstheplan.viewmodel;

import android.app.Application;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import java.util.List;

import uk.ac.reading.student.zj018597.whatstheplan.db.PlanEntity;
import uk.ac.reading.student.zj018597.whatstheplan.db.PlanRepository;

/**
 * Provides data from {@link PlanEntity} table to the UI and survives configuration changes.
 * Acts as a communication center between {@link PlanRepository} and the UI.
 */
public class PlanViewModel extends AndroidViewModel {

    private final PlanRepository mRepository;
    private final LiveData<List<PlanEntity>> mAllPlans;

    public PlanViewModel(Application application) {
        super(application);
        mRepository = new PlanRepository(application);
        mAllPlans = mRepository.getAllPlans();
    }

    public LiveData<Integer> getCount() {
        return mRepository.getCount();
    }

    public LiveData<List<PlanEntity>> getAllPlans() {
        return mAllPlans;
    }

    public void insert(PlanEntity plan) {
        mRepository.insert(plan);
    }

    public void delete(PlanEntity plan) {
        mRepository.delete(plan);
    }

    public void empty() {
        mRepository.deleteAll();
    }
}
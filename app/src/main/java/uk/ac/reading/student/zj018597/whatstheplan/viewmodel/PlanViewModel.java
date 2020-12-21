package uk.ac.reading.student.zj018597.whatstheplan.viewmodel;

import android.app.Application;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import java.util.List;

import uk.ac.reading.student.zj018597.whatstheplan.db.PlanEntity;
import uk.ac.reading.student.zj018597.whatstheplan.db.PlanRepository;

/**
 * The {@link ViewModel} role is to provide data to the UI and survive configuration changes.
 * This class acts as a communication center between the Repository and the UI.
 */
public class PlanViewModel extends AndroidViewModel {

    private PlanRepository mRepository;
    private LiveData<List<PlanEntity>> mAllPlans;

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

}
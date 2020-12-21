package uk.ac.reading.student.zj018597.whatstheplan.ui;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.BaseTransientBottomBar;
import com.google.android.material.snackbar.Snackbar;

import java.util.List;
import java.util.Objects;

import uk.ac.reading.student.zj018597.whatstheplan.R;
import uk.ac.reading.student.zj018597.whatstheplan.db.PlanEntity;
import uk.ac.reading.student.zj018597.whatstheplan.util.CustomSnackBar;
import uk.ac.reading.student.zj018597.whatstheplan.util.PlanListAdapter;
import uk.ac.reading.student.zj018597.whatstheplan.viewmodel.PlanViewModel;

import static uk.ac.reading.student.zj018597.whatstheplan.util.Animations.setFabAnimLift;

/**
 * {@link Fragment} which displays the list of {@link PlanEntity}.
 */
public class PlansFragment extends Fragment {

    //The fragment and its tag
    private Fragment plansFragment;
    private final static String TAG = "PLANS_FRAGMENT";

    private FloatingActionButton fabAdd;

    private PlanListAdapter adapter;
    private List<PlanEntity> planList;

    //Holds a plan temporarily in case the user wants to undo
    private PlanEntity tempPlan;
    private int tempPosition;

    private PlanViewModel mPlanViewModel;

    public static final int ADD_PLAN_ACTIVITY_REQUEST_CODE = 1;

    /*--------------------------------------------------------------------------------------------*/
    /*---------------------------------------- Lifecycle -----------------------------------------*/
    /*--------------------------------------------------------------------------------------------*/

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_plans, container, false);
        setFabAdd(v);
        setRecyclerView(v);
        return v;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        initFragment(savedInstanceState);

        mPlanViewModel = new ViewModelProvider(this).get(PlanViewModel.class);
        mPlanViewModel.getAllPlans().observe(getViewLifecycleOwner(), plans -> {
            // Update the cached copy of the words in the adapter.
            if (planList == null) {
                setListPlans(plans);
            } else {
                adapter.setPlans(plans);
            }
        });
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == ADD_PLAN_ACTIVITY_REQUEST_CODE && resultCode == MainActivity.RESULT_OK) {
            PlanEntity plan = new PlanEntity(data.getStringExtra(AddPlanActivity.EXTRA_REPLY_PLAN));
            mPlanViewModel.insert(plan);
        } else {
            Toast.makeText(
                    Objects.requireNonNull(getActivity()).getApplicationContext(),
                    R.string.empty_not_saved,
                    Toast.LENGTH_LONG).show();
        }
    }

    /*--------------------------------------------------------------------------------------------*/
    /*------------------------------------------ HELPER ------------------------------------------*/
    /*--------------------------------------------------------------------------------------------*/

    /**
     * Required empty public constructor
     */
    public PlansFragment() {}

    /**
     * Initialise this fragment.
     */
    private void initFragment(Bundle savedInstanceState) {
        FragmentManager manager = getChildFragmentManager();

        if (savedInstanceState != null) {
            plansFragment = manager.getFragment(savedInstanceState, TAG);

        } else {
            if (plansFragment != null) { //Do nothing
                Log.i(TAG, ": fragment exists, do nothing");

            } else { //Create new instance
                plansFragment = PlansFragment.newInstance();

            }
        }
    }

    /**
     * Creates a new instance of the fragment
     * @return new {@link PlansFragment}
     */
    public static PlansFragment newInstance() {
        return new PlansFragment();
    }

    /**
     * Sets the FloatingActionButton and its onClickListener
     */
    private void setFabAdd(View view) {
        fabAdd = view.findViewById(R.id.fab_add_plan);
        fabAdd.setOnClickListener(view1 -> {
            Intent intent = new Intent(getActivity(), AddPlanActivity.class);
            startActivityForResult(intent, ADD_PLAN_ACTIVITY_REQUEST_CODE);
        });
    }

    /**
     * Plan list.
     */
    private void setListPlans(List<PlanEntity> plans) {
        planList = plans;
        adapter.setPlans(plans);
    }

    /**
     * The {@link RecyclerView} which will display a list of {@link PlanEntity}
     */
    private void setRecyclerView(View view) {
        RecyclerView recyclerView = view.findViewById(R.id.rv_fragment_plans);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
        adapter = new PlanListAdapter(getActivity());
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(layoutManager);

        // Add DividerItemDecoration
        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(
                recyclerView.getContext(), layoutManager.getOrientation()
        );
        dividerItemDecoration.setDrawable(
                Objects.requireNonNull(ContextCompat.getDrawable(
                        Objects.requireNonNull(getActivity()), R.drawable.divider_light))
        );
        recyclerView.addItemDecoration(dividerItemDecoration);

        // Add ItemTouchHelper
        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(createHelperCallback());
        itemTouchHelper.attachToRecyclerView(recyclerView);
    }

    /**
     * ItemTouchHelper to delete plan on swipe.
     */
    private ItemTouchHelper.Callback createHelperCallback() {
        return new ItemTouchHelper.SimpleCallback(
                0, ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT) {

            //not used, as the first parameter above is 0
            @Override
            public boolean onMove(RecyclerView recyclerView1, RecyclerView.ViewHolder viewHolder,
                                  RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(final RecyclerView.ViewHolder viewHolder, int swipeDir) {
                int position = viewHolder.getAdapterPosition();

                try {
                    // temp plan
                    tempPlan = planList.get(position);
                    tempPosition = position;
                    mPlanViewModel.delete(planList.get(position));

                    //ensure View is consistent with underlying data
                    planList.remove(position);
                    adapter.notifyItemRemoved(position);
                } catch (Exception e) {
                    planList.clear();
                    mPlanViewModel.empty();
                    adapter.notifyItemRemoved(0);
                }

                displaySnackBar();
                setFabAnimLift(fabAdd);
            }
        };
    }

    /**
     * Displays undo SnackBar.
     */
    private void displaySnackBar() {
        Activity activity = getActivity();
        Context context = Objects.requireNonNull(activity).getApplicationContext();
        Snackbar snackbar = CustomSnackBar.setSnackBar(
                activity, context, R.id.root_fragment_plans, getString(R.string.undo)
        );
        // Set SnackBar action:
        // if undo is confirmed, restore exercise, update recyclerView and database
        snackbar.setAction(R.string.undo, view -> {
            if (tempPlan != null) {
                planList.add(tempPlan);
                adapter.notifyItemInserted(tempPosition);
                adapter.notifyDataSetChanged();
                mPlanViewModel.insert(tempPlan);
                // setFabAnimPull(fabAddExercise);
            } else {
                Toast.makeText(context, getText(R.string.plan_deleted), Toast.LENGTH_SHORT).show();
            }
        }).addCallback(new BaseTransientBottomBar.BaseCallback<Snackbar>() {
            // Dismiss tempPlan
            @Override
            public void onDismissed(Snackbar transientBottomBar, int event) {
                super.onDismissed(transientBottomBar, event);
                tempPlan = null;
                tempPosition = 0;
            }
        }).show();
    }

}

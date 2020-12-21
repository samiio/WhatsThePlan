package uk.ac.reading.student.zj018597.whatstheplan.ui;


import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.ViewModelProvider;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import uk.ac.reading.student.zj018597.whatstheplan.R;
import uk.ac.reading.student.zj018597.whatstheplan.db.PlanEntity;
import uk.ac.reading.student.zj018597.whatstheplan.db.RestaurantEntity;
import uk.ac.reading.student.zj018597.whatstheplan.model.AnEntity;
import uk.ac.reading.student.zj018597.whatstheplan.viewmodel.PlanViewModel;
import uk.ac.reading.student.zj018597.whatstheplan.viewmodel.RestaurantViewModel;

/**
 * A simple {@link Fragment} subclass.
 */
public class PlayFragment extends Fragment {

    //The fragment and its tag
    private Fragment playFragment;
    private final static String TAG = "PLAY_FRAGMENT";

    private Button btnPlan, btnRestaurant;
    private TextView tvPlan, tvRestaurant;
    private List<PlanEntity> planList;
    private List<RestaurantEntity> restaurantList;

    /*--------------------------------------------------------------------------------------------*/
    /*---------------------------------------- Lifecycle -----------------------------------------*/
    /*--------------------------------------------------------------------------------------------*/

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_play, container, false);
        tvPlan = v.findViewById(R.id.tv_plan);
        btnPlan = v.findViewById(R.id.btn_find_plan);
        btnPlan.setOnClickListener(new ButtonClickedPlan());

        Bundle playBundle = getArguments();
        int planListSize = playBundle.getInt(MainActivity.PLANS_LIST_SIZE);
        clickableButtonNew(btnPlan, planListSize);
        // TODO: FIX LAYOUT CONSTRAINTS
        tvRestaurant = v.findViewById(R.id.tv_restaurant);
        btnRestaurant = v.findViewById(R.id.btn_find_restaurant);
        btnRestaurant.setOnClickListener(new ButtonClickedRestaurant());
        clickableButton(btnRestaurant, restaurantList);
        return v;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        initFragment(savedInstanceState);

        PlanViewModel mPlanViewModel = new ViewModelProvider(
                this).get(PlanViewModel.class);
        mPlanViewModel.getAllPlans().observe(getViewLifecycleOwner(), this::setListPlans);

        RestaurantViewModel mRestaurantViewModel = new ViewModelProvider(
                this).get(RestaurantViewModel.class);
        mRestaurantViewModel.getAllRestaurants().observe(
                getViewLifecycleOwner(), this::setListRestaurants);
    }

    /*--------------------------------------------------------------------------------------------*/
    /*------------------------------------------ HELPER ------------------------------------------*/
    /*--------------------------------------------------------------------------------------------*/

    /**
     * Required empty public constructor
     */
    public PlayFragment() {}

    /**
     * Creates a new instance of the fragment
     * @return new {@link PlayFragment}
     */
    public static PlayFragment newInstance() {
        return new PlayFragment();
    }

    /**
     * Initialise this fragment.
     */
    private void initFragment(Bundle savedInstanceState) {
        FragmentManager manager = getChildFragmentManager();

        if (savedInstanceState != null) {
            playFragment = manager.getFragment(savedInstanceState, TAG);

        } else {
            if (playFragment != null) { //Do nothing
                Log.i(TAG, ": fragment exists, do nothing");

            } else { //Create new instance
                playFragment = PlayFragment.newInstance();
            }
        }
    }

    /**
     * List of plans.
     */
    private void setListPlans(List<PlanEntity> plans) {
        planList = plans;
    }

    /**
     * List of restaurants.
     */
    private void setListRestaurants(List<RestaurantEntity> restaurants) {
        restaurantList = restaurants;
    }

    /*-------------------------------------- BUTTON CLICKS ---------------------------------------*/

    /**
     * {@link View.OnClickListener} for {@link #btnPlan}.
     */
    private class ButtonClickedPlan implements View.OnClickListener {

        /**
         * Displays random item to the user.
         */
        @Override
        public void onClick(View view) {
            tvPlan.setText(getRandomItem(planList));
        }
    }

    /**
     * {@link View.OnClickListener} for {@link #btnRestaurant}.
     */
    private class ButtonClickedRestaurant implements View.OnClickListener {

        /**
         * Displays random item to the user.
         */
        @Override
        public void onClick(View view) {
            tvRestaurant.setText(getRandomItem(restaurantList));
        }
    }

    /**
     * Called when {@link Button} is clicked.
     * @param anEntityList list of objects which extend {@link AnEntity}.
     */
    private String getRandomItem(List<? extends AnEntity> anEntityList) {
        Random random = new Random();
        List<AnEntity> aList = new ArrayList<>(anEntityList);
        int index = random.nextInt(aList.size());
        return aList.get(index).getName();
    }

    // TODO: FIX BUTTON CLICK ISSUE
    private void clickableButton(Button button, List<? extends AnEntity> anEntityList) {
        button.setEnabled(false);
        if (anEntityList != null) {
            if (anEntityList.size() != 0) {
                button.setEnabled(true);
            }
        }
    }

    private void clickableButtonNew(Button button, int recordCount) {
        button.setEnabled(recordCount != 0);
    }

}

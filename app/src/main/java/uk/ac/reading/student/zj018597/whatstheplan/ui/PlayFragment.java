package uk.ac.reading.student.zj018597.whatstheplan.ui;


import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import uk.ac.reading.student.zj018597.whatstheplan.R;
import uk.ac.reading.student.zj018597.whatstheplan.db.PlanEntity;
import uk.ac.reading.student.zj018597.whatstheplan.db.RestaurantEntity;
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

        tvRestaurant = v.findViewById(R.id.tv_restaurant);
        btnRestaurant = v.findViewById(R.id.btn_find_restaurant);
        btnRestaurant.setOnClickListener(new ButtonClickedRestaurant());
        return v;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        initFragment(savedInstanceState);

        PlanViewModel mPlanViewModel = ViewModelProviders.of(
                this).get(PlanViewModel.class);
        mPlanViewModel.getAllPlans().observe(this, this::setListPlans);

        RestaurantViewModel mRestaurantViewModel = ViewModelProviders.of(
                this).get(RestaurantViewModel.class);
        mRestaurantViewModel.getAllRestaurants().observe(this, this::setListRestaurants);
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

        @Override
        public void onClick(View view) {
            buttonClickPlan();
        }

        /**
         * Randomly selects a Item from {@link #planList} and displays it to the user.
         * Called when {@link #btnPlan} is clicked.
         */
        private void buttonClickPlan() {
            Random random = new Random();
            List<PlanEntity> list = new ArrayList<>(planList);

            int index = random.nextInt(list.size());
            String planName = list.get(index).getName();
            tvPlan.setText(planName);
        }

    }

    /**
     * {@link View.OnClickListener} for {@link #btnRestaurant}.
     */
    private class ButtonClickedRestaurant implements View.OnClickListener {

        @Override
        public void onClick(View view) {
            buttonClickRestaurant();
        }

        /**
         * Randomly selects a Item from {@link #restaurantList} and displays it to the user.
         * Called when {@link #btnRestaurant} is clicked.
         */
        private void buttonClickRestaurant() {
            Random random = new Random();
            List<RestaurantEntity> list = new ArrayList<>(restaurantList);

            int index = random.nextInt(list.size());
            String restaurantName = list.get(index).getName();
            tvRestaurant.setText(restaurantName);
        }

    }

}

package uk.ac.reading.student.zj018597.whatstheplan.ui;


import android.os.Bundle;
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

    private TextView tvPlan, tvRestaurant;
    private List<PlanEntity> planList;
    private List<RestaurantEntity> restaurantList;
    private int planListSize, restaurantListSize;

    /*--------------------------------------------------------------------------------------------*/
    /*---------------------------------------- Lifecycle -----------------------------------------*/
    /*--------------------------------------------------------------------------------------------*/

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_play, container, false);
        tvPlan = v.findViewById(R.id.tv_plan);
        tvRestaurant = v.findViewById(R.id.tv_restaurant);
        Button btnPlan = v.findViewById(R.id.btn_find_plan);
        Button btnRestaurant = v.findViewById(R.id.btn_find_restaurant);

        btnPlan.setOnClickListener(new PlanButtonClicked());
        btnRestaurant.setOnClickListener(new RestaurantButtonClicked());

        // Get size of list from MainActivity to enable/disable buttons
        Bundle bundle = getArguments();
        if (bundle != null) {
            planListSize = bundle.getInt(MainActivity.PLANS_LIST_SIZE);
            restaurantListSize = bundle.getInt(MainActivity.RESTAURANT_LIST_SIZE);
        }
        btnPlan.setEnabled(planListSize != 0);
        btnRestaurant.setEnabled(restaurantListSize != 0);
        return v;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        initFragment(savedInstanceState);

        // ViewModels to observe lists.
        PlanViewModel pvm = new ViewModelProvider(this).get(PlanViewModel.class);
        RestaurantViewModel rvm = new ViewModelProvider(this).get(RestaurantViewModel.class);
        pvm.getAllPlans().observe(getViewLifecycleOwner(), this::setListPlans);
        rvm.getAllRestaurants().observe(getViewLifecycleOwner(), this::setListRestaurants);
    }

    /*--------------------------------------------------------------------------------------------*/
    /*------------------------------------------ HELPER ------------------------------------------*/
    /*--------------------------------------------------------------------------------------------*/

    /**
     * Required empty public constructor
     */
    public PlayFragment() {}

    /**
     * @return new {@link PlayFragment}
     */
    public static PlayFragment newInstance() {
        return new PlayFragment();
    }

    /**
     * Create new instance if fragment does not exist.
     */
    private void initFragment(Bundle savedInstanceState) {
        FragmentManager manager = getChildFragmentManager();
        if (savedInstanceState != null){
            playFragment = manager.getFragment(savedInstanceState, TAG);
        } else if (playFragment == null){
            playFragment = PlayFragment.newInstance();
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
     * Display random plan to user.
     */
    private class PlanButtonClicked implements View.OnClickListener {
        @Override
        public void onClick(View view) {
            tvPlan.setText(getRandomItem(planList));
        }
    }

    /**
     * Display random restaurant to user.
     */
    private class RestaurantButtonClicked implements View.OnClickListener {
        @Override
        public void onClick(View view) {
            tvRestaurant.setText(getRandomItem(restaurantList));
        }
    }

    /**
     * @param anEntityList list of objects which extend {@link AnEntity}.
     * @return random item from list
     */
    private String getRandomItem(List<? extends AnEntity> anEntityList) {
        Random random = new Random();
        List<AnEntity> aList = new ArrayList<>(anEntityList);
        int index = random.nextInt(aList.size());
        return aList.get(index).getName();
    }

}
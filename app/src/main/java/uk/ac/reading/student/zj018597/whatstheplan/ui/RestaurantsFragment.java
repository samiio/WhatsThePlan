package uk.ac.reading.student.zj018597.whatstheplan.ui;


import android.app.Activity;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.BaseTransientBottomBar;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import java.util.List;
import java.util.Objects;

import uk.ac.reading.student.zj018597.whatstheplan.R;
import uk.ac.reading.student.zj018597.whatstheplan.db.RestaurantEntity;
import uk.ac.reading.student.zj018597.whatstheplan.util.CustomSnackBar;
import uk.ac.reading.student.zj018597.whatstheplan.util.RestaurantListAdapter;
import uk.ac.reading.student.zj018597.whatstheplan.viewmodel.RestaurantViewModel;

import static uk.ac.reading.student.zj018597.whatstheplan.util.Animations.setFabAnimLift;

/**
 * A simple {@link Fragment} subclass.
 */
public class RestaurantsFragment extends Fragment {

    //The fragment and its tag
    private Fragment restaurantsFragment;
    private final static String TAG = "RESTAURANTS_FRAGMENT";

    private FloatingActionButton fabAdd;

    private RestaurantListAdapter adapter;
    private List<RestaurantEntity> restaurantList;

    //Holds a restaurant temporarily in case the user wants to undo
    private RestaurantEntity tempRestaurant;
    private int tempPosition;

    private RestaurantViewModel mRestaurantViewModel;

    public static final int ADD_RESTAURANT_ACTIVITY_REQUEST_CODE = 2;

    /*--------------------------------------------------------------------------------------------*/
    /*---------------------------------------- Lifecycle -----------------------------------------*/
    /*--------------------------------------------------------------------------------------------*/

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_restaurants, container, false);
        setFabAdd(v);
        setRecyclerView(v);
        return v;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        initFragment(savedInstanceState);

        mRestaurantViewModel = ViewModelProviders.of(this).get(RestaurantViewModel.class);
        mRestaurantViewModel.getAllRestaurants().observe(this, restaurants -> {
            // Update the cached copy of the words in the adapter.
            if (restaurantList == null) {
                setListRestaurants(restaurants);
            } else {
                adapter.setRestaurants(restaurants);
            }
        });
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == ADD_RESTAURANT_ACTIVITY_REQUEST_CODE && resultCode == MainActivity.RESULT_OK) {
            RestaurantEntity restaurant = new RestaurantEntity(
                    data.getStringExtra(AddRestaurantActivity.EXTRA_REPLY_RESTAURANT)
            );
            mRestaurantViewModel.insert(restaurant);
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
    public RestaurantsFragment() {}

    /**
     * Initialise this fragment.
     */
    private void initFragment(Bundle savedInstanceState) {
        FragmentManager manager = getChildFragmentManager();

        if (savedInstanceState != null){
            restaurantsFragment = manager.getFragment(savedInstanceState, TAG);
        } else {
            if (restaurantsFragment != null) { //Do nothing
                Log.i(TAG, ": fragment exists, do nothing");

            } else { //Create new instance
                restaurantsFragment = RestaurantsFragment.newInstance();

            }
        }
    }

    /**
     * Creates a new instance of the fragment
     * @return new {@link RestaurantsFragment}
     */
    public static RestaurantsFragment newInstance() {
        return new RestaurantsFragment();
    }

    /**
     * Sets the FloatingActionButton and its onClickListener
     */
    private void setFabAdd(View v) {
        fabAdd = v.findViewById(R.id.fab_add_restaurant);
        fabAdd.setOnClickListener(view1 -> {
            Intent intent = new Intent(getActivity(), AddRestaurantActivity.class);
            startActivityForResult(intent, ADD_RESTAURANT_ACTIVITY_REQUEST_CODE);
        });
    }

    /**
     * Plan list.
     */
    private void setListRestaurants(List<RestaurantEntity> restaurants) {
        restaurantList = restaurants;
        adapter.setRestaurants(restaurants);
    }

    /**
     * The {@link RecyclerView} which will display a list of {@link RestaurantEntity}
     */
    private void setRecyclerView(View v) {
        RecyclerView recyclerView = v.findViewById(R.id.rv_fragment_restaurants);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
        adapter = new RestaurantListAdapter(getActivity());
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
     * ItemTouchHelper to delete restaurant on swipe.
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

                // temp restaurant
                tempRestaurant = restaurantList.get(position);
                tempPosition = position;
                mRestaurantViewModel.delete(restaurantList.get(position));

                //ensure View is consistent with underlying data
                restaurantList.remove(position);
                adapter.notifyItemRemoved(position);

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
                activity, context, R.id.root_fragment_restaurants, getString(R.string.undo)
        );
        // Set SnackBar action:
        // if undo is confirmed, restore exercise, update recyclerView and database
        snackbar.setAction(R.string.undo, view -> {
            if (tempRestaurant != null) {
                restaurantList.add(tempRestaurant);
                adapter.notifyItemInserted(tempPosition);
                adapter.notifyDataSetChanged();
                mRestaurantViewModel.insert(tempRestaurant);
                // setFabAnimPull(fabAddExercise);
            } else {
                Toast.makeText(context, getText(R.string.restaurant_deleted), Toast.LENGTH_SHORT)
                        .show();
            }
        }).addCallback(new BaseTransientBottomBar.BaseCallback<Snackbar>() {
            // Dismiss tempRestaurant
            @Override
            public void onDismissed(Snackbar transientBottomBar, int event) {
                super.onDismissed(transientBottomBar, event);
                tempRestaurant = null;
                tempPosition = 0;
            }
        }).show();
    }

}

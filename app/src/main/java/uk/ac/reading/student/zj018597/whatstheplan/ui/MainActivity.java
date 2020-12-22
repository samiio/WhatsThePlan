package uk.ac.reading.student.zj018597.whatstheplan.ui;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import uk.ac.reading.student.zj018597.whatstheplan.R;
import uk.ac.reading.student.zj018597.whatstheplan.util.BottomNavigationViewHelper;
import uk.ac.reading.student.zj018597.whatstheplan.viewmodel.PlanViewModel;
import uk.ac.reading.student.zj018597.whatstheplan.viewmodel.RestaurantViewModel;

import static uk.ac.reading.student.zj018597.whatstheplan.util.Calculators.convertDpToPx;

/**
 * The main activity launched.
 * Holds two {@link Fragment}.
 */
public class MainActivity extends AppCompatActivity implements
        BottomNavigationView.OnNavigationItemSelectedListener {

    // The fragments and their tags
    private Fragment plansFragment, playFragment, restaurantsFragment;
    private final static String PLANS_TAG = "PLANS_FRAGMENT";
    private final static String PLAY_TAG = "PLAY_FRAGMENT";
    private final static String RESTAURANTS_TAG = "RESTAURANTS_FRAGMENT";

    private Toolbar toolbar;
    private TextView tvToolbarTitle;

    private int planCount, restaurantCount;
    public final static String PLANS_LIST_SIZE = "PLANS_LIST_SIZE";
    public final static String RESTAURANT_LIST_SIZE = "RESTAURANT_LIST_SIZE";

    /*--------------------------------------------------------------------------------------------*/
    /*---------------------------------------- Lifecycle -----------------------------------------*/
    /*--------------------------------------------------------------------------------------------*/

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initialiseFragments();
        setFragment(plansFragment, PLANS_TAG);
        setBottomNavigationView();
        setInitialToolbar();
        initialiseViewModels();
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        Fragment fragment;
        String tag;

        if (item.getItemId() == R.id.navigation_plans) {
            fragment = plansFragment;
            tag = PLANS_TAG;
            tvToolbarTitle.setText(getResources().getString(R.string.title_plans));
        } else if (item.getItemId() == R.id.navigation_restaurants) {
            fragment = restaurantsFragment;
            tag = RESTAURANTS_TAG;
            tvToolbarTitle.setText(getResources().getString(R.string.title_restaurants));
        } else {
            Bundle bundle = new Bundle();
            bundle.putInt(PLANS_LIST_SIZE, planCount);
            bundle.putInt(RESTAURANT_LIST_SIZE, restaurantCount);
            fragment = playFragment;
            fragment.setArguments(bundle);
            tag = PLAY_TAG;
            tvToolbarTitle.setText(getResources().getString(R.string.title_play));
        }
        toolbar.setElevation(toolbarElevation());
        return setFragment(fragment, tag);
    }

    /*--------------------------------------------------------------------------------------------*/
    /*------------------------------------------ HELPER ------------------------------------------*/
    /*--------------------------------------------------------------------------------------------*/

    /**
     * Fragments that will be displayed on this activity.
     */
    private void initialiseFragments() {
        plansFragment = new PlansFragment();
        playFragment = new PlayFragment();
        restaurantsFragment = new RestaurantsFragment();
    }

    /**
     * Sets a fragments on this Activity's FrameLayout.
     */
    private boolean setFragment(Fragment fragment, String tag) {
        FragmentManager manager = getSupportFragmentManager();
        manager.findFragmentByTag(tag);
        FragmentTransaction ft = manager.beginTransaction();
        if (fragment != null) {
            ft.replace(R.id.fl_main, fragment, tag).commit();
        }
        return true;
    }

    /**
     * Format the {@link BottomNavigationView}.
     */
    private void setBottomNavigationView() {
        BottomNavigationView bnv = findViewById(R.id.navigation_menu);
        BottomNavigationViewHelper.disableShiftMode(bnv);
        Menu menu = bnv.getMenu();
        MenuItem menuItem = menu.getItem(0);
        menuItem.setChecked(true);
        bnv.setElevation(toolbarElevation());
        bnv.setOnNavigationItemSelectedListener(this);
    }

    /**
     * Format the {@link Toolbar}.
     */
    private void setInitialToolbar() {
        toolbar = findViewById(R.id.tbr_main_activity);
        tvToolbarTitle = toolbar.findViewById(R.id.tbr_main_title);
        toolbar.setTitle("");
        tvToolbarTitle.setText(getResources().getString(R.string.title_plans));
        setSupportActionBar(toolbar);
    }

    /**
     * Returns pixel value for the {@link #toolbar}.
     */
    private float toolbarElevation() {
        return convertDpToPx(getApplicationContext(), 4);
    }

    /**
     * Attach {@link Observer} to set {@link #planCount} and {@link #restaurantCount}.
     */
    private void initialiseViewModels() {
        PlanViewModel pvm = new ViewModelProvider(this).get(PlanViewModel.class);
        pvm.getCount().observe(this, integer -> planCount = integer);
        RestaurantViewModel rvm = new ViewModelProvider(this).get(RestaurantViewModel.class);
        rvm.getCount().observe(this, integer -> restaurantCount = integer);
    }

}

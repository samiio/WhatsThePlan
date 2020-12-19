package uk.ac.reading.student.zj018597.whatstheplan.ui;

import android.os.Bundle;
import androidx.annotation.NonNull;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import uk.ac.reading.student.zj018597.whatstheplan.R;
import uk.ac.reading.student.zj018597.whatstheplan.util.BottomNavigationViewHelper;

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

    // Bottom navigation view for the menu
    private BottomNavigationView bottomNavigationView;

    //Toolbar and its title
    private Toolbar toolbar;
    private TextView tvToolbarTitle;

    /*--------------------------------------------------------------------------------------------*/
    /*---------------------------------------- Lifecycle -----------------------------------------*/
    /*--------------------------------------------------------------------------------------------*/

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Initialising the fragments
        initialiseFragments();

        //Setting the first fragment
        setInitialFragment(savedInstanceState);

        //Setting the BottomNavigationView
        setBottomNavigationView();

        setInitialToolbar();

    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {

        Fragment fragment = null;
        String tag = "";

        switch (item.getItemId()) {
            case R.id.navigation_plans:
                fragment = plansFragment;
                tag = PLANS_TAG;
                tvToolbarTitle.setText(getResources().getString(R.string.title_plans));
                toolbar.setElevation(toolbarElevation());
                break;

            case R.id.navigation_restaurants:
                fragment = restaurantsFragment;
                tag = RESTAURANTS_TAG;
                tvToolbarTitle.setText(getResources().getString(R.string.title_restaurants));
                toolbar.setElevation(toolbarElevation());
                break;

            case R.id.navigation_play:
                fragment = playFragment;
                tag = PLAY_TAG;
                tvToolbarTitle.setText(getResources().getString(R.string.title_play));
                toolbar.setElevation(toolbarElevation());
                break;

            default:
                break;
        }
        return setTheFragment(fragment, tag);
    }

    /*--------------------------------------------------------------------------------------------*/
    /*------------------------------------------ HELPER ------------------------------------------*/
    /*--------------------------------------------------------------------------------------------*/

    /**
     * Initialise the fragments which will be accessed from the {@link #bottomNavigationView}.
     */
    private void initialiseFragments() {
        plansFragment = new PlansFragment();
        playFragment = new PlayFragment();
        restaurantsFragment = new RestaurantsFragment();
    }

    /**
     * Sets up the initial fragment that will be launched when the main activity is launched
     */
    private void setInitialFragment(Bundle savedInstanceState) {

        FragmentManager manager = getSupportFragmentManager();
        if (savedInstanceState == null) {
            plansFragment = PlansFragment.newInstance();
            replaceFragment(manager, plansFragment, R.id.fl_main, PLANS_TAG);
        } else {
            plansFragment = manager.getFragment(savedInstanceState, PLANS_TAG);
        }
    }

    /**
     * Called to replace a {@link Fragment}.
     */
    public static void replaceFragment(FragmentManager manager, Fragment fragment,
                                       int frameId, String tag) {
        FragmentTransaction transaction = manager.beginTransaction();
        transaction.replace(frameId, fragment, tag);
        transaction.commit();
    }

    /**
     * Sets the fragments from {@link #initialiseFragments()} to the frameLayout.
     * https://stackoverflow.com/questions/18305945/how-to-resume-fragment-from-backstack-if-exists
     */
    private boolean setTheFragment(Fragment fragment, String tag) {
        FragmentManager manager = getSupportFragmentManager();
        manager.findFragmentByTag(tag);
        FragmentTransaction ft = manager.beginTransaction();

        // No fragment in backStack with same tag..
        if (manager.findFragmentByTag(tag) == null) {
            ft.replace(R.id.fl_main, fragment, tag)
                    .commit();
            return true;

        }
        else {
            ft.show(manager.findFragmentByTag(tag))
                    .commit();
            return false;
        }
    }

    /**
     * Sets up the {@link BottomNavigationView}
     */
    private void setBottomNavigationView() {
        bottomNavigationView = findViewById(R.id.navigation_menu);

        // Formatting the bnv
        BottomNavigationViewHelper.disableShiftMode(bottomNavigationView);
        Menu menu = bottomNavigationView.getMenu();
        MenuItem menuItem = menu.getItem(0);
        menuItem.setChecked(true);
        bottomNavigationView.setElevation(toolbarElevation());
        bottomNavigationView.setOnNavigationItemSelectedListener(this);
    }

    /**
     * Sets up the {@link #toolbar} title
     */
    private void setInitialToolbar() {
        toolbar = findViewById(R.id.tbr_main_activity);
        tvToolbarTitle = toolbar.findViewById(R.id.tbr_main_title);

        toolbar.setTitle("");
        tvToolbarTitle.setText(getResources().getString(R.string.title_plans));
        setSupportActionBar(toolbar);
    }

    /**
     * Toolbar elevation.
     */
    private float toolbarElevation() {
        return convertDpToPx(getApplicationContext(), 4);
    }

}

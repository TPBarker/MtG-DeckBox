package com.example.mtgdeckbox;

import android.os.Bundle;
import android.view.View;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.example.mtgdeckbox.databinding.HomeBinding;

/**
 * This is the Home Activity which the User it sent to when they have logged
 * into the application. It contains the navigation drawers for the User to navigate
 * around the rest of the application.
 * @author: Tom Barker
 */
public class HomeActivity extends AppCompatActivity {
    private HomeBinding binding;
    private AppBarConfiguration mAppBarConfiguration;

    /**
     * This is the default constructor.
     */
    public HomeActivity() {}

    /**
     * This is the Accessor method for the binding field.
     * @return a HomeBinding object which is used to bind all the views in
     * the Activity.
     */
    public HomeBinding getBinding() {
        return binding;
    }

    /**
     * This is the Accessor method for the mAppBarConfiguration field.
     * @return an AppBarConfiguration object which is used to create the Navigation
     * Drawers used to navigate this Activity.
     */
    public AppBarConfiguration getmAppBarConfiguration() {
        return mAppBarConfiguration;
    }

    /**
     * This method runs when the Activity is launched.
     * @param savedInstanceState a Bundle which contains the saved instance state.
     *                           The Activity can may restore itself to a saved
     *                           state under special circumstances.
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // Initialise some fields and setup the Activity.
        super.onCreate(savedInstanceState);
        binding = HomeBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);

        // Navigation Drawer setup.
        setSupportActionBar(binding.appBar.toolbar);

        /* Setup an AppBarConfiguration with all top-level destinations.
         * Developer reminder:
         * To set a destination as not 'top-level', do not list it here
         * (its icon will become an arrow instead of a hamburger, and it will be
         * able to navigate 'backwards' to the top-level).
         */
        mAppBarConfiguration = new AppBarConfiguration.Builder(
                R.id.homeFragment,
                R.id.aboutFragment,
                R.id.builderFragment)
                .setOpenableLayout(binding.drawerLayoutMain)
                .build();
        FragmentManager fragmentManager = getSupportFragmentManager();
        NavHostFragment navHostFragment = (NavHostFragment)
                fragmentManager.findFragmentById(R.id.nav_host_fragment);
        NavController navController = navHostFragment.getNavController();
        NavigationUI.setupWithNavController(binding.navView, navController);
        NavigationUI.setupWithNavController(binding.appBar.toolbar, navController,
                mAppBarConfiguration);
    }

    /**
     * This is the Mutator method for the binding field.
     * @param binding a HomeBinding object which will be used to bind all of the
     *                views in the Activity.
     */
    public void setBinding(HomeBinding binding) {
        this.binding = binding;
    }

    /**
     * This is the Mutator method for the mAppBarConfiguration field.
     * @param mAppBarConfiguration an AppBarConfiguration object which will be
     *                             used to setup the Navigation Drawers.
     */
    public void setmAppBarConfiguration(AppBarConfiguration mAppBarConfiguration) {
        this.mAppBarConfiguration = mAppBarConfiguration;
    }
}

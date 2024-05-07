package github.hmasum52.androidstarterjava.view;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;
import androidx.navigation.NavDestination;
import androidx.navigation.fragment.NavHostFragment;
import androidx.navigation.ui.NavigationUI;

import android.os.Bundle;
import android.view.View;

import javax.inject.Inject;

import dagger.hilt.android.AndroidEntryPoint;
import github.hmasum52.androidstarterjava.R;
import github.hmasum52.androidstarterjava.api.FakeStoreApi;
import github.hmasum52.androidstarterjava.databinding.ActivityMainBinding;
import github.hmasum52.androidstarterjava.util.GPS;

@AndroidEntryPoint
public class MainActivity extends AppCompatActivity {

    ActivityMainBinding binding;

    int currentFragment;

    @Inject
    GPS gps; // this is a must call to avoid exception

    @Inject
    FakeStoreApi api;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        NavHostFragment navHostFragment = (NavHostFragment) getSupportFragmentManager()
                .findFragmentById(R.id.nav_host_fragment);

        NavController navController = navHostFragment.getNavController();

        NavigationUI.setupWithNavController(binding.bottomNav, navController);

        navController.addOnDestinationChangedListener(new NavController.OnDestinationChangedListener() {
            @Override
            public void onDestinationChanged(@NonNull NavController navController, @NonNull NavDestination navDestination, @Nullable Bundle bundle) {
                if(navDestination.getId() == R.id.welcomeFragment){
                    binding.bottomNav.setVisibility(View.GONE);
                }else{
                    binding.bottomNav.setVisibility(View.VISIBLE);
                }
                currentFragment = navDestination.getId();
            }
        });
    }

    @Override
    public void onBackPressed() {
        if(currentFragment == R.id.homeFragment || currentFragment == R.id.searchFragment || currentFragment == R.id.profileFragment){
            super.finish();
        }else{
            super.onBackPressed();
        }
    }
}
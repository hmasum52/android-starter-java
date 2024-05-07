package github.hmasum52.androidstarterjava.hilt;

import androidx.fragment.app.FragmentActivity;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import dagger.hilt.InstallIn;
import dagger.hilt.android.components.ActivityComponent;
import dagger.hilt.android.scopes.ActivityScoped;
import github.hmasum52.androidstarterjava.util.GPS;

@Module
@InstallIn(ActivityComponent.class)
public class ActivityModule {

    @Provides
    @ActivityScoped
    public static GPS provideDeviceLocationFinder(FragmentActivity mainActivity){
        return new GPS(mainActivity);
    }
}

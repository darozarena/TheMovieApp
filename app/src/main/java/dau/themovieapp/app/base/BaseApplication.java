package dau.themovieapp.app.base;

import android.app.Application;

import dau.themovieapp.di.component.AppContextComponent;
import dau.themovieapp.di.component.DaggerAppContextComponent;
import dau.themovieapp.di.component.MainComponent;
import dau.themovieapp.di.module.AppContextModule;
import dau.themovieapp.di.module.MainModule;
import dau.themovieapp.di.module.NetworkModule;

public class BaseApplication extends Application {
    private AppContextComponent mAppContextComponent;
    private MainComponent mMainComponent;

    @Override
    public void onCreate() {
        super.onCreate();
        mAppContextComponent = createAppComponent();
    }

    private AppContextComponent createAppComponent() {
        return DaggerAppContextComponent.builder()
                .appContextModule(new AppContextModule(this))
                .networkModule(new NetworkModule())
                .build();
    }

    public MainComponent createMainComponent() {
        mMainComponent = mAppContextComponent.plus(new MainModule());
        return mMainComponent;
    }

    public void releaseMainComponent() {
        mMainComponent = null;
    }

    public MainComponent getMainComponent() {
        return mMainComponent;
    }
}

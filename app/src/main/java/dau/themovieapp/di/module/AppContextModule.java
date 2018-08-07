package dau.themovieapp.di.module;

import android.app.Application;
import android.content.Context;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module
public class AppContextModule {
    private Context mContext;

    public AppContextModule(Application application) {
        mContext = application;
    }

    @Provides
    @Singleton
    public Context provideContext() {
        return mContext;
    }

}

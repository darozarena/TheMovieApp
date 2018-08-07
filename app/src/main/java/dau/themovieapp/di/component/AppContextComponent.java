package dau.themovieapp.di.component;

import javax.inject.Singleton;

import dagger.Component;
import dau.themovieapp.di.module.AppContextModule;
import dau.themovieapp.di.module.MainModule;
import dau.themovieapp.di.module.NetworkModule;

@Singleton
@Component(modules = {AppContextModule.class, NetworkModule.class})
public interface AppContextComponent {
    MainComponent plus(MainModule mainModule);
}

package dau.themovieapp.di.component;

import dagger.Subcomponent;
import dau.themovieapp.app.ui.MainFragment;
import dau.themovieapp.di.module.MainModule;
import dau.themovieapp.di.scope.MainScope;

@MainScope
@Subcomponent(modules = MainModule.class)
public interface MainComponent {
    MainFragment inject(MainFragment fragment);
}

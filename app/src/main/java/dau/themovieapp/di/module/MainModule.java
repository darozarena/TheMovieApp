package dau.themovieapp.di.module;

import dagger.Module;
import dagger.Provides;
import dau.themovieapp.domain.interactor.MainInteractor;
import dau.themovieapp.domain.interactor.MainInteractorImpl;
import dau.themovieapp.presentation.MainPresenter;
import dau.themovieapp.presentation.MainPresenterImpl;
import dau.themovieapp.domain.rest.TheMovieRepository;

@Module
public class MainModule {
    @Provides
    MainInteractor provideMainInteractor(TheMovieRepository theMovieRepository) {
        return new MainInteractorImpl(theMovieRepository);
    }

    @Provides
    MainPresenter provideMainPresenter(MainInteractor interactor) {
        return new MainPresenterImpl(interactor);
    }
}

package dau.themovieapp.domain.interactor;

import android.support.annotation.NonNull;

import java.util.List;

import dau.themovieapp.domain.model.Movie;
import dau.themovieapp.domain.model.MoviesContent;
import dau.themovieapp.domain.rest.TheMovieRepository;
import io.reactivex.Observable;

public class MainInteractorImpl implements MainInteractor {
    private TheMovieRepository theMovieRepository;

    public MainInteractorImpl(TheMovieRepository theMovieRepository) {
        this.theMovieRepository = theMovieRepository;
    }

    @Override
    public Observable<List<Movie>> getMovies(int page) {
        return theMovieRepository.getMovies(page).map(MoviesContent::getMoviesList);
    }

    @Override
    public Observable<List<Movie>> searchMovie(@NonNull String searchQuery) {
        return theMovieRepository.searchMovies(searchQuery).map(MoviesContent::getMoviesList);
    }
}

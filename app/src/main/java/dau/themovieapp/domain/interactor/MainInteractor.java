package dau.themovieapp.domain.interactor;

import java.util.List;

import dau.themovieapp.domain.model.Movie;
import io.reactivex.Observable;

public interface MainInteractor {
    Observable<List<Movie>> getMovies(int page);

    Observable<List<Movie>> searchMovie(String searchQuery);
}

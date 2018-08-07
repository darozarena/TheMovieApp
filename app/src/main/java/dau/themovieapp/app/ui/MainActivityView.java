package dau.themovieapp.app.ui;

import java.util.List;

import dau.themovieapp.domain.model.Movie;

public interface MainActivityView {
    void showMovies(List<Movie> movies);

    void loadingFailed(String errorMessage);

    String getEmptyMessage();
}

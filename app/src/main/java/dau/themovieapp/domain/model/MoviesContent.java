package dau.themovieapp.domain.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class MoviesContent {

    @SerializedName("results")
    private List<Movie> movies;

    public List<Movie> getMoviesList() {
        return movies;
    }

    public void setMoviesList(List<Movie> movieList) {
        this.movies = movieList;
    }
}

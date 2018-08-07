package dau.themovieapp.domain.rest;

import dau.themovieapp.domain.api.Api;
import dau.themovieapp.domain.model.MoviesContent;
import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface TheMovieRepository {

    @GET(Api.GET_MOVIES)
    Observable<MoviesContent> getMovies(@Query("page") int page);

    @GET(Api.SEARCH_MOVIES)
    Observable<MoviesContent> searchMovies(@Query("query") String searchQuery);

}

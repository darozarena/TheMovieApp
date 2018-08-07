package dau.themovieapp.presentation;

import dau.themovieapp.app.ui.MainActivityView;

public interface MainPresenter {
    void firstPage();

    void nextPage();

    void setmView(MainActivityView mView);

    void searchMovie(String searchText);

    void searchMovieBackPressed();

    void destroy();
}

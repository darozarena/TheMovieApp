package dau.themovieapp.presentation;

import android.support.annotation.NonNull;
import android.text.TextUtils;

import java.util.ArrayList;
import java.util.List;

import dau.themovieapp.app.ui.MainActivityView;
import dau.themovieapp.app.utils.EspressoIdlingResource;
import dau.themovieapp.domain.interactor.MainInteractor;
import dau.themovieapp.domain.model.Movie;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class MainPresenterImpl implements MainPresenter {
    private MainActivityView mView;
    private MainInteractor mMoviesInteractor;
    private Disposable mFetchSubscription;
    private Disposable mMovieSearchSubscription;
    private int mCurrentPage = 1;
    private List<Movie> mLoadedMovies = new ArrayList<>();
    private boolean mShowingSearchResult = false;

    public MainPresenterImpl(MainInteractor interactor) {
        mMoviesInteractor = interactor;
    }

    @Override
    public void setmView(MainActivityView mView) {
        this.mView = mView;
        if (!mShowingSearchResult) {
            getMovies();
        }
    }

    @Override
    public void destroy() {
        mView = null;
        for (Disposable subscription : new Disposable[]{mFetchSubscription, mMovieSearchSubscription}) {
            if (subscription != null && !subscription.isDisposed()) {
                subscription.dispose();
            }
        }
    }

    private void getMovies() {
        EspressoIdlingResource.increment();
        mFetchSubscription = mMoviesInteractor.getMovies(mCurrentPage)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doFinally(() -> {
                    if (!EspressoIdlingResource.getIdlingResource().isIdleNow()) {
                        EspressoIdlingResource.decrement();
                    }
                })
                .subscribe(this::onGetMoviesSuccess, this::onGetMoviesFailed);
    }

    private void getMovieSearchResult(@NonNull final String searchText) {
        mShowingSearchResult = true;
        mMovieSearchSubscription = mMoviesInteractor.searchMovie(searchText)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(this::onGetMovieSearchSuccess, this::onGetMovieSearchFailed);
    }

    @Override
    public void firstPage() {
        mCurrentPage = 1;
        mLoadedMovies.clear();
        getMovies();
    }

    @Override
    public void nextPage() {
        mCurrentPage++;
        getMovies();
    }

    @Override
    public void searchMovie(final String searchText) {
        if (searchText == null || TextUtils.isEmpty(searchText)) {
            getMovies();
        } else {
            getMovieSearchResult(searchText);
        }

    }

    @Override
    public void searchMovieBackPressed() {
        if (mShowingSearchResult) {
            mShowingSearchResult = false;
            mLoadedMovies.clear();
            getMovies();
        }
    }

    private void onGetMoviesSuccess(List<Movie> movies) {
        mLoadedMovies.addAll(movies);
        if (isViewAttached()) {
            mView.showMovies(mLoadedMovies);
        }
    }

    private void onGetMoviesFailed(Throwable e) {
        mView.loadingFailed(e.getMessage());
    }

    private void onGetMovieSearchSuccess(List<Movie> movies) {
        mLoadedMovies.clear();
        mLoadedMovies = new ArrayList<>(movies);
        if (isViewAttached()) {
            mView.showMovies(mLoadedMovies);
        }
        if (movies.size() == 0) {
            mView.loadingFailed(mView.getEmptyMessage());
        }
    }

    private void onGetMovieSearchFailed(Throwable e) {
        mView.loadingFailed(e.getMessage());
    }

    private boolean isViewAttached() {
        return mView != null;
    }
}

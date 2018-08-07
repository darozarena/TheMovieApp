package dau.themovieapp.app.ui;

import android.os.Bundle;
import android.os.Parcelable;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import dau.themovieapp.R;
import dau.themovieapp.app.base.BaseApplication;
import dau.themovieapp.app.ui.adapter.MoviesAdapter;
import dau.themovieapp.app.utils.Constants;
import dau.themovieapp.domain.model.Movie;
import dau.themovieapp.presentation.MainPresenter;

public class MainFragment extends Fragment implements MainActivityView {
    @Inject
    MainPresenter mMainPresenter;

    @BindView(R.id.rv_movies)
    RecyclerView mRvMovies;

    private RecyclerView.Adapter mAdapter;
    private List<Movie> mMovies = new ArrayList<>(20);
    private Unbinder mUnbinder;

    public MainFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRetainInstance(true);
        ((BaseApplication) getActivity().getApplication()).createMainComponent().inject(this);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_main, container, false);
        mUnbinder = ButterKnife.bind(this, rootView);
        initLayoutReferences();
        mRvMovies.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);

                if (!recyclerView.canScrollVertically(1)) {
                    mMainPresenter.nextPage();
                }
            }
        });
        return rootView;
    }

    @Override
    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mMainPresenter.setmView(this);
        if (savedInstanceState != null) {
            mMovies = savedInstanceState.getParcelableArrayList(Constants.MOVIES);
            mAdapter.notifyDataSetChanged();
            mRvMovies.setVisibility(View.VISIBLE);
        } else {
            mMainPresenter.firstPage();
        }
    }

    private void initLayoutReferences() {
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getActivity());

        mRvMovies.setLayoutManager(layoutManager);
        mAdapter = new MoviesAdapter(mMovies);
        mRvMovies.setAdapter(mAdapter);
    }

    @Override
    public void showMovies(List<Movie> movies) {
        this.mMovies.clear();
        this.mMovies.addAll(movies);
        mRvMovies.setVisibility(View.VISIBLE);
        mAdapter.notifyDataSetChanged();
    }

    @Override
    public void loadingFailed(String errorMessage) {
        Snackbar.make(mRvMovies, errorMessage, Snackbar.LENGTH_SHORT).show();
    }

    @Override
    public String getEmptyMessage() {
        return getString(R.string.empty_search);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        mMainPresenter.destroy();
        mUnbinder.unbind();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        ((BaseApplication) getActivity().getApplication()).releaseMainComponent();
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putParcelableArrayList(Constants.MOVIES, (ArrayList<? extends Parcelable>) mMovies);
    }

    public void searchViewClicked(String searchText) {
        mMainPresenter.searchMovie(searchText);
    }

    public void searchViewBackButtonClicked() {
        mMainPresenter.searchMovieBackPressed();
    }

}

package dau.themovieapp;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.ArrayList;
import java.util.List;

import dau.themovieapp.app.ui.MainActivityView;
import dau.themovieapp.domain.interactor.MainInteractor;
import dau.themovieapp.domain.model.Movie;
import dau.themovieapp.presentation.MainPresenterImpl;
import io.reactivex.Observable;

import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class MainPresenterImplTest {
    @Rule
    public RxSchedulerRule mRule = new RxSchedulerRule();
    @Mock
    private MainInteractor mInteractor;
    @Mock
    private MainActivityView mView;

    private List<Movie> mMovies = new ArrayList<>(0);

    private MainPresenterImpl mPresenter;

    @Before
    public void setUp() throws Exception {
        mPresenter = new MainPresenterImpl(mInteractor);
    }

    @After
    public void teardown() {
        mPresenter.destroy();
    }

    @Test
    public void shouldBeAbleToDisplayMovies() {
        // given:
        Observable<List<Movie>> responseObservable = Observable.just(mMovies);
        when(mInteractor.getMovies(anyInt())).thenReturn(responseObservable);

        // when:
        mPresenter.setmView(mView);

        // then:
        verify(mView).showMovies(mMovies);
    }
}
package dau.themovieapp.app.ui;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.VisibleForTesting;
import android.support.test.espresso.IdlingResource;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import com.jakewharton.rxbinding2.support.v7.widget.RxSearchView;

import java.util.concurrent.TimeUnit;

import dau.themovieapp.R;
import dau.themovieapp.app.utils.EspressoIdlingResource;
import io.reactivex.disposables.Disposable;

public class MainActivity extends AppCompatActivity {

    private Disposable mSearchViewTextSubscription;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setToolbar();
    }

    private void setToolbar() {
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        if (getSupportActionBar() != null) {
            getSupportActionBar().setTitle(R.string.app_name);
            getSupportActionBar().setDisplayShowTitleEnabled(true);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        final MenuItem searchItem = menu.findItem(R.id.action_search);
        final SearchView searchView = (SearchView) searchItem.getActionView();
        final MainFragment mlFragment = (MainFragment) getSupportFragmentManager().findFragmentById(R.id.fragment_movies);
        searchItem.setOnActionExpandListener(new MenuItem.OnActionExpandListener() {
            @Override
            public boolean onMenuItemActionExpand(MenuItem item) {
                return true;
            }

            @Override
            public boolean onMenuItemActionCollapse(MenuItem item) {
                MainFragment mlFragment = (MainFragment) getSupportFragmentManager().findFragmentById(R.id.fragment_movies);
                mlFragment.searchViewBackButtonClicked();
                return true;
            }
        });

        mSearchViewTextSubscription = RxSearchView.queryTextChanges(searchView)
                .debounce(300, TimeUnit.MILLISECONDS)
                .subscribe(charSequence -> {
                    if (charSequence.length() > 0) {
                        mlFragment.searchViewClicked(charSequence.toString());
                    }
                });

        return true;
    }

    @VisibleForTesting
    @NonNull
    public IdlingResource getCountingIdlingResource() {
        return EspressoIdlingResource.getIdlingResource();
    }

    @Override
    protected void onDestroy() {
        if (mSearchViewTextSubscription != null && !mSearchViewTextSubscription.isDisposed()) {
            mSearchViewTextSubscription.dispose();
        }
        super.onDestroy();
    }
}
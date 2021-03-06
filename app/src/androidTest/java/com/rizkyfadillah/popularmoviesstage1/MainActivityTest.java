package com.rizkyfadillah.popularmoviesstage1;

import android.app.Instrumentation;
import android.support.test.InstrumentationRegistry;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

import com.rizkyfadillah.popularmoviesstage1.di.ActivityScope;
import com.rizkyfadillah.popularmoviesstage1.ui.main.MainActivity;
import com.rizkyfadillah.popularmoviesstage1.ui.main.MainActivityComponent;
import com.rizkyfadillah.popularmoviesstage1.ui.main.MainViewModel;
import com.rizkyfadillah.popularmoviesstage1.vo.Movie;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.ArrayList;
import java.util.List;

import dagger.BindsInstance;
import dagger.Component;
import io.reactivex.Observable;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static org.hamcrest.CoreMatchers.not;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

/**
 * Created by Rizky on 19/02/18.
 */
@RunWith(AndroidJUnit4.class)
public class MainActivityTest {

    private MainViewModel mainViewModel;

    @Rule
    public ActivityTestRule<MainActivity> activityTestRule = new ActivityTestRule<>(
            MainActivity.class,
            true,
            false
    );

    @ActivityScope
    @Component
    public interface MainActivityTestComponent extends MainActivityComponent {

        @Component.Builder
        interface Builder {
            MainActivityTestComponent build();

            @BindsInstance Builder mainViewModel(MainViewModel mainViewModel);
        }

    }

    @Before
    public void setup() {
        Instrumentation instrumentation = InstrumentationRegistry.getInstrumentation();

        TestApp testApp = (TestApp) instrumentation.getTargetContext().getApplicationContext();

        mainViewModel = mock(MainViewModel.class);

        MainActivityTestComponent mainActivityTestComponent =
                DaggerMainActivityTest_MainActivityTestComponent
                        .builder()
                        .mainViewModel(mainViewModel)
                        .build();

        testApp.setMainActivityComponent(mainActivityTestComponent);
    }

    @Test
    public void test() {
        List<Movie> movies = new ArrayList<>();
        movies.add(new Movie());
        movies.add(new Movie());
        movies.add(new Movie());

        Observable<List<Movie>> observable = Observable.just(movies);

        when(mainViewModel.getMovies("popular")).thenReturn(observable);

        activityTestRule.launchActivity(null);

        onView(withId(R.id.progressbar)).check(matches(not(isDisplayed())));
    }

}

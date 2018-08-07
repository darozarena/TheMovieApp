package dau.themovieapp;

import org.junit.rules.TestRule;
import org.junit.runner.Description;
import org.junit.runners.model.Statement;

import java.util.concurrent.Callable;

import io.reactivex.Scheduler;
import io.reactivex.android.plugins.RxAndroidPlugins;
import io.reactivex.functions.Function;
import io.reactivex.plugins.RxJavaPlugins;
import io.reactivex.schedulers.Schedulers;

/**
 * JUnit Test Rule which overrides RxJava and Android schedulers for use in unit tests.
 * <p>
 * All schedulers are replaced with Schedulers.trampoline().
 */
public class RxSchedulerRule implements TestRule {

    private Scheduler mScheduler = Schedulers.trampoline();

    private Function<Scheduler, Scheduler> mSchedulerFunction = scheduler -> mScheduler;

    private Function<Callable<Scheduler>, Scheduler> mSchedulerFunctionLazy = schedulerCallable -> mScheduler;

    @Override
    public Statement apply(final Statement base, Description description) {
        return new Statement() {
            @Override
            public void evaluate() throws Throwable {
                RxAndroidPlugins.reset();
                RxAndroidPlugins.setInitMainThreadSchedulerHandler(mSchedulerFunctionLazy);

                RxJavaPlugins.reset();
                RxJavaPlugins.setIoSchedulerHandler(mSchedulerFunction);
                RxJavaPlugins.setNewThreadSchedulerHandler(mSchedulerFunction);
                RxJavaPlugins.setComputationSchedulerHandler(mSchedulerFunction);

                base.evaluate();

                RxAndroidPlugins.reset();
                RxJavaPlugins.reset();
            }
        };
    }
}
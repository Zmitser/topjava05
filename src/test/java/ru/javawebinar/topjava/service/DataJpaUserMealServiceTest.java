package ru.javawebinar.topjava.service;

import org.junit.Rule;
import org.junit.rules.Stopwatch;
import org.junit.runner.Description;
import org.springframework.test.context.ActiveProfiles;
import ru.javawebinar.topjava.LoggerWrapper;
import ru.javawebinar.topjava.Profiles;

import java.util.concurrent.TimeUnit;



@ActiveProfiles(profiles = {Profiles.HSQLDB, Profiles.DATAJPA})
public class DataJpaUserMealServiceTest extends BasicUserMealServiceTest {
    private static final LoggerWrapper LOG = LoggerWrapper.get(DataJpaUserMealServiceTest.class);

    @Rule
    public Stopwatch stopwatch = new Stopwatch() {
        private void logInfo(Description description, long nanos) {
            LOG.info(String.format("+++ Test %s spent %d microseconds",
                    description.getMethodName(), TimeUnit.NANOSECONDS.toMicros(nanos)));
        }

        @Override
        protected void finished(long nanos, Description description) {
            logInfo(description, nanos);
        }
    };

}
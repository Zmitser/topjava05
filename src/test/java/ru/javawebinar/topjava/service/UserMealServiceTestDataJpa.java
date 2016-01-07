package ru.javawebinar.topjava.service;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.Stopwatch;
import org.junit.runner.Description;
import org.springframework.test.context.ActiveProfiles;
import ru.javawebinar.topjava.LoggerWrapper;
import ru.javawebinar.topjava.Profiles;
import ru.javawebinar.topjava.UserTestData;
import ru.javawebinar.topjava.model.User;
import ru.javawebinar.topjava.model.UserMeal;

import java.util.concurrent.TimeUnit;

import static ru.javawebinar.topjava.MealTestData.MATCHER;
import static ru.javawebinar.topjava.MealTestData.MEAL1;
import static ru.javawebinar.topjava.MealTestData.MEAL1_ID;
import static ru.javawebinar.topjava.UserTestData.*;


@ActiveProfiles(profiles = {Profiles.POSTGRES, Profiles.DATAJPA})
public class UserMealServiceTestDataJpa extends UserMealServiceTest {
    private static final LoggerWrapper LOG = LoggerWrapper.get(UserMealServiceTestDataJpa.class);
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

    @Test
    public void testGetAllWithUser() throws Exception {
        UserMeal mealWithUser = service.getMealWithUser(MEAL1_ID);
        User user = mealWithUser.getUser();
        MATCHER.assertEquals(MEAL1, mealWithUser);
        UserTestData.MATCHER.assertEquals(USER, user);

    }

}
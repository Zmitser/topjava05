package ru.javawebinar.topjava.service;

import org.junit.Test;
import org.springframework.test.context.ActiveProfiles;
import ru.javawebinar.topjava.Profiles;
import ru.javawebinar.topjava.UserTestData;
import ru.javawebinar.topjava.model.User;
import ru.javawebinar.topjava.model.UserMeal;

import java.util.List;
import java.util.Map;

import static ru.javawebinar.topjava.MealTestData.MATCHER;
import static ru.javawebinar.topjava.MealTestData.USER_MEALS;
import static ru.javawebinar.topjava.UserTestData.USER;
import static ru.javawebinar.topjava.UserTestData.USER_ID;


@ActiveProfiles(profiles = {Profiles.POSTGRES, Profiles.DATAJPA})
public class DataJpaUserServiceTest extends BasicUserServiceTest {


    @Test
    public void testGetUserWithMeals() throws Exception {
        Map< User, List<UserMeal>> allWithUser = service.getUserWithMeals(USER_ID);
        for (Map.Entry< User, List<UserMeal>> pair : allWithUser.entrySet()) {
            UserTestData.MATCHER.assertEquals(USER, pair.getKey());
            MATCHER.assertCollectionEquals(USER_MEALS, pair.getValue());
        }

    }
}
package ru.javawebinar.topjava.service;

import org.junit.Test;
import org.springframework.test.context.ActiveProfiles;
import ru.javawebinar.topjava.MealTestData;
import ru.javawebinar.topjava.Profiles;
import ru.javawebinar.topjava.UserTestData;
import ru.javawebinar.topjava.model.User;
import ru.javawebinar.topjava.model.UserMeal;

import java.util.List;

@ActiveProfiles(profiles = {Profiles.POSTGRES, Profiles.DATAJPA})
public class UserServiceTestDataJpa extends UserServiceTest {


    @Test
    public void testGetUserWithMeals() throws Exception {
        User userWithMeals = service.getUserWithMeals(UserTestData.USER_ID);
        List<UserMeal> meals = userWithMeals.getMeals();
        UserTestData.MATCHER.assertEquals(UserTestData.USER, userWithMeals);
        MealTestData.MATCHER.assertCollectionEquals(MealTestData.USER_MEALS, meals);

    }
}
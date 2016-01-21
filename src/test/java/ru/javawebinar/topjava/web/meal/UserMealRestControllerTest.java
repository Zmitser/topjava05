package ru.javawebinar.topjava.web.meal;

import org.junit.Test;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.transaction.annotation.Transactional;
import ru.javawebinar.topjava.TestUtil;
import ru.javawebinar.topjava.model.UserMeal;
import ru.javawebinar.topjava.web.AbstractMealControllerTest;
import ru.javawebinar.topjava.web.json.JsonUtil;

import java.util.Arrays;

import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static ru.javawebinar.topjava.MealTestData.*;
import static ru.javawebinar.topjava.UserTestData.USER_ID;

@Transactional
public class UserMealRestControllerTest extends AbstractMealControllerTest {
    public static final String REST_URL = UserMealRestController.REST_URL + '/';


    @Test
    public void testGet() throws Exception {
        TestUtil.print(mockMvc.perform(get(REST_URL + MEAL1_ID)))
                .andExpect(status().isOk())
                .andExpect(content().contentType(APPLICATION_JSON))
                .andExpect(MATCHER.contentMatcher(MEAL1));
    }

    @Test
    public void testDelete() throws Exception {
        mockMvc.perform(delete(REST_URL + MEAL1_ID).contentType(APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk());
        MATCHER.assertCollectionEquals(Arrays.asList(MEAL6, MEAL5, MEAL4, MEAL3, MEAL2), service.getAll(USER_ID));

    }


    @Test
    public void testGetAll() throws Exception {
        TestUtil.print(mockMvc.perform(get(REST_URL).contentType(APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(APPLICATION_JSON)))
                .andExpect(MATCHER_WITH_EXCEED.contentListMatcher(USER_MEALS_WITH_EXCEED));
    }

    @Test
    public void testUpdate() throws Exception {
        UserMeal updated = getUpdated();
        mockMvc.perform(put(REST_URL + MEAL1_ID)
                .contentType(APPLICATION_JSON)
                .content(JsonUtil.writeValue(updated)))
                .andExpect(status().isOk());
        MATCHER.assertEquals(updated, service.get(MEAL1_ID, USER_ID));
    }

    @Test
    public void testCreateWithLocation() throws Exception {
        UserMeal created = getCreated();
        ResultActions actions = mockMvc.perform(post(REST_URL)
                .contentType(APPLICATION_JSON)
                .content(JsonUtil.writeValue(created)))
                .andExpect(status().isCreated());
        UserMeal returned = MATCHER.fromJsonAction(actions);
        created.setId(returned.getId());
        MATCHER.assertEquals(created, returned);
        MATCHER.assertCollectionEquals(Arrays.asList(created, MEAL6, MEAL5, MEAL4, MEAL3, MEAL2, MEAL1), service.getAll(USER_ID));

    }

    @Test
    public void testGetBetween() throws Exception {
        mockMvc.perform(get(REST_URL + "filter?startDate=2015-05-30T10:00:00&endDate=2015-05-30T22:00:00"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(MATCHER_WITH_EXCEED.contentListMatcher(USER_MEALS_WITH_EXCEED_BETWEEN));
    }
}
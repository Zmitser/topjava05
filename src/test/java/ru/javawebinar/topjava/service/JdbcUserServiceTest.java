package ru.javawebinar.topjava.service;

import org.springframework.test.context.ActiveProfiles;
import ru.javawebinar.topjava.Profiles;


@ActiveProfiles(profiles = {Profiles.HSQLDB, Profiles.JDBC})
public class JdbcUserServiceTest extends BasicUserServiceTest {

}
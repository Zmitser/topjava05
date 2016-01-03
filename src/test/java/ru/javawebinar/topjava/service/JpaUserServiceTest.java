package ru.javawebinar.topjava.service;

import org.springframework.test.context.ActiveProfiles;
import ru.javawebinar.topjava.Profiles;


@ActiveProfiles(profiles = {Profiles.HSQLDB, Profiles.JPA})
public class JpaUserServiceTest extends BasicUserServiceTest {

}
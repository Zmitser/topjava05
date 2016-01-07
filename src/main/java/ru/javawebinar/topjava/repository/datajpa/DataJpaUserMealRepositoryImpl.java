package ru.javawebinar.topjava.repository.datajpa;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import ru.javawebinar.topjava.model.User;
import ru.javawebinar.topjava.model.UserMeal;
import ru.javawebinar.topjava.repository.UserMealRepository;

import java.time.LocalDateTime;
import java.util.List;

/**
 * GKislin
 * 27.03.2015.
 */
@Repository
public class DataJpaUserMealRepositoryImpl implements UserMealRepository {

    @Autowired
    private ProxyUserMealRepository proxyUserMealRepository;


    @Autowired
    private ProxyUserRepository proxy;

    @Override
    public UserMeal save(UserMeal userMeal, int userId) {
        User ref = proxy.getOne(userId);
        userMeal.setUser(ref);
        if (!userMeal.isNew() && get(userMeal.getId(), userId) == null) {
            return null;
        }
        return proxyUserMealRepository.save(userMeal);
    }

    @Override
    public boolean delete(int id, int userId) {
        return proxyUserMealRepository.deleteByIdAndUserId(id, userId) != 0;
    }

    @Override
    public UserMeal get(int id, int userId) {
        return proxyUserMealRepository.findOneByIdAndUserId(id, userId);
    }

    @Override
    public List<UserMeal> getAll(int userId) {
        return proxyUserMealRepository.findAllByUserIdOrderByDateTimeDesc(userId);
    }

    @Override
    public List<UserMeal> getBetween(LocalDateTime startDate, LocalDateTime endDate, int userId) {
        return proxyUserMealRepository.findBetween(startDate, endDate, userId);
    }

    @Override
    public UserMeal getMealWithUser(int id) {
        return proxyUserMealRepository.getMealWithUser(id);
    }
}






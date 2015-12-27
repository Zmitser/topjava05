package ru.javawebinar.topjava.repository.jpa;

import org.springframework.dao.support.DataAccessUtils;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import ru.javawebinar.topjava.model.User;
import ru.javawebinar.topjava.model.UserMeal;
import ru.javawebinar.topjava.repository.UserMealRepository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.time.LocalDateTime;
import java.util.List;

/**
 * User: gkisline
 * Date: 26.08.2014
 */

@Repository
@Transactional(readOnly = true)
public class JpaUserMealRepositoryImpl implements UserMealRepository {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    @Transactional
    public UserMeal save(UserMeal userMeal, int userId) {
        User ref = entityManager.getReference(User.class, userId);
        userMeal.setUser(ref);
        if (userMeal.isNew()) {
            entityManager.persist(userMeal);
            return userMeal;
        } else {
            UserMeal userMeal1 = get(userMeal.getId(), userId);
            if (userMeal1 != null)
                return entityManager.merge(userMeal);
        }
        return null;
    }

    @Override
    @Transactional
    public boolean delete(int id, int userId) {
        return entityManager.createNamedQuery(UserMeal.DELETE_MEAL)
                .setParameter("id", id)
                .setParameter("user_id", userId)
                .executeUpdate() != 0;
    }

    @Override
    public UserMeal get(int id, int userId) {
        List<UserMeal> userMealTypedQuery = entityManager.createNamedQuery(UserMeal.GET_SINGLE, UserMeal.class)
                .setParameter("id", id)
                .setParameter("user_id", userId)
                .getResultList();
        return DataAccessUtils.singleResult(userMealTypedQuery);
    }

    @Override
    @Transactional
    public List<UserMeal> getAll(int userId) {
        return entityManager.createNamedQuery(UserMeal.ALL_SORTED_BY_USER, UserMeal.class)
                .setParameter("user_id", userId)
                .getResultList();
    }

    @Override
    @Transactional
    public List<UserMeal> getBetween(LocalDateTime startDate, LocalDateTime endDate, int userId) {
        return entityManager.createNamedQuery(UserMeal.BETWEEN_DATES, UserMeal.class)
                .setParameter("user_id", userId)
                .setParameter("min", startDate)
                .setParameter("max", endDate)
                .getResultList();
    }
}

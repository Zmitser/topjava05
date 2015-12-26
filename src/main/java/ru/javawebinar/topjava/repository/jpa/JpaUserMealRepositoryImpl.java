package ru.javawebinar.topjava.repository.jpa;

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
        if (userMeal.isNew()) {
            userMeal.setUser(ref);
            entityManager.persist(userMeal);
            return userMeal;
        } else {
            UserMeal userMeal1 = get(userMeal.getId(), userId);
            if (userMeal1 != null &&  userMeal1.equals(userMeal))
                return entityManager.merge(userMeal);
        }
        return null;
    }

    @Override
    @Transactional
    public boolean delete(int id, int userId) {
        User ref = entityManager.getReference(User.class, userId);
        return entityManager.createNamedQuery(UserMeal.DELETE_MEAL)
                .setParameter("id", id)
                .setParameter("user", ref)
                .executeUpdate() != 0;
    }

    @Override
    public UserMeal get(int id, int userId) {
        User ref = entityManager.getReference(User.class, userId);
        try {
            return entityManager.createNamedQuery(UserMeal.GET_SINGLE, UserMeal.class)
                    .setParameter("id", id)
                    .setParameter("user", ref)
                    .getSingleResult();
        } catch (Exception e) {
            return null;
        }

    }

    @Override
    @Transactional
    public List<UserMeal> getAll(int userId) {
        User ref = entityManager.getReference(User.class, userId);
        return entityManager.createNamedQuery(UserMeal.ALL_SORTED_BY_USER, UserMeal.class)
                .setParameter("user", ref)
                .getResultList();
    }

    @Override
    @Transactional
    public List<UserMeal> getBetween(LocalDateTime startDate, LocalDateTime endDate, int userId) {
        User ref = entityManager.getReference(User.class, userId);
        return entityManager.createNamedQuery(UserMeal.BETWEEN_DATES, UserMeal.class)
                .setParameter("user", ref)
                .setParameter("min", startDate)
                .setParameter("max", endDate)
                .getResultList();
    }
}

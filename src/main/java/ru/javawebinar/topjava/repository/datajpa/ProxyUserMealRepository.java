package ru.javawebinar.topjava.repository.datajpa;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;
import ru.javawebinar.topjava.model.UserMeal;

import java.time.LocalDateTime;
import java.util.List;



@Transactional(readOnly = true)
public interface ProxyUserMealRepository extends JpaRepository<UserMeal, Integer> {
    @Modifying
    @Transactional

    int deleteByIdAndUserId(Integer id, Integer userId);
    UserMeal findOneByIdAndUserId(Integer id, Integer userId);

    @Override
    @Transactional
    UserMeal save(UserMeal userMeal);

    @Transactional
    @Query("select um FROM UserMeal um where um.dateTime>=:startTime AND um.dateTime<=:endTime AND um.user.id=:userId ORDER BY um.dateTime DESC")
    List<UserMeal> findBetween(@Param("startTime")LocalDateTime startTime, @Param("endTime")LocalDateTime endTime, @Param("userId")Integer userId);
    @Transactional
    List<UserMeal> findAllByUserIdOrderByDateTimeDesc(Integer userId);
}


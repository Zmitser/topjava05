package ru.javawebinar.topjava.model;

import org.hibernate.validator.constraints.NotEmpty;
import org.springframework.format.annotation.DateTimeFormat;
import ru.javawebinar.topjava.util.exception.LocalDateTimeAttributeConverter;

import javax.persistence.*;
import javax.validation.constraints.Digits;
import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * GKislin
 * 11.01.2015.
 */

@Entity
@Table(name = "meals", uniqueConstraints = {@UniqueConstraint(columnNames = "date_time", name = "meals_unique_user_datetime_idx")})
@NamedQueries({
        @NamedQuery(name = UserMeal.DELETE_MEAL, query = "DELETE FROM UserMeal u WHERE u.id=:id AND u.user=:user"),
        @NamedQuery(name = UserMeal.ALL_SORTED_BY_USER, query = "SELECT u FROM UserMeal u WHERE u.user=:user ORDER BY u.dateTime DESC"),
        @NamedQuery(name = UserMeal.BETWEEN_DATES, query = "SELECT u FROM UserMeal u WHERE u.dateTime>=:min AND u.dateTime<=:max AND u.user=:user ORDER BY u.dateTime DESC "),
        @NamedQuery(name = UserMeal.GET_SINGLE, query = "SELECT u FROM UserMeal u WHERE u.id=:id AND u.user=:user")
})
public class UserMeal extends BaseEntity implements Serializable {

    public static final String DELETE_MEAL = "UserMeal.delete";
    public static final String ALL_SORTED_BY_USER = "UserMeal.getAllSorted";
    public static final String BETWEEN_DATES = "UserMeal.getBetweenDates";
    public static final String GET_SINGLE = "UserMeal.getSingle";

    @Column(name = "date_time", columnDefinition = "timestamp default now()", unique = true)
    @Convert(converter = LocalDateTimeAttributeConverter.class)
    @DateTimeFormat
    protected LocalDateTime dateTime;

    @Column(name = "description", nullable = false)
    @NotEmpty
    protected String description;

    @Column(name = "calories", columnDefinition = "default 2000")
    @Digits(fraction = 0, integer = 4)
    protected int calories;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", updatable = false, insertable = true)
    private User user;

    public UserMeal(LocalDateTime dateTime, String description, int calories) {
        this(null, dateTime, description, calories);
    }

    public UserMeal(Integer id, LocalDateTime dateTime, String description, int calories) {
        super(id);
        this.dateTime = dateTime;
        this.description = description;
        this.calories = calories;
    }

    public UserMeal() {
    }

    public void setId(int id) {
        this.id = id;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public LocalDateTime getDateTime() {
        return dateTime;
    }

    public String getDescription() {
        return description;
    }

    public int getCalories() {
        return calories;
    }

    public Integer getId() {
        return id;
    }

    public void setDateTime(LocalDateTime dateTime) {
        this.dateTime = dateTime;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setCalories(int calories) {
        this.calories = calories;
    }

    @Override
    public String toString() {
        return "UserMeal{" +
                "id=" + id +
                ", dateTime=" + dateTime +
                ", description='" + description + '\'' +
                ", calories=" + calories +
                '}';
    }

}
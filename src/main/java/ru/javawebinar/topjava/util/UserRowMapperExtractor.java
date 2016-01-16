package ru.javawebinar.topjava.util;

import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.ResultSetExtractor;
import ru.javawebinar.topjava.model.Role;
import ru.javawebinar.topjava.model.User;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;


public class UserRowMapperExtractor implements ResultSetExtractor<List<User>> {

    @Override
    public List<User> extractData(ResultSet rs) throws SQLException, DataAccessException {

        Map<Integer, User> map = new HashMap<>();
        while (rs.next()) {
            int user_id = rs.getInt("id");
            User user = map.get(user_id);
            if (user == null) {
                user = new User();
                user.setId(user_id);
                user.setName(rs.getString("name"));
                user.setEmail(rs.getString("email"));
                user.setPassword(rs.getString("password"));
                user.setRegistered(rs.getTimestamp("registered"));
                user.setEnabled(rs.getBoolean("enabled"));
                user.setCaloriesPerDay(rs.getInt("calories_per_day"));
                map.put(user_id, user);
            }
            if (user.getRoles() == null) {
                Set<Role> roles = new HashSet<>();
                user.setRoles(roles);
            }

            if (user_id == rs.getInt("user_id")) {
                if (rs.getString("role").equalsIgnoreCase("ROLE_USER")) {
                    user.getRoles().add(Role.ROLE_ADMIN);
                } else if (rs.getString("role").equalsIgnoreCase("ROLE_ADMIN")) {
                    user.getRoles().add(Role.ROLE_USER);
                }
            }


        }
        return new ArrayList<>(map.values());
    }
}

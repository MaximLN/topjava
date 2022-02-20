package ru.javawebinar.topjava.util;

import org.springframework.jdbc.core.RowMapper;
import ru.javawebinar.topjava.model.Meal;

import java.sql.ResultSet;
import java.sql.SQLException;

public class MealRowMapper implements RowMapper<Meal> {

    @Override
    public Meal mapRow(ResultSet rs, int i) throws SQLException {
        Meal meal = new Meal();
        meal.setId(Integer.parseInt(rs.getString("id")));
        meal.setDateTime(rs.getTimestamp("datetime").toLocalDateTime());
        meal.setCalories(Integer.parseInt(rs.getString("calories")));
        meal.setDescription(rs.getString("description"));
        return meal;
    }

}

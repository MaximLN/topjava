package ru.javawebinar.topjava.repository.jdbc;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.util.MealRowMapper;
import ru.javawebinar.topjava.repository.MealRepository;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;

@Repository
public class JdbcMealRepository implements MealRepository {

    private final JdbcTemplate jdbcTemplate;

    private final NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    private final SimpleJdbcInsert insertUser;

    @Autowired
    public JdbcMealRepository(JdbcTemplate jdbcTemplate, NamedParameterJdbcTemplate namedParameterJdbcTemplate) {
        this.insertUser = new SimpleJdbcInsert(jdbcTemplate)
                .withTableName("meals")
                .usingGeneratedKeyColumns("id");

        this.jdbcTemplate = jdbcTemplate;
        this.namedParameterJdbcTemplate = namedParameterJdbcTemplate;
        System.out.println("JdbcMealRepository Constructor");
    }

    @Override
    public Meal save(Meal meal, int userId) {
        if (meal.getId() == null) {
            return jdbcTemplate.update("INSERT INTO meals (userid, dateTime, description, calories) " +
                    "VALUES ( " + userId + " , '" + Timestamp.valueOf(meal.getDateTime()) +
                    "' , '" + meal.getDescription() + "' , " + meal.getCalories() + " ) ") == 1 ? meal : null;
        } else {
            return jdbcTemplate.update("UPDATE meals SET" +
                    " id = " + meal.getId() +
                    ", userid = " + userId +
                    ", dateTime = '" + Timestamp.valueOf(meal.getDateTime()) +
                    "', description = '" + meal.getDescription() +
                    "', calories = " + meal.getCalories() +
                    " WHERE id = " + meal.getId() + " AND userid = " + userId) == 1 ? meal : null;
        }
    }

    @Override
    public boolean delete(int id, int userId) {
        return jdbcTemplate.update("DELETE FROM meals WHERE id = " + id + " AND userid = " + userId) == 1;
    }

    @Override
    public Meal get(int id, int userId) {
        System.out.println(id + " " + userId);
        List<Meal> list = jdbcTemplate.query("SELECT * FROM meals WHERE id = " + id +
                " AND userid = " + userId, new MealRowMapper());
        return (list.size() == 1) ? list.get(0) : null;
    }

    @Override
    public List<Meal> getAll(int userId) {
        return jdbcTemplate.query("SELECT * FROM meals WHERE userid = " + userId +
                " ORDER BY datetime DESC", new MealRowMapper());
    }

    @Override
    public List<Meal> getBetweenHalfOpen(LocalDateTime startDateTime, LocalDateTime endDateTime, int userId) {
        return jdbcTemplate.query("SELECT * FROM meals WHERE datetime >= '" + Timestamp.valueOf(startDateTime) +
                "' AND datetime <= '" + Timestamp.valueOf(endDateTime) +
                "' AND userId = '" + userId +
                "' ORDER BY datetime DESC", new MealRowMapper());

    }
}

package ru.javawebinar.topjava.repository.jpa;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.model.User;
import ru.javawebinar.topjava.repository.MealRepository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.time.LocalDateTime;
import java.util.List;

@Repository
@Transactional(readOnly = true)
public class JpaMealRepository implements MealRepository {

    @PersistenceContext
    private EntityManager em1;

    @Override
    @Transactional
    public Meal save(Meal meal, int userId) {
        User user = em1.getReference(User.class, userId);
        if (meal.isNew()) {
            meal.setUser(user);
            em1.persist(meal);
            return meal;
        } else {
            if (em1.getReference(Meal.class, meal.getId()).getUser().getId() == userId) {
                meal.setUser(user);
                return em1.merge(meal);
            } else return null;
        }
    }

    @Override
    @Transactional()
    public boolean delete(int id, int userId) {
        return em1.createNamedQuery(Meal.DELETE)
                .setParameter("user_id", userId)
                .setParameter("id", id)
                .executeUpdate() != 0;
    }

    @Override
    public Meal get(int id, int userId) {
        Meal meal = em1.find(Meal.class, id);
        if (meal != null) {
            if (meal.getUser().getId() == userId) {
                return meal;
            } else return null;
        } else return null;
    }

    @Override
    public List<Meal> getAll(int userId) {
        return em1.createNamedQuery(Meal.ALL_SORTED, Meal.class)
                .setParameter(1, userId)
                .getResultList();
    }

    @Override
    public List<Meal> getBetweenHalfOpen(LocalDateTime startDateTime, LocalDateTime endDateTime, int userId) {
        return em1.createNamedQuery(Meal.ALL_SORTED_FILTERED, Meal.class)
                .setParameter("user_id", userId)
                .setParameter("start_date_time", startDateTime)
                .setParameter("end_date_time", endDateTime)
                .getResultList();
    }
}

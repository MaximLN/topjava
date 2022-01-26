package ru.javawebinar.topjava.util;

import ru.javawebinar.topjava.model.UserMeal;
import ru.javawebinar.topjava.model.UserMealWithExcess;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.Month;
import java.util.*;
import java.util.stream.Collectors;

public class UserMealsUtil {
    public static void main(String[] args) {
        List<UserMeal> meals = Arrays.asList(
                new UserMeal(LocalDateTime.of(2020, Month.JANUARY, 30, 10, 0), "Завтрак", 500),
                new UserMeal(LocalDateTime.of(2020, Month.JANUARY, 30, 13, 0), "Обед", 1000),
                new UserMeal(LocalDateTime.of(2020, Month.JANUARY, 30, 20, 0), "Ужин", 500),
                new UserMeal(LocalDateTime.of(2020, Month.JANUARY, 31, 0, 0), "Еда на граничное значение", 100),
                new UserMeal(LocalDateTime.of(2020, Month.JANUARY, 31, 10, 0), "Завтрак", 1000),
                new UserMeal(LocalDateTime.of(2020, Month.JANUARY, 31, 13, 0), "Обед", 500),
                new UserMeal(LocalDateTime.of(2020, Month.JANUARY, 31, 20, 0), "Ужин", 410)
        );
        List<UserMealWithExcess> mealsTo = filteredByCycles(meals, LocalTime.of(7, 0), LocalTime.of(12, 0), 2000);
        mealsTo.forEach(System.out::println);

        System.out.println(filteredByStreams(meals, LocalTime.of(7, 0), LocalTime.of(12, 0), 2000));
    }

    public static List<UserMealWithExcess> filteredByCycles(List<UserMeal> meals, LocalTime startTime, LocalTime endTime, int caloriesPerDay) {
        // TODO return filtered list with excess. Implement by cycles
        List<UserMealWithExcess> listArterTimeFilter = new ArrayList<>();
        List<UserMealWithExcess> listResult = new ArrayList<>();
        Map<LocalDate, Integer> map = new HashMap<>();
        for (UserMeal userMeal : meals) {
            LocalDate localDateCurrentUser = userMeal.getDateTime().toLocalDate();
            map.merge(localDateCurrentUser, userMeal.getCalories(), Integer::sum);
            if (TimeUtil.isBetweenHalfOpen(userMeal.getDateTime().toLocalTime(), startTime, endTime)) {
                listArterTimeFilter.add(new UserMealWithExcess(userMeal.getDateTime(), userMeal.getDescription(), userMeal.getCalories(), false));
            }
        }
        for (UserMealWithExcess userMealWithExcess : listArterTimeFilter) {
            if (map.get(userMealWithExcess.getDateTime().toLocalDate()) > caloriesPerDay) {
                listResult.add(new UserMealWithExcess(userMealWithExcess.getDateTime(), userMealWithExcess.getDescription(), userMealWithExcess.getCalories(), true));
            } else {
                listResult.add(new UserMealWithExcess(userMealWithExcess.getDateTime(), userMealWithExcess.getDescription(), userMealWithExcess.getCalories(), false));
            }
        }
        return listResult;
    }

    public static List<UserMealWithExcess> filteredByStreams(List<UserMeal> meals, LocalTime startTime, LocalTime endTime, int caloriesPerDay) {
        // TODO Implement by streams
        List<UserMealWithExcess> listUserMealWithExcesses;
        listUserMealWithExcesses = meals.stream()
                .filter(s -> TimeUtil.isBetweenHalfOpen(s.getDateTime().toLocalTime(), startTime, endTime))
                .map(s -> new UserMealWithExcess(s.getDateTime(), s.getDescription(), s.getCalories(), false))
                .collect(Collectors.toList());
        Map<LocalDate, Integer> mapDateAndSumCalories =
                meals.stream().collect(HashMap::new,
                        (maps, c) -> maps.merge(c.getDateTime().toLocalDate(), c.getCalories(), Integer::sum),
                        (maps, u) -> {
                        });
        List<UserMealWithExcess> listResult;
        listResult = listUserMealWithExcesses.stream()
                .map(s -> mapDateAndSumCalories.get(s.getDateTime().toLocalDate()) > caloriesPerDay ?
                        new UserMealWithExcess(s.getDateTime(), s.getDescription(), s.getCalories(), true) : new UserMealWithExcess(s.getDateTime(), s.getDescription(), s.getCalories(), false))
                .collect(Collectors.toList());

        return listResult;
    }
}

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
        Map<LocalDate, Integer> mapDateAndSumCalories = new HashMap<>();
        for (UserMeal userMeal : meals) {
            LocalDate localDateCurrentUser = userMeal.getDateTime().toLocalDate();
            mapDateAndSumCalories.merge(localDateCurrentUser, userMeal.getCalories(), Integer::sum);
        }
        List<UserMealWithExcess> listResult = new ArrayList<>();
        for (UserMeal userMeal : meals) {
            if (TimeUtil.isBetweenHalfOpen(userMeal.getDateTime().toLocalTime(), startTime, endTime)) {
                listResult.add(new UserMealWithExcess(userMeal.getDateTime(), userMeal.getDescription(), userMeal.getCalories(), mapDateAndSumCalories.get(userMeal.getDateTime().toLocalDate()) > caloriesPerDay));
            }
        }
        return listResult;
    }

    public static List<UserMealWithExcess> filteredByStreams(List<UserMeal> meals, LocalTime startTime, LocalTime endTime, int caloriesPerDay) {
        Map<LocalDate, Integer> mapDateAndSumCalories = meals.stream()
                .collect(Collectors.groupingBy(UserMeal -> UserMeal.getDateTime().toLocalDate(),
                        Collectors.summingInt(UserMeal::getCalories)));
        List<UserMealWithExcess> listResult;
        listResult = meals.stream()
                .filter((UserMeal s) -> TimeUtil.isBetweenHalfOpen(s.getDateTime().toLocalTime(), startTime, endTime))
                .map((UserMeal s) -> new UserMealWithExcess(s.getDateTime(), s.getDescription(), s.getCalories(), mapDateAndSumCalories.get(s.getDateTime().toLocalDate()) > caloriesPerDay))
                .collect(Collectors.toList());
        return listResult;
    }
}
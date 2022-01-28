package ru.javawebinar.topjava.util;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;

public class CaloriesInDay {
    static Map<LocalDate, Integer> mapDateAndSumCalories = new HashMap<>();
    public static void addCalories (LocalDate localDate, Integer calories){
        mapDateAndSumCalories.merge(localDate,calories, Integer::sum);
    }
}

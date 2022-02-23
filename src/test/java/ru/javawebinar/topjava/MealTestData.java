package ru.javawebinar.topjava;

import ru.javawebinar.topjava.model.Meal;

import java.time.LocalDateTime;
import java.time.Month;
import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static ru.javawebinar.topjava.model.AbstractBaseEntity.START_SEQ;

public class MealTestData {
    public static final int ID10 = START_SEQ + 10;
    public static final int ID15 = START_SEQ + 15;

    public static final Meal meal = new Meal(LocalDateTime.of(2022, Month.FEBRUARY, 20, 21, 0), "Админ ужин", 1500);
    public static final Meal mealByDublicate = new Meal(LocalDateTime.of(2022, Month.FEBRUARY, 5, 5, 5), "ByDublicate", 1500);

    public static Meal adminMeal1 = new Meal(100016, LocalDateTime.of(2022, Month.FEBRUARY, 21, 23, 0), "description7Admin", 1500);
    public static Meal adminMeal2 = new Meal(100015, LocalDateTime.of(2022, Month.FEBRUARY, 21, 21, 0), "description6Admin", 200);
    static Meal adminMeal3 = new Meal(100010, LocalDateTime.of(2022, Month.FEBRUARY, 21, 14, 0), "description1Admin", 100);
    static Meal adminMeal4 = new Meal(100011, LocalDateTime.of(2022, Month.FEBRUARY, 21, 12, 0), "description2Admin", 1000);
    static Meal adminMeal5 = new Meal(100012, LocalDateTime.of(2022, Month.FEBRUARY, 20, 11, 0), "descriptio3Admin", 2000);
    static Meal adminMeal6 = new Meal(100013, LocalDateTime.of(2022, Month.FEBRUARY, 18, 9, 0), "description4Admin", 100);
    static Meal adminMeal7 = new Meal(100014, LocalDateTime.of(2022, Month.FEBRUARY, 17, 22, 0), "description5Admin", 1200);

    public static final List<Meal> adminList = Arrays.asList
            (adminMeal1, adminMeal2, adminMeal3, adminMeal4, adminMeal5, adminMeal6, adminMeal7);

    static Meal userMeal1 = new Meal(100009, LocalDateTime.of(2022, Month.FEBRUARY, 21, 23, 0), "descriptionuser7", 1500);
    static Meal userMeal2 = new Meal(100008, LocalDateTime.of(2022, Month.FEBRUARY, 21, 21, 0), "descriptionuser6", 200);
    static Meal userMeal3 = new Meal(100003, LocalDateTime.of(2022, Month.FEBRUARY, 21, 14, 0), "descriptionuser1", 100);
    static Meal userMeal4 = new Meal(100004, LocalDateTime.of(2022, Month.FEBRUARY, 21, 12, 0), "descriptionuser2", 1000);
    static Meal userMeal5 = new Meal(100005, LocalDateTime.of(2022, Month.FEBRUARY, 20, 11, 0), "descriptionuser3", 2000);

    public static final List<Meal> userFilteredList = Arrays.asList
            (userMeal1, userMeal2, userMeal3, userMeal4, userMeal5);


    public static Meal getNew() {
        return new Meal(LocalDateTime.of(2022, Month.FEBRUARY, 20, 21, 0), "Админ ужин", 1500);
    }

    public static Meal getUpdated() {
        Meal updated = new Meal(meal);
        updated.setDateTime(LocalDateTime.of(2022, Month.FEBRUARY, 21, 0, 0));
        updated.setDescription("UpdatedDescription");
        updated.setCalories(330);
        return updated;
    }

    public static void assertMatch(Meal actual, Meal expected) {
        assertThat(actual.getDescription()).isEqualTo(expected.getDescription());
        assertThat(actual.getCalories()).isEqualTo(expected.getCalories());
        assertThat(actual.getDateTime()).isEqualTo(expected.getDateTime());
    }

    public static void assertMatch(Iterable<Meal> actual, Iterable<Meal> expected) {
        assertThat(actual).usingRecursiveFieldByFieldElementComparator().isEqualTo(expected);
    }
}

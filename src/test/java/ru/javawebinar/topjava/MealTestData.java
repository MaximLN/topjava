package ru.javawebinar.topjava;

import ru.javawebinar.topjava.model.Meal;

import java.time.LocalDateTime;
import java.time.Month;
import java.util.Arrays;

import static org.assertj.core.api.Assertions.assertThat;
import static ru.javawebinar.topjava.model.AbstractBaseEntity.START_SEQ;

public class MealTestData {
    public static final int ID = START_SEQ + 1000;

    public static final int USER_ID = START_SEQ;
    public static final int ADMIN_ID = START_SEQ + 1;
    public static final int GUEST_ID = START_SEQ + 2;
    public static final int NOT_FOUND = 10;

    public static final int ID200 = 100200;
    public static final int ID201 = 100201;

    public static final Meal meal = new Meal(LocalDateTime.of(2022, Month.FEBRUARY, 20, 21, 0), "Админ ужин", 1500);
    public static final Meal mealWithId = new Meal(ID200, LocalDateTime.of(2022, Month.FEBRUARY, 20, 21, 0), "Админ ужин", 1500);
    public static final Meal mealByDublicate = new Meal(LocalDateTime.of(2022, Month.FEBRUARY, 5, 5, 5), "ByDublicate", 1500);


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
//        assertThat(actual).usingRecursiveComparison().ignoringFields("id","datatime").isEqualTo(expected);
        assertThat(actual).usingRecursiveComparison().ignoringFields("id").isEqualTo(expected);
    }

    public static void assertMatch(Iterable<Meal> actual, Meal... expected) {
        assertMatch(actual, Arrays.asList(expected));
    }

    public static void assertMatch(Iterable<Meal> actual, Iterable<Meal> expected) {
        assertThat(actual).usingRecursiveFieldByFieldElementComparatorIgnoringFields("registered", "roles").isEqualTo(expected);
    }
}

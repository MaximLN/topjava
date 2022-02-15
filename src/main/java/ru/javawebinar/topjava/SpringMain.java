package ru.javawebinar.topjava;

import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.model.Role;
import ru.javawebinar.topjava.model.User;
import ru.javawebinar.topjava.web.meal.MealRestController;
import ru.javawebinar.topjava.web.user.AdminRestController;
import java.time.LocalDateTime;
import java.time.Month;
import java.util.Arrays;

public class SpringMain {
    public static void main(String[] args) {
        // java 7 automatic resource management (ARM)
        System.out.println("SpringMain.main() run");
        try (ConfigurableApplicationContext appCtx = new ClassPathXmlApplicationContext("spring/spring-app.xml")) {
            System.out.println("Bean definition names: " + Arrays.toString(appCtx.getBeanDefinitionNames()));
            AdminRestController adminUserController = appCtx.getBean(AdminRestController.class);
            adminUserController.create(new User(null, "userName", "email@mail.ru", "password", Role.ADMIN));
            MealRestController mealRestController = appCtx.getBean(MealRestController.class);
            System.out.println("mealRestController.get(1): " + mealRestController.get(1));
            System.out.println("mealRestController.getAll(): " + mealRestController.getAll());
            mealRestController.delete(1);
            System.out.println("mealRestController.delete(1)");
            mealRestController.update(new Meal(2, 1, LocalDateTime.of(2021, Month.APRIL, 3, 20, 0), "Ужин Update appCtx.getBean", 1000), 2);
            System.out.println("mealRestController.update()");
            mealRestController.create(new Meal(null, null, LocalDateTime.of(2021, Month.MAY, 3, 20, 0), "Ужин Create appCtx.getBean", 1200));
            System.out.println("mealRestController.create()");
        }
    }
}

package ru.javawebinar.topjava.web.meal;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.to.MealTo;

import java.net.URI;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

@RestController
@RequestMapping(value = MealRestController.REST_URL, produces = MediaType.APPLICATION_JSON_VALUE)
public class MealRestController extends AbstractMealController {
    static final String REST_URL = "/rest/meals";
    private static final Logger log = LoggerFactory.getLogger(MealRestController.class);

    @Override
    @GetMapping
    public List<MealTo> getAll() {
        return super.getAll();
    }

    @GetMapping("/filtered")
    public List<MealTo> getBetween(@RequestParam LocalDate startDate,
                                   @RequestParam LocalTime startTime,
                                   @RequestParam LocalDate endDate,
                                   @RequestParam LocalTime endTime) {
        if (startDate.equals(LocalDate.MIN)) {
            startDate = null;
        }
        if (endDate.equals(LocalDate.MIN)) {
            endDate = null;
        }
        if (startTime.equals(LocalTime.MIN)) {
            startTime = null;
        }
        if (endTime.equals(LocalTime.MIN)) {
            endTime = null;
        }
        return super.getBetween(startDate, startTime, endDate, endTime);
    }

    @Override
    @GetMapping("/{id}")
    public Meal get(@PathVariable int id) {
        return super.get(id);
    }

    @PostMapping
    public ResponseEntity<Meal> createWithLocation(@RequestBody Meal meal) {
        Meal created = super.create(meal);
        URI uriOfNewResource = ServletUriComponentsBuilder.fromCurrentContextPath()
                .path(REST_URL + "/{id}")
                .buildAndExpand(created.getId()).toUri();
        return ResponseEntity.created(uriOfNewResource).body(created);
    }

    @Override
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable int id) {
        super.delete(id);
    }

    @Override
    @PutMapping(value = "/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void update(@RequestBody Meal meal, @PathVariable int id) {
        super.update(meal, id);
    }
}
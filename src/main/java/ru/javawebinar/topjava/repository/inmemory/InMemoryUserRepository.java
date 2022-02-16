package ru.javawebinar.topjava.repository.inmemory;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;
import ru.javawebinar.topjava.model.Role;
import ru.javawebinar.topjava.model.User;
import ru.javawebinar.topjava.repository.UserRepository;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Repository
public class InMemoryUserRepository implements UserRepository {
    private static final Logger log = LoggerFactory.getLogger(InMemoryUserRepository.class);
    private static final Map<Integer, User> repository = new ConcurrentHashMap<>();
    private final AtomicInteger counter = new AtomicInteger(10);

    static {
        repository.put(1, new User(1, "Андрей", "a@m.ru", "", 1000, true, Collections.singleton(Role.USER)));
        repository.put(2, new User(2, "Mike", "b@m.ru", "", 2000, true, Collections.singleton(Role.USER)));
        repository.put(3, new User(3, "Dack", "c@m.ru", "", 3000, true, Collections.singleton(Role.USER)));
        repository.put(4, new User(4, "Dack", "x@m.ru", "", 3000, true, Collections.singleton(Role.USER)));
        repository.put(5, new User(5, "Dack", "a@m.ru", "", 3000, true, Collections.singleton(Role.USER)));
    }

    @Override
    public boolean delete(int id) {
        log.info("delete {}", id);
        return repository.remove(id) != null;
    }

    @Override
    public User save(User user) {
        log.info("save {}", user);
        if (user.isNew()) {
            user.setId(counter.incrementAndGet());
            repository.put(user.getId(), user);
            return user;
        }
        // handle case: update, but not present in storage
        return repository.computeIfPresent(user.getId(), (id, oldUser) -> user);
    }

    @Override
    public User get(int id) {
        log.info("get {}", id);
        return repository.get(id);
    }

    @Override
    public List<User> getAll() {
        log.info("getAll");
        List<User> list = new ArrayList<>(repository.values());
        Stream<User> userStream = list.stream().sorted(User.COMPARE_BY_NAME.thenComparing(User::getEmail));
        return userStream.collect(Collectors.toList());
    }

    @Override
    public User getByEmail(String email) {
        log.info("getByEmail {}", email);
        try {
            return repository.entrySet().stream()
                    .filter(entry -> entry.getValue().getEmail().equalsIgnoreCase(email))
                    .findFirst()
                    .get().getValue();
        } catch (NoSuchElementException noSuchElementException) {
            throw new NoSuchElementException("Not Found email: " + email);
        }
    }
}



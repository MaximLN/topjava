package ru.javawebinar.topjava.repository.jdbc;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.support.DataAccessUtils;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import ru.javawebinar.topjava.model.Role;
import ru.javawebinar.topjava.model.User;
import ru.javawebinar.topjava.repository.UserRepository;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import java.sql.ResultSet;
import java.util.*;
import java.util.stream.Collectors;

@Repository
public class JdbcUserRepository implements UserRepository {

    private static final BeanPropertyRowMapper<User> ROW_MAPPER = BeanPropertyRowMapper.newInstance(User.class);

    private final JdbcTemplate jdbcTemplate;

    private final NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    private final SimpleJdbcInsert insertUser;

    @Autowired
    public JdbcUserRepository(JdbcTemplate jdbcTemplate, NamedParameterJdbcTemplate namedParameterJdbcTemplate) {
        this.insertUser = new SimpleJdbcInsert(jdbcTemplate)
                .withTableName("users")
                .usingGeneratedKeyColumns("id");

        this.jdbcTemplate = jdbcTemplate;
        this.namedParameterJdbcTemplate = namedParameterJdbcTemplate;
    }

    @Override
    @Transactional
    public User save(User user) {
        List<Role> roles = new ArrayList<>(user.getRoles());
        BeanPropertySqlParameterSource parameterSource = new BeanPropertySqlParameterSource(user);
        if (user.isNew()) {
            Number newKey = insertUser.executeAndReturnKey(parameterSource);
            user.setId(newKey.intValue());
            for (Role role : roles) {
                if (jdbcTemplate.update("INSERT INTO user_roles (user_id, role) VALUES(?, ?)", user.getId(), role.toString()) == 0) {
                    return null;
                }
            }
        } else if (namedParameterJdbcTemplate.update("""
                   UPDATE users SET name=:name, email=:email, password=:password, 
                   registered=:registered, enabled=:enabled, calories_per_day=:caloriesPerDay WHERE id=:id
                """, parameterSource) == 0 || jdbcTemplate.update("DELETE FROM user_roles WHERE user_id=?", user.id()) == 0) {
            return null;
        } else if (roles.size() > 0) {
            for (Role role : roles) {
                if (jdbcTemplate.update("INSERT INTO user_roles (user_id, role) VALUES(?, ?)", user.getId(), role.toString()) == 0) {
                    return null;
                }
            }
        }
        return user;
    }

    @Override
    @Transactional
    public boolean delete(int id) {
        return jdbcTemplate.update("DELETE FROM users WHERE id=?", id) != 0;
    }

    @Override
    @Transactional
    public User get(int id) {
        List<User> users = jdbcTemplate.query("SELECT * FROM users WHERE id=?", ROW_MAPPER, id);
        if (!users.isEmpty()) {
            DataAccessUtils.requiredSingleResult(users).setRoles(getRoles(id));
        }
        return DataAccessUtils.singleResult(users);
//        return validation(DataAccessUtils.singleResult(users));
    }

    @Transactional
    public EnumSet getRoles(int id) {
        SqlRowSet rowSet = jdbcTemplate.queryForRowSet("SELECT * FROM user_roles WHERE user_id=?", id);
        List<String> roles = new ArrayList<>();
        while (rowSet.next()) {
            roles.add(rowSet.getString("role"));
        }
        return roles.stream()
                .map(Role::valueOf)
                .collect(Collectors.toCollection(() -> EnumSet.noneOf(Role.class)));
    }

    @Override
    @Transactional
    public User getByEmail(String email) {
        List<User> users = jdbcTemplate.query("SELECT * FROM users WHERE email=?", ROW_MAPPER, email);
        if (!users.isEmpty()) {
            DataAccessUtils.requiredSingleResult(users).setRoles(EnumSet.copyOf(getRoles(DataAccessUtils.singleResult(users).getId())));
        }
        return validation(DataAccessUtils.singleResult(users));
    }

    @Override
    @Transactional
    public List<User> getAll() {
        List<User> uncheckedUsers = jdbcTemplate.query("SELECT * FROM users ORDER BY name, email", ROW_MAPPER);
        List<User> users = new ArrayList<>();
        for (User user : uncheckedUsers) {
            if (validation(user) != null) {
                users.add(user);
            }
        }
        Map<Integer, List<String>> userIdAndRoles = new HashMap<>();
        jdbcTemplate.query("SELECT user_id, role FROM user_roles", (ResultSet rs) -> {
            while (rs.next()) {
                userIdAndRoles.computeIfAbsent(Integer.parseInt(rs.getString("user_id")),
                        k -> new ArrayList<>()).add(rs.getString("role"));
            }
            return userIdAndRoles;
        });
        for (User user : users) {
            if (userIdAndRoles.get(user.getId()) != null) {
                EnumSet<Role> roles = userIdAndRoles.get(user.getId()).stream()
                        .map(Role::valueOf)
                        .collect(Collectors.toCollection(() -> EnumSet.noneOf(Role.class)));
                user.setRoles(EnumSet.copyOf(roles));
            } else {
                user.setRoles(EnumSet.noneOf(Role.class));
            }
        }
        return users;
    }

    public User validation(User user) {
        ValidatorFactory validatorFactory = Validation.buildDefaultValidatorFactory();
        Validator validator = validatorFactory.getValidator();
        Set<ConstraintViolation<User>> violations = validator.validate(user);
        return violations.size() == 0 ? user : null;
    }
}

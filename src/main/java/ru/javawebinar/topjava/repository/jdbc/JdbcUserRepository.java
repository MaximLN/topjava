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

import java.util.ArrayList;
import java.util.EnumSet;
import java.util.List;
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
        String newRole = user.getRoles().iterator().next().toString();
        BeanPropertySqlParameterSource parameterSource = new BeanPropertySqlParameterSource(user);
        if (user.isNew()) {
            Number newKey = insertUser.executeAndReturnKey(parameterSource);
            user.setId(newKey.intValue());
            jdbcTemplate.execute("INSERT INTO user_roles (user_id, role) VALUES(" + user.getId() + ", '" + newRole + "')");
        } else if (namedParameterJdbcTemplate.update("""
                   UPDATE users SET name=:name, email=:email, password=:password, 
                   registered=:registered, enabled=:enabled, calories_per_day=:caloriesPerDay WHERE id=:id
                """, parameterSource) == 0 || jdbcTemplate.update("UPDATE user_roles SET role=? WHERE user_id=?", newRole, user.id()) == 0) {
            return null;
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
        DataAccessUtils.requiredSingleResult(users).setRoles(EnumSet.copyOf(getRoles(DataAccessUtils.singleResult(users).getId())));
        return DataAccessUtils.singleResult(users);
    }

    @Override
    @Transactional
    public List<User> getAll() {
//        return jdbcTemplate.query("SELECT * FROM users ORDER BY name, email", ROW_MAPPER);
        return jdbcTemplate.query("SELECT * FROM users JOIN user_roles ON users.id = user_roles.user_id ORDER BY name, email", ROW_MAPPER);
    }
}

package ru.javawebinar.topjava.web;

import org.slf4j.Logger;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import ru.javawebinar.topjava.SpringMain;
import ru.javawebinar.topjava.model.Role;
import ru.javawebinar.topjava.model.User;
import ru.javawebinar.topjava.repository.UserRepository;
import ru.javawebinar.topjava.repository.inmemory.InMemoryUserRepository;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Collections;
import java.util.Objects;

import static org.slf4j.LoggerFactory.getLogger;

public class UserServlet extends HttpServlet {
    private static final Logger log = getLogger(UserServlet.class);
    UserRepository repository;

    @Override
    public void init() {
        repository = SpringMain.appCtx.getBean(InMemoryUserRepository.class);
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        log.debug("forward to users");
        String action = request.getParameter("action");
        log.info("repository user size {}", repository.getAll().size());
        switch (action == null ? "all" : action) {
            case "delete":
                int id = getId(request);
                log.info("Delete user {}", id);
                repository.delete(id);
                response.sendRedirect("users");
                break;
            case "create":
            case "auth":
                SecurityUtil.setUserId(Integer.parseInt(request.getParameter("user")));
                response.sendRedirect("meals");
                break;
            case "update":
                final User user = "create".equals(action) ?
                        new User(null, request.getParameter("name"), request.getParameter("email"),
                                request.getParameter("password"), Integer.parseInt(request.getParameter("caloriesInDay")),
                                true, Collections.singleton(Role.USER)) :
                        repository.get(getId(request));
                request.setAttribute("user", user);
                request.getRequestDispatcher("/userForm.jsp").forward(request, response);
                break;
            case "all":
            default:
                log.info("getAll user");
                request.setAttribute("users", repository.getAll());
                request.getRequestDispatcher("/users.jsp").forward(request, response);
                break;
        }
    }

    private int getId(HttpServletRequest request) {
        String paramId = Objects.requireNonNull(request.getParameter("id"));
        return Integer.parseInt(paramId);
    }
}

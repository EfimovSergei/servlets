package org.efimov.servlet;

import org.efimov.Handler.Handler;
import org.efimov.controller.PostController;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;


public class MainServlet extends HttpServlet {
    private final Map<String, Map<String, Handler>> handlers = new ConcurrentHashMap<>();
    private PostController controller;

    @Override
    public void init() {

        final var context = new AnnotationConfigApplicationContext("org.efimov");
        controller = context.getBean(PostController.class);

        addHandler("GET", "/api/posts", ((path, req, resp) -> controller.all(resp)));
        addHandler("GET", "/api/posts/", ((path, req, resp) -> {
            final var id = Long.parseLong(path.substring(path.lastIndexOf("/") + 1));
            controller.getById(id, resp);
        }));
        addHandler("POST", "/api/posts", ((path, req, resp) -> controller.save(req.getReader(), resp)));
        addHandler("DELETE", "/api/posts/", ((path, req, resp) -> {
            long id = Long.parseLong(path.substring(path.lastIndexOf("/") + 1));
            controller.removeById(id, resp);
        }));
    }

    @Override
    protected void service(HttpServletRequest req, HttpServletResponse resp) {
        try {
            final var path = req.getRequestURI();
            final var method = req.getMethod();

            Handler handler;
            if (path.startsWith("/api/posts/") && path.matches("/api/posts/\\d+")) {
                handler = handlers.get(method).get("/api/posts/");
            } else {
                handler = handlers.get(method).get(path);
            }
            if (handler == null) {
                resp.setStatus(HttpServletResponse.SC_NOT_FOUND);
                return;
            }
            handler.handle(path, req, resp);

        } catch (Exception e) {
            e.printStackTrace();
            resp.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
        }
    }

    private void addHandler(String method, String path, Handler handler) {
        Map<String, Handler> map = new ConcurrentHashMap<>();
        if (handlers.containsKey(method)) {
            map = handlers.get(method);
        }
        map.put(path, handler);
        handlers.put(method, map);
    }
}
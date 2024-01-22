package org.efimov;

import org.efimov.controller.PostController;
import org.efimov.service.PostService;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class Main {
    public static void main(String[] args) {
        final var context = new AnnotationConfigApplicationContext("org.efimov");
        final var controller = context.getBean(PostController.class);
        final var service = context.getBean(PostService.class);
    }
}

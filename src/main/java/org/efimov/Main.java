package org.efimov;


import org.efimov.controller.PostController;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.efimov.config.JavaConfig;
import org.efimov.service.PostService;

public class Main {
    public static void main(String[] args) {
        // отдаём класс конфигурации
        final var context = new AnnotationConfigApplicationContext(JavaConfig.class);

        // получаем по имени бина
        final var controller = context.getBean(PostController.class);

        // получаем по классу бина
        final var service = context.getBean(PostService.class);
    }
}
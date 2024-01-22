package org.efimov.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.efimov.controller.PostController;
import org.efimov.repository.PostRepository;
import org.efimov.repository.PostRepositoryRecord;
import org.efimov.service.PostService;

@Configuration
public class JavaConfig {

    @Bean
    public PostController postController() {
        return new PostController(postService());
    }
    @Bean
    public PostService postService() {
        return new PostService(postRepository());
    }

    @Bean
    public PostRepository postRepository() {
        return new PostRepositoryRecord();
    }
}
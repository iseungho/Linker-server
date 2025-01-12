package org.zerock.apiserver.config.Initializer;

import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import org.zerock.apiserver.service.CategoryService;

@Component
public class CategoryInitializer implements CommandLineRunner {
    private final CategoryService categoryService;

    public CategoryInitializer(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    @Override
    public void run(String... args) throws Exception {
        categoryService.initializeCategories();
    }
}

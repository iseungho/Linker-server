package org.zerock.apiserver.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.zerock.apiserver.domain.Category;
import org.zerock.apiserver.repository.CategoryRepository;

@Service
@RequiredArgsConstructor
public class CategoryServiceImpl implements CategoryService {
    private final CategoryRepository categoryRepository;

    public void initializeCategories() {
        saveCategoryIfNotExists("glove", "Description for glove");
        saveCategoryIfNotExists("smartPhone", "Description for smartphone");
        saveCategoryIfNotExists("earphones", "Description for earphones");
        saveCategoryIfNotExists("bag", "Description for bag");
        saveCategoryIfNotExists("keyring", "Description for keyring");
    }

    public void saveCategoryIfNotExists(String name, String description) {
        categoryRepository.findByName(name).ifPresentOrElse(
                category -> System.out.println("Category '" + name + "' already exists."),
                () -> categoryRepository.save(
                        Category.builder()
                                .name(name)
                                .description(description)
                                .build()
                )
        );
    }
}

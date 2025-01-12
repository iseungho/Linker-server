package org.zerock.apiserver.service;

public interface CategoryService {
    void initializeCategories();

    void saveCategoryIfNotExists(String name, String description);
}

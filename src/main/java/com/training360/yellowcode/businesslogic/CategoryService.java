package com.training360.yellowcode.businesslogic;

import com.training360.yellowcode.database.CategoryDao;
import com.training360.yellowcode.dbTables.Category;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class CategoryService {

    private CategoryDao categoryDao;

    public CategoryService(CategoryDao categoryDao) {
        this.categoryDao = categoryDao;
    }

    public Optional<Category> findCategoryById(long id) {
        return categoryDao.findCategoryById(id);
    }
}

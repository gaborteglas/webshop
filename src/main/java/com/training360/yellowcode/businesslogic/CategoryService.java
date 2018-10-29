package com.training360.yellowcode.businesslogic;

import com.training360.yellowcode.database.CategoryDao;
import com.training360.yellowcode.dbTables.Category;
import org.springframework.stereotype.Service;

import java.util.List;
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

//    public List<Category> listCategorys() {
//        return categoryDao.listCategorys();
//    }
//
//    public void createCategory(Category category) {
//        categoryDao.createCategory(category);
//    }
//
//    public void updateCategory(long id, Category category) {
//        categoryDao.updateCategory(id, category);
//    }
//
//    public void deleteCategoryUpdateProducts(long id) {
//        categoryDao.deleteCategoryUpdateProducts(id);
//    }
//
//    public void deleteCategory(long id) {
//        categoryDao.deleteCategory(id);
//    }
}

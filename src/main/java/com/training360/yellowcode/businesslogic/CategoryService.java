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

    public List<Category> listCategorys() {
        return categoryDao.listCategorys();
    }

    public void createCategory(Category category) {
//        if (category.getName() == null || "".equals(category.getName().trim())) {
//            throw new IllegalArgumentException("A név kitöltése kötelező!");
//        }

        long allCategoryNumber = listCategorys().size();
        long thisCategoryPosition = category.getPositionNumber();

        if (thisCategoryPosition ==  allCategoryNumber + 1) {
            categoryDao.createCategory(category);
        } else if (thisCategoryPosition > allCategoryNumber + 1) {
            category.setPositionNumber(allCategoryNumber + 1);
            categoryDao.createCategory(category);
        } else if (thisCategoryPosition <= allCategoryNumber ) {
            categoryDao.updateCategoryPosition(thisCategoryPosition);
            categoryDao.createCategory(category);
        }
    }

    public void updateCategory(Category category) {
//        if (category.getName() == null || "".equals(category.getName().trim())) {
//            throw new IllegalArgumentException("A név kitöltése kötelező!");
//        }

        long allCategoryNumber = listCategorys().size();
        long thisCategoryPosition = findCategoryById(category.getId()).get().getPositionNumber();
        long newPosition = category.getPositionNumber();

        if (thisCategoryPosition == newPosition) {
            categoryDao.updateCategory(category);
        } else if (thisCategoryPosition ==  allCategoryNumber + 1) {
            categoryDao.updateCategory(category);
        } else if (thisCategoryPosition > allCategoryNumber + 1) {
            category.setPositionNumber(allCategoryNumber +1);
            categoryDao.updateCategory(category);
        } else if (thisCategoryPosition <= allCategoryNumber ) {
            categoryDao.updateCategoryPosition(thisCategoryPosition);
            categoryDao.updateCategory(category);
        }
    }


    public void deleteCategory(Category category) {
        categoryDao.deleteCategoryUpdateProducts(category.getId());
        categoryDao.deleteCategory(category);
        categoryDao.updateCategoryPositionAfterDelete(category.getId());
    }
}

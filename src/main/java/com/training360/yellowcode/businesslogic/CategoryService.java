package com.training360.yellowcode.businesslogic;

import com.training360.yellowcode.database.CategoryDao;
import com.training360.yellowcode.dbTables.Category;
import org.springframework.security.access.prepost.PreAuthorize;
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

    @PreAuthorize("hasRole('ADMIN')")
    public void createCategory(Category category) {
        if (category.getName() == null || "".equals(category.getName().trim())) {
            throw new IllegalArgumentException("A név kitöltése kötelező!");
        }

        long allCategoryNumber = listCategorys().size();
        long thisCategoryPosition = category.getPositionNumber();

        if (thisCategoryPosition == 0) {
            thisCategoryPosition = allCategoryNumber;
        }

        if (thisCategoryPosition > allCategoryNumber + 1) {
            throw new IllegalStateException("A megadott szám túl nagy");
        }

        if (thisCategoryPosition ==  allCategoryNumber + 1) {
            categoryDao.createCategory(category);
        } else if (thisCategoryPosition <= allCategoryNumber ) {
            categoryDao.updateCategoryPosition(thisCategoryPosition);
            categoryDao.createCategory(category);
        }
    }

    @PreAuthorize("hasRole('ADMIN')")
    public void updateCategory(Category category) {
        if (category.getName() == null || "".equals(category.getName().trim())) {
            throw new IllegalArgumentException("A név kitöltése kötelező!");
        }

        long allCategoryNumber = listCategorys().size();
        long thisCategoryPosition = findCategoryById(category.getId()).get().getPositionNumber();
        long newPosition = category.getPositionNumber();

        if (newPosition > allCategoryNumber) {
            throw new IllegalStateException("A megadott szám túl nagy");
        }

        if (newPosition > thisCategoryPosition) {
                categoryDao.updateCategoryPositionMinus(thisCategoryPosition, newPosition);
            } else if (newPosition < thisCategoryPosition) {
                categoryDao.updateCategoryPositionPlus(thisCategoryPosition, newPosition);
            }
            categoryDao.updateCategory(category);
        }


    @PreAuthorize("hasRole('ADMIN')")
    public void deleteCategory(Category category) {
        long id = category.getId();
        long position = category.getPositionNumber();
        categoryDao.deleteCategoryUpdateProducts(id);
        categoryDao.updateCategoryPositionAfterDelete(position);
        categoryDao.deleteCategory(category);
    }
}

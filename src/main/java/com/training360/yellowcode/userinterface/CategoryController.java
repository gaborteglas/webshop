package com.training360.yellowcode.userinterface;

import com.training360.yellowcode.businesslogic.CategoryService;
import com.training360.yellowcode.dbTables.Category;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Optional;

@RestController
public class CategoryController {

    private CategoryService categoryService;

    public CategoryController(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    @RequestMapping(value = "/api/category/{id}", method = RequestMethod.GET)
    public Optional<Category> findCategoryById(@PathVariable long id) {
        return categoryService.findCategoryById(id);
    }

    @RequestMapping(value = "/api/categories", method = RequestMethod.GET)
    public List<Category> listCategorys() {
        return categoryService.listCategorys();
    }

    @RequestMapping(value = "/api/categories", method = RequestMethod.POST)
    public void createCategory(Category category) {
        categoryService.createCategory(category);
    }

    @RequestMapping(value = "/api/categories/{id}", method = RequestMethod.POST)
    public void updateCategory(Category category) {
        categoryService.updateCategory(category);
    }

    @RequestMapping(value = "/api/categories/{id}", method = RequestMethod.DELETE)
    public void deleteCategory(Category category) {
        categoryService.deleteCategory(category);
    }
}

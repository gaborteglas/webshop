package com.training360.yellowcode.userinterface;

import com.training360.yellowcode.businesslogic.CategoryService;
import com.training360.yellowcode.dbTables.Category;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

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
}

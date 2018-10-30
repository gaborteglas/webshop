package com.training360.yellowcode;

import com.training360.yellowcode.businesslogic.Response;
import com.training360.yellowcode.dbTables.Category;
import com.training360.yellowcode.userinterface.CategoryController;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;
import java.util.Optional;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

@RunWith(SpringRunner.class)
@SpringBootTest
@Sql(scripts = "classpath:/clearcategory.sql")
@WithMockUser(username = "testadmin", roles = "ADMIN")
public class YellowCodeCategoryTest {

    @Autowired
    private CategoryController categoryController;

    @Before
    public void addCategories() {
        categoryController.createCategory(new Category(1, "a", 1));
        categoryController.createCategory(new Category(2, "b", 2));
        categoryController.createCategory(new Category(3, "c", 3));
        categoryController.createCategory(new Category(4, "d", 4));
    }


    @Test
    public void testCategoryCreateOrderedInput() {
        List<Category> categories = categoryController.listCategorys();
        assertEquals(4, categories.size());
        Response response = categoryController.createCategory(new Category(5, "e", 5));
        assertTrue(response.isValidRequest());
        categories = categoryController.listCategorys();
        assertEquals(5, categories.size());
    }

    @Test
    public void testCategoryCreateUnorderedInput() {
        List<Category> categories = categoryController.listCategorys();
        assertEquals(4, categories.size());
        Response response = categoryController.createCategory(new Category(5, "e", 7));
        assertFalse(response.isValidRequest());
        categories = categoryController.listCategorys();
        assertEquals(4, categories.size());
    }

    @Test
    public void testCategoryCreatePutBetweenTwo() {
        List<Category> categories = categoryController.listCategorys();
        assertEquals(4, categories.size());
        Response response = categoryController.createCategory(new Category(5, "e", 2));
        assertTrue(response.isValidRequest());
        categories = categoryController.listCategorys();
        assertEquals(5, categories.size());

        assertEquals(categories.get(1).getPositionNumber(), 2);
        assertEquals(categories.get(1).getId(), 5);
        assertEquals(categories.get(1).getName(), "e");

        assertEquals(categories.get(2).getPositionNumber(), 3);
        assertEquals(categories.get(2).getId(), 2);
        assertEquals(categories.get(2).getName(), "b");

        assertEquals(categories.get(0).getPositionNumber(), 1);
        assertEquals(categories.get(0).getId(), 1);
        assertEquals(categories.get(0).getName(), "a");
    }

    @Test
    public void testCategoryCreateWithoutName() {
        List<Category> categories = categoryController.listCategorys();
        assertEquals(4, categories.size());
        Response response = categoryController.createCategory(new Category(5, null, 2));
        assertFalse(response.isValidRequest());
        categories = categoryController.listCategorys();
        assertEquals(4, categories.size());
    }

    @Test
    public void testFindCategorById() {
        Optional<Category> category = categoryController.findCategoryById(1);

        assertTrue(category.isPresent());
        assertEquals("a", category.get().getName());
    }

    @Test
    public void testCategoryUpdateWithChangedOrder() {
        Response response = categoryController.updateCategory(new Category(1, "a", 4));
        assertTrue(response.isValidRequest());

        List<Category> allCategory = categoryController.listCategorys();

        assertEquals(allCategory.get(0).getId(), 2);
        assertEquals(allCategory.get(0).getPositionNumber(), 1);
        assertEquals(allCategory.get(1).getId(), 3);
        assertEquals(allCategory.get(1).getPositionNumber(), 2);
        assertEquals(allCategory.get(2).getId(), 4);
        assertEquals(allCategory.get(2).getPositionNumber(), 3);
        assertEquals(allCategory.get(3).getId(), 1);
        assertEquals(allCategory.get(3).getPositionNumber(), 4);
    }

    @Test
    public void testCategoryUpdateWithNonFittingPosition() {
        Response response = categoryController.updateCategory(new Category(1, "a", 6));
        assertFalse(response.isValidRequest());
        List<Category> allCategory = categoryController.listCategorys();

        assertEquals(allCategory.get(0).getId(), 1);
        assertEquals(allCategory.get(0).getPositionNumber(), 1);
        assertEquals(allCategory.get(1).getId(), 2);
        assertEquals(allCategory.get(1).getPositionNumber(), 2);
        assertEquals(allCategory.get(2).getId(), 3);
        assertEquals(allCategory.get(2).getPositionNumber(), 3);
        assertEquals(allCategory.get(3).getId(), 4);
        assertEquals(allCategory.get(3).getPositionNumber(), 4);
    }

    @Test
    public void testCategoryUpdateWithoutName() {
        List<Category> categories = categoryController.listCategorys();
        assertEquals(4, categories.size());
        Response response = categoryController.updateCategory(new Category(5, null, 2));
        assertFalse(response.isValidRequest());
        categories = categoryController.listCategorys();
        assertEquals(4, categories.size());
    }

    @Test
    public void testCategoryDeleteExistingCategoryFromMiddle() {
        Response response = categoryController.deleteCategory(new Category(2, "b", 2));
        assertTrue(response.isValidRequest());
        List<Category> allCategory = categoryController.listCategorys();

        assertEquals(allCategory.get(0).getId(), 1);
        assertEquals(allCategory.get(0).getPositionNumber(), 1);
        assertEquals(allCategory.get(1).getId(), 3);
        assertEquals(allCategory.get(1).getPositionNumber(), 2);
        assertEquals(allCategory.get(2).getId(), 4);
        assertEquals(allCategory.get(2).getPositionNumber(), 3);
    }
}

package com.training360.yellowcode;

import com.training360.yellowcode.database.CategoryDao;
import com.training360.yellowcode.dbTables.Category;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;
import java.util.Optional;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

@RunWith(SpringRunner.class)
@SpringBootTest
@Sql(scripts = "classpath:/clearcategory.sql")
public class YellowCodeCategoryDaoTest {

    @Autowired
    private CategoryDao categoryDao;

    @Before
    public void addCategories() {
        categoryDao.createCategory(new Category(1, "a", 1));
        categoryDao.createCategory(new Category(2, "b", 2));
        categoryDao.createCategory(new Category(3, "c", 3));
        categoryDao.createCategory(new Category(4, "d", 4));
    }


    @Test
    public void testCategoryDao(){
        categoryDao.updateCategoryPositionMinus(1, 4);

        List<Category> allCategory = categoryDao.listCategorys();

        assertEquals(allCategory.get(0).getId(), 1);
        assertEquals(allCategory.get(1).getPositionNumber(), 1);
        assertEquals(allCategory.get(2).getPositionNumber(), 2);
        assertEquals(allCategory.get(3).getPositionNumber(), 3);
    }

    @Test
    public void testFindCategorById() {
        Optional<Category> category = categoryDao.findCategoryById(1);

        assertTrue(category.isPresent());
        assertEquals("a", category.get().getName());
    }

    @Test
    public void testCategoryUpdate() {
        categoryDao.updateCategory(new Category(1, "a", 4));

        List<Category> allCategory = categoryDao.listCategorys();

        assertEquals(allCategory.get(0).getId(), 2);
        assertEquals(allCategory.get(0).getPositionNumber(), 1);
        assertEquals(allCategory.get(1).getId(), 3);
        assertEquals(allCategory.get(1).getPositionNumber(), 2);
        assertEquals(allCategory.get(2).getId(), 4);
        assertEquals(allCategory.get(2).getPositionNumber(), 3);
        assertEquals(allCategory.get(3).getId(), 1);
        assertEquals(allCategory.get(3).getPositionNumber(), 4);
    }
}

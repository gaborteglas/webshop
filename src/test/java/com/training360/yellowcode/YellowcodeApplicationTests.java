package com.training360.yellowcode;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.training360.yellowcode.dbTables.Product;
import com.training360.yellowcode.dbTables.ProductStatusType;
import com.training360.yellowcode.userinterface.ProductController;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.json.JsonTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.json.JacksonTester;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.util.List;
import java.util.Optional;

import static org.junit.Assert.assertEquals;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

@RunWith(SpringRunner.class)
@SpringBootTest
@Sql(scripts = "classpath:/clear.sql")
public class YellowcodeApplicationTests {

    private JacksonTester<Product> json;

    @Autowired
    private WebApplicationContext context;

    private MockMvc mvc;

    private ProductController productController;

    @Before
    public void init() throws Exception {
        JacksonTester.initFields(this, new ObjectMapper());

        mvc = MockMvcBuilders.webAppContextSetup(context).apply(springSecurity()).build();

        Product[] products = {
                new Product(1, "Az aliceblue 50 árnyalata","az-aliceblue-50-arnyalata", "E. L. Doe", 9999, ProductStatusType.ACTIVE),
                new Product(2, "Legendás programozók és megfigyelésük", "legendas-programozok-es-megfigyelesuk", "J. K. Doe",  3999, ProductStatusType.ACTIVE),
                new Product(3, "Az 50 első Trainer osztály", "az-50-elso-trainer-osztaly", "Jack Doe", 5999, ProductStatusType.ACTIVE),
                new Product(4, "Hogyan neveld a junior fejlesztődet", "hogyan-neveld-a-junior-fejlesztodet", "Jane Doe", 6499, ProductStatusType.ACTIVE),
                new Product(5, "Junior most és mindörökké", "junior-most-es-mindorokke", "James Doe", 2999, ProductStatusType.ACTIVE)
        };

        for (Product product: products) {
            mvc.perform(post("/api/products").contentType(MediaType.APPLICATION_JSON_UTF8).content(
                    "{\n" +
                            " \"id\": " + product.getId() + ",\n" +
                            " \"name\": \"" + product.getName() +"\",\n" +
                            " \"producer\": \"" + product.getProducer() +"\",\n" +
                            " \"currentPrice\": " + product.getCurrentPrice() +"\n" +
                            " }"
            )).andReturn();
        }
    }

    @WithMockUser(roles = {"ADMIN"})
    @Test
    public void testFindProductByAddress() throws Exception{

        String responseBody = mvc.perform(get("/api/products/az-aliceblue-50-arnyalata"))
                .andReturn()
                .getResponse()
                .getContentAsString();
        Product product = json.parse(responseBody).getObject();

        assertEquals(product.getAddress(), "az-aliceblue-50-arnyalata");
        assertEquals(product.getProducer(), "E. L. Doe");
        assertEquals(product.getName(), "Az aliceblue 50 árnyalata");
        assertEquals(product.getCurrentPrice(), 9999);
        assertEquals(product.getId(), 1);
    }

    @WithMockUser(roles = {"ADMIN"})
    @Test
    public void testFindProductByInvalidAddress() throws Exception{
        String responseBody = mvc.perform(get("/api/products/henger")).andReturn().getResponse().getContentAsString();
        assertEquals(responseBody, "null");
    }

    @WithMockUser(roles = {"ADMIN"})
    @Test
    public void testCreateProduct() {
        List<Product> products = productController.listProducts();

        productController.createProduct(new Product(
                6, "A Java ura: A classok szövetsége", "a-java-ura:-a-classok-szovetsege", "J.R.R. Doe", 2899, ProductStatusType.ACTIVE
                ));
        List<Product> products2 = productController.listProducts();
        assertEquals(products.size(), 5);
        assertEquals(products2.size(), 6);

    }

    @WithMockUser(roles = {"ADMIN"})
    @Test
    public void testUpdateProduct() {
        productController.createProduct(new Product(
                6, "A Java ura: A classok szövetsége", "a-java-ura:-a-classok-szovetsege", "J.R.R. Doe", 2899, ProductStatusType.ACTIVE
        ));
        productController.updateProduct(new Product(
                6, "A Java ura: A classok szövetsége", "a-java-ura:-a-classok-szovetsege", "J.R.R. Doe", 3899, ProductStatusType.ACTIVE),
                6);

        List<Product> products = productController.listProducts();
        assertEquals(products.size(), 6);
        assertEquals(productController.findProductByAddress("a-java-ura:-a-classok-szovetsege").get().getCurrentPrice(), 3899);
    }

    @WithMockUser(roles = {"ADMIN"})
    @Test
    public void testDeleteProduct() {
        productController.createProduct(new Product(
                6, "A Java ura: A classok szövetsége", "a-java-ura:-a-classok-szovetsege", "J.R.R. Doe", 2899, ProductStatusType.ACTIVE
        ));
        productController.deleteProduct(6);

        List<Product> products = productController.listProducts();
        List<Product> allProducts = productController.listAllProducts();

        assertEquals(products.size(), 5);
        assertEquals(allProducts.size(), 6);

        assertEquals(productController.findProductByAddress("a-java-ura:-a-classok-szovetsege").get().getStatus(), ProductStatusType.INACTIVE);
    }

    //Ez átmeneti, amíg a CRUD nem teljes
    @WithMockUser(roles = {"ADMIN"})
    @Test
    public void testListSortedProductsByNameThenProducer() {
        List<Product> products = productController.listProducts();
        assertEquals(products.size(), 5);
        assertEquals(products.get(0).getName(), "Az 50 első Trainer osztály");
        assertEquals(products.get(products.size() - 1).getName(), "Legendás programozók és megfigyelésük");
    }



}

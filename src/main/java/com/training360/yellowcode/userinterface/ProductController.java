package com.training360.yellowcode.userinterface;

import com.training360.yellowcode.businesslogic.ProductService;
import com.training360.yellowcode.database.DuplicateProductException;
import com.training360.yellowcode.dbTables.Product;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
public class ProductController {

    private ProductService productService;

    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @RequestMapping(value = "/api/products/{address}", method = RequestMethod.GET)
    public Optional<Product> findProductByAddress(@PathVariable String address) {
        return productService.findProductByAddress(address);
    }

    @RequestMapping(value = "/api/products", method = RequestMethod.GET)
    public List<Product> listProducts() {
        return productService.listProducts();
    }

    @RequestMapping(value = "/api/products", method = RequestMethod.POST)
    public ResponseEntity<String> createProduct(@RequestBody Product product) {
        try {
            productService.createProduct(product);
            return ResponseEntity.ok("Successfully created.");
        } catch (DuplicateProductException dpe) {
            return ResponseEntity.status(HttpStatus.CONFLICT).build();
        }
    }

    @RequestMapping(value = "/api/products/{id}", method = RequestMethod.POST)
    public ResponseEntity<String> updateProduct(@RequestBody Product product, @PathVariable long id) {
        try {
            productService.updateProduct(id, product);
            return ResponseEntity.ok("Successfully created.");
        } catch (DuplicateProductException dpe) {
            return ResponseEntity.status(HttpStatus.CONFLICT).build();
        }
    }

    @RequestMapping(value = "/api/products/{id}", method = RequestMethod.DELETE)
    public void deleteProduct(@PathVariable long id) {
        productService.deleteProduct(id);
    }
}
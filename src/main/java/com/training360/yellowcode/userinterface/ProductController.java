package com.training360.yellowcode.userinterface;

import com.training360.yellowcode.businesslogic.ProductService;
import com.training360.yellowcode.dbTables.Product;
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

    @RequestMapping(value = "/api/allproducts", method = RequestMethod.GET)
    public List<Product> listAllProducts() {
        return productService.listAllProducts();
    }

    @RequestMapping(value = "/api/products", method = RequestMethod.POST)
    public void createProduct(@RequestBody Product product) {
        productService.createProduct(product.getId(), product.getName(), product.getAddress(), product.getProducer(), product.getCurrentPrice());
    }

    @RequestMapping(value = "/api/products/{id}", method = RequestMethod.POST)
    public void updateProduct(@RequestBody Product product, @PathVariable long id) {
        productService.updateProduct(id, product.getId(), product.getName(), product.getAddress(), product.getProducer(), product.getCurrentPrice());
    }

    @RequestMapping(value = "/api/products/{id}", method = RequestMethod.DELETE)
    public void deleteProduct(@PathVariable long id) {
        productService.deleteProduct(id);
    }
}
package com.training360.yellowcode.userinterface;

import com.training360.yellowcode.businesslogic.ProductService;
import com.training360.yellowcode.dbTables.Product;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

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
    public List<Product> listEmployees() {
        return productService.listProducts();
    }
}
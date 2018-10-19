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

    @RequestMapping(value = "/api/employees/{address}", method = RequestMethod.GET)
    public Optional<Product> findProductByAddress(@PathVariable String address){
        return productService.findProductByAddress(address);
    }
}

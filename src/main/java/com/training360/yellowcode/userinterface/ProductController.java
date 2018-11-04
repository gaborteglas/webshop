package com.training360.yellowcode.userinterface;

import com.training360.yellowcode.businesslogic.ProductService;
import com.training360.yellowcode.businesslogic.Response;
import com.training360.yellowcode.database.DuplicateProductException;
import com.training360.yellowcode.dbTables.Product;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.net.URLEncoder;
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

    @RequestMapping(value = "/api/products/category/{categoryId}", method = RequestMethod.GET)
    public List<Product> listProductsByCategory(@PathVariable long categoryId) {
        return productService.listProductsByCategory(categoryId);
    }

    @RequestMapping(value = "/api/products", method = RequestMethod.GET)
    public List<Product> listProducts() {
        return productService.listProducts();
    }

    @RequestMapping(value = "/api/products", method = RequestMethod.POST)
    public Response createProduct(@RequestBody Product product) {
        try {
            productService.createProduct(product);
            return new Response(true, "Hozzáadva.");
        } catch (DuplicateProductException dpe) {
            return new Response(false, "A megadott id vagy cím már foglalt.");
        } catch (IllegalArgumentException iae) {
            return new Response(false, "Az ár megadása kötelező, csak egész szám lehet és nem haladhatja meg a 2.000.000 Ft-ot.");
        }
    }

    @RequestMapping(value = "/api/products/{id}", method = RequestMethod.POST)
    public Response updateProduct(@RequestBody Product product, @PathVariable long id) {
        try {
            productService.updateProduct(id, product);
            return new Response(true, "Módosítva.");
        } catch (DuplicateProductException dpe) {
            return new Response(false, "A megadott id vagy cím már foglalt.");
        } catch (IllegalArgumentException iae) {
            return new Response(false, "Az ár megadása kötelező, csak egész szám lehet és nem haladhatja meg a 2.000.000 Ft-ot.");
        }
    }

    @RequestMapping(value = "/api/products/{id}", method = RequestMethod.DELETE)
    public void deleteProduct(@PathVariable long id) {
        productService.deleteProduct(id);
    }

    @RequestMapping (value = "/api/products/lastsold", method = RequestMethod.GET)
    public List<Product> showLastThreeSoldProducts() {
        return productService.showLastThreeSoldProducts();
    }

    @RequestMapping(value = "/api/upload/{id}", method = RequestMethod.POST)
    public Response uploadPicture(@RequestBody MultipartFile file, @PathVariable long id) {
        try {
            byte[] imageBytes = file.getBytes();
            productService.uploadPicture(imageBytes, id);
        } catch (IOException ioe) {
            return new Response(false, "Egy");
        }
        return new Response(true, "Kettő");
    }
}
package com.training360.yellowcode.businesslogic;

import com.training360.yellowcode.database.ProductDao;
import com.training360.yellowcode.dbTables.Product;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;

import java.text.Normalizer;
import java.util.List;
import java.util.Optional;

@Service
public class ProductService {

    public static final Logger LOGGER = LoggerFactory.getLogger(ProductService.class);
    private ProductDao productDao;

    public ProductService(ProductDao productDao) {
        this.productDao = productDao;
    }

    public Optional<Product> findProductByAddress(String address) {
        return productDao.findProductByAddress(address);
    }

    public List<Product> listProducts() {
        return productDao.listProducts();
    }

    @PreAuthorize("hasRole('ADMIN')")
    public List<Product> listAllProducts() {
        return productDao.listAllProducts();
    }

    @PreAuthorize("hasRole('ADMIN')")
    public void createProduct(Product product) {
        if (product.getAddress() == null) {
            product.setAddress(ProductAddressGenerator.generateUserFriendlyAddress(product));
        }
        productDao.createProduct(product);
    }

    @PreAuthorize("hasRole('ADMIN')")
    public void updateProduct(long id, Product product) {
        productDao.updateProduct(id, product);
    }

    @PreAuthorize("hasRole('ADMIN')")
    public void deleteProduct(long id) {
        productDao.deleteProduct(id);
    }


}

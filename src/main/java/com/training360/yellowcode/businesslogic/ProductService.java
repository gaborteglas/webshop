package com.training360.yellowcode.businesslogic;

import com.training360.yellowcode.database.ProductDao;
import com.training360.yellowcode.dbTables.Product;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ProductService {

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

    public List<Product> listAllProducts() {
        return productDao.listAllProducts();
    }

    public void createProduct(long id, String name, String address, String producer, long currentPrice) {
        productDao.createProduct(id, name, address, producer, currentPrice);
    }

    public void updateProduct(long id, long newId, String name, String address, String producer, long currentPrice) {
        productDao.updateProduct(id, newId, name, address, producer, currentPrice);
    }

    public void deleteProduct(long id) {
        productDao.deleteProduct(id);
    }
}

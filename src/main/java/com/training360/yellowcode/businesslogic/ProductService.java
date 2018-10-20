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
}

package com.training360.yellowcode.businesslogic;

import com.training360.yellowcode.database.BasketsDao;
import com.training360.yellowcode.database.ProductDao;
import com.training360.yellowcode.dbTables.Basket;
import com.training360.yellowcode.dbTables.Product;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class BasketsService {

    public static final Logger LOGGER = LoggerFactory.getLogger(ProductService.class);
    private BasketsDao basketsDao;
    private ProductDao productDao;

    public BasketsService(BasketsDao basketsDao,ProductDao productDao) {
        this.basketsDao = basketsDao;
        this.productDao = productDao;
    }

    public List<Basket> listProducts() {
        return basketsDao.listProducts();
    }

    public List<Basket> listBasketsByUserId(long id){
        List<Basket> allBasket = new ArrayList<>(basketsDao.listProducts());
        List<Basket> usersBasket = new ArrayList<>();
        for(Basket basket:allBasket){
            if(basket.getUserId()== id){
                usersBasket.add(basket);
            }
        }
        return usersBasket;
    }

    public List<Product> listProductsByUserId(long id){
        List<Basket> allBasket = new ArrayList<>(basketsDao.listProducts());
        List<Basket> usersBasket = new ArrayList<>();
        for(Basket basket:allBasket){
            if(basket.getUserId()== id){
                usersBasket.add(basket);
            }
        }
        System.out.println(usersBasket.toString());
        List<Product> usersBasketProducts = new ArrayList<>();
        List<Product> allProducts = new ArrayList<>(productDao.listProducts());
        for(Basket basket:usersBasket){
            for(Product product:allProducts){
                if(basket.getProductId() == product.getId()){
                    usersBasketProducts.add(product);
                }
            }
        }
        return usersBasketProducts;

    }

    public void addToBasket(Basket basket) {
        basketsDao.addToBasket(basket);
    }

    public void deleteFromBasketByUserId(long userId) {
        basketsDao.deleteFromBasketByUserId(userId);
    }

    public void deleteFromBasketByProductIdAndUserId(long userId, long productId) {
        basketsDao.deleteFromBasketByProductIdAndUserId(userId, productId);
    }
}

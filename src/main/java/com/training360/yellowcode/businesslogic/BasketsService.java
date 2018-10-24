package com.training360.yellowcode.businesslogic;

import com.training360.yellowcode.database.BasketsDao;
import com.training360.yellowcode.dbTables.Basket;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BasketsService {

    public static final Logger LOGGER = LoggerFactory.getLogger(ProductService.class);
    private BasketsDao basketsDao;

    public BasketsService(BasketsDao basketsDao) {
        this.basketsDao = basketsDao;
    }

    public List<Basket> listProducts() {
        return basketsDao.listProducts();
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

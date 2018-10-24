package com.training360.yellowcode.businesslogic;

import com.training360.yellowcode.database.BasketsDao;
import com.training360.yellowcode.dbTables.Basket;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BasketsService {

    private BasketsDao basketsDao;

    public BasketsService(BasketsDao basketsDao) {
        this.basketsDao = basketsDao;
    }

    public List<Basket> listProducts() {
        return basketsDao.listProducts();
    }

    public void addToBasket(long userId, long productId) {
        basketsDao.addToBasket(userId,productId);
    }


    public void deleteFromBasketById(long userId) {
        basketsDao.deleteFromBasketById(userId);
    }

    public void deleteFromBasketByProductIdAndUserId(long userId, long productId) {
        basketsDao.deleteFromBasketByProductIdAndUserId(userId, productId);
    }
}

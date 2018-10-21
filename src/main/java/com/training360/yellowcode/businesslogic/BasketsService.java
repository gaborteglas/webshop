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

    public void addToBasket(long userId, long productId, long id) {
        basketsDao.addToBasket(userId, productId, id);
    }
}

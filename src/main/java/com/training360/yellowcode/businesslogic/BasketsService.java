package com.training360.yellowcode.businesslogic;

import com.training360.yellowcode.database.BasketsDao;
import com.training360.yellowcode.dbTables.Basket;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.text.MessageFormat;
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

    public List<Basket> findBasketByUserIdAndProductId(Basket basket) {
        return basketsDao.findBasketByByUserIdAndProductId(basket);
    }

    public Response addToBasket(Basket basket) {
        List<Basket> sameProductInUserBasket = findBasketByUserIdAndProductId(basket);
        if (sameProductInUserBasket.size() == 0) {
            basketsDao.addToBasket(basket);
            LOGGER.info(MessageFormat.format("Product (id: {0}) added to basket of user (id: {1})",
                    basket.getUserId(), basket.getProductId()));
            return new Response(true, "Termék hozzáadva a kosárhoz.");
        } else {
            return new Response(false, "A termék már szerepel a kosárban.");
        }
    }

    public Response deleteFromBasketByUserId(long userId) {
        basketsDao.deleteFromBasketByUserId(userId);
        LOGGER.info(MessageFormat.format("Basket of (userId: {0}) user has been removed", userId));
        return new Response(true, "Kosár ürítve.");
    }

    public Response deleteFromBasketByProductIdAndUserId(long userId, long productId) {
        basketsDao.deleteFromBasketByProductIdAndUserId(userId, productId);
        LOGGER.info(MessageFormat.format("Product (productId: {0}) of user (userId: {1}) has been removed",
                productId, userId));
        return new Response(true, "A termék törölve lett a kosárból.");
    }
}

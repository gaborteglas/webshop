package com.training360.yellowcode.userinterface;

import com.training360.yellowcode.businesslogic.BasketsService;
import com.training360.yellowcode.dbTables.Basket;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
@RestController
public class BasketController {

    private BasketsService basketsService;

    public BasketController(BasketsService basketsService) {
        this.basketsService = basketsService;
    }

    @RequestMapping(value = "/api/basket", method = RequestMethod.GET)
    public List<Basket> listProducts() {
        return basketsService.listProducts();
    }

    @RequestMapping(value = "/api/basket", method = RequestMethod.POST)
    public void addToBasket(@RequestBody Basket basket){
        basketsService.addToBasket(basket.getUserId(),basket.getProductId());
    }

    @RequestMapping(value = "/api/basket", method = RequestMethod.DELETE)
    public void deleteAll(){
        basketsService.deleteAll();
    }
}

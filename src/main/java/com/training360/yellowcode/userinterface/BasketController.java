package com.training360.yellowcode.userinterface;

import com.training360.yellowcode.businesslogic.BasketsService;
import com.training360.yellowcode.dbTables.Basket;
import org.springframework.web.bind.annotation.*;

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
        basketsService.addToBasket(basket);
    }

    @RequestMapping(value = "/api/basket/{userId}",method = RequestMethod.DELETE)
    public void deleteFromBasketById(@PathVariable long userId){
        basketsService.deleteFromBasketById(userId);
    }

    @RequestMapping(value= "/api/basket/{userId}/{productId}",method = RequestMethod.DELETE)
    public void deleteFromBasketByProductIdAndUserId(@PathVariable long userId,@PathVariable long productId){
        basketsService.deleteFromBasketByProductIdAndUserId(userId,productId);
    }
}

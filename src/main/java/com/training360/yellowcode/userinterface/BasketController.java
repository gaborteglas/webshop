package com.training360.yellowcode.userinterface;

import com.training360.yellowcode.businesslogic.BasketsService;
import com.training360.yellowcode.database.DuplicateProductException;
import com.training360.yellowcode.dbTables.Basket;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
    public ResponseEntity<String> addToBasket(@RequestBody Basket basket){
        try {
            basketsService.addToBasket(basket);
            return ResponseEntity.ok("Successfully added to basket.");
        } catch (DuplicateProductException dpe) {
            return ResponseEntity.status(HttpStatus.CONFLICT).build();
        }

    }

    @RequestMapping(value = "/api/basket/{userId}",method = RequestMethod.DELETE)
    public void deleteFromBasketByUserId(@PathVariable long userId){
        basketsService.deleteFromBasketByUserId(userId);
    }

    @RequestMapping(value= "/api/basket/{userId}/{productId}",method = RequestMethod.DELETE)
    public void deleteFromBasketByProductIdAndUserId(@PathVariable long userId,@PathVariable long productId){
        basketsService.deleteFromBasketByProductIdAndUserId(userId,productId);
    }
}

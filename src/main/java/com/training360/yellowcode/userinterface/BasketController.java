package com.training360.yellowcode.userinterface;

import com.training360.yellowcode.businesslogic.BasketsService;
import com.training360.yellowcode.businesslogic.UserService;
import com.training360.yellowcode.database.DuplicateProductException;
import com.training360.yellowcode.dbTables.Basket;
import com.training360.yellowcode.dbTables.Product;
import com.training360.yellowcode.dbTables.User;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;
@RestController
public class BasketController {

    private BasketsService basketsService;
    private UserService userService;

    public BasketController(BasketsService basketsService, UserService userService) {
        this.basketsService = basketsService;
        this.userService = userService;
    }



    @RequestMapping(value = "/api/basket", method = RequestMethod.GET)
    public List<Basket> listProducts() {
        return basketsService.listProducts();
    }

    @RequestMapping(value = "/api/basket/{id}", method = RequestMethod.GET)
    public List<Product> listProductsByUserId(@PathVariable long id){
        return basketsService.listProductsByUserId(id);
    }

    @RequestMapping(value = "/api/basket", method = RequestMethod.POST)
    public ResponseEntity<String> addToBasket(@RequestBody Basket basket){
        try {
            if (!isAllowedToChangeBasketForUserId(basket.getUserId())) {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
            }
            basketsService.addToBasket(basket);
            return ResponseEntity.ok("Successfully added to basket.");

        } catch (DuplicateProductException dpe) {
            return ResponseEntity.status(HttpStatus.CONFLICT).build();
        }
    }

    @RequestMapping(value = "/api/basket/{userId}",method = RequestMethod.DELETE)
    public ResponseEntity<String> deleteFromBasketByUserId(@PathVariable long userId){
        if (!isAllowedToChangeBasketForUserId(userId)) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
        basketsService.deleteFromBasketByUserId(userId);
        return ResponseEntity.ok("Successfully emptied basket.");

    }

    @RequestMapping(value= "/api/basket/{userId}/{productId}",method = RequestMethod.DELETE)
    public ResponseEntity<String> deleteFromBasketByProductIdAndUserId(@PathVariable long userId,@PathVariable long productId){
        if (!isAllowedToChangeBasketForUserId(userId)) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
        basketsService.deleteFromBasketByProductIdAndUserId(userId,productId);
        return ResponseEntity.ok("Successfully deleted from basket.");
    }


    private boolean isAllowedToChangeBasketForUserId(long userId) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null || authentication instanceof AnonymousAuthenticationToken) {     //nincs bejelentkezve senki
            return false;
        }

        User user = userService.findUserByUserName(authentication.getName()).get();
        if (user.getId() != userId) {
            return false;
        }
        return true;
    }
}

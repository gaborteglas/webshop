package com.training360.yellowcode.userinterface;

import com.training360.yellowcode.businesslogic.BasketsService;
import com.training360.yellowcode.businesslogic.Response;
import com.training360.yellowcode.businesslogic.UserService;
import com.training360.yellowcode.database.DuplicateProductException;
import com.training360.yellowcode.dbTables.Basket;
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

    @RequestMapping(value = "/api/basket", method = RequestMethod.POST)
    public Response addToBasket(@RequestBody Basket basket) {
        if (isAllowedToChangeBasketForUserId(basket.getUserId())) {
            return basketsService.addToBasket(basket);
        } else {
            return new Response(false, "A falhasználó nem jogosult a kosár módosítására");
        }
    }

    @RequestMapping(value = "/api/basket/{userId}", method = RequestMethod.DELETE)
    public Response deleteFromBasketByUserId(@PathVariable long userId) {
        if (isAllowedToChangeBasketForUserId(userId)) {
            return basketsService.deleteFromBasketByUserId(userId);
        } else {
            return new Response(false, "A felhasználó nem jogosult a törlésre.");
        }
    }

    @RequestMapping(value = "/api/basket/{userId}/{productId}", method = RequestMethod.DELETE)
    public Response deleteFromBasketByProductIdAndUserId(@PathVariable long userId, @PathVariable long productId) {
        if (isAllowedToChangeBasketForUserId(userId)) {
            return basketsService.deleteFromBasketByProductIdAndUserId(userId, productId);
        } else {
            return new Response(false, "A felhasználó nem jogosult a törlésre.");
        }
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

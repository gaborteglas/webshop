package com.training360.yellowcode.userinterface;

import com.training360.yellowcode.businesslogic.BasketsService;
import com.training360.yellowcode.businesslogic.Response;
import com.training360.yellowcode.businesslogic.UserService;
import com.training360.yellowcode.dbTables.Basket;
import com.training360.yellowcode.dbTables.Product;
import com.training360.yellowcode.dbTables.User;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
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
        User user = getAuthenticatedUserId();
        if (user != null) {
            return basketsService.listProducts(user.getId());
        } else {
            return new ArrayList<>();
        }
    }

    @RequestMapping(value = "/api/basket/{productId}", method = RequestMethod.POST)
    public Response addToBasket(@PathVariable long productId) {
        User user = getAuthenticatedUserId();
        if (user != null) {
            return basketsService.addToBasket(new Basket(user.getId(), productId));
        } else {
            return new Response(false, "A felhasználó nem jogosult a kosár módosítására.");
        }
    }

    @RequestMapping(value = "/api/basket", method = RequestMethod.DELETE)
    public Response deleteWholeBasket() {
        User user = getAuthenticatedUserId();
        if (user != null) {
            return basketsService.deleteFromBasketByUserId(user.getId());
        } else {
            return new Response(false, "A felhasználó nem jogosult a törlésre.");
        }
    }

    @RequestMapping(value = "/api/basket/{productId}", method = RequestMethod.DELETE)
    public Response deleteSingleProduct(@PathVariable long productId) {
        User user = getAuthenticatedUserId();
        if (user != null) {
            return basketsService.deleteFromBasketByProductIdAndUserId(user.getId(), productId);
        } else {
            return new Response(false, "A felhasználó nem jogosult a törlésre.");
        }
    }


    private User getAuthenticatedUserId() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null || authentication instanceof AnonymousAuthenticationToken) {     //nincs bejelentkezve
            return null;
        }
        User user = userService.findUserByUserName(authentication.getName()).get();
        return user;
    }
}

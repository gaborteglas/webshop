package com.training360.yellowcode.userinterface;

import com.training360.yellowcode.businesslogic.UserService;
import com.training360.yellowcode.database.DuplicateProductException;
import com.training360.yellowcode.dbTables.User;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;

@RestController
public class UserController {

    private UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @RequestMapping(value = "/api/users", method = RequestMethod.POST)
    public ResponseEntity<String> createUser(@RequestBody User user) {
        try {
            userService.createUser(user);
            return ResponseEntity.ok("Successfully created.");
        } catch (DuplicateProductException dpe) {
            return ResponseEntity.status(HttpStatus.CONFLICT).build();
        }
    }

    @RequestMapping("/api/user")
    public User getUser(Authentication authentication) {
        if (authentication == null) {
            return null;
        } else {
            UserDetails userDetails = (UserDetails) authentication.getPrincipal();
            String name = userDetails.getUsername();
            String role = new ArrayList<GrantedAuthority>(userDetails.getAuthorities()).get(0).getAuthority();
            return new User(name, role);
        }
    }


}

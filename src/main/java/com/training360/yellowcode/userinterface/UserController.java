package com.training360.yellowcode.userinterface;

import com.training360.yellowcode.dbTables.User;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;

@RestController
public class UserController {

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

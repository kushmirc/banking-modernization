package com.banking.controller;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Collection;

@Controller
@RequestMapping("/dashboard")
@PreAuthorize("isAuthenticated()")
public class DashboardController {

    @GetMapping("")
    public String dashboard(Authentication authentication, Model model) {
        String username = authentication.getName();
        // get the roles (even though there will be only one in the list)
        Collection<? extends GrantedAuthority> authorities = authentication.getAuthorities();
        // get the role from the list
        String role = "";
        for(GrantedAuthority authority : authorities){
            role = authority.getAuthority();
            break;
        }

        return switch (role){
            case "ROLE_ADMINISTRATOR" -> {
                yield  "dashboard/admin-dashboard";
            }
            case "ROLE_BANKER" -> {
                yield  "dashboard/banker-dashboard";
            }
            case "ROLE_CUSTOMER" -> {
                yield  "dashboard/customer-dashboard";
            }
            default -> "home";
        };




    }
}

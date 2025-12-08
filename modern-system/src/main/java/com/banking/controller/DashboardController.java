package com.banking.controller;

import com.banking.dto.CustomerDashboardDTO;
import com.banking.model.Banker;
import com.banking.repository.BankerRepository;
import com.banking.service.DashboardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Collection;

@Controller
@RequestMapping("/dashboard")
//@PreAuthorize("isAuthenticated()")
public class DashboardController {

    @Autowired
    DashboardService dashboardService;

    @Autowired
    BankerRepository bankerRepository;

    @GetMapping("")
    public String dashboard(Authentication authentication, Model model) {
        String username = authentication.getName();

        String role = "";
        // get the roles (even though there will be only one in the list)
        Collection<? extends GrantedAuthority> authorities = authentication.getAuthorities();
        // get the role from the list
        for(GrantedAuthority authority : authorities){
            role = authority.getAuthority();
            //break;
        }

        return switch (role){
            case "ROLE_ADMINISTRATOR" -> prepareCustomerDashboard(username, model);
            case "ROLE_BANKER" -> {
                Banker banker = bankerRepository.findByUserid(username)
                                .orElseThrow(() -> new UsernameNotFoundException("Banker not found"));

                model.addAttribute("firstName", banker.getFname()); // Temporary
                yield  "dashboard/banker-dashboard";
            }
            case "ROLE_CUSTOMER" -> prepareCustomerDashboard(username, model);
            default -> "redirect:/login";
        };
    }

    private String prepareCustomerDashboard(String username, Model model) {
        CustomerDashboardDTO customerDashboard = dashboardService.getCustomerDashboard(username);

        model.addAttribute("firstName", customerDashboard.getFirstName());
        return "dashboard/customer-dashboard";
    }


}

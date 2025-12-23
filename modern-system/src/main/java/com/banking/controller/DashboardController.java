package com.banking.controller;

import com.banking.dto.dashboard.AdministratorDashboardDTO;
import com.banking.dto.dashboard.CustomerDashboardDTO;
import com.banking.repository.BankerRepository;
import com.banking.security.BankingUserDetails;
import com.banking.service.DashboardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

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
        BankingUserDetails userDetails = (BankingUserDetails) authentication.getPrincipal();
        String role = userDetails.getRole();

        return switch (role){
            case "ADMINISTRATOR" -> prepareAdministratorDashboard(userDetails, model);

            case "BANKER" -> {
                model.addAttribute("firstName", userDetails.getFirstName());
                yield  "dashboard/banker-dashboard";
            }

            case "CUSTOMER" -> prepareCustomerDashboard(userDetails, model);

            default -> "redirect:/login";
        };
    }

    private String prepareAdministratorDashboard(BankingUserDetails user, Model model) {
        AdministratorDashboardDTO administratorDashboard = dashboardService.getAdministratorDashboard(user.getUserId());

        model.addAttribute("firstName", user.getFirstName());
        model.addAttribute("recentComplaints", administratorDashboard.getRecentComplaints());
        model.addAttribute("allComplaints", administratorDashboard.getAllComplaints());
        return "dashboard/admin-dashboard";
    }

    private String prepareCustomerDashboard(BankingUserDetails user, Model model) {

        CustomerDashboardDTO customerDashboard = dashboardService.getCustomerDashboard(user.getUserId());

        model.addAttribute("firstName", user.getFirstName());
        model.addAttribute("accountNumber", customerDashboard.getAccountNumber());
        model.addAttribute("formattedBalance", customerDashboard.getFormattedBalance());
        model.addAttribute("transactionsFromAccount", customerDashboard.getTransactionsFromAccount());
        return "dashboard/customer-dashboard";
    }

}

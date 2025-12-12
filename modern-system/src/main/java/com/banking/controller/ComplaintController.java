package com.banking.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/complaints")
public class ComplaintController {

    @GetMapping("/customer")
    public String customerComplaints(Model model) {
        return "complaints/customer-complaints";
    }

    @GetMapping("/banker")
    public String bankerComplaints(Model model) {
        return "complaints/banker-complaints";
    }

    @GetMapping("/admin")
    public String adminComplaints(Model model){
        return "complaints/admin-complaints";

    }

    @GetMapping("/new")
    public String newComplaint(Model model) {
        return "complaints/new";
    }

}

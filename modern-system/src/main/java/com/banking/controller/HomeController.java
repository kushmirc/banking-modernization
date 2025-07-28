package com.banking.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class HomeController {
    
    @GetMapping("/")
    public String home() {
        return "home";
    }
    
    @GetMapping("/login")
    public String login() {
        return "login";
    }

    @GetMapping("/about")
    public String about() {
        return "about";
    }

    @GetMapping("/contact")
    public String contact() {
        return "contact";
    }

    @PostMapping("/contact")
    public String submitContactForm(
            @RequestParam String name,
            @RequestParam String email,
            @RequestParam(required = false) String phone,
            @RequestParam String subject,
            @RequestParam String message,
            RedirectAttributes redirectAttributes) {
        
        // TODO: In a real implementation, this would:
        // 1. Validate the form data
        // 2. Save the message to database
        // 3. Send email notification to staff
        // 4. Send confirmation email to customer
        
        // For now, we'll just log the submission and redirect with success message
        System.out.println("Contact form submitted:");
        System.out.println("Name: " + name);
        System.out.println("Email: " + email);
        System.out.println("Phone: " + phone);
        System.out.println("Subject: " + subject);
        System.out.println("Message: " + message);
        
        redirectAttributes.addFlashAttribute("successMessage", 
            "Thank you for contacting us, " + name + ". We will get back to you within 24 hours.");
        
        return "redirect:/contact";
    }

}
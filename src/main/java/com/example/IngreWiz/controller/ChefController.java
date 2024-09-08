package com.example.IngreWiz.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import com.example.IngreWiz.service.ChefService;
import com.example.IngreWiz.model.Chef;
import com.example.IngreWiz.model.Category; // Assuming Category is an enum

@Controller
public class ChefController {

    @Autowired
    private ChefService chefService;

    @GetMapping("/")
    public String home() {
        return "home";
    }

    @GetMapping("/register")
    public String registerForm(Model model) {
        model.addAttribute("chef", new Chef());
        model.addAttribute("categories", Category.values()); // Add this line
        return "register";
    }

    @PostMapping("/register")
    public String registerChef(@ModelAttribute Chef chef) {
        chefService.createChef(chef.getChefName(), chef.getEmail(), chef.getPreferredCuisineCategory());
        return "success";
    }
}

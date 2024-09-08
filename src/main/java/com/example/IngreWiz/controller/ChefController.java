package com.example.IngreWiz.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import com.example.IngreWiz.service.ChefService;
import com.example.IngreWiz.model.Chef;
import com.example.IngreWiz.model.Category; 
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import java.util.Optional;
import com.example.IngreWiz.repository.ChefRepository;
import com.example.IngreWiz.service.ChefService;




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
    public String registerChef(@ModelAttribute Chef chef, Model model) {
        Chef savedChef = chefService.createChef(chef.getChefName(), chef.getEmail(), chef.getPreferredCuisineCategory());
        System.out.println("Chef ID: " + savedChef.getId()); // Debug statement
        model.addAttribute("chef", savedChef);
        return "success";
    }

    @GetMapping("/chef/{id}/profile")
    public String viewChefProfile(@PathVariable Long id, Model model) {
        Optional<Chef> chef = chefService.getChefById(id);
        if (chef.isPresent()) {
            model.addAttribute("chef", chef.get());
        } else {
            throw new IllegalArgumentException("Chef does not exist");
        }
        return "viewChefProfile";
    }

    @GetMapping("/chef/{id}/edit")
    public String editChefForm(@PathVariable Long id, Model model) {
        Optional<Chef> chef = chefService.getChefById(id);
        model.addAttribute("chef", chef.get());
        return "editChef";
    }

    @PostMapping("/chef/{id}/update")
    public String updateChef(@PathVariable Long id, @ModelAttribute Chef chef) {
        chefService.updateChefProfile(id, chef.getPhoneNumber(), chef.getProfilePictureUrl());
        return "redirect:/chef/" + id + "/edit?success";
    }
}

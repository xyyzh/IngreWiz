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
import com.example.IngreWiz.model.Recipe;
import com.example.IngreWiz.service.RecipeService;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import java.util.Optional;
import com.example.IngreWiz.repository.ChefRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RequestBody;



@Controller
public class ChefController {

    @Autowired
    private ChefService chefService;

    @Autowired
    private RecipeService recipeService;

    @GetMapping("/")
    public String home() {
        return "home";
    }

    @GetMapping("/register")
    public String registerForm(Model model) {
        model.addAttribute("chef", new Chef());
        model.addAttribute("categories", Category.values()); 
        return "register";
    }

    @PostMapping("/register")
    public String registerChef(@ModelAttribute Chef chef, Model model) {
        Chef savedChef = chefService.createChef(chef.getChefName(), chef.getEmail(), chef.getPreferredCuisineCategory());
        System.out.println("Chef ID: " + savedChef.getId()); // Debug statement
        model.addAttribute("chef", savedChef);
        return "success";
    }

    @GetMapping("/login")
    public String loginForm(Model model) {
        model.addAttribute("chef", new Chef());
        return "login";
    }

    @GetMapping("/chef/{chefId}/profile")
    public String viewChefProfile(@PathVariable Long chefId, Model model) {
        Optional<Chef> chef = chefService.getChefById(chefId);
        if (chef.isPresent()) {
            model.addAttribute("chef", chef.get());
        } else {
            throw new IllegalArgumentException("Chef does not exist");
        }
        return "viewChefProfile";
    }


    @PostMapping("/validate")
    @ResponseBody
    public ResponseEntity<Boolean> validateChef(@RequestParam Long id, @RequestParam String chefName) {
        Optional<Chef> chef = chefService.getChefById(id);
        if (chef.isPresent() && chef.get().getChefName().equals(chefName)) {
            return ResponseEntity.ok(true);
        } else {
            return ResponseEntity.ok(false);
        }
    }

    @GetMapping("/chef/{chefId}/update")
    public String showUpdateForm(@PathVariable Long chefId, Model model) {
        Optional<Chef> chef = chefService.getChefById(chefId);
        if (chef.isPresent()) {
            model.addAttribute("chef", chef.get());
            model.addAttribute("categories", Category.values()); 
            return "updateChefProfile";
        } else {
            throw new IllegalArgumentException("Chef not found");
        }
    }

    @PostMapping("/chef/{chefId}/update")
    public String updateChefProfile(@PathVariable Long chefId, @RequestParam String email, @RequestParam Category preferredCuisineCategory, @RequestParam String phoneNumber, @RequestParam String bio, Model model) {
        Optional<Chef> chefOptional = chefService.getChefById(chefId);
        if (chefOptional.isPresent()) {
            Chef chef = chefOptional.get();
            chef.setPhoneNumber(phoneNumber);
            chef.setBio(bio);
            chefService.updateChefProfile(chefId, email, preferredCuisineCategory, phoneNumber, bio);
            model.addAttribute("chef", chef);
            model.addAttribute("categories", Category.values()); 
            return "redirect:/chef/" + chefId + "/profile";
        } else {
            throw new IllegalArgumentException("Chef not found");
        }
    }

}

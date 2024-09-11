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
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RequestBody;






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

    @GetMapping("/chef/{id}/update")
    public String showUpdateForm(@PathVariable Long id, Model model) {
        Optional<Chef> chef = chefService.getChefById(id);
        if (chef.isPresent()) {
            model.addAttribute("chef", chef.get());
            return "updateChefProfile";
        } else {
            throw new IllegalArgumentException("Chef not found");
        }
    }

    @PostMapping("/chef/{id}/update")
    public String updateChefProfile(@PathVariable Long id, @RequestParam String phoneNumber, @RequestParam String profilePictureUrl, Model model) {
        Optional<Chef> chefOptional = chefService.getChefById(id);
        if (chefOptional.isPresent()) {
            Chef chef = chefOptional.get();
            chef.setPhoneNumber(phoneNumber);
            chef.setProfilePictureUrl(profilePictureUrl);
            chefService.updateChefProfile(id, phoneNumber, profilePictureUrl);
            model.addAttribute("chef", chef);
            return "redirect:/chef/" + id + "/profile";
        } else {
            throw new IllegalArgumentException("Chef not found");
        }
    }
}

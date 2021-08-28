package com.k4rnaj1k.bestcafe.controller.web;

import com.k4rnaj1k.bestcafe.service.MenuService;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.security.Principal;

@Controller
public class WebController {

    private final MenuService menuService;

    public WebController(MenuService menuService) {
        this.menuService = menuService;
    }

    @GetMapping("home")
    public String homePage(Model model){
        return "home";
    }

    @GetMapping("ingredient")
    public String addIngredient(Model model, @AuthenticationPrincipal Principal principal){
        System.out.println(principal.getName());
        model.addAttribute("ingredients", menuService.getIngredients());
        return "createIngredient";
    }

    @GetMapping("login")
    public String login(Model model){
        return "login";
    }
}

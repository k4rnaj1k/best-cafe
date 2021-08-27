package com.k4rnaj1k.bestcafe.controller;

import com.k4rnaj1k.bestcafe.service.MenuService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class WebController {

    private final MenuService menuService;

    public WebController(MenuService menuService) {
        this.menuService = menuService;
    }

    @GetMapping("ingredient")
    public String addIngredient(Model model){
        model.addAttribute("ingredients", menuService.getIngredients());
        return "createIngredient";
    }
}


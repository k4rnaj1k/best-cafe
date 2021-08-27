package com.k4rnaj1k.bestcafe;

import com.k4rnaj1k.bestcafe.model.Ingredient;
import com.k4rnaj1k.bestcafe.service.MenuService;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.web.server.ResponseStatusException;

@SpringBootTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class K4rnaj1ksCafeApplicationTests {

    @Autowired
    private MenuService menuService;

    @Test
    @Order(1)
    void savesIngredient(){
        Ingredient ingredient = new Ingredient();
        ingredient.setName("potato");
        menuService.createIngredient(ingredient);
        Assertions.assertEquals(1, menuService.getIngredients().size());
    }

    @Test
    @Order(2)
    void doesntSaveDuplicateIngredients() {
        Ingredient duplicateIngredient = new Ingredient();
        duplicateIngredient.setName("potato");
        Assertions.assertThrows(ResponseStatusException.class, () -> menuService.createIngredient(duplicateIngredient));
        Assertions.assertEquals(1, menuService.getIngredients().size());
    }
}

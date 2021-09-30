package com.k4rnaj1k.bestcafe.service;

import com.k4rnaj1k.bestcafe.dto.menuitem.DishPostDTO;
import com.k4rnaj1k.bestcafe.dto.menuitem.DrinkPostDTO;
import com.k4rnaj1k.bestcafe.dto.menuitem.IngredientDTO;
import com.k4rnaj1k.bestcafe.exception.CafeExceptionUtils;
import com.k4rnaj1k.bestcafe.repository.menu.DishRepository;
import com.k4rnaj1k.bestcafe.repository.menu.IngredientRepository;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@SpringBootTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class MenuServiceTest {

    @Autowired
    private MenuService menuService;
    @Autowired
    private DishRepository dishRepository;
    @Autowired
    private IngredientRepository ingredientRepository;

    @Test
    @Order(1)
    @DisplayName("Create ingredient")
    void createIngredientTest() {
        createIngredient();
        Assertions.assertEquals(1, menuService.getIngredients().size());
    }

    private void createIngredient() {
        IngredientDTO ingredient = new IngredientDTO("potato");
        menuService.createIngredient(ingredient);
    }

    @Test
    @Order(2)
    @DisplayName("Can't create duplicate ingredient.")
    void doesntSaveDuplicateIngredients() {
        Assertions.assertThrows(ResponseStatusException.class, this::createIngredient);
        Assertions.assertEquals(1, menuService.getIngredients().size());
    }

    @Test
    @Order(3)
    @DisplayName("Create dish.")
    void createDishTest() {
        createDish();
        Assertions.assertEquals(1, menuService.getDishes().size());
    }

    private void createDish() {
        DishPostDTO dishPostDTO = new DishPostDTO("French fries", List.of(1L), 30.5);
        menuService.createDish(dishPostDTO);
    }

    @Test
    @Order(4)
    @DisplayName("Can't create duplicate dish.")
    void doesNotCreateDuplicateDish() {
        Assertions.assertThrows(ResponseStatusException.class, this::createDish);
        Assertions.assertEquals(1, menuService.getDishes().size());
    }

    @Test
    @Order(5)
    @DisplayName("Can't delete ingredient that has dish with only one ingredient.")
    void cantDeleteIngredientThatHasDishWithOneIngredient() {
        Assertions.assertThrows(ResponseStatusException.class, () -> menuService.removeIngredientById(1L));
    }

    @Test
    @Order(6)
    @DisplayName("Can delete dish and ingredient.")
    void canDeleteDishAndIngredient() {
        menuService.removeDishById(1L);
        Assertions.assertDoesNotThrow(() -> menuService.removeIngredientById(1L), CafeExceptionUtils.dishWithOneIngredient(1L).getMessage());
    }

    @Test
    @Order(7)
    @DisplayName("Update ingredient.")
    void updateIngredient() {
        createIngredient();

        String updated = "Updated potato";
        IngredientDTO ingredientDTO = new IngredientDTO(updated);
        menuService.updateIngredient(2L, ingredientDTO);
        IngredientDTO ingredient = menuService.getIngredientById(2L);
        Assertions.assertEquals(ingredient.name(), updated);

        String previous = "potato";
        IngredientDTO previousName = new IngredientDTO(previous);
        menuService.updateIngredient(2L, previousName);
        ingredient = menuService.getIngredientById(2L);
        Assertions.assertEquals(ingredient.name(), previous);
    }

    @Test
    @Order(8)
    @DisplayName("Create drink.")
    void createDrinkTest() {
        createDrink();
        Assertions.assertEquals(1, menuService.getIngredients().size());
    }

    private void createDrink() {
        DrinkPostDTO drinkPostDTO = new DrinkPostDTO("Lemonade", 10.5);
        Assertions.assertNotNull(menuService.createDrink(drinkPostDTO));
    }

    @Test
    @Order(9)
    @DisplayName("Create duplicate drink.")
    void createDuplicateDrink() {
        Assertions.assertThrows(ResponseStatusException.class, this::createDrink);
    }

    @AfterAll
    void afterAll() {
        dishRepository.deleteAll();
        ingredientRepository.deleteAll();
    }
}

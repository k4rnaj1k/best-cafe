package com.k4rnaj1k.bestcafe.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

public final class CafeException {
    private CafeException() {

    }

    public static ResponseStatusException emptyOrderException() {
        return new ResponseStatusException(HttpStatus.BAD_REQUEST, "Order cannot be sent without any drinks or dishes. Take your time and think the order through ;)");
    }

    public static ResponseStatusException ingredientDoesntExist(Long id) {
        return new ResponseStatusException(HttpStatus.BAD_REQUEST, "An ingredient with id " + id + " doesn't exist.");
    }

    public static ResponseStatusException ingredientAlreadyExists(String name) {
        return new ResponseStatusException(HttpStatus.BAD_REQUEST, "An ingredient with name " + name + " already exists.");
    }

    public static ResponseStatusException orderStatusException() {
        return new ResponseStatusException(HttpStatus.CONFLICT, "Order's status in database is already same or higher than the given one.");
    }

    public static ResponseStatusException dishDoesntExist(Long id) {
        return new ResponseStatusException(HttpStatus.BAD_REQUEST, "Dish with id " + id + " doesn't exist.");
    }

    public static ResponseStatusException orderDoesntExist(Long orderId) {
        return new ResponseStatusException(HttpStatus.NOT_FOUND, "Order with id " + orderId + " doesn't exist.");
    }

    public static ResponseStatusException tooManyExcludedIngredientsException() {
        return new ResponseStatusException(HttpStatus.BAD_REQUEST, "Order's item has too many excluded ingredients.");
    }

    public static ResponseStatusException dishWithOneIngredient(Long dish) {
        return new ResponseStatusException(HttpStatus.BAD_REQUEST, "Dish with id " + dish + " includes only the current ingredient.");
    }

    public static ResponseStatusException orderAcceptedException(Long orderId) {
        return new ResponseStatusException(HttpStatus.CONFLICT, "Order with id " + orderId + " has already been accepted or ready.");
    }

    public static ResponseStatusException excludedIngredientsException() {
        return new ResponseStatusException(HttpStatus.BAD_REQUEST, "Excluded ingredient that is not present in the dish.");
    }

    public static ResponseStatusException dishAlreadyExists(String name) {
        return new ResponseStatusException(HttpStatus.BAD_REQUEST, "Dish with name " + name + " already exists.");
    }

    public static ResponseStatusException drinkExistsException(String name) {
        return new ResponseStatusException(HttpStatus.BAD_REQUEST, "Drink with name " + name + " already exists");
    }
}

package com.k4rnaj1k.bestcafe.exception;

import com.k4rnaj1k.bestcafe.model.Order;
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
}

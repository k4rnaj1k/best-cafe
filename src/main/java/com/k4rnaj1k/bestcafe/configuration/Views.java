package com.k4rnaj1k.bestcafe.configuration;

public final class Views {
    public interface PostIngredient {
    }

    public interface PostDish {
    }

    public interface PostDrink {
    }

    public interface PutOrder {
    }

    public interface PostOrder {
    }

    public interface Get extends PostIngredient, PostDish, PostOrder, PutOrder {
    }
}

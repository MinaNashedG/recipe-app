package com.favourite.recipe.utils.builder;

import com.favourite.recipe.api.request.IngredientRequest;
import com.favourite.recipe.model.Ingredient;

import java.time.LocalDateTime;
import java.util.List;

public class IngredientTestDataBuilder {
    public static IngredientRequest createIngredientRequest() {
        return new CreateIngredientRequestBuilder()
                .withName("tomato")
                .build();
    }

    public static Ingredient createIngredient() {
        return new IngredientModelBuilder()
                .withName("tomato")
                .build();
    }

    public static Ingredient createIngredientWithNameParam(String name) {
        return new IngredientModelBuilder()
                .withName(name)
                .build();
    }


    public static List<Ingredient> createIngredientList() {
        return createIngredientList(false);
    }

    public static List<Ingredient> createIngredientList(boolean withId) {
        Ingredient i1 = new IngredientModelBuilder()
                .withId(withId ? 10 : null)
                .withName("tomato")
                .withCreatedAt(LocalDateTime.now())
                .withUpdatedAt(LocalDateTime.now())
                .build();

        Ingredient i2 = new IngredientModelBuilder()
                .withId(withId ? 11 : null)
                .withName("cabbage")
                .withCreatedAt(LocalDateTime.now())
                .withUpdatedAt(LocalDateTime.now())
                .build();

        return List.of(i1, i2);
    }
}

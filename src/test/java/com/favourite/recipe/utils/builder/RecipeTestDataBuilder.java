package com.favourite.recipe.utils.builder;

import com.favourite.recipe.model.Recipe;
import com.favourite.recipe.model.RecipeType;

public class RecipeTestDataBuilder {
    public static Recipe createRecipe() {
        return createRecipe(null);
    }

    public static Recipe createRecipe(Integer id) {
        Recipe recipe = new Recipe();
        recipe.setId(id);
        recipe.setName("pasta");
        recipe.setNumberOfServings(5);
        recipe.setType(RecipeType.OTHER);
        recipe.setInstructions("someInstruction");

        return recipe;
    }
}

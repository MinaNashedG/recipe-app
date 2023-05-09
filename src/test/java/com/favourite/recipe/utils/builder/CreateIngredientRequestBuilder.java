package com.favourite.recipe.utils.builder;

import com.favourite.recipe.api.request.IngredientRequest;

public class CreateIngredientRequestBuilder {
    private String name;

    public IngredientRequest build() {
        return new IngredientRequest(name);
    }

    public CreateIngredientRequestBuilder withName(String firstName) {
        this.name = firstName;
        return this;
    }

}

package com.favourite.recipe.api.request;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

import static com.favourite.recipe.config.RuleConfig.MAX_LENGTH_NAME;
import static com.favourite.recipe.config.RuleConfig.PATTERN_NAME;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class IngredientRequest {

	@NotBlank(message = "{ingredient.notBlank}")
	@Size(max = MAX_LENGTH_NAME, message = "{ingredient.size}")
	@Pattern(regexp = PATTERN_NAME, message = "{ingredient.pattern}")
	@Schema(description = "The name of the ingredient", example = "Tomato")
	private String name;
}

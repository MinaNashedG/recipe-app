package com.favourite.recipe.api.request;

import com.favourite.recipe.model.RecipeType;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.validation.annotation.Validated;

import java.util.List;

import static com.favourite.recipe.config.RuleConfig.MAX_LENGTH_DEFAULT;
import static com.favourite.recipe.config.RuleConfig.MAX_LENGTH_NAME;
import static com.favourite.recipe.config.RuleConfig.PATTERN_INSTRUCTION;
import static com.favourite.recipe.config.RuleConfig.PATTERN_NAME;

@Validated
@AllArgsConstructor
@NoArgsConstructor
@Data
public class RecipeRequest {
	@NotBlank(message = "{recipeName.notBlank}")
	@Size(max = MAX_LENGTH_NAME, message = "{recipeName.size}")
	@Pattern(regexp = PATTERN_NAME, message = "{recipeName.pattern}")
	@Schema(description = "The name of the recipe", example = "Pasta")
	private String name;

	@Schema(description = "The type of the recipe", example = "VEGETARIAN")
	@Enumerated(EnumType.STRING)
	@NotNull(message = "{recipeType.invalid}")
	private RecipeType type;

	@NotNull(message = "{numberOfServings.notNull}")
	@Positive(message = "{numberOfServings.positive}")
	@Schema(description = "The number of servings per recipe", example = "4")
	private Integer numberOfServings;

	@Schema(description = "The ids of the ingredients need to make the recipe", example = "[1,2]")
	@NotNull
	@NotEmpty
	private List<Integer> ingredientIds;

	@NotBlank(message = "{instructions.notBlank}")
	@Size(max = MAX_LENGTH_DEFAULT, message = "{instructions.size}")
	@Pattern(regexp = PATTERN_INSTRUCTION, message = "{instructions.pattern}")
	@Schema(description = "The instructions to create the recipe", example = "Cut the potato, add tomato and then " +
			"bake it in the oven")
	private String instructions;

}

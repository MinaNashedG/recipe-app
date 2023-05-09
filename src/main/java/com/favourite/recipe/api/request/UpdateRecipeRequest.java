package com.favourite.recipe.api.request;

import com.favourite.recipe.model.RecipeType;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.validation.annotation.Validated;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import java.util.List;

import static com.favourite.recipe.config.RuleConfig.MAX_LENGTH_DEFAULT;
import static com.favourite.recipe.config.RuleConfig.MAX_LENGTH_NAME;
import static com.favourite.recipe.config.RuleConfig.PATTERN_INSTRUCTION;
import static com.favourite.recipe.config.RuleConfig.PATTERN_NAME;

@Data
@Validated
@AllArgsConstructor
@NoArgsConstructor
public class UpdateRecipeRequest {

	@NotNull(message = "{id.notNull}")
	@Positive(message = "{id.positive}")
	@Schema(description = "Id of the attribute", example = "1")
	private Integer id;

	@Size(max = MAX_LENGTH_NAME, message = "{recipeName.size}")
	@Pattern(regexp = PATTERN_NAME, message = "{recipeName.pattern}")
	@Schema(description = "The name of the recipe", example = "Pasta")
	private String name;

	@Schema(description = "The type of the recipe", example = "VEGETARIAN")
	@Enumerated(EnumType.STRING)
	private RecipeType type;

	@Positive(message = "{numberOfServings.positive}")
	@Schema(description = "The number of servings per recipe", example = "4")
	private Integer numberOfServings;

	@Schema(description = "The ids of the ingredients need to make the recipe", example = "[1,2]")
	private List<Integer> ingredientIds;

	@NotBlank(message = "{instructions.notBlank}")
	@Size(max = MAX_LENGTH_DEFAULT, message = "{instructions.size}")
	@Pattern(regexp = PATTERN_INSTRUCTION, message = "{instructions.pattern}")
	@Schema(description = "The instructions to create the recipe", example = "Chop the tomato, stir and fry, " +
			"boil" + " and serve")
	private String instructions;
}

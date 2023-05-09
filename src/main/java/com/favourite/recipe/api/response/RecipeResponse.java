package com.favourite.recipe.api.response;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
import com.favourite.recipe.model.Recipe;
import com.favourite.recipe.model.RecipeType;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Data
@NoArgsConstructor
public class RecipeResponse extends BaseResponse {

	@Schema(description = "The name of the returned recipe", example = "Pasta")
	private String name;

	@Schema(description = "The type of the returned recipe", example = "VEGETARIAN")
	@Enumerated(EnumType.STRING)
	private RecipeType type;

	@Schema(description = "Number of servings", example = "1")
	private int numberOfServings;

	@JsonIgnoreProperties("ingredients")
	private Set<IngredientResponse> ingredients;

	@Schema(description = "The instructions of the returned recipe", example = "Chop the onion, add to pasta and" +
			"boil it")
	private String instructions;

	@CreationTimestamp
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	@JsonSerialize(using = LocalDateTimeSerializer.class)
	private LocalDateTime createdAt;

	@UpdateTimestamp
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	@JsonSerialize(using = LocalDateTimeSerializer.class)
	private LocalDateTime updatedAt;

	public RecipeResponse(Recipe recipe) {
		super(recipe.getId());
		this.name = recipe.getName();
		this.type = recipe.getType();
		this.instructions = recipe.getInstructions();
		this.createdAt = recipe.getCreatedAt();
		this.updatedAt = recipe.getUpdatedAt();
		this.numberOfServings = recipe.getNumberOfServings();
		this.ingredients = Optional.ofNullable(recipe.getIngredient())
				.orElse(new ArrayList<>())
				.stream()
				.map(IngredientResponse::new)
				.collect(Collectors.toSet());
	}
}

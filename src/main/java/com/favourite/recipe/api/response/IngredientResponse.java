package com.favourite.recipe.api.response;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
import com.favourite.recipe.model.Ingredient;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
public class IngredientResponse extends BaseResponse {

	@Schema(description = "The name of the returned recipe", example = "Tomato")
	private String name;

	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
	@JsonSerialize(using = LocalDateTimeSerializer.class)
	private LocalDateTime createdAt;

	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
	@JsonSerialize(using = LocalDateTimeSerializer.class)
	private LocalDateTime updatedAt;

	public IngredientResponse(Ingredient ingredient) {
		super(ingredient.getId());
		this.name = ingredient.getName();
		this.createdAt = ingredient.getCreatedAt();
		this.updatedAt = ingredient.getUpdatedAt();
	}
}

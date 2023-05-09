package com.favourite.recipe.controller;

import com.favourite.recipe.api.request.IngredientRequest;
import com.favourite.recipe.api.response.BaseResponse;
import com.favourite.recipe.api.response.IngredientResponse;
import com.favourite.recipe.service.IngredientService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import java.util.List;

@Tag(name = "Ingredient Controller API")
@Slf4j
@Validated
@AllArgsConstructor
@RestController
@RequestMapping(value = "api/v1/ingredients")
public class IngredientController {

	private final IngredientService ingredientService;

	@Operation(summary = "List one ingredient by ID")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "Success"),
			@ApiResponse(responseCode = "404", description = "Ingredient with given Id not found")
	})
	@RequestMapping(value = "/{id}", method = RequestMethod.GET)
	public IngredientResponse getIngredient(
			@Parameter(description = "Ingredient ID", required = true) @PathVariable(name = "id") Integer id) {
		log.info("Getting the ingredient by ID: {}", id);
		return new IngredientResponse(ingredientService.findById(id));
	}

	@Operation(description = "Create an ingredient")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "201", description = "Ingredient created"),
			@ApiResponse(responseCode = "400", description = "Bad Request")
	})
	@RequestMapping(method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
	@ResponseStatus(HttpStatus.CREATED)
	public BaseResponse createIngredient(
			@Parameter(description = "Ingredient Properties", required = true) @Valid @RequestBody IngredientRequest request) {
		log.info("Creating an ingredient with given request: {}", request);
		return new BaseResponse(ingredientService.createIngredient(request));
	}

	@Operation(description = "Delete an ingredient")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "204", description = "Deleted successfully"),
			@ApiResponse(responseCode = "400", description = "Invalid request provided"),
			@ApiResponse(responseCode = "404", description = "Ingredient not found by the given ID")
	})
	@DeleteMapping(value = "/{id}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void deleteIngredient(
			@Parameter(description = "Ingredient ID", required = true) @NotNull(message = "{id.notNull}")
			@Positive(message = "{id.positive}")
			@PathVariable(name = "id") Integer id) {
		log.info("Deleting the ingredient by ID: {}", id);
		ingredientService.delete(id);
	}

	@Operation(description = "List all ingredients")
	@ApiResponses(value = {@ApiResponse(responseCode = "200", description = "Success")})
	@GetMapping
	public List<IngredientResponse> getIngredientList(@RequestParam(name = "page") int page,
			@RequestParam(name = "size") int size) {
		log.info("Received Request for Listing ingredients by page {} and size {}", page, size);
		return ingredientService.getIngredients(page, size);
	}
}

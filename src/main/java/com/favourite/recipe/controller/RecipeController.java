package com.favourite.recipe.controller;

import com.favourite.recipe.api.request.RecipeRequest;
import com.favourite.recipe.api.request.RecipeSearchRequest;
import com.favourite.recipe.api.request.UpdateRecipeRequest;
import com.favourite.recipe.api.response.BaseResponse;
import com.favourite.recipe.api.response.RecipeResponse;
import com.favourite.recipe.service.RecipeService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import java.util.List;

@Tag(name = "Recipe Controller API")
@Slf4j
@Validated
@AllArgsConstructor
@RestController
@RequestMapping(value = "api/v1/recipes")
public class RecipeController {

	private final RecipeService recipeService;

	@Operation(summary = "List all recipes")
	@ApiResponses(value = {@ApiResponse(responseCode = "200")})
	@GetMapping
	public List<RecipeResponse> getRecipeList(@RequestParam(name = "page") int page,
			@RequestParam(name = "size") int size) {
		log.info("List all recipes by given page {} and size {}", page, size);

		return recipeService.getRecipeList(page, size);
	}

	@Operation(summary = "List recipe by Id")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "Successful request", content = @Content(schema =
			@Schema(implementation = RecipeResponse.class))),
			@ApiResponse(responseCode = "404", description = "Recipe not found by ID")
	})
	@GetMapping(value = "/{id}")
	public RecipeResponse getRecipe(
			@Parameter(name = "Recipe Id", required = true) @PathVariable(name = "id") Integer id) {
		log.info("List recipe by Id: {}", id);
		return new RecipeResponse(recipeService.getRecipeById(id));
	}

	@Operation(summary = "Create a new recipe")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "201", description = "Recipe created successfully"),
			@ApiResponse(responseCode = "400", description = "Invalid inputs")
	})
	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public BaseResponse createRecipe(
			@Parameter(description = "Properties of the recipe", required = true) @Valid @RequestBody RecipeRequest request) {
		log.info("Creating the recipe with properties");
		Integer id = recipeService.createRecipe(request);
		return new BaseResponse(id);
	}

	@Operation(summary = "Update the recipe")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "Ingredient Updated"),
			@ApiResponse(responseCode = "400", description = "Bad input")
	})
	@PatchMapping
	public void updateRecipe(
			@Parameter(description = "Properties of the recipe", required = true) @Valid @RequestBody UpdateRecipeRequest updateRecipeRequest) {
		log.info("Updating the recipe by given properties");
		recipeService.updateRecipe(updateRecipeRequest);
	}

	@Operation(summary = "Delete the recipe")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "204", description = "Recipe Deleted"),
			@ApiResponse(responseCode = "400", description = "Invalid input"),
			@ApiResponse(responseCode = "404", description = "Recipe not found by the given Id")
	})
	@DeleteMapping(value = "/{id}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void deleteRecipe(
			@Parameter(description = "Recipe ID", required = true) @NotNull(message = "{id.notNull}") @PathVariable(name =
					"id") Integer id) {
		log.info("Delete recipe by given id {}", id);
		recipeService.deleteRecipe(id);
	}

	@Operation(summary = "Search recipes by given parameters")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "Success"),
			@ApiResponse(responseCode = "404", description = "Given Search Criteria is not exist")

	})
	@RequestMapping(method = RequestMethod.POST, path = "/search")
	public List<RecipeResponse> searchByRequestCriteria(@RequestParam(name = "page", defaultValue = "0") int page,
			@RequestParam(name = "size", defaultValue = "10") int size,
			@RequestParam(name = "sortBy", defaultValue = "name") String sortBy,
			@Parameter(description = "Request Criteria of the the search")
			@RequestBody @Valid @NotNull RecipeSearchRequest recipeSearchRequest) {
		log.info("Find recipes by given search criteria: {}", recipeSearchRequest);
		return recipeService.findBySearchCriteria(recipeSearchRequest, page, size, sortBy);
	}
}

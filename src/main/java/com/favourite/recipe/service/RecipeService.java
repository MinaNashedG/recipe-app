package com.favourite.recipe.service;

import com.favourite.recipe.api.request.RecipeRequest;
import com.favourite.recipe.api.request.RecipeSearchRequest;
import com.favourite.recipe.api.request.UpdateRecipeRequest;
import com.favourite.recipe.api.response.RecipeResponse;
import com.favourite.recipe.config.MessageProvider;
import com.favourite.recipe.exception.NotFoundException;
import com.favourite.recipe.model.DataOption;
import com.favourite.recipe.model.Ingredient;
import com.favourite.recipe.model.Recipe;
import com.favourite.recipe.repository.RecipeRepository;
import com.favourite.recipe.search.RecipeSpecificationBuilder;
import com.favourite.recipe.search.SearchCriteria;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@AllArgsConstructor
@Service
@Transactional
public class RecipeService {

	public static final String RECIPE_NOT_FOUND = "recipe.notFound";
	private final RecipeRepository recipeRepository;
	private final IngredientService ingredientService;
	private final MessageProvider messageProvider;

	public Integer createRecipe(RecipeRequest recipeRequest) {
		Set<Ingredient> ingredients = ingredientService.getIngredientsByIds(recipeRequest.getIngredientIds());

		Recipe createdRecipe = recipeRepository.save(Recipe.builder()
				.name(recipeRequest.getName())
				.instructions(recipeRequest.getInstructions())
				.type(recipeRequest.getType())
				.numberOfServings(recipeRequest.getNumberOfServings())
				.ingredient(new ArrayList<>(ingredients))
				.build());

		return createdRecipe.getId();
	}

	public List<RecipeResponse> getRecipeList(int page, int size) {
		Pageable pageRequest = PageRequest.of(page, size);

		return recipeRepository.findAll(pageRequest).getContent()
				.stream()
				.map(RecipeResponse::new)
				.collect(Collectors.toList());
	}

	public Recipe getRecipeById(int id) {
		return recipeRepository.findById(id)
				.orElseThrow(() -> new NotFoundException(messageProvider.getMessage(RECIPE_NOT_FOUND)));
	}

	public void updateRecipe(UpdateRecipeRequest updateRecipeRequest) {
		Recipe recipe = recipeRepository.findById(updateRecipeRequest.getId())
				.orElseThrow(() -> new NotFoundException(messageProvider.getMessage(RECIPE_NOT_FOUND)));

		recipe.setName(updateRecipeRequest.getName());
		recipe.setType(updateRecipeRequest.getType());
		recipe.setNumberOfServings(updateRecipeRequest.getNumberOfServings());
		recipe.setInstructions(updateRecipeRequest.getInstructions());
		if (!CollectionUtils.isEmpty(updateRecipeRequest.getIngredientIds())) {
			Optional.ofNullable(ingredientService.getIngredientsByIds(updateRecipeRequest.getIngredientIds()))
					.map(ArrayList::new)
					.ifPresent(recipe::setIngredient);
		}

		recipeRepository.save(recipe);
	}

	public void deleteRecipe(int id) {
		if (!recipeRepository.existsById(id)) {
			throw new NotFoundException(messageProvider.getMessage(RECIPE_NOT_FOUND));
		}
		recipeRepository.deleteById(id);
	}

	public List<RecipeResponse> findBySearchCriteria(RecipeSearchRequest recipeSearchRequest, int page, int size,
			String sortBy) {
		List<SearchCriteria> searchCriterionRequests = new ArrayList<>();
		RecipeSpecificationBuilder builder = new RecipeSpecificationBuilder(searchCriterionRequests);
		Pageable pageRequest = PageRequest.of(page, size, Sort.by(sortBy));

		Specification<Recipe> recipeSpecification = createRecipeSpecification(recipeSearchRequest, builder);
		Page<Recipe> filteredRecipes = recipeRepository.findAll(recipeSpecification, pageRequest);

		return filteredRecipes.toList().stream()
				.map(RecipeResponse::new)
				.collect(Collectors.toList());
	}

	private Specification<Recipe> createRecipeSpecification(RecipeSearchRequest recipeSearchRequest,
			RecipeSpecificationBuilder builder) {

		List<SearchCriteria> searchCriteriaList = Optional.ofNullable(recipeSearchRequest.getCriteria())
				.orElse(Collections.emptyList())
				.stream()
				.map(SearchCriteria::new)
				.collect(Collectors.toList());

		searchCriteriaList.forEach(
				createRecipe -> createRecipe.setDataOption(recipeSearchRequest.getDataOption() != null
						? DataOption.valueOf(recipeSearchRequest.getDataOption().name()) : DataOption.ALL));

		return builder.recipeSpecifications(searchCriteriaList).build()
				.orElseThrow(() -> new NotFoundException(messageProvider.getMessage("criteria.notFound")));
	}
}

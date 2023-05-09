package com.favourite.recipe.service;

import com.favourite.recipe.api.request.RecipeRequest;
import com.favourite.recipe.api.request.RecipeSearchRequest;
import com.favourite.recipe.api.request.UpdateRecipeRequest;
import com.favourite.recipe.config.MessageProvider;
import com.favourite.recipe.exception.NotFoundException;
import com.favourite.recipe.model.Recipe;
import com.favourite.recipe.repository.RecipeRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static com.favourite.recipe.model.RecipeType.OTHER;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class RecipeServiceTest {
	@Mock
	private RecipeRepository recipeRepository;

	@Mock
	private IngredientService ingredientService;

	@Mock
	private MessageProvider messageProvider;

	@InjectMocks
	private RecipeService recipeService;

	@Test
	public void test_createRecipe_successfully() {
		RecipeRequest request = new RecipeRequest("pasta", OTHER, 4, null, "instructions");


		Recipe response = new Recipe();
		response.setName("Name");
		response.setInstructions("instructions");
		response.setNumberOfServings(4);

		when(recipeRepository.save(any(Recipe.class))).thenReturn(response);

		Integer id = recipeService.createRecipe(request);

		assertThat(id).isSameAs(response.getId());
	}

	@Test
	public void test_updateRecipe_successfully() {
		Recipe response = new Recipe();
		response.setName("Name");
		response.setType(OTHER);
		response.setNumberOfServings(4);
		response.setId(5);

		UpdateRecipeRequest request = new UpdateRecipeRequest(1, "pasta", OTHER, 4, null, "instructions");

		when(recipeRepository.save(any(Recipe.class))).thenReturn(response);
		when(recipeRepository.findById(anyInt())).thenReturn(Optional.of(response));

		recipeService.updateRecipe(request);
	}

	@Test
	public void test_updateRecipe_notFound() {
		UpdateRecipeRequest request = new UpdateRecipeRequest(1, "pasta", OTHER, 4, null, "instructions");

		when(recipeRepository.findById(anyInt())).thenReturn(Optional.empty());

		Assertions.assertThrows(NotFoundException.class, () -> recipeService.updateRecipe(request));
	}

	@Test
	public void test_deleteRecipe_successfully() {
		when(recipeRepository.existsById(anyInt())).thenReturn(true);
		doNothing().when(recipeRepository).deleteById(anyInt());

		recipeService.deleteRecipe(1);
	}

	@Test
	public void test_deleteRecipe_notFound() {
		when(recipeRepository.existsById(anyInt())).thenReturn(false);

		Assertions.assertThrows(NotFoundException.class, () -> recipeService.deleteRecipe(1));
	}

	@Test
	public void test_findBySearchCriteria_notFound() {
		RecipeSearchRequest recipeSearchRequest = mock(RecipeSearchRequest.class);

		Assertions.assertThrows(NotFoundException.class,
				() -> recipeService.findBySearchCriteria(recipeSearchRequest, 0, 10, "name"));

	}

}
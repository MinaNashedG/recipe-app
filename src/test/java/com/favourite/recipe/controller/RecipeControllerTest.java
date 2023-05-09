package com.favourite.recipe.controller;

import com.favourite.recipe.api.request.RecipeRequest;
import com.favourite.recipe.api.request.UpdateRecipeRequest;
import com.favourite.recipe.api.response.BaseResponse;
import com.favourite.recipe.api.response.RecipeResponse;
import com.favourite.recipe.model.Recipe;
import com.favourite.recipe.model.RecipeType;
import com.favourite.recipe.service.RecipeService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;


@ExtendWith(MockitoExtension.class)
public class RecipeControllerTest {
	@Mock
	private RecipeService recipeService;

	@InjectMocks
	private RecipeController recipeController;

	@Test
	public void test_createRecipe_successfully() {
		RecipeRequest request = new RecipeRequest("pasta", RecipeType.OTHER, 4, null, "instructions");

		when(recipeService.createRecipe(any(RecipeRequest.class))).thenReturn(1);

		BaseResponse response = recipeController.createRecipe(request);

		assertThat(response).isNotNull();
		assertThat(response.getId()).isSameAs(1);
	}

	@Test
	public void test_listRecipe_successfully() {
		Recipe Recipe = new Recipe();
		Recipe.setId(5);
		Recipe.setName("name");

		when(recipeService.getRecipeById(anyInt())).thenReturn(Recipe);

		RecipeResponse response = recipeController.getRecipe(5);

		assertThat(response.getId()).isSameAs(Recipe.getId());
		assertThat(response.getName()).isSameAs(Recipe.getName());
	}

	@Test
	public void test_listRecipes_successfully() {
		Recipe recipe = new Recipe();
		recipe.setId(5);
		recipe.setName("name1");

		Recipe recipe1 = new Recipe();
		recipe1.setId(6);
		recipe1.setName("name2");

		List<Recipe> storedRecipeList = new ArrayList<>();
		storedRecipeList.add(recipe);
		storedRecipeList.add(recipe1);
		List<RecipeResponse> recipeResponses = storedRecipeList.stream().map(RecipeResponse::new).toList();

		when(recipeService.getRecipeList(anyInt(), anyInt())).thenReturn(recipeResponses);

		List<RecipeResponse> recipeList = recipeController.getRecipeList(anyInt(), anyInt());

		assertThat(storedRecipeList.size()).isSameAs(recipeList.size());
		assertThat(storedRecipeList.get(0).getId()).isSameAs(recipeList.get(0).getId());
		assertThat(storedRecipeList.get(1).getId()).isSameAs(recipeList.get(1).getId());
	}

	@Test
	public void test_updateRecipe_successfully() {
		Recipe recipe = new Recipe();
		recipe.setName("name1");
		recipe.setType(RecipeType.OTHER);
		recipe.setInstructions("ins");

		doNothing().when(recipeService).updateRecipe(any());
		recipe.setName("name2");

		UpdateRecipeRequest request = new UpdateRecipeRequest(1, "pasta", RecipeType.OTHER, 4, null, "instructions");

		recipeController.updateRecipe(request);
	}

	@Test
	public void test_deleteRecipe_successfully() {
		doNothing().when(recipeService).deleteRecipe(anyInt());
		recipeController.deleteRecipe(5);
	}
}
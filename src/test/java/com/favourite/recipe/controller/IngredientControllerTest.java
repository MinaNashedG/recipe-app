package com.favourite.recipe.controller;


import com.favourite.recipe.api.request.IngredientRequest;
import com.favourite.recipe.api.response.BaseResponse;
import com.favourite.recipe.api.response.IngredientResponse;
import com.favourite.recipe.model.Ingredient;
import com.favourite.recipe.service.IngredientService;
import com.favourite.recipe.utils.builder.IngredientTestDataBuilder;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class IngredientControllerTest {
	@Mock
	private IngredientService ingredientService;

	@InjectMocks
	private IngredientController ingredientController;

	@Test
	public void test_createIngredient_successfully() {
		when(ingredientService.createIngredient(any(IngredientRequest.class))).thenReturn(1);

		IngredientRequest request = IngredientTestDataBuilder.createIngredientRequest();
		BaseResponse response = ingredientController.createIngredient(request);

		assertThat(response).isNotNull();
		assertThat(response.getId()).isSameAs(1);
	}

	@Test
	public void test_getIngredient_successfully() {
		Ingredient ingredient = IngredientTestDataBuilder.createIngredient();
		ingredient.setId(5);

		when(ingredientService.findById(anyInt())).thenReturn(ingredient);

		IngredientResponse response = ingredientController.getIngredient(5);
		assertThat(response.getId()).isSameAs(5);
	}

	@Test
	public void test_listIngredients_successfully() {
		List<Ingredient> storedIngredientList = IngredientTestDataBuilder.createIngredientList(true);
		final List<IngredientResponse> ingredientResponses = storedIngredientList.stream().map(IngredientResponse::new)
				.toList();
		when(ingredientService.getIngredients(anyInt(), anyInt())).thenReturn(ingredientResponses);

		List<IngredientResponse> ingredientList = ingredientController.getIngredientList(anyInt(), anyInt());

		assertThat(storedIngredientList.size()).isSameAs(ingredientList.size());
		assertThat(storedIngredientList.get(0).getId()).isSameAs(ingredientList.get(0).getId());
		assertThat(storedIngredientList.get(1).getId()).isSameAs(ingredientList.get(1).getId());
	}

	@Test
	public void test_deleteIngredient_successfully() {
		doNothing().when(ingredientService).delete(anyInt());

		ingredientController.deleteIngredient(5);
	}

}
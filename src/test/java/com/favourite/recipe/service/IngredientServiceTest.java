package com.favourite.recipe.service;

import com.favourite.recipe.api.request.IngredientRequest;
import com.favourite.recipe.config.MessageProvider;
import com.favourite.recipe.exception.NotFoundException;
import com.favourite.recipe.model.Ingredient;
import com.favourite.recipe.repository.IngredientRepository;
import com.favourite.recipe.utils.builder.IngredientTestDataBuilder;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class IngredientServiceTest {
	@Mock
	private IngredientRepository ingredientRepository;

	@Mock
	private MessageProvider messageProvider;

	@InjectMocks
	private IngredientService ingredientService;

	@Test
	public void test_createIngredient_successfully() {
		IngredientRequest request = IngredientTestDataBuilder.createIngredientRequest();
		Ingredient response = IngredientTestDataBuilder.createIngredient();
		response.setId(5);

		when(ingredientRepository.save(any(Ingredient.class))).thenReturn(response);
		Integer id = ingredientService.createIngredient(request);
		assertThat(id).isSameAs(response.getId());
	}


	@Test
	public void test_deleteIngredient_successfully() {
		when(ingredientRepository.existsById(anyInt())).thenReturn(true);
		doNothing().when(ingredientRepository).deleteById(anyInt());

		ingredientService.delete(5);
	}

	@Test
	public void test_deleteIngredient_notFound() {
		when(ingredientRepository.existsById(anyInt())).thenReturn(false);
		Assertions.assertThrows(NotFoundException.class, () -> ingredientService.delete(1));
	}
}
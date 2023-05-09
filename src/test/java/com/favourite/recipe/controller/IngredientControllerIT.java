package com.favourite.recipe.controller;

import com.favourite.recipe.api.request.IngredientRequest;
import com.favourite.recipe.api.response.IngredientResponse;
import com.favourite.recipe.model.Ingredient;
import com.favourite.recipe.repository.IngredientRepository;
import com.favourite.recipe.service.IngredientService;
import com.favourite.recipe.utils.builder.IngredientTestDataBuilder;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class IngredientControllerIT extends AbstractControllerIT {
	@Autowired
	private IngredientRepository ingredientRepository;

	@Autowired
	private IngredientService ingredientService;

	protected MockMvc mockMvc;

	@BeforeEach
	void setUp() {
		mockMvc = MockMvcBuilders.standaloneSetup(new IngredientController(ingredientService))
				.build();
		ingredientRepository.deleteAll();
	}

	@Test
	public void test_createIngredient_successfully() throws Exception {
		IngredientRequest request = new IngredientRequest("tomato");

		MvcResult result = performPost("/api/v1/ingredients", request)
				.andExpect(status().isCreated())
				.andReturn();

		Integer id = readByJsonPath(result, "$.id");

		Optional<Ingredient> ingredient = ingredientRepository.findById(id);

		assertTrue(ingredient.isPresent());
		assertEquals(ingredient.get().getName(), request.getName());
	}

	@Test
	public void test_createIngredient_successfully_upper_case() throws Exception {
		IngredientRequest request = new IngredientRequest("TOMATO");

		MvcResult result = performPost("/api/v1/ingredients", request)
				.andExpect(status().isCreated())
				.andReturn();

		Integer id = readByJsonPath(result, "$.id");

		Optional<Ingredient> ingredient = ingredientRepository.findById(id);

		assertTrue(ingredient.isPresent());
		assertEquals(ingredient.get().getName(), request.getName().toLowerCase());
	}

	@Test
	public void test_listIngredient_successfully() throws Exception {
		Ingredient ingredient = IngredientTestDataBuilder.createIngredient();
		Ingredient savedIngredient = ingredientRepository.save(ingredient);

		performGet("/api/v1/ingredients/" + savedIngredient.getId())
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.id").value(savedIngredient.getId()))
				.andExpect(jsonPath("$.name").value(ingredient.getName()))
				.andExpect(jsonPath("$.createdAt").value(
						ingredient.getCreatedAt().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"))))
				.andExpect(jsonPath("$.updatedAt").value(
						ingredient.getUpdatedAt().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"))));
	}

	@Test
	public void test_listIngredient_notFound() throws Exception {
		performGet("/api/v1/ingredients/1")
				.andExpect(status().isNotFound())
				.andExpect(jsonPath("$.message").value("Ingredient not found."));
	}

	@Test
	public void test_listIngredients_successfully() throws Exception {


		List<Ingredient> ingredientList = IngredientTestDataBuilder.createIngredientList();
		ingredientRepository.saveAll(ingredientList);

		MvcResult result = performGet("/api/v1/ingredients?page=0&size=10")
				.andExpect(status().isOk())
				.andReturn();

		List<IngredientResponse> responses = getListFromMvcResult(result, IngredientResponse.class);

		assertEquals(ingredientList.size(), responses.size());
	}

	@Test
	public void test_deleteIngredients_successfully() throws Exception {
		Ingredient ingredient = IngredientTestDataBuilder.createIngredient();
		Ingredient savedIngredient = ingredientRepository.save(ingredient);

		performDelete("/api/v1/ingredients/" + savedIngredient.getId())
				.andExpect(status().isNoContent());

		Optional<Ingredient> deletedIngredient = ingredientRepository.findById(savedIngredient.getId());
		assertTrue(deletedIngredient.isEmpty());
	}

	@Test
	public void test_findIngredientById_successfully() throws Exception {
		Ingredient ingredient = IngredientTestDataBuilder.createIngredient();
		Ingredient savedIngredient = ingredientRepository.save(ingredient);

		MvcResult result = performGet("/api/v1/ingredients/" + savedIngredient.getId())
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.id").value(savedIngredient.getId()))
				.andExpect(jsonPath("$.name").value(savedIngredient.getName()))
				.andReturn();

		IngredientResponse ingredientResponse = getFromMvcResult(result, IngredientResponse.class);
		assertEquals(savedIngredient.getName(), ingredientResponse.getName());
	}

	@Test
	public void test_findIngredientById_fails() throws Exception {

		performGet("/api/v1/ingredients/" + 1)
				.andExpect(status().isNotFound())
				.andExpect(jsonPath("$.message").exists());
	}

	@Test
	public void test_deleteIngredient_notFound() throws Exception {

		performDelete("/api/v1/ingredients?id=11")
				.andExpect(status().isNotFound())
				.andExpect(jsonPath("$.message").exists());
	}
}

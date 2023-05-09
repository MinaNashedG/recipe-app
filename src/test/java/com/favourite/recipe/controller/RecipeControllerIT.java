package com.favourite.recipe.controller;

import com.favourite.recipe.api.request.DataOptionReqInput;
import com.favourite.recipe.api.request.FilterKeyReqInput;
import com.favourite.recipe.api.request.RecipeRequest;
import com.favourite.recipe.api.request.RecipeSearchRequest;
import com.favourite.recipe.api.request.SearchCriteriaRequest;
import com.favourite.recipe.api.request.SearchOperationReqInput;
import com.favourite.recipe.api.response.RecipeResponse;
import com.favourite.recipe.model.Ingredient;
import com.favourite.recipe.model.Recipe;
import com.favourite.recipe.model.RecipeType;
import com.favourite.recipe.repository.IngredientRepository;
import com.favourite.recipe.repository.RecipeRepository;
import com.favourite.recipe.utils.builder.IngredientTestDataBuilder;
import com.favourite.recipe.utils.builder.RecipeTestDataBuilder;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class RecipeControllerIT extends AbstractControllerIT {
	@Autowired
	private RecipeRepository recipeRepository;

	@Autowired
	private IngredientRepository ingredientRepository;

	@BeforeEach
	public void before() {
		recipeRepository.deleteAll();
	}

	@Test
	public void test_createRecipe_400_invalid_input_when_ingredients_null() throws Exception {
		RecipeRequest request = new RecipeRequest("pasta",
				RecipeType.OTHER, 5, null, "someInstruction");

		performPost("/api/v1/recipes", request)
				.andExpect(status().is4xxClientError())
				.andReturn();
	}

	@Test
	public void test_createRecipe_successfully() throws Exception {
		Ingredient ingredient = IngredientTestDataBuilder.createIngredientWithNameParam("Tomato");
		Ingredient savedIngredient = ingredientRepository.save(ingredient);

		RecipeRequest request = new RecipeRequest("pasta",
				RecipeType.OTHER, 5, List.of(savedIngredient.getId()), "someInstruction");

		MvcResult result = performPost("/api/v1/recipes", request)
				.andExpect(status().isCreated())
				.andReturn();

		Integer id = readByJsonPath(result, "$.id");
		Optional<Recipe> optionalRecipe = recipeRepository.findById(id);
		assertTrue(optionalRecipe.isPresent());
		assertEquals(optionalRecipe.get().getName(), request.getName());
	}

	@Test
	public void test_getRecipe_successfully() throws Exception {
		Recipe Recipe = RecipeTestDataBuilder.createRecipe();
		Recipe savedRecipe = recipeRepository.save(Recipe);

		performGet("/api/v1/recipes/" + savedRecipe.getId())
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.id").value(savedRecipe.getId()))
				.andExpect(jsonPath("$.name").value(savedRecipe.getName()))
				.andExpect(jsonPath("$.instructions").value(savedRecipe.getInstructions()))
				.andExpect(jsonPath("$.numberOfServings").value(savedRecipe.getNumberOfServings()));
	}

	@Test
	public void test_getRecipe_notFound() throws Exception {

		performGet("/api/v1/recipes/1")
				.andExpect(status().isNotFound())
				.andExpect(jsonPath("$.message").exists());
	}

	@Test
	public void test_listRecipe_successfully() throws Exception {
		Recipe recipe1 = new Recipe();
		recipe1.setId(5);
		recipe1.setName("name1");
		recipe1.setInstructions("Ins1");
		recipe1.setType(RecipeType.OTHER);

		Recipe recipe2 = new Recipe();
		recipe2.setId(6);
		recipe2.setName("name2");
		recipe2.setInstructions("Ins2");
		recipe2.setType(RecipeType.OTHER);

		List<Recipe> storedRecipeList = new ArrayList<>();
		storedRecipeList.add(recipe1);
		storedRecipeList.add(recipe2);

		recipeRepository.saveAll(storedRecipeList);

		MvcResult result = performGet("/api/v1/recipes?page=0&size=10")
				.andExpect(status().isOk())
				.andReturn();

		List<RecipeResponse> RecipeList = getListFromMvcResult(result, RecipeResponse.class);

		assertEquals(storedRecipeList.size(), RecipeList.size());
		assertEquals(storedRecipeList.get(0).getName(), RecipeList.get(0).getName());
		assertEquals(storedRecipeList.get(1).getName(), RecipeList.get(1).getName());
	}

	@Test
	public void test_updateRecipe_successfully() throws Exception {
		Recipe testRecipe = new Recipe();
		testRecipe.setName("lasagna");
		testRecipe.setType(RecipeType.OTHER);
		testRecipe.setInstructions("chop the onion, potato");
		testRecipe.setNumberOfServings(2);

		Recipe savedRecipe = recipeRepository.save(testRecipe);

		savedRecipe.setName("meat-lasagna");
		savedRecipe.setInstructions("add meat add pasta");

		performPatch("/api/v1/recipes", savedRecipe)
				.andExpect(status().isOk());

		Optional<Recipe> updatedRecipe = recipeRepository.findById(savedRecipe.getId());

		assertTrue(updatedRecipe.isPresent());
		assertEquals(savedRecipe.getName(), updatedRecipe.get().getName());
		assertEquals(savedRecipe.getNumberOfServings(), updatedRecipe.get().getNumberOfServings());
		assertEquals(savedRecipe.getInstructions(), updatedRecipe.get().getInstructions());

	}

	@Test
	public void test_updateRecipe_idIsNull() throws Exception {
		Recipe testRecipe = new Recipe();
		testRecipe.setName("sarmale");
		testRecipe.setInstructions("take grape leaf, take meat, cook them");
		testRecipe.setNumberOfServings(3);
		testRecipe.setType(RecipeType.OTHER);

		performPatch("/api/v1/recipes", testRecipe)
				.andDo(MockMvcResultHandlers.print())
				.andExpect(status().isBadRequest());
	}

	@Test
	public void test_updateRecipe_notFound() throws Exception {
		Recipe testRecipe = RecipeTestDataBuilder.createRecipe(1);

		performPatch("/api/v1/recipes", testRecipe)
				.andExpect(status().isNotFound())
				.andExpect(jsonPath("$.message").exists());
	}

	@Test
	public void test_deleteRecipe_successfully() throws Exception {
		Recipe testRecipe = RecipeTestDataBuilder.createRecipe();
		Recipe savedRecipe = recipeRepository.save(testRecipe);

		performDelete("/api/v1/recipes/" + savedRecipe.getId())
				.andExpect(status().isNoContent());

		Optional<Recipe> deletedRecipe = recipeRepository.findById(savedRecipe.getId());

		assertTrue(deletedRecipe.isEmpty());
	}

	@Test
	public void test_deleteRecipe_notFound() throws Exception {
		performDelete("/api/v1/recipes/1")
				.andExpect(status().isNotFound())
				.andExpect(jsonPath("$.message").exists());
	}

	@Test
	public void test_SearchRecipeByCriteria_successfully() throws Exception {
		//create ingredient for recipe
		Ingredient ingredient = IngredientTestDataBuilder.createIngredientWithNameParam("Pepper");
		Ingredient savedIngredient = ingredientRepository.save(ingredient);

		//create the recipe
		RecipeRequest recipeRequest = new RecipeRequest("pasta",
				RecipeType.OTHER, 5, List.of(savedIngredient.getId()), "someInstruction");

		MvcResult createdRecipe = performPost("/api/v1/recipes", recipeRequest)
				.andExpect(status().isCreated())
				.andReturn();

		//prepare the search criteria and by newly created id
		Integer id = readByJsonPath(createdRecipe, "$.id");

		RecipeSearchRequest request = new RecipeSearchRequest();
		List<SearchCriteriaRequest> searchCriteriaList = new ArrayList<>();
		SearchCriteriaRequest searchCriteria = new SearchCriteriaRequest(FilterKeyReqInput.name, "Pasta",
				SearchOperationReqInput.EQ, null);

		searchCriteriaList.add(searchCriteria);

		request.setDataOption(DataOptionReqInput.ALL);
		request.setCriteria(searchCriteriaList);

		MvcResult result = performPost("/api/v1/recipes/search", request)
				.andExpect(status().isOk())
				.andReturn();

		Optional<Recipe> optionalRecipe = recipeRepository.findById(id);


		List<RecipeResponse> listRecipeList = getListFromMvcResult(result, RecipeResponse.class);
		assertEquals(listRecipeList.size(), listRecipeList.size());
		assertTrue(optionalRecipe.isPresent());
		assertEquals(listRecipeList.get(0).getName(), optionalRecipe.get().getName());
		assertEquals(listRecipeList.get(0).getInstructions(), optionalRecipe.get().getInstructions());
		assertEquals(listRecipeList.get(0).getNumberOfServings(), optionalRecipe.get().getNumberOfServings());
	}

	@Test
	public void test_SearchRecipeByCriteria_fails() throws Exception {
		performPost("/api/v1/recipes/search", null)
				.andExpect(status().is4xxClientError())
				.andExpect(jsonPath("$.message").exists())
				.andReturn();
	}

}

package com.favourite.recipe.repository;

import com.favourite.recipe.model.Recipe;
import com.favourite.recipe.model.RecipeType;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@DataJpaTest
public class RecipeRepositoryTest {
	@Autowired
	private TestEntityManager entityManager;
	@Autowired
	private RecipeRepository recipeRepository;

	@Test
	public void test_whenTryToSaveIngredientSuccess() {
		Recipe entity = new Recipe();
		entity.setType(RecipeType.OTHER);
		entity.setInstructions("some instructions");
		entity.setName("pasta");
		Recipe savedRecipe = recipeRepository.save(entity);
		assertNotNull(savedRecipe);

		assertEquals(RecipeType.OTHER, savedRecipe.getType());
		assertNotNull(savedRecipe.getId());
	}

	@Test
	public void test_whenTryGetTokenListSuccess() {
		Recipe entity1 = new Recipe();
		entity1.setType(RecipeType.OTHER);
		entity1.setName("lasagna");

		Recipe entity2 = new Recipe();
		entity2.setType(RecipeType.OTHER);
		entity2.setName("pizza");


		Recipe firstSavedEntity = recipeRepository.save(entity1);
		Recipe secondSavedEntity = recipeRepository.save(entity2);
		assertNotNull(firstSavedEntity);
		assertNotNull(secondSavedEntity);

		assertFalse(recipeRepository.findAll().isEmpty());
		assertEquals(2, recipeRepository.findAll().size());
	}
}
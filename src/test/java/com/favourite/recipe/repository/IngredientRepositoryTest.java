package com.favourite.recipe.repository;

import com.favourite.recipe.model.Ingredient;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@ExtendWith(SpringExtension.class)
@DataJpaTest
public class IngredientRepositoryTest {
	@Autowired
	private TestEntityManager entityManager;
	@Autowired
	private IngredientRepository ingredientRepository;

	@Test
	public void test_whenTryToSaveIngredientSuccess() {
		Ingredient entity = new Ingredient();
		entity.setName("Tomato");
		Ingredient savedIngredient = ingredientRepository.save(entity);
		assertNotNull(savedIngredient);

		assertEquals("Tomato", savedIngredient.getName());
		assertNotNull(savedIngredient.getId());
	}

	@Test
	public void test_whenTryGetTokenListSuccess() {
		Ingredient entity1 = new Ingredient();
		entity1.setName("Tomato");

		Ingredient entity2 = new Ingredient();
		entity2.setName("Potato");

		Ingredient firstSavedEntity = ingredientRepository.save(entity1);
		Ingredient secondSavedEntity = ingredientRepository.save(entity2);
		assertNotNull(firstSavedEntity);
		assertNotNull(secondSavedEntity);

		assertFalse(ingredientRepository.findAll().isEmpty());
		assertEquals(2, ingredientRepository.findAll().size());
	}

	@Test
	public void test_whenTryAddSameIngredientTwiceTokenListFails() {
		Ingredient entity1 = new Ingredient();
		entity1.setName("Tomato");

		Ingredient entity2 = new Ingredient();
		entity2.setName("Tomato");

		ingredientRepository.save(entity1);
		Assertions.assertThrows(DataIntegrityViolationException.class, () -> ingredientRepository.save(entity2));

	}
}
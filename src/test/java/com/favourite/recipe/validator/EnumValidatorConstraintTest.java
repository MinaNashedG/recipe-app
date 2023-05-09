package com.favourite.recipe.validator;

import com.favourite.recipe.api.request.IngredientRequest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import java.util.Set;
import java.util.stream.Collectors;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;


public class EnumValidatorConstraintTest {
	private static Validator validator;

	@BeforeEach
	public void setupValidatorInstance() {
		validator = Validation.buildDefaultValidatorFactory()
				.getValidator();
	}

	@Test
	public void whenNotBlankName_thenNoConstraintViolations() {
		IngredientRequest request = new IngredientRequest("pasta");

		Set<ConstraintViolation<IngredientRequest>> violations = validator.validate(request);

		assertThat(violations.size()).isEqualTo(0);
	}

	@Test
	public void whenBlankName_thenOneConstraintViolation() {
		IngredientRequest request = new IngredientRequest(null);

		Set<ConstraintViolation<IngredientRequest>> violations = validator.validate(request);
		String collect = violations.stream()
				.map(ConstraintViolation::getMessage)
				.collect(Collectors.joining(", "));

		assertEquals(collect, "{ingredient.notBlank}");
		assertThat(violations.size()).isEqualTo(1);
	}

	@Test
	public void whenEmptyName_thenOneConstraintViolation() {
		IngredientRequest request = new IngredientRequest(null);

		Set<ConstraintViolation<IngredientRequest>> violations = validator.validate(request);
		String collect = violations.stream()
				.map(ConstraintViolation::getMessage)
				.collect(Collectors.joining(", "));

		assertEquals(collect, "{ingredient.notBlank}");
		assertThat(violations.size()).isEqualTo(1);
	}

	@Test
	public void whenNameDoesNotFitPattern_thenOneConstraintViolation() {
		IngredientRequest request = new IngredientRequest("-.1!@$!#@");

		Set<ConstraintViolation<IngredientRequest>> violations = validator.validate(request);
		String collect = violations.stream()
				.map(ConstraintViolation::getMessage)
				.collect(Collectors.joining(", "));

		assertEquals(collect, "{ingredient.pattern}");
		assertThat(violations.size()).isEqualTo(1);
	}
}

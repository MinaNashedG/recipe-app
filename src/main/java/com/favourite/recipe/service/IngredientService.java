package com.favourite.recipe.service;

import com.favourite.recipe.api.request.IngredientRequest;
import com.favourite.recipe.api.response.IngredientResponse;
import com.favourite.recipe.config.MessageProvider;
import com.favourite.recipe.exception.NotFoundException;
import com.favourite.recipe.model.Ingredient;
import com.favourite.recipe.repository.IngredientRepository;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

@AllArgsConstructor
@Service
@Transactional
public class IngredientService {

	private static final String INGREDIENT_NOT_FOUND = "ingredient.notFound";

	private final IngredientRepository ingredientRepository;

	private final MessageProvider messageProvider;

	public Integer createIngredient(IngredientRequest request) {

		Ingredient createdIngredient = ingredientRepository.save(Ingredient.builder()
				.name(request.getName().toLowerCase())
				.build());

		return createdIngredient.getId();
	}

	public Set<Ingredient> getIngredientsByIds(List<Integer> ingredientIds) {
		return ingredientIds.stream()
				.filter(Objects::nonNull)
				.map(this::findById)
				.collect(Collectors.toSet());
	}

	public Ingredient findById(int id) {
		return ingredientRepository.findById(id)
				.orElseThrow(() -> new NotFoundException(messageProvider.getMessage(INGREDIENT_NOT_FOUND)));
	}

	public List<IngredientResponse> getIngredients(int page, int size) {
		Pageable pageRequest = PageRequest.of(page, size);

		return ingredientRepository.findAll(pageRequest).getContent()
				.stream()
				.map(IngredientResponse::new)
				.collect(Collectors.toList());
	}

	public void delete(int id) {
		if (!ingredientRepository.existsById(id)) {
			throw new NotFoundException(messageProvider.getMessage(INGREDIENT_NOT_FOUND));
		}
		ingredientRepository.deleteById(id);
	}
}

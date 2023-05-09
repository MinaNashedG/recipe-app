package com.favourite.recipe.search;

import com.favourite.recipe.model.DataOption;
import com.favourite.recipe.model.FilterKey;
import com.favourite.recipe.model.Recipe;
import com.favourite.recipe.model.SearchOperation;
import org.junit.jupiter.api.Test;
import org.springframework.data.jpa.domain.Specification;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class RecipeSpecificationBuilderTest {
	List<SearchCriteria> params;

	public RecipeSpecificationBuilderTest() {
		params = new ArrayList<>();
	}

	@Test
	public void test_buildMethodWhenParamsIsEmpty_successfully() {
		RecipeSpecificationBuilder builder = new RecipeSpecificationBuilder(params);
		Optional<Specification<Recipe>> build = builder.build();
		assertEquals(Optional.empty(), build);
	}

	@Test
	public void test_buildMethodWhenParamsIsNotEmpty_successfully() {
		RecipeSpecificationBuilder builder = new RecipeSpecificationBuilder(params);
		SearchCriteria searchCriteria = new SearchCriteria();
		searchCriteria.setDataOption(DataOption.ALL);
		searchCriteria.setFilterKey(FilterKey.name);
		searchCriteria.setOperation(SearchOperation.CN);
		searchCriteria.setValue("pasta");
		params.add(searchCriteria);
		Optional<Specification<Recipe>> build = builder.build();
		assertTrue(build.isPresent());
	}

}

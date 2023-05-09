package com.favourite.recipe.search;

import com.favourite.recipe.model.DataOption;
import com.favourite.recipe.model.Recipe;
import lombok.AllArgsConstructor;
import org.springframework.data.jpa.domain.Specification;

import java.util.List;
import java.util.Optional;

@AllArgsConstructor
public class RecipeSpecificationBuilder {
	private final List<SearchCriteria> params;

	public final RecipeSpecificationBuilder recipeSpecifications(List<SearchCriteria> searchCriteriaRequest) {
		params.addAll(searchCriteriaRequest);
		return this;
	}

	public Optional<Specification<Recipe>> build() {
		if (params.isEmpty()) {
			return Optional.empty();
		}

		Specification<Recipe> result = new RecipeSpecification(params.get(0));

		for (int i = 1; i < params.size(); i++) {
			SearchCriteria criteria = params.get(i);
			result = (criteria.getDataOption() == DataOption.ALL)
					? Specification.where(result).and(new RecipeSpecification(criteria))
					: Specification.where(result).or(new RecipeSpecification(criteria));

		}
		return Optional.of(result);
	}
}

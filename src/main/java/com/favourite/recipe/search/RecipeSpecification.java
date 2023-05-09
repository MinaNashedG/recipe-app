package com.favourite.recipe.search;

import com.favourite.recipe.model.FilterKey;
import com.favourite.recipe.model.Recipe;
import com.favourite.recipe.model.SearchOperation;
import com.favourite.recipe.search.filter.SearchFilter;
import com.favourite.recipe.search.filter.SearchFilterContains;
import com.favourite.recipe.search.filter.SearchFilterDoesNotContain;
import com.favourite.recipe.search.filter.SearchFilterEqual;
import com.favourite.recipe.search.filter.SearchFilterNotEqual;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Join;
import jakarta.persistence.criteria.JoinType;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import lombok.AllArgsConstructor;
import org.springframework.data.jpa.domain.Specification;

import java.util.List;

@AllArgsConstructor
public class RecipeSpecification implements Specification<Recipe> {

	private static final String RECIPE_INGREDIENTS = "ingredient";

	private final SearchCriteria criteria;

	private static final List<SearchFilter> searchFilters = List.of(new SearchFilterEqual(),
			new SearchFilterNotEqual(),
			new SearchFilterContains(),
			new SearchFilterDoesNotContain());

	@Override
	public Specification<Recipe> and(Specification<Recipe> other) {
		return Specification.super.and(other);
	}

	@Override
	public Specification<Recipe> or(Specification<Recipe> other) {
		return Specification.super.or(other);
	}

	@Override
	public Predicate toPredicate(Root<Recipe> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {

		SearchOperation operation = criteria.getOperation();
		String filterValue = criteria.getValue().toString().toLowerCase();
		FilterKey filterKey = criteria.getFilterKey();

		Join<Object, Object> subRoot = root.join(RECIPE_INGREDIENTS, JoinType.INNER);
		query.distinct(true);

		return searchFilters.stream()
				.filter(searchFilter -> searchFilter.match(operation))
				.findFirst()
				.map(searchFilter -> searchFilter.apply(criteriaBuilder, filterKey, filterValue, root, subRoot))
				.orElse(null);
	}

}

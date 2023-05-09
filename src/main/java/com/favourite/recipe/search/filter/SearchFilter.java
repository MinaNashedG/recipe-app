package com.favourite.recipe.search.filter;

import com.favourite.recipe.model.FilterKey;
import com.favourite.recipe.model.Recipe;
import com.favourite.recipe.model.SearchOperation;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.Join;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;

public interface SearchFilter {
	FilterKey INGREDIENT_KEY = FilterKey.ingredient;

	boolean match(SearchOperation opt);

	Predicate apply(CriteriaBuilder cb, FilterKey filterKey, String filterValue, Root<Recipe> root,
			Join<Object, Object> subRoot);
}
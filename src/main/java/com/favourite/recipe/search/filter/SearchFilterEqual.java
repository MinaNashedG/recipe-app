package com.favourite.recipe.search.filter;

import com.favourite.recipe.model.FilterKey;
import com.favourite.recipe.model.Recipe;
import com.favourite.recipe.model.SearchOperation;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.Join;
import jakarta.persistence.criteria.Path;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;


public class SearchFilterEqual implements SearchFilter {

	@Override
	public boolean match(SearchOperation opt) {
		return opt == SearchOperation.EQ;
	}

	@Override
	public Predicate apply(CriteriaBuilder cb, FilterKey filterKey, String filterValue, Root<Recipe> root,
			Join<Object, Object> subRoot) {
		if (filterKey.equals(INGREDIENT_KEY)) {
			return getPredicate(cb, subRoot.get(String.valueOf(filterKey)), filterValue);
		}

		return getPredicate(cb, root.get(String.valueOf(filterKey)), filterValue);
	}

	private Predicate getPredicate(CriteriaBuilder cb, Path<Object> root, String filterValue) {
		return cb.equal(cb.lower(root.as(String.class)), filterValue);
	}
}

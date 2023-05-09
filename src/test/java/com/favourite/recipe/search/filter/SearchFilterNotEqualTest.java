package com.favourite.recipe.search.filter;

import com.favourite.recipe.model.SearchOperation;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class SearchFilterNotEqualTest {

	@Test
	public void couldBeAppliedReturnsTrueWhenOperationIsNotEqual() {
		SearchFilterNotEqual filterNotEqual = new SearchFilterNotEqual();
		boolean match = filterNotEqual.match(SearchOperation.NE);
		assertTrue(match);
	}

	@Test
	public void couldBeAppliedReturnsFalseWhenOperationIsEqual() {
		SearchFilterNotEqual filterNotEqual = new SearchFilterNotEqual();
		boolean match = filterNotEqual.match(SearchOperation.EQ);
		assertFalse(match);
	}

	@Test
	public void couldBeAppliedReturnsFalseWhenOperationIsDoesNotContain() {
		SearchFilterNotEqual filterNotEqual = new SearchFilterNotEqual();
		boolean match = filterNotEqual.match(SearchOperation.NC);
		assertFalse(match);
	}

	@Test
	public void couldBeAppliedReturnsFalseWhenOperationIsContain() {
		SearchFilterNotEqual filterNotEqual = new SearchFilterNotEqual();
		boolean match = filterNotEqual.match(SearchOperation.CN);
		assertFalse(match);
	}

	@Test
	public void couldBeAppliedReturnsFalseWhenOperationIsNull() {
		SearchFilterNotEqual filterNotEqual = new SearchFilterNotEqual();
		boolean match = filterNotEqual.match(null);
		assertFalse(match);
	}

}
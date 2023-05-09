package com.favourite.recipe.search.filter;

import com.favourite.recipe.model.SearchOperation;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class SearchFilterContainsTest {

	@Test
	public void couldBeAppliedReturnsFalseWhenOperationIsNotEqual() {
		SearchFilterContains filter = new SearchFilterContains();
		boolean match = filter.match(SearchOperation.NE);
		assertFalse(match);
	}

	@Test
	public void couldBeAppliedReturnsFalseWhenOperationIsEqual() {
		SearchFilterContains filter = new SearchFilterContains();
		boolean match = filter.match(SearchOperation.EQ);
		assertFalse(match);

	}

	@Test
	public void couldBeAppliedReturnsFalseWhenOperationIsDoesNotContain() {
		SearchFilterContains filter = new SearchFilterContains();
		boolean match = filter.match(SearchOperation.NC);
		assertFalse(match);
	}

	@Test
	public void couldBeAppliedReturnsTrueWhenOperationIsContain() {
		SearchFilterContains filter = new SearchFilterContains();
		boolean match = filter.match(SearchOperation.CN);
		assertTrue(match);
	}

	@Test
	public void couldBeAppliedReturnsFalseWhenOperationIsNull() {
		SearchFilterContains filter = new SearchFilterContains();
		boolean match = filter.match(null);
		assertFalse(match);
	}

}
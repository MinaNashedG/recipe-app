package com.favourite.recipe.search.filter;

import com.favourite.recipe.model.SearchOperation;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class SearchFilterDoesNotContainTest {

	@Test
	public void couldBeAppliedReturnsFalseWhenOperationIsNotEqual() {
		SearchFilterDoesNotContain filter = new SearchFilterDoesNotContain();
		boolean match = filter.match(SearchOperation.NE);
		assertFalse(match);
	}

	@Test
	public void couldBeAppliedReturnsFalseWhenOperationIsEqual() {
		SearchFilterDoesNotContain filter = new SearchFilterDoesNotContain();
		boolean match = filter.match(SearchOperation.EQ);
		assertFalse(match);

	}

	@Test
	public void couldBeAppliedReturnsTrueWhenOperationIsDoesNotContain() {
		SearchFilterDoesNotContain filter = new SearchFilterDoesNotContain();
		boolean match = filter.match(SearchOperation.NC);
		assertTrue(match);
	}

	@Test
	public void couldBeAppliedReturnsFalseWhenOperationIsContain() {
		SearchFilterDoesNotContain filter = new SearchFilterDoesNotContain();
		boolean match = filter.match(SearchOperation.CN);
		assertFalse(match);
	}

	@Test
	public void couldBeAppliedReturnsFalseWhenOperationIsNull() {
		SearchFilterDoesNotContain filter = new SearchFilterDoesNotContain();
		boolean match = filter.match(null);
		assertFalse(match);
	}

}
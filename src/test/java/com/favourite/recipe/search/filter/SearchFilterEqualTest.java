package com.favourite.recipe.search.filter;

import com.favourite.recipe.model.SearchOperation;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class SearchFilterEqualTest {

	@Test
	public void couldBeAppliedReturnsFalseWhenOperationIsNotEqual() {
		SearchFilterEqual filterEqual = new SearchFilterEqual();
		boolean match = filterEqual.match(SearchOperation.NE);
		assertFalse(match);
	}

	@Test
	public void couldBeAppliedReturnsTrueWhenOperationIsEqual() {
		SearchFilterEqual filterEqual = new SearchFilterEqual();
		boolean match = filterEqual.match(SearchOperation.EQ);
		assertTrue(match);
	}

	@Test
	public void couldBeAppliedReturnsFalseWhenOperationIsDoesNotContain() {
		SearchFilterEqual filterEqual = new SearchFilterEqual();
		boolean match = filterEqual.match(SearchOperation.NC);
		assertFalse(match);
	}

	@Test
	public void couldBeAppliedReturnsFalseWhenOperationIsContain() {
		SearchFilterEqual filterEqual = new SearchFilterEqual();
		boolean match = filterEqual.match(SearchOperation.CN);
		assertFalse(match);
	}

	@Test
	public void couldBeAppliedReturnsFalseWhenOperationIsNull() {
		SearchFilterEqual filterEqual = new SearchFilterEqual();
		boolean match = filterEqual.match(null);
		assertFalse(match);
	}

}
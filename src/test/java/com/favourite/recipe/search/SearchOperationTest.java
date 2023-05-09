package com.favourite.recipe.search;

import com.favourite.recipe.model.SearchOperation;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class SearchOperationTest {
	@Test
	public void simpleEnumExampleInsideClassTest() {
		SearchOperation contains = SearchOperation.CN;
		SearchOperation doesNotContain = SearchOperation.NC;
		SearchOperation equal = SearchOperation.EQ;
		SearchOperation notEqual = SearchOperation.NE;
		assertEquals("CONTAINS", contains.getValue());
		assertEquals("NOT_CONTAINS", doesNotContain.getValue());
		assertEquals("EQUALS", equal.getValue());
		assertEquals("NOT_EQUALS", notEqual.getValue());
	}
}

package com.favourite.recipe.model;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum SearchOperation {
	CN("CONTAINS"), NC("NOT_CONTAINS"), EQ("EQUALS"), NE("NOT_EQUALS");
	private final String value;
}

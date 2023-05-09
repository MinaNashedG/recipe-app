package com.favourite.recipe.config;

public interface RuleConfig {

	String PATTERN_NAME = "^(?:\\p{L}\\p{M}*|[',. \\-]|\\s)*$";

	int MAX_LENGTH_NAME = 100;

	int MAX_LENGTH_DEFAULT = 255;

	String PATTERN_INSTRUCTION = "^(?:\\p{L}\\p{M}*|[0-9]*|[\\/\\-+.,?!*();\"]|\\s)*$";
}

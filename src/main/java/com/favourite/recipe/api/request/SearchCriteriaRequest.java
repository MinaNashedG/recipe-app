package com.favourite.recipe.api.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.validation.annotation.Validated;

@Validated
@Data
@AllArgsConstructor
@NoArgsConstructor
public class SearchCriteriaRequest {

	@Schema(description = "The field name for searching as following " +
			"name, " +
			"numberOfServings, " +
			"type, " +
			"instructions, " +
			"ingredientName)", example = "name")
	@Enumerated(EnumType.STRING)
	@NotNull(message = "{filterKey.invalid}")
	private FilterKeyReqInput filterKey;

	@Schema(description = "The value of the field name that you want to search on", example = "Cake")
	private Object value;

	@Schema(description = "The operation type you wanted to search (CN - contains, " +
			"NC - doesn't contain, " +
			"EQ - equals, " +
			"NE - not equals", example = "CN")
	@Enumerated(EnumType.STRING)
	@NotNull(message = "{searchOperation.invalid}")
	private SearchOperationReqInput operation;

	@Schema(hidden = true)
	@Enumerated(EnumType.STRING)
	private DataOptionReqInput dataOption;
}

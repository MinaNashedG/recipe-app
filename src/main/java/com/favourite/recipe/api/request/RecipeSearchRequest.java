package com.favourite.recipe.api.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.validation.annotation.Validated;

import java.util.List;

@Data
@Validated
@NoArgsConstructor
public class RecipeSearchRequest {
	@Schema(description = "Search criteria you want to search recipe with")
	@NotEmpty
	@NotNull
	private List<SearchCriteriaRequest> criteria;

	@Schema(description = "If you want all or just one criteria is enough for filter to work", example = "ALL")
	@Enumerated(EnumType.STRING)
	@NotNull(message = "{dataOption.invalid}")
	private DataOptionReqInput dataOption;

}

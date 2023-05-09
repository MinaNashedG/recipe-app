package com.favourite.recipe.search;

import com.favourite.recipe.api.request.DataOptionReqInput;
import com.favourite.recipe.api.request.SearchCriteriaRequest;
import com.favourite.recipe.model.DataOption;
import com.favourite.recipe.model.FilterKey;
import com.favourite.recipe.model.SearchOperation;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Optional;

@Data
@NoArgsConstructor
public class SearchCriteria {
	private FilterKey filterKey;
	private Object value;
	private SearchOperation operation;
	private DataOption dataOption;

	public SearchCriteria(SearchCriteriaRequest request) {
		this.filterKey = FilterKey.valueOf(request.getFilterKey().name());
		this.operation = SearchOperation.valueOf(request.getOperation().name());
		this.value = request.getValue();
		this.dataOption = Optional.ofNullable(request.getDataOption())
				.map(DataOptionReqInput::name)
				.map(DataOption::valueOf)
				.orElse(null);
	}
}

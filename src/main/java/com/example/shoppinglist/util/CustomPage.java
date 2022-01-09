package com.example.shoppinglist.util;

import java.util.Collection;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CustomPage<T> {

	private Collection<T> content;
	private CustomPagebale pageable;
}

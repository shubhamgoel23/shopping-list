package com.example.shoppinglist.util;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Collection;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CustomPage<T> {

    private Collection<T> content;
    private CustomPagebale pageable;
}

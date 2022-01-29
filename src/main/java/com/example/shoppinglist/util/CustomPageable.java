package com.example.shoppinglist.util;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CustomPageable {
    private int pageNumber;
    private int pageSize;
    private long totalElements;

}

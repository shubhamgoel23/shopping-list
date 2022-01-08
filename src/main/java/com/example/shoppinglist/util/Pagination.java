package com.example.shoppinglist.util;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class Pagination {

    private int totalItems;

    private int totalPages;

    private int currentPage;

}

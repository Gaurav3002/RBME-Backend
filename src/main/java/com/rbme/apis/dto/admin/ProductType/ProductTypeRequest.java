package com.rbme.apis.dto.admin.ProductType;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class ProductTypeRequest {

    @NotNull(message = "Category can not be null!")
    private Long categoryId;
    @NotNull(message = "ProductType can not be null!")
    private String name;

    private Boolean active;
}

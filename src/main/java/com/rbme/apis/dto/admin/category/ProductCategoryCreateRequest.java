package com.rbme.apis.dto.admin.category;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class ProductCategoryCreateRequest {

    @NotNull(message = "Company is Required!")
    private String name;

    @NotNull(message = "Company is Required!")
    private Long companyId;

    private boolean active = true;

}

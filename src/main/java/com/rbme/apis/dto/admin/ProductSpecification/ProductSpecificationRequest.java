package com.rbme.apis.dto.admin.ProductSpecification;

import lombok.Data;

@Data
public class ProductSpecificationRequest {
  private Long productId;
  private String specificationName;
  private String specificationValue;

}

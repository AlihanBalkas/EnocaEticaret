package com.enoca.eticaret.web.product.mapper;

import com.enoca.eticaret.web.product.entity.Product;
import com.enoca.eticaret.web.product.model.ProductRequest;
import com.enoca.eticaret.web.product.model.ProductResponse;
import com.enoca.eticaret.web.product.model.ProductUpdate;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface ProductMapper {
    ProductResponse mapResponseToEntity(Product product);
    Product mapRequestToEntity(ProductRequest productRequest);
    List<ProductResponse> mapResponseToEntityList(List<Product> products);
    Product mapUpdateToEntity(ProductUpdate productUpdate);

}
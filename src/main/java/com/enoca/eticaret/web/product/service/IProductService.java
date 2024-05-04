package com.enoca.eticaret.web.product.service;

import com.enoca.eticaret.web.product.model.ProductRequest;
import com.enoca.eticaret.web.product.model.ProductResponse;
import com.enoca.eticaret.web.product.model.ProductUpdate;

import java.util.List;

public interface IProductService {
    ProductResponse createProduct(ProductRequest productRequest);
    List<ProductResponse> findAll();
    ProductResponse findById(String id);
    ProductResponse update(ProductUpdate productUpdate);
    void deleteById(String id);
}

package com.enoca.eticaret.web.product.service.impl;

import com.enoca.eticaret.web.common.exception.CustomNotFoundException;
import com.enoca.eticaret.web.product.entity.Product;
import com.enoca.eticaret.web.product.mapper.ProductMapper;
import com.enoca.eticaret.web.product.model.ProductRequest;
import com.enoca.eticaret.web.product.model.ProductResponse;
import com.enoca.eticaret.web.product.model.ProductUpdate;
import com.enoca.eticaret.web.product.repository.ProductRepository;
import com.enoca.eticaret.web.product.service.IProductService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class ProductService implements IProductService {

    private final ProductRepository productRepository;
    private final ProductMapper productMapper;

    @Override
    public ProductResponse createProduct(ProductRequest productRequest) {

        Product product = productMapper.mapRequestToEntity(productRequest);

        Product save = productRepository.save(product);

        return productMapper.mapResponseToEntity(save);
    }

    @Override
    public List<ProductResponse> findAll() {
        List<Product> products = productRepository.findAll();
        return productMapper.mapResponseToEntityList(products);
    }

    @Override
    public ProductResponse findById(String id) {
        Product product = productRepository.findById(id).orElseThrow(() -> new CustomNotFoundException("Ürün Bulunamadı"));
        return productMapper.mapResponseToEntity(product);
    }

    @Override
    public ProductResponse update(ProductUpdate productUpdate) {
        Product product = productMapper.mapUpdateToEntity(productUpdate);
        Product savedEntity = productRepository.save(product);
        return productMapper.mapResponseToEntity(savedEntity);
    }

    @Override
    public void deleteById(String id) {
        Optional<Product> optionalProduct = productRepository.findById(id);
        if (optionalProduct.isPresent()){
            Product product = optionalProduct.get();
            product.setDeletedDate(new Date());
            productRepository.save(product);
        }else {
            throw new CustomNotFoundException("Ürün Bulunamadı");
        }
    }
}

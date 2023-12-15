package com.example.springwebtest.service;

import com.example.springwebtest.entity.Product;
import com.example.springwebtest.exception.ProductNotFoundException;
import com.example.springwebtest.model.request.ProductCreationRequest;
import com.example.springwebtest.model.response.ProductResponse;
import com.example.springwebtest.repository.ProductRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
@AllArgsConstructor
public class ProductService {

    private final ProductRepository productRepository;

    public List<ProductResponse> getAll() {
        List<Product> products = productRepository.getAll();

        return products.stream().map(p ->
                ProductResponse.builder()
                        .id(p.getId())
                        .name(p.getName())
                        .price(p.getPrice())
                        .description(p.getDescription())
                        .image(p.getImage())
                        .build()
        ).collect(Collectors.toList());
    }

    public void createProduct(ProductCreationRequest request, MultipartFile image) {
        // luu anh
        String filePath = "product_images" + File.separator + image.getOriginalFilename();
        try {
            Files.copy(image.getInputStream(), Paths.get(filePath), StandardCopyOption.REPLACE_EXISTING);
        } catch (IOException e) {
            log.error(e.getMessage(), e);
        }

        Product product = Product.builder()
                .name(request.getName())
                .price(request.getPrice())
                .description(request.getDescription())
                .image(image.getOriginalFilename())
                .build();
        productRepository.save(product);
    }

    public ProductResponse getProductById(Long id) throws ProductNotFoundException {
        Product product = productRepository.findById(id);
        return ProductResponse.builder()
                .id(product.getId())
                .name(product.getName())
                .price(product.getPrice())
                .description(product.getDescription())
                .image(product.getImage())
                .build();
    }
}

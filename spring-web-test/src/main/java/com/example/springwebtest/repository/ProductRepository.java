package com.example.springwebtest.repository;

import com.example.springwebtest.entity.Product;
import com.example.springwebtest.exception.ProductNotFoundException;
import com.example.springwebtest.util.FileUtil;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.Comparator;
import java.util.List;

@Repository
@AllArgsConstructor
public class ProductRepository {

    private final FileUtil<Product> fileUtil;

    private static final String PRODUCT_FILE_NAME = "data/products.json";

    public List<Product> getAll() {
        return fileUtil.readDataFromFile(PRODUCT_FILE_NAME, Product[].class);
    }

    public void save(Product product) {
        List<Product> products = getAll();
        product.setId(findMaxId(products) + 1);
        products.add(product);
        fileUtil.writeDataToFile(PRODUCT_FILE_NAME, products);
    }

    private Long findMaxId(List<Product> products) {
        return products.stream().map(Product::getId).max(Comparator.comparingLong(Long::longValue)).orElse(0L);
    }

    public Product findById(Long id) throws ProductNotFoundException {
        List<Product> products = getAll();
        return products
                .stream()
                .filter(p -> p.getId().equals(id))
                .findFirst()
                .orElseThrow(() -> new ProductNotFoundException("Product not found"));
    }
}

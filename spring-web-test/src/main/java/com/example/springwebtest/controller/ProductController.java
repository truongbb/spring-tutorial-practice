package com.example.springwebtest.controller;

import com.example.springwebtest.exception.ProductNotFoundException;
import com.example.springwebtest.model.request.ProductCreationRequest;
import com.example.springwebtest.model.response.ProductResponse;
import com.example.springwebtest.service.ProductService;
import com.google.gson.Gson;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Controller
@RequestMapping("/admin/products")
@AllArgsConstructor
public class ProductController {

    private final ProductService productService;
    private final Gson gson;


    @GetMapping
    public String getAllProduct(Model model) {
        List<ProductResponse> products = productService.getAll();
        model.addAttribute("products", products);
        return "admin/product";
    }

    @PostMapping
    public ResponseEntity<?> creatProduct(@RequestPart("productRequest") String productCreationRequest,
                                          @RequestPart("image") MultipartFile image) {
        ProductCreationRequest request = gson.fromJson(productCreationRequest, ProductCreationRequest.class);
        productService.createProduct(request, image);
        return ResponseEntity.ok(null);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProductResponse> getProductById(@PathVariable Long id) throws ProductNotFoundException {
        return ResponseEntity.ok(productService.getProductById(id));
    }


}

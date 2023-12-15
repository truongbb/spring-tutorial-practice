package com.example.springwebtest.controller;

import com.example.springwebtest.model.response.ProductResponse;
import com.example.springwebtest.service.ProductService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller
@AllArgsConstructor
public class HomeController {

    private final ProductService productService;

    @GetMapping
    public String home(Model model) {
        List<ProductResponse> products = productService.getAll();
        model.addAttribute("products", products);
        return "index";
    }

}

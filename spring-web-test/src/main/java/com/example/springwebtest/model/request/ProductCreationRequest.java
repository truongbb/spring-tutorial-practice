package com.example.springwebtest.model.request;

import lombok.Data;

@Data
public class ProductCreationRequest {

    private String name;
    private String description;
    private Float price;

}

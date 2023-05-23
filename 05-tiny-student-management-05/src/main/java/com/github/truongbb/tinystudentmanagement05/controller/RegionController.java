package com.github.truongbb.tinystudentmanagement05.controller;

import com.github.truongbb.tinystudentmanagement05.model.response.RegionResponse;
import com.github.truongbb.tinystudentmanagement05.service.RegionService;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/regions")
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class RegionController {

    RegionService regionService;

    @GetMapping
    public List<RegionResponse> getAll() {
        return regionService.getAll();
    }

}

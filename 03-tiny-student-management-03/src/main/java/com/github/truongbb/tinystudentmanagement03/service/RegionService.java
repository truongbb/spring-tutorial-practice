package com.github.truongbb.tinystudentmanagement03.service;

import com.github.truongbb.tinystudentmanagement03.dto.RegionDto;
import com.github.truongbb.tinystudentmanagement03.statics.Region;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class RegionService {

    public List<RegionDto> getAll() {
        List<RegionDto> dtos = new ArrayList<>();
        dtos.add(new RegionDto(Region.MIEN_BAC.toString(), Region.MIEN_BAC.name));
        dtos.add(new RegionDto(Region.MIEN_TRUNG.toString(), Region.MIEN_TRUNG.name));
        dtos.add(new RegionDto(Region.MIEN_NAM.toString(), Region.MIEN_NAM.name));
        return dtos;
    }

}

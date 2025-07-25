package com.payetonkawa.product.factory;

import com.github.javafaker.Faker;
import com.payetonkawa.product.dto.PatchProductDto;

import java.util.ArrayList;
import java.util.List;

public class PatchProductDtoFactory {
    private static final Faker faker = new Faker();

    public static PatchProductDto generatePatchDto() {
        PatchProductDto dto = new PatchProductDto();
        dto.setIdProduct(faker.number().numberBetween(1, 1000));
        dto.setLibelle(faker.commerce().productName());
        dto.setStock(faker.number().numberBetween(0, 100));
        dto.setUnitPrice(faker.number().numberBetween(1, 500));
        return dto;
    }

    public static List<PatchProductDto> generatePatchDtoList(int count) {
        List<PatchProductDto> list = new ArrayList<>();
        for (int i = 0; i < count; i++) {
            list.add(generatePatchDto());
        }
        return list;
    }
}

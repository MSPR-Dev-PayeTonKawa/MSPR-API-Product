package com.payetonkawa.product.factory;

import com.github.javafaker.Faker;
import com.payetonkawa.product.dto.PostProductDto;

import java.util.ArrayList;
import java.util.List;

public class PostProductDtoFactory {
    private static final Faker faker = new Faker();

    public static PostProductDto generatePostDto() {
        PostProductDto dto = new PostProductDto();
        dto.setLibelle(faker.commerce().productName());
        dto.setStock(faker.number().numberBetween(0, 100));
        dto.setUnitPrice(faker.number().numberBetween(1, 500));
        return dto;
    }

    public static List<PostProductDto> generatePostDtoList(int count) {
        List<PostProductDto> list = new ArrayList<>();
        for (int i = 0; i < count; i++) {
            list.add(generatePostDto());
        }
        return list;
    }
}

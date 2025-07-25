package com.payetonkawa.product.factory;

import com.github.javafaker.Faker;
import com.payetonkawa.product.entity.Product;

import java.util.ArrayList;
import java.util.List;

public class ProductFactory {
    private static final Faker faker = new Faker();

    public static Product generateProduct() {
        Product product = new Product();
        product.setIdProduct(faker.number().numberBetween(1, 1000));
        product.setLibelle(faker.commerce().productName());
        product.setStock(faker.number().numberBetween(0, 100));
        product.setUnitPrice(faker.number().numberBetween(0, 10));
        return product;
    }

    public static List<Product> generateProductList(int count) {
        List<Product> list = new ArrayList<>();
        for (int i = 0; i < count; i++) {
            list.add(generateProduct());
        }
        return list;
    }
}

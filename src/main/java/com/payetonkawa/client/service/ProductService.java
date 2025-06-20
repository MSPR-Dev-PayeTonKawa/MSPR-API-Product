package com.payetonkawa.product.service;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.payetonkawa.product.entity.Product;
import com.payetonkawa.product.repository.ProductRepository;

import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;

@Service
@Transactional
@AllArgsConstructor
public class ProductService {

    private final ProductRepository productRepository;

    public List<Product> findall() {
        return productRepository.findAll();
    }

    public Optional<Product> findById(Integer id) {
        return productRepository.findById(id);
    }

    public Product insert(Product product) throws IllegalStateException {
        if (product.getIdProduct() != null && productRepository.existsById(product.getIdProduct())) {
            throw new IllegalStateException("Entity already exists. Use update.");
        }
        // TODO actions sur le messages broker pour synchroniser les autres bdd
        return productRepository.save(product);
    }

    public Product update(Product product) throws IllegalStateException {
        if (product.getIdProduct() == null || !productRepository.existsById(product.getIdProduct())) {
            throw new IllegalStateException("Entity doesn't exist. Use insert.");
        }
        // TODO actions sur le messages broker pour synchroniser les autres bdd
        return productRepository.save(product);
    }

    public void delete(Integer id){
        // TODO actions sur le messages broker pour synchroniser les autres bdd
        productRepository.deleteById(id);
    }
}

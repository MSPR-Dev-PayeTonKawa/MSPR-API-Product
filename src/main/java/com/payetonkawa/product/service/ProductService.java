package com.payetonkawa.product.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import com.paketonkawa.resources.message.Action;
import com.paketonkawa.resources.message.MessageDTO;
import com.paketonkawa.resources.message.Table;
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

    private final MessagePublisher messagePublisher;

    public List<Product> findAll() {
        return productRepository.findAll();
    }

    public Optional<Product> findById(Integer id) {
        return productRepository.findById(id);
    }

    public void updateQuantityById(Integer id, Integer quantity){
        Optional<Product> optProduct = productRepository.findById(id);
        if(optProduct.isPresent()){
            Product product = optProduct.get();
            product.setStock(product.getStock() - quantity);
        }
    }

    public Product insert(Product product) throws IllegalStateException {
        if (product.getIdProduct() != null && productRepository.existsById(product.getIdProduct())) {
            throw new IllegalStateException("Entity already exists. Use update.");
        }
        return productRepository.save(product);
    }

    public Product update(Product product) throws IllegalStateException {
        if (product.getIdProduct() == null || !productRepository.existsById(product.getIdProduct())) {
            throw new IllegalStateException("Entity doesn't exist. Use insert.");
        }
        Map<String, Object> information = new HashMap<>();
        information.put("productId", product.getIdProduct());
        messagePublisher.sendMessage(new MessageDTO(Action.UPDATE, Table.PRODUCT, information), "order");
        return productRepository.save(product);
    }

    public void delete(Integer id){
        if (!productRepository.existsById(id)) {
            throw new IllegalStateException("Entity doesn't exist. Can't delete.");
        }
        Product product = productRepository.findById(id).get();
        Map<String, Object> information = new HashMap<>();
        information.put("productId", product.getIdProduct());
        messagePublisher.sendMessage(new MessageDTO(Action.DELETE, Table.PRODUCT, information), "order");
        productRepository.deleteById(id);
    }
}

package com.payetonkawa.product.service;

import com.paketonkawa.resources.message.Action;
import com.paketonkawa.resources.message.MessageDTO;
import com.paketonkawa.resources.message.Table;
import com.payetonkawa.product.entity.Product;
import com.payetonkawa.product.factory.ProductFactory;
import com.payetonkawa.product.repository.ProductRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class ProductServiceTest {

    private ProductRepository productRepository;
    private MessagePublisher messagePublisher;
    private ProductService productService;

    @BeforeEach
    void setUp() {
        productRepository = mock(ProductRepository.class);
        messagePublisher = mock(MessagePublisher.class);
        productService = new ProductService(productRepository, messagePublisher);
    }

    @Test
    void findAll_shouldReturnAllProducts() {
        List<Product> products = List.of(ProductFactory.generateProduct());
        when(productRepository.findAll()).thenReturn(products);

        List<Product> result = productService.findAll();

        assertEquals(1, result.size());
        verify(productRepository).findAll();
    }

    @Test
    void findById_shouldReturnProduct_whenExists() {
        Product product = ProductFactory.generateProduct();
        when(productRepository.findById(1)).thenReturn(Optional.of(product));

        Optional<Product> result = productService.findById(1);

        assertTrue(result.isPresent());
        verify(productRepository).findById(1);
    }

    @Test
    void updateQuantityById_shouldSubtractStock_whenProductExists() {
        Product product = ProductFactory.generateProduct();
        product.setStock(10);
        when(productRepository.findById(1)).thenReturn(Optional.of(product));

        productService.updateQuantityById(1, 3);

        assertEquals(7, product.getStock());
        verify(productRepository).findById(1);
    }

    @Test
    void insert_shouldSaveProduct_whenNew() {
        Product product = ProductFactory.generateProduct();
        product.setIdProduct(null);
        when(productRepository.save(product)).thenReturn(product);

        Product result = productService.insert(product);

        assertEquals(product, result);
        verify(productRepository).save(product);
    }

    @Test
    void insert_shouldThrow_whenProductExists() {
        Product product = ProductFactory.generateProduct();
        product.setIdProduct(1);
        when(productRepository.existsById(1)).thenReturn(true);

        assertThrows(IllegalStateException.class, () -> productService.insert(product));
    }

    @Test
    void update_shouldSaveProduct_andSendMessage_whenExists() {
        Product product = ProductFactory.generateProduct();
        product.setIdProduct(1);
        when(productRepository.existsById(1)).thenReturn(true);
        when(productRepository.save(product)).thenReturn(product);

        Product result = productService.update(product);

        assertEquals(product, result);
        verify(messagePublisher).sendMessage(any(MessageDTO.class), eq("order"));
        verify(productRepository).save(product);
    }

    @Test
    void update_shouldThrow_whenProductDoesNotExist() {
        Product product = ProductFactory.generateProduct();
        product.setIdProduct(99);
        when(productRepository.existsById(99)).thenReturn(false);

        assertThrows(IllegalStateException.class, () -> productService.update(product));
    }

    @Test
    void delete_shouldRemoveProductAndSendMessage_whenExists() {
        Product product = ProductFactory.generateProduct();
        product.setIdProduct(1);
        when(productRepository.existsById(1)).thenReturn(true);
        when(productRepository.findById(1)).thenReturn(Optional.of(product));

        productService.delete(1);

        verify(messagePublisher).sendMessage(any(MessageDTO.class), eq("order"));
        verify(productRepository).deleteById(1);
    }

    @Test
    void delete_shouldThrow_whenProductDoesNotExist() {
        when(productRepository.existsById(1)).thenReturn(false);

        assertThrows(IllegalStateException.class, () -> productService.delete(1));
    }
}

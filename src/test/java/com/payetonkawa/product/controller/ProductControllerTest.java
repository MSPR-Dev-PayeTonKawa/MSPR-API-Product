package com.payetonkawa.product.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.payetonkawa.product.factory.ProductFactory;
import com.payetonkawa.product.dto.PatchProductDto;
import com.payetonkawa.product.dto.PostProductDto;
import com.payetonkawa.product.entity.Product;
import com.payetonkawa.product.mapper.ProductMapper;
import com.payetonkawa.product.mapper.ProductMapperImpl;
import com.payetonkawa.product.service.ProductService;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(ProductController.class)
@Import(ProductControllerTest.MockConfig.class)
class ProductControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private ProductMapper productMapper;

    @Autowired
    private ProductService productService;

    @TestConfiguration
    static class MockConfig {
        @Bean
        public ProductService productService() {
            return Mockito.mock(ProductService.class);
        }

        @Bean
        public ProductMapper productMapper() {
            return new ProductMapperImpl();
        }
    }

    @AfterEach
    void resetMocks() {
        reset(productService);
    }

    @Test
    void findAll_success() throws Exception {
        List<Product> products = ProductFactory.generateProductList(2);
        when(productService.findAll()).thenReturn(products);
        mockMvc.perform(get("/product"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(2))
                .andExpect(jsonPath("$[0].idProduct").value(products.get(0).getIdProduct()))
                .andExpect(jsonPath("$[1].idProduct").value(products.get(1).getIdProduct()));
    }

    @Test
    void findAll_error() throws Exception {
        when(productService.findAll()).thenThrow(new RuntimeException());
        mockMvc.perform(get("/product"))
                .andExpect(status().isInternalServerError());
    }

    @Test
    void find_success() throws Exception {
        Product product = ProductFactory.generateProduct();
        when(productService.findById(1)).thenReturn(Optional.of(product));
        mockMvc.perform(get("/product/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.idProduct").value(product.getIdProduct()))
                .andExpect(jsonPath("$.libelle").value(product.getLibelle()))
                .andExpect(jsonPath("$.stock").value(product.getStock()))
                .andExpect(jsonPath("$.unitPrice").value(product.getUnitPrice()));
    }

    @Test
    void find_notFound() throws Exception {
        when(productService.findById(1)).thenReturn(Optional.empty());
        mockMvc.perform(get("/product/1"))
                .andExpect(status().isNotFound());
    }

    @Test
    void find_error() throws Exception {
        when(productService.findById(1)).thenThrow(new RuntimeException());
        mockMvc.perform(get("/product/1"))
                .andExpect(status().isInternalServerError());
    }

    @Test
    void create_success() throws Exception {
        Product product = ProductFactory.generateProduct();
        PostProductDto postProductDto = productMapper.toPostDto(product);
        when(productService.insert(any())).thenReturn(product);
        mockMvc.perform(post("/product")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(postProductDto)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.idProduct").value(product.getIdProduct()))
                .andExpect(jsonPath("$.libelle").value(product.getLibelle()))
                .andExpect(jsonPath("$.stock").value(product.getStock()))
                .andExpect(jsonPath("$.unitPrice").value(product.getUnitPrice()));
    }

    @Test
    void create_badRequest() throws Exception {
        Product product = ProductFactory.generateProduct();
        PostProductDto postProductDto = productMapper.toPostDto(product);
        when(productService.insert(any())).thenThrow(new IllegalStateException());
        mockMvc.perform(post("/product")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(postProductDto)))
                .andExpect(status().isBadRequest());
    }

    @Test
    void create_error() throws Exception {
        Product product = ProductFactory.generateProduct();
        PostProductDto postProductDto = productMapper.toPostDto(product);
        when(productService.insert(any())).thenThrow(new RuntimeException());
        mockMvc.perform(post("/product")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(postProductDto)))
                .andExpect(status().isInternalServerError());
    }

    @Test
    void update_success() throws Exception {
        Product product = ProductFactory.generateProduct();
        PatchProductDto patchProductDto = productMapper.toPatchDto(product);
        when(productService.update(any())).thenReturn(product);
        mockMvc.perform(put("/product")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(patchProductDto)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.idProduct").value(product.getIdProduct()))
                .andExpect(jsonPath("$.libelle").value(product.getLibelle()))
                .andExpect(jsonPath("$.stock").value(product.getStock()))
                .andExpect(jsonPath("$.unitPrice").value(product.getUnitPrice()));
    }

    @Test
    void update_badRequest() throws Exception {
        Product product = ProductFactory.generateProduct();
        PatchProductDto patchProductDto = productMapper.toPatchDto(product);
        when(productService.update(any())).thenThrow(new IllegalStateException());
        mockMvc.perform(put("/product")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(patchProductDto)))
                .andExpect(status().isBadRequest());
    }

    @Test
    void update_error() throws Exception {
        Product product = ProductFactory.generateProduct();
        PatchProductDto patchProductDto = productMapper.toPatchDto(product);
        when(productService.update(any())).thenThrow(new RuntimeException());
        mockMvc.perform(put("/product")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(patchProductDto)))
                .andExpect(status().isInternalServerError());
    }

    @Test
    void delete_success() throws Exception {
        doNothing().when(productService).delete(1);
        mockMvc.perform(delete("/product/1"))
                .andExpect(status().isOk());
    }

    @Test
    void delete_badRequest() throws Exception {
        doThrow(new IllegalStateException()).when(productService).delete(1);
        mockMvc.perform(delete("/product/1"))
                .andExpect(status().isNotFound());
    }

    @Test
    void delete_error() throws Exception {
        doThrow(new RuntimeException()).when(productService).delete(1);
        mockMvc.perform(delete("/product/1"))
                .andExpect(status().isInternalServerError());
    }
}